package com.atguigu.gmall.all.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TestController {

//	@ResponseBody
	@RequestMapping("test")
	public String test(){
		return  "test";
	}
//	@RequestMapping("test")
//	public String test(ModelMap modelMap){
//
//		List<String> list = new ArrayList<>();
//
//		for (int i = 0; i < 5; i++) {
//			list.add("元素"+i);
//		}
//
//		modelMap.put("value","value");
//		modelMap.put("text","text");
//		modelMap.put("list",list);
//		modelMap.put("num",0);
//		modelMap.put("obj","伍兹");
//		modelMap.put("gname","<span style=\"color:green\">宝强</span>");
//		return "test";
//	}
//
}
