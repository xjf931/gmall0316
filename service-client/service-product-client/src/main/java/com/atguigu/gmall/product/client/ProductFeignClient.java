package com.atguigu.gmall.product.client;


import com.atguigu.gmall.model.product.BaseCategoryView;
import com.atguigu.gmall.model.product.SkuInfo;
import com.atguigu.gmall.model.product.SpuSaleAttr;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@FeignClient(value = "service-product")
public interface ProductFeignClient {

    @RequestMapping("api/product/inner/getCategoryView/{category3Id}")
    BaseCategoryView getCategoryView(@PathVariable("category3Id") String category3Id);

    @RequestMapping("api/product/inner/getSkuInfo/{skuId}")
    SkuInfo getSkuInfo(@PathVariable("skuId")  String skuId);

    @RequestMapping("api/product/inner/getSkuPrice/{skuId}")
    BigDecimal getSkuPrice(@PathVariable("skuId") String skuId);

    @RequestMapping("api/product/inner/getMySpuSaleAttrs/{spuId}/{skuId}")
    List<SpuSaleAttr> getMySpuSaleAttrs(@PathVariable("spuId") Long spuId,@PathVariable("skuId") Long skuId);

    @RequestMapping("api/product/inner/getSkuValueIdsMap/{spuId}")
    Map<String,String> getSkuValueIdsMap(@PathVariable("spuId") Long spuId);
}
