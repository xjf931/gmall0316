package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.product.BaseAttrInfo;
import com.atguigu.gmall.model.product.BaseAttrValue;
import com.atguigu.gmall.product.service.BaseAttrInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin/product")
@CrossOrigin

public class AttrApiController {
//	自动装匹配,解决get,set问题
	@Autowired
	BaseAttrInfoService baseAttrInfoService;

	@RequestMapping("getAttrValueList/{attrId}")
	public Result getAttrValueList(@PathVariable("attrId") String attrId){

		List<BaseAttrValue> baseAttrValues = baseAttrInfoService.getAttrValueList(attrId);

		return Result.ok(baseAttrValues);
	}
//	保存功能
	@RequestMapping("saveAttrInfo")
	public Result saveAttrInfo(@RequestBody BaseAttrInfo baseAttrInfo){

		baseAttrInfoService.saveAttrInfo(baseAttrInfo);

		return Result.ok();
	}

	//	三级目录
	@RequestMapping("attrInfoList/{category1Id}/{category2Id}/{category3Id}")
	public Result attrInfoList(@PathVariable("category1Id")String category1Id,@PathVariable("category2Id")String category2Id,@PathVariable("category3Id") String category3Id){
		List<BaseAttrInfo> baseAttrInfos = baseAttrInfoService.attrInfoList(category1Id,category2Id,category3Id);
		return Result.ok(baseAttrInfos);
	}
}
