package com.zhu.demo.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhu.demo.Utils.Md5Util;
import com.zhu.demo.Utils.RedisUtil;
import com.zhu.demo.Utils.UserUtil;
import com.zhu.demo.common.ReturnData;
import com.zhu.demo.dao.UserMapper;
import com.zhu.demo.dto.UserDto;
import com.zhu.demo.entity.User;
import com.zhu.demo.service.UserMoneyService;
import com.zhu.demo.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private RedisUtil redisUtil;

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserMoneyService userMoneyService;

    private String passwordMd5(String password){
        return Md5Util.getMD5(password, 32);
    }

    @Override
    public IPage<User> getAll(UserDto userDto) {
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>(user);
        Page<User> userPage = new Page<>(userDto.getCurrent(), userDto.getSize());
        IPage<User> list = userMapper.selectPage(userPage, queryWrapper);
        return list;
    }

    @Override
    @Transactional
    public User getUserInfo(String username) {
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("name", username);
        return userMapper.selectOne(qw);
    }

    @Override
    @Transactional
    public void register(User user) throws Exception {

        if(StringUtils.isEmpty(user.getName())){
            throw new Exception("用户名不能为空");
        }else{
            //校验用户名是否已被使用
            checkUserName(user.getName());
        }
        if(StringUtils.isEmpty(user.getPassword())){
            throw new Exception("密码不能为空");
        }
        user.setPassword(passwordMd5(user.getPassword()));
        user.setStatus(1);
        user.setCreateBy(user.getName());
        user.setCreateDate(new Date());
        userMapper.insert(user);

        //建立用户钱包信息
        userMoneyService.add(user.getId());

    }

    @Override
    @Transactional
    public UserDto login(UserDto userDto) throws Exception {
        User user = userMapper.login(userDto);
        if(user == null){
            throw new Exception("账号或密码错误");
        }
        BeanUtils.copyProperties(user, userDto);
        userDto.setToken(getToken(userDto.getName()));
        updateUserUtil(userDto);
        return userDto;
    }

    //全局记录用户信息
    @Override
    public void updateUserUtil(UserDto userDto){
        UserUtil.id = userDto.getId();
        UserUtil.name = userDto.getName();
        UserUtil.email = userDto.getEmail();
        UserUtil.token = userDto.getToken();
        UserUtil.loginDate = new Date().toString();
    }

    public String getToken(String username){
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        redisUtil.set(token, username);
        return token;
    }

    private void checkUserName(String username) throws Exception {
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("name", username);
        User user = userMapper.selectOne(qw);
        if (user != null) {
            throw new Exception("用户名已存在");
        }
    }
}
