package com.zhu.demo.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.zhu.demo.entity.UserOrder;
import com.zhu.demo.entity.UserMoney;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMoneyMapper extends BaseMapper<UserMoney>{
    IPage<UserMoney> list(IPage<UserOrder> orderIPage, @Param(Constants.WRAPPER) Wrapper<UserMoney> wrapper);
}
