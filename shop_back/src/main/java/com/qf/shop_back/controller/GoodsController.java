package com.qf.shop_back.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.google.gson.Gson;
import com.qf.Utils.HttpCilpetUtil;
import com.qf.entity.Goods;
import com.qf.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    @Value("${image.path}")
    private  String imgpath;

    @Reference
    private IGoodsService goodsService;

    @RequestMapping("/goodslist")
    public String getGoodslist(Model model){

        List<Goods> goodslist = goodsService.getAllGoods();
        model.addAttribute("goods",goodslist);
        model.addAttribute("imgpath",imgpath);
        //将查到的数据库刷新到索引库中
        //HttpCilpetUtil.sendJonsPost("http://localhost:8083/solr/getAllGoods",new Gson().toJson(goodslist));
        return "goodslist";
    }

    @RequestMapping("/addGoods")
    public String addGoods(@RequestParam("file") MultipartFile file, Goods goods) throws IOException {
        // 进行文件的上传 - fastdfs - 获得fastdfs回写的url
        StorePath storePath = fastFileStorageClient.uploadImageAndCrtThumbImage
                (file.getInputStream(),file.getSize(),"JPG",null);
        String fullPath = storePath.getFullPath();
        //将url放入goods对象中
        goods.setGimage(fullPath);
        //调用serivce层保存进数据库
        int id = goodsService.addGoods(goods);
        goods.setId(id);


        //调用索引工程同步索引库 - URL - HttpClient
        //传参：对象json
        HttpCilpetUtil.sendJonsPost("http://localhost:8083/solr/addsolr",new Gson().toJson(goods));
        HttpCilpetUtil.sendJonsPost("http://localhost:8888/item/staticpage",new Gson().toJson(goods));

        return "redirect:/goods/goodslist";
    }


    @RequestMapping("getnewGoods")
    @ResponseBody
    @CrossOrigin
    public List<Goods> getnewGoods(){

        List<Goods> goodsall =  goodsService.getnewGoods();
        return goodsall;
    }



}
