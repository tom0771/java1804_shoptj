package com.qf.shop_kill.controller;

import com.qf.entity.Kill;
import com.qf.shop_kill.service.IKillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/kill")
@Controller
public class KillController {

    @Autowired
    private IKillService killService;

    //查询秒杀的商品信息
    @RequestMapping("/queryinfo")
    public String querykillGoods(Integer gid, Model model){
        Kill kill = killService.queryKillInfo(gid);
        System.out.println("秒杀的商品信息为："+kill);
        model.addAttribute("kill",kill);

        return "killgoods";
    }

    @RequestMapping("/miaosha")
    @ResponseBody
   public  String miaoshaGoods(Integer gid,Integer number){
        System.out.println("拿到的信息："+gid+"----"+number);

        int i = killService.killGoods(gid, number, 1);
        if(i>0){
            return "ok";
        }

        return "失败";
   }

}
