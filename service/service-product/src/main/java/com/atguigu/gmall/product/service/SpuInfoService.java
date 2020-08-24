package com.atguigu.gmall.product.service;

import com.atguigu.gmall.model.product.BaseSaleAttr;
import com.atguigu.gmall.model.product.SpuInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

public interface SpuInfoService {
	IPage<SpuInfo> spuList(Page<SpuInfo> pageParam, String category3Id);

	List<BaseSaleAttr> baseSaleAttrList();

	void saveSpuInfo(SpuInfo spuInfo);
}
