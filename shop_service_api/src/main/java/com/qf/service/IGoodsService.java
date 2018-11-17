package com.qf.service;

import com.qf.entity.Goods;

import java.util.List;

public interface IGoodsService {

    List<Goods>  getAllGoods();


    int addGoods(Goods goods);

    List<Goods> getnewGoods();

    Goods queryGoodsById(Integer gsid);
}
