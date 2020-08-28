package com.atguigu.gmall.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.atguigu.gmall.model.product.SkuAttrValue;
import com.atguigu.gmall.model.product.SkuImage;
import com.atguigu.gmall.model.product.SkuInfo;
import com.atguigu.gmall.model.product.SkuSaleAttrValue;
import com.atguigu.gmall.product.mapper.SkuAttrValueMapper;
import com.atguigu.gmall.product.mapper.SkuImageMapper;
import com.atguigu.gmall.product.mapper.SkuInfoMapper;
import com.atguigu.gmall.product.mapper.SkuSaleAttrValueMapper;
import com.atguigu.gmall.product.service.SkuInfoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class SkuInfoServiceImpl implements SkuInfoService {

	@Autowired
	SkuInfoMapper skuInfoMapper;

	@Autowired
	SkuImageMapper skuImageMapper;

	@Autowired
	SkuAttrValueMapper skuAttrValueMapper;

	@Autowired
	SkuSaleAttrValueMapper skuSaleAttrValueMapper;

	@Autowired
	RedisTemplate redisTemplate;

	@Override
	public void saveSkuInfo(SkuInfo skuInfo) {

		skuInfoMapper.insert(skuInfo);

		Long skuId = skuInfo.getId();

		for (SkuImage skuImage : skuInfo.getSkuImageList()) {
			skuImage.setSkuId(skuId);
			skuImageMapper.insert(skuImage);
		}

		for (SkuAttrValue skuAttrValue : skuInfo.getSkuAttrValueList()) {
			skuAttrValue.setSkuId(skuId);
			skuAttrValueMapper.insert(skuAttrValue);
		}

		for (SkuSaleAttrValue skuSaleAttrValue : skuInfo.getSkuSaleAttrValueList()) {
			skuSaleAttrValue.setSkuId(skuId);
			skuSaleAttrValue.setSpuId(skuInfo.getSpuId());
			skuSaleAttrValueMapper.insert(skuSaleAttrValue);
		}


	}

	@Override
	public IPage<SkuInfo> list(Page<SkuInfo> pageParam) {
		IPage<SkuInfo> skuInfoIPage = skuInfoMapper.selectPage(pageParam, null);
		return skuInfoIPage;
	}

	@Override
	public void cancelSale(String skuId) {

		SkuInfo skuInfo = new SkuInfo();
		skuInfo.setId(Long.parseLong(skuId));
		skuInfo.setIsSale(0);
		skuInfoMapper.updateById(skuInfo);

	}

	@Override
	public void onSale(String skuId) {
		SkuInfo skuInfo = new SkuInfo();
		skuInfo.setId(Long.parseLong(skuId));
		skuInfo.setIsSale(1);
		skuInfoMapper.updateById(skuInfo);
	}

	@Override
	public SkuInfo getSkuInfo(String skuId) {

//		long currentTimeMillisStart = System.currentTimeMillis();

		SkuInfo skuInfo = null;

//		查询缓存
		String skuStrFromCache =  (String)redisTemplate.opsForValue().get("sku:" + skuId + ":info");
		if(StringUtils.isBlank(skuStrFromCache)) {

			Boolean lock = redisTemplate.opsForValue().setIfAbsent("sku:" + skuId + ":lock", 1, 10000, TimeUnit.SECONDS);

			if (lock){
				skuInfo = getSkuInfoFromDb(skuId);
				if (null != skuInfo) {
					redisTemplate.opsForValue().set("sku:" + skuId + ":info", JSON.toJSONString(skuInfo));
				}else {
					redisTemplate.opsForValue().set("sku:" + skuId + ":info", JSON.toJSONString(new SkuInfo()),10,TimeUnit.SECONDS);

				}
//			skuInfo = getSkuInfoFromDb(skuId);
//			if (null != skuInfo) {
//				redisTemplate.opsForValue().set("sku:" + skuId + ":info", JSON.toJSONString(skuInfo));
//				获得分布式🔒的线程操作完毕,归还分布式锁
				redisTemplate.delete("sku:" + skuId + ":info");
			}else {
//				自旋
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return getSkuInfo(skuId);
//				redisTemplate.opsForValue().set("sku:" + skuId + ":info", JSON.toJSONString(new SkuInfo()),10,TimeUnit.SECONDS);
			}
		}else {
				skuInfo = JSON.parseObject(skuStrFromCache,SkuInfo.class);
			}
//				long currentTimeMillisEnd = System.currentTimeMillis();

//				System.out.println(currentTimeMillisEnd - currentTimeMillisStart);

//		查询DB
//		skuInfo = getSkuInfoFromDb(skuId);

//		SkuInfo skuInfo = skuInfoMapper.selectById(skuId);
//		QueryWrapper<SkuImage> queryWrapper = new QueryWrapper<>();
//		queryWrapper.eq("sku_id",skuId);
//		List<SkuImage> skuImages = skuImageMapper.selectList(queryWrapper);
//		skuInfo.setSkuImageList(skuImages);
		return skuInfo;
	}

	private SkuInfo getSkuInfoFromDb(String skuId) {

		SkuInfo skuInfo = skuInfoMapper.selectById(skuId);
		QueryWrapper<SkuImage> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("sku_id",skuId);
		List<SkuImage> skuImages = skuImageMapper.selectList(queryWrapper);
		skuInfo.setSkuImageList(skuImages);
		return skuInfo;

	}

	@Override
	public BigDecimal getSkuPrice(String skuId) {

		BigDecimal bigDecimal = new BigDecimal("0");

		SkuInfo skuInfo = skuInfoMapper.selectById(skuId);

		if(null!=skuInfo){
			bigDecimal = skuInfo.getPrice();
		}
		return bigDecimal;
	}

	@Override
	public Map<String, String> getSkuValueIdsMap(Long spuId) {

		List<Map<String,Object>> resultMapList =  skuSaleAttrValueMapper.selectSkuValueIdsMap(spuId);
		Map<String, String> valuleIdsMap = new HashMap<>();
		for (Map<String, Object> map : resultMapList) {
			String skuId =  map.get("sku_id").toString();
			String valueId =  map.get("value_ids").toString();
			valuleIdsMap.put(valueId,skuId);
		}

		return valuleIdsMap;
	}
}
