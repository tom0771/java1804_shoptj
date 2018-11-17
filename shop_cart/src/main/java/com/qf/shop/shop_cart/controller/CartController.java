package com.qf.shop.shop_cart.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qf.Utils.Constant;
import com.qf.Utils.IsLogin;
import com.qf.entity.Cart;
import com.qf.entity.User;
import com.qf.service.ICartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/cart")
@Controller
public class CartController {

    @Reference
    private ICartService cartService;


    @IsLogin
    @RequestMapping("/addcart")
    public  String addCart(Cart cart, User user
            , HttpServletResponse response
            , @CookieValue(value = "cart_token",required = false) String carts){
        System.out.println("是否添加到购物车="+cart);
        System.out.println("是否登陆="+user);

        //判断是否登陆
        if(user!=null){
            //登陆状态存放在数据库
            cart.setUid(user.getId());
            //查询该用户是否已添加该商品到购物车
            Cart  existcart=  cartService.queryIsGoods(cart);
            if(existcart!=null){//有就累加数量，没有就添加
                cartService.updataGoodsnums(existcart.getId(),(existcart.getGoodsnums()+cart.getGoodsnums()));
            }else{
                cartService.addCart(cart);
            }


        }else {
            //非登陆状态存放在Cookie里面
            List<Cart> cartList = null;

            if(carts !=null){ //cookie中有购物车信息
                //将cookie中的购物车集合提取出来，再将新的购物车商品信息添加进去
                TypeToken<List<Cart>> token = new TypeToken<List<Cart>>(){};
                cartList= new Gson().fromJson(carts, token.getType());
                for(int i = 0;i<cartList.size();i++){//查询cookie中是否存在该商品
                    if(cartList.get(i).getGsid()==cart.getGsid()){
                        //有就累加数量，没有就添加
                        cartList.get(i).setGoodsnums(cartList.get(i).getGoodsnums()+
                                cart.getGoodsnums());
                        break;
                    }
                    if(i==cartList.size()-1){
                        cartList.add(cart);
                    }
                }




            }else {//cookie中没有购物信息
                //直接添加一个购物车商品信息添加到购物车集合中
            cartList = new ArrayList<>();
            cartList.add(cart);

            }
            System.out.println("购物车信息："+cartList);
            //将购物车商品的集合全部放到cookie中
            String s = new Gson().toJson(cartList);
            try {
                s = URLEncoder.encode(s, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            Cookie cookie = new Cookie(Constant.CART_TOKEN,s);
            cookie.setMaxAge(7*24*60*60);
            cookie.setPath("/");
            response.addCookie(cookie);

        }


        return "addCartOk";
    }

    //登陆时将Cookie中的购物车合并到用户的购物车
    @RequestMapping("/merge")
    @ResponseBody
    public  String mergeCart(Integer uid,@CookieValue(value = "cart_token",required = false) String carts) {

        TypeToken<List<Cart>> token = new TypeToken<List<Cart>>(){};
        List<Cart> cartList = new Gson().fromJson(carts,token.getType());

        for (Cart cart : cartList) {
            cart.setUid(uid);

            //查询该用户是否已添加该商品到购物车
            Cart  existcart=  cartService.queryIsGoods(cart);
            if(existcart!=null){//有就累加数量，没有就添加
                cartService.updataGoodsnums(existcart.getId(),(existcart.getGoodsnums()+cart.getGoodsnums()));
            }else{
                cartService.addCart(cart);
            }
        }
        return "ok";
    }

    //查询购物车信息展示在前端
    @IsLogin
    @RequestMapping("/getCarts")
    @ResponseBody
    public  String getCart(@CookieValue(value = "cart_token",required = false)String carts,User user){
           List<Cart>  cartlist  =cartService.getCarts(user, carts);
        return "getCart("+new Gson().toJson(cartlist)+")";
    }

    @IsLogin
    @RequestMapping("/getCartGoods")
    public  String getCart(@CookieValue(value = "cart_token",required = false)String carts
            , User user, Model model){
        List<Cart>  cartlist  =cartService.getCarts(user, carts);
        model.addAttribute("carts",cartlist);

        return "goodsCart";
    }


}
