package com.qf.Utils;

import com.qf.entity.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Aspect
public class LoginAop {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private RedisTemplate redisTemplate;

    //使用环绕增强判断是否登陆
    //指定以Controller为后缀的类，并添加@IsLogin注解的
    @Around("execution(* *..*Controller.*(..)) && @annotation(com.qf.Utils.IsLogin)")
    public Object isLogin(ProceedingJoinPoint proceedingJoinPoint){

        //获得自定义注解IsLogin的判断
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = methodSignature.getMethod();
        IsLogin isLogin = method.getAnnotation(IsLogin.class);


        String token = null;
        User user =null;


        //判断是否登陆
        Cookie[] cookies = request.getCookies();
        if(cookies!=null) {
            for (Cookie cookie : cookies) {
                //从cookie拿到token的值
                if (cookie.getName().equals(Constant.LOGIN_TOKEN)) {
                    token = cookie.getValue();
                    break;
                }

            }
        }

        //通过token拿到User对象
        if(token!=null&& !token.equals("")){
            user = (User) redisTemplate.opsForValue().get(token);

        }

        //判断是否需要强制跳到登录页
        if(user==null&& isLogin.tologin()){
            String backUrl = request.getRequestURL() + "?" + request.getQueryString();
            backUrl = backUrl.replace("&","~");
            return "redirect:http://localhost:8084/sso/tologin?backUrl="+backUrl;

        }

        //如果已经登陆，获得目标的实参列表
        Object[] args = proceedingJoinPoint.getArgs();
        for(int i = 0 ;i<args.length;i++){
            if(args[i]!=null && args[i].getClass()==User.class){
                args[i] = user;
            }
        }

        Object o =null;

        try {
            o= proceedingJoinPoint.proceed(args);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }


        return o;
    }
















}
