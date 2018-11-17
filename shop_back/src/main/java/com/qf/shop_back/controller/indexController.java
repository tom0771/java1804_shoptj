package com.qf.shop_back.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class indexController {

    @RequestMapping("/topage/{addGoods}")
    public String addGoods(@PathVariable("addGoods") String addGoods){

        return addGoods;
    }


}
