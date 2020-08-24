package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.product.BaseSaleAttr;
import com.atguigu.gmall.model.product.SpuInfo;
import com.atguigu.gmall.product.service.SpuInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
//通过@pathvariable可以将URL中占位符参数绑定到控制器处理方法的入参中:
// URL中的{XXX}占位符可以通过@pathvariable("XXX)绑定到操作方法的入参中

import java.util.List;


@RestController
@RequestMapping("admin/product")
@CrossOrigin
public class SpuApiController {
	@Autowired
	SpuInfoService spuInfoService;

	@RequestMapping("saveSpuInfo")
	public Result saveSpuInfo(@RequestBody SpuInfo spuInfo){

		spuInfoService.saveSpuInfo(spuInfo);

		return Result.ok();
	}


	@RequestMapping("baseSaleAttrList")
	public Result baseSaleAttrList(){

		List<BaseSaleAttr> baseSaleAttrs  = spuInfoService.baseSaleAttrList();

		return Result.ok(baseSaleAttrs);
	}

	@RequestMapping("{page}/{limit}")
	public Result spuList(@PathVariable("page") Long page, @PathVariable("limit") Long limit, String category3Id){

		Page<SpuInfo> pageParam = new Page<>(page, limit);

		IPage<SpuInfo> spuInfoIPage = spuInfoService.spuList(pageParam,category3Id);

		return Result.ok(spuInfoIPage);
	}
}
