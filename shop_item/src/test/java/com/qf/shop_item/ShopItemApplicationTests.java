package com.qf.shop_item;

import com.qf.entity.Goods;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileWriter;
import java.io.Writer;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopItemApplicationTests {

	@Autowired
	private Configuration configuration;


	@Test
	public void contextLoads() throws Exception {
		//1、加载指定的模板
		Template template = configuration.getTemplate("hello.ftl");
		//2.准备数据
		Map<String,Object> map = new HashMap<>();
		map.put("key","hello.freemarker");
		List<Goods> goodslist = new ArrayList<>();
		for (int i =1;i<6;i++) {
			Goods goods = new Goods();
			goods.setId(i);
			goods.setTitle("电视机"+i);
			goods.setGinfo("新品上市"+i+"号");
			goods.setPrice((double) (1000+(100*i)));
			goodslist.add(goods);
		}
		map.put("goods",goodslist);

		Date date = new Date();
		map.put("time",date);

		//3.生成静态页
		Writer writer = new FileWriter("C:\\Users\\admin\\Desktop\\helloFreemarker.ftl");
		template.process(map,writer);
		writer.close();





	}

}
