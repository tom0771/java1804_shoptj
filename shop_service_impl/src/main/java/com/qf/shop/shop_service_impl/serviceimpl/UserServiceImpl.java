package com.qf.shop.shop_service_impl.serviceimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.dao.IUserDao;
import com.qf.entity.ResultData;
import com.qf.entity.User;
import com.qf.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserDao userDao;

    @Override
    public ResultData<User> loginUser(String username, String password) {
        User user = userDao.loginGetUser(username);
        Integer flag;
        String msg;
        if(user!=null&&!"".equals(user)){
            //用户存在
            if(user.getPassword().equals(password)){
                //用户名密码正确
                flag=1;
                msg="登陆成功";

            }else{
                //密码错误
                flag=2;
                msg="密码错误";
                user=null;
            }

        }else {
            //用户名不存在
            flag=3;
            msg="用户名不存在";

        }
        ResultData<User>  result = new ResultData<>(flag,msg,user);



        return result;
    }
}
