package com.atguigu.gmall.list.service.impl;

import com.atguigu.gmall.list.repository.GoodsElasticsearchRepository;
import com.atguigu.gmall.list.service.ListService;
import com.atguigu.gmall.model.list.Goods;
import com.atguigu.gmall.model.list.SearchAttr;
import com.atguigu.gmall.model.product.*;
import com.atguigu.gmall.product.client.ProductFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ListServiceImpl implements ListService {


    @Autowired
    GoodsElasticsearchRepository goodsElasticsearchRepository;

    @Autowired
    ProductFeignClient productFeignClient;

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public void cancelSale(String skuId) {

        Goods goods = new Goods();
        goods.setId(Long.parseLong(skuId));
        goodsElasticsearchRepository.delete(goods);
    }

    @Override
    public void onSale(String skuId) {

        SkuInfo skuInfo = productFeignClient.getSkuInfo(skuId);

        BaseCategoryView categoryView = productFeignClient.getCategoryView(skuInfo.getCategory3Id() + "");

        List<BaseAttrInfo> baseAttrInfos = productFeignClient.getAttrList(skuId);

        BaseTrademark baseTrademark = productFeignClient.getTrademark(skuInfo.getTmId());

        // 从mysql中查询出es需要的数据封装给goods
        Goods goods = new Goods();
        if(null != baseAttrInfos) {
            List<SearchAttr> searchAttrList =  baseAttrInfos.stream().map(baseAttrInfo -> {
                SearchAttr searchAttr = new SearchAttr();
                searchAttr.setAttrId(baseAttrInfo.getId());
                searchAttr.setAttrName(baseAttrInfo.getAttrName());
                List<BaseAttrValue> baseAttrValueList = baseAttrInfo.getAttrValueList();
                searchAttr.setAttrValue(baseAttrValueList.get(0).getValueName());
                return searchAttr;
            }).collect(Collectors.toList());

            goods.setAttrs(searchAttrList);
        }

        if (baseTrademark != null){
            goods.setTmId(skuInfo.getTmId());
            goods.setTmName(baseTrademark.getTmName());
            goods.setTmLogoUrl(baseTrademark.getLogoUrl());

        }


        if (categoryView != null) {
            goods.setCategory1Id(categoryView.getCategory1Id());
            goods.setCategory1Name(categoryView.getCategory1Name());
            goods.setCategory2Id(categoryView.getCategory2Id());
            goods.setCategory2Name(categoryView.getCategory2Name());
            goods.setCategory3Id(categoryView.getCategory3Id());
            goods.setCategory3Name(categoryView.getCategory3Name());
        }

        goods.setDefaultImg(skuInfo.getSkuDefaultImg());
        goods.setPrice(skuInfo.getPrice().doubleValue());
        goods.setId(skuInfo.getId());
        goods.setTitle(skuInfo.getSkuName());
        goods.setCreateTime(new Date());

        goodsElasticsearchRepository.save(goods);

    }

    @Override
    public void hotScore(String skuId) {
        Long hotScore = 0l;
//        查询原来的热度值
        hotScore = redisTemplate.opsForZSet().incrementScore("hotScore","sku:" + skuId,1).longValue();
//        热度值加一
//        hotScore++;
//        判断是否达到同步的阈值
        if (hotScore%10==0){
//            修改es
            Optional<Goods> goodsOptional = goodsElasticsearchRepository.findById(Long.parseLong(skuId));
            Goods goods = goodsOptional.get();
            goods.setHotScore(hotScore);
            goodsElasticsearchRepository.save(goods);
        }
    }
}
