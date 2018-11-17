package com.qf.shop_kill.dao;

import com.qf.entity.Kill;
import com.qf.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IKillDao {


    //根据id查询秒杀表格
    Kill queryKillById(Integer id);

    //秒杀成功修改库存
    int updatesave(@Param("id") Integer id,@Param("number") Integer number );

    //秒杀成功添加订单
    int addOrder(Orders orders);

    //批量添加订单
    int addOrders(@Param("orders")List<Orders> orders);

    //根据用户抢购成功减库存
    int updateSaveNumber(@Param("id") Integer id,@Param("nuber") Integer number);

}
