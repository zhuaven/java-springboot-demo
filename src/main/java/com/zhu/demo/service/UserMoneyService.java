package com.zhu.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhu.demo.dto.UserMoneyDto;
import com.zhu.demo.entity.UserMoney;

public interface UserMoneyService {
    IPage<UserMoney> getAll(UserMoneyDto userMoneyDto);

    void add(Integer userId);

    UserMoney getUserMoneyInfo();

    //扣钱
    void costMoney(Double cost) throws Exception;
}
