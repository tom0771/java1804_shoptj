package com.qf.shop_order.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.Utils.IsLogin;
import com.qf.entity.Address;
import com.qf.entity.Cart;
import com.qf.entity.Orders;
import com.qf.entity.User;
import com.qf.service.IAddressService;
import com.qf.service.ICartService;
import com.qf.service.IOrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/order")
public class orderController {

    @Reference
    private ICartService cartService;

    @Reference
    private IAddressService addressService;

    @Reference
    private IOrderService orderService;


    //生成订单页面
    @IsLogin(tologin = true)
    @RequestMapping("/orderedit")
    public String orderedit(int[] gsid, User user, Model model){

        List<Cart> cartList = cartService.getCartBygsidAnduid(gsid,user.getId());
        System.out.println("选中的购物车商品信息："+cartList);

        List<Address> addresses = addressService.queryAddressByUid(user.getId());


        model.addAttribute("carts",cartList);
        model.addAttribute("addresses",addresses);

        return "orderedit";
    }

    //添加地址
    @IsLogin
    @RequestMapping("/addAddress")
    @ResponseBody
    public Address addAddress(Address address,User user){
        System.out.println("打印地址1："+address);
            address.setUid(user.getId());
            address = addressService.adddizhi(address);
        System.out.println("打印地址："+address);
        return address;
    }

    //提交订单
    @IsLogin
    @RequestMapping("/addorder")
    @ResponseBody
    public String addorder(Integer aid,Integer[] cid,User user ){
            System.out.println("地址id："+aid);
            System.out.println("购物车id:"+ Arrays.toString(cid));
        String orderid = null;

        try {
            orderid=orderService.addOrderAndOrderDetils(cid,aid,user.getId());
        }catch (Exception e){
            e.printStackTrace();
        }

        System.out.println(orderid);
        return orderid;
    }

    @IsLogin
    @RequestMapping("/orderlist")
    public String qureyOrder(User user, Model model){
        List<Orders> ordersList = orderService.queryByUid(user.getId());
        model.addAttribute("orders",ordersList);
        return "orderlist";
    }

}
