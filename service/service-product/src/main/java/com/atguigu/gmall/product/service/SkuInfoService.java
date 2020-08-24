package com.atguigu.gmall.product.service;

import com.atguigu.gmall.model.product.SkuInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public interface SkuInfoService {
	void saveSkuInfo(SkuInfo skuInfo);

	IPage<SkuInfo> list(Page<SkuInfo> pageParam);

	void cancelSale(String skuId);

	void onSale(String skuId);
}
