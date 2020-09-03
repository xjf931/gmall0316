package com.atguigu.gmall.list.service;

public interface ListService {
    void cancelSale(String skuId);

    void onSale(String skuId);

    void hotScore(String skuId);
}
