package com.qf.dao;

import com.qf.entity.OrderDetils;
import com.qf.entity.Orders;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IOrderDao {

    int addOrder(Orders orders);

    int addOrderDetils(@Param("orderdetils")List<OrderDetils> orderdetils);

    List<Orders> queryByUid(Integer uid);


    Orders queryByOrderid(String orderid);

    int updateStatusByOrderid(@Param("orderid") String orderid,@Param("status") int status);
}
