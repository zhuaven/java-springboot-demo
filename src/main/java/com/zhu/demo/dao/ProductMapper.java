package com.zhu.demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhu.demo.entity.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper extends BaseMapper<Product>{
    List<Product> list();
}
