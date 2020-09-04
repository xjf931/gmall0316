package com.atguigu.gmall.all.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.list.client.ListFeignClient;
import com.atguigu.gmall.model.list.SearchParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
public class ListController {


    @Autowired
    ListFeignClient listFeignClient;

    @RequestMapping({"list.html","search.html"})
//    @RequestMapping({"list.html"})

    public String list(SearchParam searchParam,Model model){
        Result<Map> result = listFeignClient.list(searchParam);
        model.addAllAttributes(result.getData());
        model.addAttribute("searchParam",searchParam);

        return "list/index";
    }

}
