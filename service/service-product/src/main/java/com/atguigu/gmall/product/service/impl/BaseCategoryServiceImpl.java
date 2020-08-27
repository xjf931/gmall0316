package com.atguigu.gmall.product.service.impl;

import com.atguigu.gmall.model.product.BaseCategory1;
import com.atguigu.gmall.model.product.BaseCategory2;
import com.atguigu.gmall.model.product.BaseCategory3;
import com.atguigu.gmall.model.product.BaseCategoryView;
import com.atguigu.gmall.product.mapper.BaseCategory1Mapper;
import com.atguigu.gmall.product.mapper.BaseCategory2Mapper;
import com.atguigu.gmall.product.mapper.BaseCategory3Mapper;
import com.atguigu.gmall.product.mapper.BaseCategoryViewMapper;
import com.atguigu.gmall.product.service.BaseCategoryService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BaseCategoryServiceImpl implements BaseCategoryService{

  @Autowired
  BaseCategory1Mapper baseCategory1Mapper;

  @Autowired
  BaseCategory2Mapper baseCategory2Mapper;


  @Autowired
  BaseCategory3Mapper baseCategory3Mapper;

  @Autowired
  BaseCategoryViewMapper baseCategoryViewMapper;

  @Override
  public List<BaseCategory1> getCategory1() {
    return baseCategory1Mapper.selectList(null);
  }

  @Override
  public List<BaseCategory2> getCategory2(String category1Id) {

    QueryWrapper<BaseCategory2> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("category1_id",category1Id);
    List<BaseCategory2> baseCategory2s = baseCategory2Mapper.selectList(queryWrapper);
    return baseCategory2s;
  }

  @Override
  public List<BaseCategory3> getCategory3(String category2Id) {
    QueryWrapper<BaseCategory3> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("category2_id",category2Id);
    List<BaseCategory3> baseCategory3s = baseCategory3Mapper.selectList(queryWrapper);
    return baseCategory3s;
  }

  @Override
  public BaseCategoryView getCategoryView(String category3Id) {

    BaseCategoryView baseCategoryView = baseCategoryViewMapper.selectById(category3Id);

    return baseCategoryView;
  }
}
