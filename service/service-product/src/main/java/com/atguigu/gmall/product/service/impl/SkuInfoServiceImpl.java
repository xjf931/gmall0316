package com.atguigu.gmall.product.service.impl;

import com.atguigu.gmall.model.product.SkuAttrValue;
import com.atguigu.gmall.model.product.SkuImage;
import com.atguigu.gmall.model.product.SkuInfo;
import com.atguigu.gmall.model.product.SkuSaleAttrValue;
import com.atguigu.gmall.product.mapper.SkuAttrValueMapper;
import com.atguigu.gmall.product.mapper.SkuImageMapper;
import com.atguigu.gmall.product.mapper.SkuInfoMapper;
import com.atguigu.gmall.product.mapper.SkuSaleAttrValueMapper;
import com.atguigu.gmall.product.service.SkuInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
