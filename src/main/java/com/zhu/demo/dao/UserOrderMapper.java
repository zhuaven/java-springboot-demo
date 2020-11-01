package com.zhu.demo.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.zhu.demo.dto.OrderDto;
import com.zhu.demo.entity.UserOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserOrderMapper extends BaseMapper<UserOrder>{

    IPage<OrderDto> list(IPage<UserOrder> orderIPage, @Param(Constants.WRAPPER) Wrapper<UserOrder> wrapper);
}
