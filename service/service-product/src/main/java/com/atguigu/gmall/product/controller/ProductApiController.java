package com.atguigu.gmall.product.controller;


import com.atguigu.gmall.model.product.BaseCategoryView;
import com.atguigu.gmall.model.product.SkuInfo;
import com.atguigu.gmall.model.product.SpuSaleAttr;
import com.atguigu.gmall.product.service.BaseCategoryService;
import com.atguigu.gmall.product.service.SkuInfoService;
import com.atguigu.gmall.product.service.SpuInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("api/product")
@CrossOrigin
public class ProductApiController {

  @Autowired
  BaseCategoryService baseCategoryService;

  @Autowired
  SkuInfoService skuInfoService;

  @Autowired
  SpuInfoService spuInfoService;


  @RequestMapping("testProductApi")
  public String testProductApi(){
    return "testProductApi";
  }

  @RequestMapping("auth/testProductApi2")
  public String testProductApi2(){
    return "testProductApi2";
  }

//  @RequestMapping("inner/getCategoryView/{category3Id}")
//  BaseCategoryView getCategoryView(@PathVariable String category3Id){
//    return null;


  @RequestMapping("inner/getCategoryView/{category3Id}")
  BaseCategoryView getCategoryView(@PathVariable("category3Id") String category3Id){
//    BaseCategoryView getCategoryView(@PathVariable("category3Id") String category3Id){


    BaseCategoryView baseCategoryView = baseCategoryService.getCategoryView(category3Id);
//      return null;
    return baseCategoryView;
  }

  @RequestMapping("inner/getSkuInfo/{skuId}")
  SkuInfo getSkuInfo(@PathVariable("skuId")  String skuId){
    SkuInfo skuInfo = skuInfoService.getSkuInfo(skuId);
    return skuInfo;
  }
  @RequestMapping("inner/getSkuPrice/{skuId}")
  BigDecimal getSkuPrice(@PathVariable("skuId") String skuId){
    BigDecimal price = skuInfoService.getSkuPrice(skuId);
    return price;
  };
  @RequestMapping("inner/getMySpuSaleAttrs/{spuId}/{skuId}")
  List<SpuSaleAttr> getMySpuSaleAttrs(@PathVariable("spuId") Long spuId,@PathVariable("skuId") Long skuId){
    List<SpuSaleAttr> spuSaleAttrs = spuInfoService.getSpuSaleAttrListCheckBySku(spuId,skuId);//getMySpuSaleAttrs(spuId);
    return spuSaleAttrs;
  };
  @RequestMapping("inner/getSkuValueIdsMap/{spuId}")
  Map<String,String> getSkuValueIdsMap(@PathVariable("spuId") Long spuId){
    Map<String,String> valueIdsMap = skuInfoService.getSkuValueIdsMap(spuId);

    return valueIdsMap;
  }



}