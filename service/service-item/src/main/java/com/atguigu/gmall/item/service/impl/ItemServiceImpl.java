package com.atguigu.gmall.item.service.impl;

import com.atguigu.gmall.item.service.ItemService;
import com.atguigu.gmall.model.product.BaseCategoryView;
import com.atguigu.gmall.model.product.SkuInfo;
import com.atguigu.gmall.model.product.SpuSaleAttr;
import com.atguigu.gmall.product.client.ProductFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    ProductFeignClient productFeignClient;

    @Override
    public Map<String, Object> getItem(String skuId) {

//        商品详情汇总封装基础数据
        Map<String, Object> map = new HashMap<>();
        BigDecimal price = new BigDecimal("0");
        price = productFeignClient.getSkuPrice(skuId);
        map.put("price",price);

        SkuInfo skuInfo = productFeignClient.getSkuInfo(skuId);
//        price = productFeignClient.getSkuPrice(skuId);
        map.put("skuInfo",skuInfo);


//        查询分类列表
        BaseCategoryView baseCategoryView = productFeignClient.getCategoryView("61");
        map.put("categoryView",baseCategoryView);

//        查询销售属性
        List<SpuSaleAttr> spuSaleAttrs = productFeignClient.getMySpuSaleAttrs(skuInfo.getSpuId());
        map.put("spuSaleAttrList",spuSaleAttrs);


        return map;
    }
}
