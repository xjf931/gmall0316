package com.atguigu.gmall.list.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "service-list")
public interface ListFeignClient {

    @RequestMapping("api/list/cancelSale/{skuId}")
    void cancelSale(@PathVariable("skuId") String skuId);

    @RequestMapping("api/list/onSale/{skuId}")
    void onSale(@PathVariable("skuId") String skuId);
}