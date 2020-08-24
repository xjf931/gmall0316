package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.product.SkuInfo;
import com.atguigu.gmall.model.product.SpuInfo;
import com.atguigu.gmall.product.service.SkuInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin/product")
@CrossOrigin
public class SkuApiController {

	@Autowired
	SkuInfoService skuInfoService;
//	@RequestMapping("saveSkuInfo")
//	public Result saveSkuInfo(@RequestBody SkuInfo skuInfo){
//		skuInfoService.saveSkuInfo(skuInfo);
//		return Result.ok();
//	}

	@RequestMapping("cancelSale/{skuId}")
	public Result cancelSale(@PathVariable String skuId){
		skuInfoService.cancelSale(skuId);
		return Result.ok();
	}


	@RequestMapping("onSale/{skuId}")
	public Result onSale(@PathVariable String skuId){
		skuInfoService.onSale(skuId);
		return Result.ok();
	}


	@RequestMapping("list/{page}/{limit}")
	public Result list(@PathVariable("page") Long page, @PathVariable("limit") Long limit){
		Page<SkuInfo> pageParam = new Page<>(page, limit);
		IPage<SkuInfo> skuInfoIPage = skuInfoService.list(pageParam);
		return Result.ok(skuInfoIPage);
	}


}