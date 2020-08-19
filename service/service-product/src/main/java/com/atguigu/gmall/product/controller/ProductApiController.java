package com.atguigu.gmall.product.controller;


import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.product.BaseCategory1;
import com.atguigu.gmall.model.product.BaseCategory2;
import com.atguigu.gmall.product.service.BaseCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("admin/product")
@CrossOrigin
public class ProductApiController {

    @Autowired
    BaseCategoryService baseCategoryService;

    @RequestMapping("testProductApi")
    public String testProductApi(){
        return "testProductApi";
    }

    @RequestMapping("getCategory1")
    public Result getCategory1(){

        List<BaseCategory1> baseCategory1s =  baseCategoryService.getCategory1();

        return Result.ok(baseCategory1s);
    }

    @RequestMapping("getCategory2/{category1Id}")
    public Result getCategory2(@PathVariable("category1Id") String category1Id){

        List<BaseCategory2> baseCategory2s =  baseCategoryService.getCategory2(category1Id);

        return Result.ok(baseCategory2s);
    }


}
