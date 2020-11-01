package com.zhu.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhu.demo.common.ReturnData;
import com.zhu.demo.dto.UserDto;
import com.zhu.demo.entity.User;
import com.zhu.demo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(value="用户controller",tags={"用户操作接口"})
@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping(value = "doGet")
    @ResponseBody
    @ApiOperation("查询测试")
    public String doGet(){
        return "doGet";
    }

    @PostMapping(value = "list")
    @ResponseBody
    @ApiOperation("查询所有用户")
    public ReturnData list(@RequestBody UserDto userDto){
        ReturnData rd = new ReturnData();
        IPage<User> userList = userService.getAll(userDto);
        rd.setData(userList);
        return rd;
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "UserDto of method", notes = "")
    @ApiParam(value = "param")
    public ReturnData login(@Valid @RequestBody UserDto userDto){
        ReturnData rd = new ReturnData();
        try {
            userService.login(userDto);
            rd.setData(userDto);
        }catch (Exception e){
            log.error(e.getMessage());
            return new ReturnData("500001", e.getMessage());
        }
        return rd;
    }

    @PostMapping("register")
    @ResponseBody
    public ReturnData register(@RequestBody User user) throws Exception {
        try{
            userService.register(user);
        }catch (Exception e){
            return new ReturnData("600001", e.getMessage());
        }

        return new ReturnData();
    }
}
