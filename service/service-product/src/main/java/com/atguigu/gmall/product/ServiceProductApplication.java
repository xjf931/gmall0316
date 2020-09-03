package com.atguigu.gmall.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({"com.atguigu.gmall"})
@SpringBootApplication
@EnableFeignClients(basePackages= {"com.atguigu.gmall"})
public class ServiceProductApplication {

  public static void main(String[] args) {
    SpringApplication.run(ServiceProductApplication.class, args);
  }

}