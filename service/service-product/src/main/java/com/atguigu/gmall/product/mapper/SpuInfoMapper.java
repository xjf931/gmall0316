package com.atguigu.gmall.product.mapper;

import com.atguigu.gmall.model.product.SpuInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
//添写Mapper是为了交给SPRING管理
@Mapper
public interface SpuInfoMapper extends BaseMapper<SpuInfo> {
}
