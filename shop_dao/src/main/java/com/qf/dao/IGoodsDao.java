package com.qf.dao;

import com.qf.entity.Goods;

import java.util.List;

public interface IGoodsDao {

    List<Goods> getAllGoods();


    int addGoods(Goods goods);

    List<Goods> getnewGoods();

    Goods queryGoodsById(Integer gsid);
}
