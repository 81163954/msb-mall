package com.msb.mall.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@EnableFeignClients(basePackages = "com.msb.mall.product.fegin")
//放开注册中心，让这个微服务可以去注册
@EnableDiscoveryClient

@SpringBootApplication
@MapperScan("com.msb.mall.product.dao")
@ComponentScan("com.msb.mall")
public class MallProductApplication {

	public static void main(String[] args) {
		SpringApplication.run(MallProductApplication.class, args);

	}

}
