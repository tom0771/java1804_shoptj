package com.qf.shop.shop_service_impl;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//指向具体的实现类
@DubboComponentScan("com.qf.shop.shop_service_impl.serviceimpl")
//指向dao层
@MapperScan("com.qf.dao")
public class ShopServiceImplApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopServiceImplApplication.class, args);
	}
}
