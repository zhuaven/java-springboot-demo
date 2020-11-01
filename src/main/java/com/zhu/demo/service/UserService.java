package com.zhu.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhu.demo.dto.UserDto;
import com.zhu.demo.entity.User;

public interface UserService {
    IPage<User> getAll(UserDto userDto);

    User getUserInfo(String username);

    void register(User user) throws Exception;

    UserDto login(UserDto UserDto) throws Exception;

    void updateUserUtil(UserDto userDto);
}
