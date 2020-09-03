package com.atguigu.gmall.list.controller;


import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.list.service.ListService;
import com.atguigu.gmall.model.list.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/list/")
public class ListApiController {

    @Autowired
    ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Autowired
    ListService listService;

    @RequestMapping("hotScore/{skuId}")
    void hotScore(@PathVariable("skuId") String skuId){

        listService.hotScore(skuId);
    }

    @RequestMapping("createIndex")
    public Result createIndex(){

//        调用es的api导入数据结构
        elasticsearchRestTemplate.createIndex(Goods.class);
        elasticsearchRestTemplate.putMapping(Goods.class);
        return Result.ok();
    }
    @RequestMapping("cancelSale/{skuId}")
    void cancelSale(@PathVariable("skuId") String skuId){
        listService.cancelSale(skuId);
    }

    @RequestMapping("onSale/{skuId}")
    void onSale(@PathVariable("skuId") String skuId){
        listService.onSale(skuId);
    }

}

