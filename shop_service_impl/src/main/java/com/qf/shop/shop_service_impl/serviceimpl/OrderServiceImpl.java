package com.qf.shop.shop_service_impl.serviceimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.dao.IAddressDao;
import com.qf.dao.ICartDao;
import com.qf.dao.IOrderDao;
import com.qf.entity.Address;
import com.qf.entity.Cart;
import com.qf.entity.OrderDetils;
import com.qf.entity.Orders;
import com.qf.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class OrderServiceImpl implements IOrderService {
   @Autowired
   private IOrderDao orderDao;

   @Autowired
   private ICartDao cartDao;

   @Autowired
   private IAddressDao addressDao;


    @Override
    @Transactional
    public String addOrderAndOrderDetils(Integer[] cids, Integer aid, Integer uid) {
        System.out.println("测试购物车id数组:"+ Arrays.toString(cids));
        //1、根据购物车的id查询购物车的列表
        List<Cart> cartList = cartDao.queryByCids(cids);

        //2、根据地址的id获得收货地址的详细信息
        Address address = addressDao.queryById(aid);

        //计算订单总价格
        double allprice =0;

        for (Cart cart : cartList) {
            allprice+=cart.getGoods().getPrice()*cart.getGoodsnums();
        }

        //3、根据购物车列表生成订单和订单详情对象

        //设置订单数据
        Orders orders = new Orders();
        orders.setStatus(0);//0 - 未支付 1 - 已支付/待发货  2 - 已发货/待收货  3 - 已收货/待评价 4 - 评价/完成
        orders.setOrderid(UUID.randomUUID().toString());
        orders.setUid(uid);
        orders.setPerson(address.getPerson());
        orders.setAddress(address.getAddress());
        orders.setPhone(address.getPhone());
        orders.setCode(address.getCode());
        orders.setOprice(allprice);
        orders.setOrdertime(new Date());

        //添加订单，主键回填
        orderDao.addOrder(orders);

        //设置订单详情数据
        List<OrderDetils> odslist = new ArrayList<>();

        for (Cart cart : cartList) {
            OrderDetils ods = new OrderDetils();
            ods.setOid(orders.getId());
            ods.setGid(cart.getGsid());
            ods.setGname(cart.getGoods().getTitle());
            ods.setGinfo(cart.getGoods().getGinfo());
            ods.setPrice(cart.getGoods().getPrice());
            ods.setGcount(cart.getGoodsnums());
            ods.setGimage(cart.getGoods().getGimage());
            odslist.add(ods);
        }

        //添加订单详情集合
        orderDao.addOrderDetils(odslist);

        //删除购物车
        for (Cart cart : cartList) {
            cartDao.deleteByid(cart.getId());
        }

        return orders.getOrderid();
    }

    @Override
    public List<Orders> queryByUid(Integer uid) {
            return orderDao.queryByUid(uid);
        }

    @Override
    public Orders queryByOrderid(String orderid) {



        return orderDao.queryByOrderid(orderid);
    }

        @Override
        public int updateStatusByOrderid(String orderid, int status) {

        return orderDao.updateStatusByOrderid(orderid,status);
    }


}
