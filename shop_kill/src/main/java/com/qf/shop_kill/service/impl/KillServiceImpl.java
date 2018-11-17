package com.qf.shop_kill.service.impl;

import com.qf.entity.Kill;
import com.qf.shop_kill.dao.IKillDao;
import com.qf.shop_kill.service.IKillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KillServiceImpl implements IKillService {

    @Autowired
    private IKillDao killDao;

    @Override
    public Kill queryKillInfo(Integer gid) {
        return killDao.queryKillById(gid);
    }

    @Override
    public int killGoods(Integer gid, Integer number, Integer uid) {
        Kill kill = killDao.queryKillById(gid);
        if(kill.getSave()>number){
            killDao.updatesave(gid, number);
            return 1;
        }
        return 0;
    }
}
