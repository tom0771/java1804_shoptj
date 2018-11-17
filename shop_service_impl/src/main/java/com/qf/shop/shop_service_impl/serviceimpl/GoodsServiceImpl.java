package com.qf.shop.shop_service_impl.serviceimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.dao.IGoodsDao;
import com.qf.entity.Goods;
import com.qf.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class GoodsServiceImpl implements IGoodsService {

    @Autowired
    private IGoodsDao goodsDao;


    @Override
    public List<Goods> getAllGoods() {
        List<Goods> goodslist = goodsDao.getAllGoods();

        return goodslist;
    }

    @Override
    public int addGoods(Goods goods) {
        goodsDao.addGoods(goods);

        return goods.getId();
    }

    @Override
    public List<Goods> getnewGoods() {

        return goodsDao.getnewGoods();
    }

    @Override
    public Goods queryGoodsById(Integer gsid) {


        return goodsDao.queryGoodsById(gsid);
    }
}
