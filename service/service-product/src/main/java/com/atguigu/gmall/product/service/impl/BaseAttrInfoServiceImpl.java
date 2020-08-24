package com.atguigu.gmall.product.service.impl;

import com.atguigu.gmall.model.product.BaseAttrInfo;
import com.atguigu.gmall.model.product.BaseAttrValue;
import com.atguigu.gmall.product.mapper.BaseAttrInfoMapper;
import com.atguigu.gmall.product.mapper.BaseAttrValueMapper;
import com.atguigu.gmall.product.service.BaseAttrInfoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BaseAttrInfoServiceImpl implements BaseAttrInfoService {

	@Autowired
	BaseAttrInfoMapper baseAttrInfoMapper;
//	自动装配,使用get和set方法
//	随后编写对应的BaseAttrValueMapper接口
	@Autowired
	BaseAttrValueMapper baseAttrValueMapper;
	@Override
	public List<BaseAttrInfo> attrInfoList(String category1Id, String category2Id, String category3Id) {

		QueryWrapper<BaseAttrInfo> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("category_level",3);
		queryWrapper.eq("category_id",category3Id);
		List<BaseAttrInfo> baseAttrInfos = baseAttrInfoMapper.selectList(queryWrapper);

		// 属性值集合
		for (BaseAttrInfo baseAttrInfo : baseAttrInfos) {
			Long attrId = baseAttrInfo.getId();
			QueryWrapper<BaseAttrValue> queryWrapperValue = new QueryWrapper<>();
			queryWrapperValue.eq("attr_id",attrId);
			List<BaseAttrValue> baseAttrValues = baseAttrValueMapper.selectList(queryWrapperValue);
			baseAttrInfo.setAttrValueList(baseAttrValues);
		}

		return baseAttrInfos;	}

	@Override
	public void saveAttrInfo(BaseAttrInfo baseAttrInfo) {

		Long attrId = baseAttrInfo.getId();
		if(null!=attrId){
			// 修改属性表
			baseAttrInfoMapper.updateById(baseAttrInfo);
			// 删除属性值
			QueryWrapper<BaseAttrValue> queryWrapperValue = new QueryWrapper<>();
			queryWrapperValue.eq("attr_id",attrId);
			baseAttrValueMapper.delete(queryWrapperValue);

		}else{
			// 保存属性表
			baseAttrInfoMapper.insert(baseAttrInfo);
		}

		// 保存属性值表
		List<BaseAttrValue> attrValueList = baseAttrInfo.getAttrValueList();
		for (BaseAttrValue baseAttrValue : attrValueList) {
			baseAttrValue.setAttrId(attrId);
			baseAttrValueMapper.insert(baseAttrValue);
		}

	}

	@Override
	public List<BaseAttrValue> getAttrValueList(String attrId) {
//		新建条件构造器
		QueryWrapper<BaseAttrValue> queryWrapperValue = new QueryWrapper<>();
//		equal
		queryWrapperValue.eq("attr_id", attrId);
		List<BaseAttrValue> baseAttrValues = baseAttrValueMapper.selectList(queryWrapperValue);
		return baseAttrValues;
	}
}
