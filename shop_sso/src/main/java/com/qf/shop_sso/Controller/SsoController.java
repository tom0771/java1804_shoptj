package com.qf.shop_sso.Controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.gson.Gson;
import com.qf.Utils.Constant;
import com.qf.Utils.HttpCilpetUtil;
import com.qf.entity.ResultData;
import com.qf.entity.User;
import com.qf.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/sso")
public class SsoController {

    @Autowired
    private RedisTemplate redisTemplate;


    @Reference
    private IUserService userService;

    @RequestMapping("/tologin")
    public String goLogin(String backUrl, Model model){
        model.addAttribute("backUrl",backUrl);
        return "login";
    }
///usr/local/software/redis-3.2.4/src/redis-server redis.conf
    //登陆时拿到用户信息判断后，为1用户则成功登陆，登陆后设置一个UUID作为KEY值存放在redis里面
    //并将该key值存储在Cookie中
    @RequestMapping("/login")
    public String login(String username, String password, Model model,
                        HttpServletResponse response,String backUrl,@CookieValue(value = "cart_token", required = false) String carts){
        ResultData<User> loginResult = userService.loginUser(username, password);
        switch (loginResult.getFlag()){
            case 1:
                if(backUrl == null|| backUrl.equals("")){
                    backUrl="http://localhost:8082";
                }else {
                    backUrl = backUrl.replace("~","&");
                }

                //拿到UUID
                String token = UUID.randomUUID().toString();
                //调用redis将token作为key，将user放入reids中
                redisTemplate.opsForValue().set(token,loginResult.getData());
                //设置时间
                redisTemplate.expire(token,3, TimeUnit.DAYS);
                //设置Cookie并写到浏览器中
                Cookie cookie = new Cookie(Constant.LOGIN_TOKEN,token);
                cookie.setMaxAge(3*24*60*60);
                //跨域登陆需要添加"/"
                cookie.setPath("/");
                //将cookie添加到浏览器中
                response.addCookie(cookie);

                //登陆成功后合并购物车
                if(carts !=null){  //cookie中有购物车
                    //设置参数
                    Map<String,String> params = new HashMap<>();
                    params.put("uid",loginResult.getData().getId()+"");
                    Map<String,String> header = new HashMap<>();
                    try {
                        header.put("Cookie","cart_token="+ URLEncoder.encode(carts,"utf-8"));

                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    String result = HttpCilpetUtil.sendPostParamAndHeader("http://localhost:8085/cart/merge", params, header);
                    //如果合并成功
                    if(result.equals("ok")){
                        //清空Cookie中的购物车信息
                        Cookie cartCookie = new Cookie(Constant.CART_TOKEN,null);
                        cartCookie.setMaxAge(0);
                        cartCookie.setPath("/");
                        response.addCookie(cartCookie);


                    }

                }

                //登陆成功后返回到前台的页面
                return "redirect:"+backUrl;
            default:
                //拿到登陆失败的原因
                model.addAttribute("data",loginResult);
                //跳转到登陆页面
                return "login";
        }


    }
    @RequestMapping("/islogin")
    @ResponseBody
    public String isLogin(@CookieValue(value = "login_token",required = false) String login_token){
        //通过login_token拿到User对象
        User user=null;
        //如果token不为空则存在User对象，将User通过Gson方式返回给前台页面
        if (login_token!=null){
            user = (User) redisTemplate.opsForValue().get(login_token);
        }
        return  "callback("+ new Gson().toJson(user)+")";
    }
    //注销
    @RequestMapping("/logout")
    public String logOut(@CookieValue(value = "login_token",required = false) String login_token
                        ,HttpServletResponse response){
        //删除redis
        redisTemplate.delete(login_token);
        //删除Cookie，需要设置好存在时间与跨域的路径，添加一个瞬时为空的Cookie覆盖删除以前的Cookie;
        Cookie cookie = new Cookie(Constant.LOGIN_TOKEN,null);

        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);

        return "login";
    }

}
