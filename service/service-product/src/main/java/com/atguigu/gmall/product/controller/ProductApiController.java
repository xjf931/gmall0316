package com.atguigu.gmall.product.controller;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("admin/product")
@CrossOrigin
public class ProductApiController {


  @RequestMapping("testProductApi")
  public String testProductApi(){
    return "testProductApi";
  }


}