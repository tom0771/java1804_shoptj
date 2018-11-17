package com.qf.shop_item.controller;

import com.qf.entity.Goods;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private Configuration configuration;


    @RequestMapping("/staticpage")
    public String staticpage(@RequestBody Goods goods, HttpServletRequest request){

        Writer fileout=null;
        try {
            Template template = configuration.getTemplate("tiem.ftl");
            Map<String,Object> map = new HashMap<>();
            map.put("goods",goods);
            map.put("context", request.getContextPath());

            System.out.println(request.getContextPath());
            String path  =this.getClass().getResource("/").getPath()+"static/page/"+goods.getId()+ ".html";

             fileout = new FileWriter(path);

                template.process(map,fileout);



        }catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fileout!=null){
                try {
                    fileout.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        return null;
    }




}
