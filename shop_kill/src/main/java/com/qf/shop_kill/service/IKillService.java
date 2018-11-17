package com.qf.shop_kill.service;

import com.qf.entity.Kill;

public interface IKillService {


    /**
     * 根据商品id查询商品的信息
     * @param gid
     * @return
     */
    Kill queryKillInfo(Integer gid);

    /**
     * 根据商品的id进行秒杀
     * @param gid
     * @param number
     * @param uid
     * @return
     */
    int killGoods(Integer gid,Integer number,Integer uid);
}
