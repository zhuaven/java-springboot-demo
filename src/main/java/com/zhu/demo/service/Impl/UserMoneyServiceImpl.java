package com.zhu.demo.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhu.demo.Utils.UserUtil;
import com.zhu.demo.dao.UserMoneyMapper;
import com.zhu.demo.dto.UserMoneyDto;
import com.zhu.demo.entity.UserOrder;
import com.zhu.demo.entity.UserMoney;
import com.zhu.demo.service.UserMoneyService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class UserMoneyServiceImpl implements UserMoneyService {

    @Resource
    private UserMoneyMapper userMoneyMapper;

    @Override
    public IPage<UserMoney> getAll(UserMoneyDto userMoneyDto) {
        UserMoney userMoney = new UserMoney();
        BeanUtils.copyProperties(userMoneyDto, userMoney);
        QueryWrapper<UserMoney> queryWrapper = new QueryWrapper<>(userMoney);
        Page<UserOrder> orderPage = new Page<>(userMoneyDto.getCurrent(), userMoneyDto.getSize());
        IPage<UserMoney> list = userMoneyMapper.list(orderPage, queryWrapper);
        return list;
    }

    @Override
    public UserMoney getUserMoneyInfo(){
        QueryWrapper<UserMoney> qw = new QueryWrapper<>();
        qw.eq("user_id", UserUtil.id);
        UserMoney userMoney = userMoneyMapper.selectOne(qw);
        return userMoney;
    }

    //购买商品后,减去用户金额
    @Override
    public void costMoney(Double price) throws Exception {
        UserMoney userMoney = getUserMoneyInfo();
        Double userAllMoney = userMoney.getMoney();
        Double newMoney = 0.0;
        if(userAllMoney >= price){
            newMoney = userAllMoney - price;
        }else{
            throw new Exception("余额不足");
        }

        userMoney.setMoney(newMoney);
        userMoneyMapper.updateById(userMoney);
    }

    @Override
    public void add(Integer userId) {
        UserMoney userMoney = new UserMoney();
        if(StringUtils.isEmpty(userId)){
            userMoney.setUserId(UserUtil.id);
        }else{
            userMoney.setUserId(userId);
        }
        userMoney.setMoney(0.0);
        userMoney.setStatus(1);
        userMoney.setCreateBy(UserUtil.name);
        userMoney.setCreateDate(new Date());
        userMoneyMapper.insert(userMoney);
    }
}
