package com.atguigu.gmall.test.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

	@Value("${myName}")
	private String myName;

	@RequestMapping("test")
	@ResponseBody
	public String test(){

		return myName;
	}

}
