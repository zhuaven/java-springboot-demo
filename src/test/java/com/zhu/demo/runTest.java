package com.zhu.demo;

import com.zhu.demo.dao.UserMapper;
import com.zhu.demo.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class runTest {

    @Resource
    private UserMapper userMapper;

    @Test
    public void contextLoads(){

//        User user = new User;
//        user.setName("wdf");
//        user.setStatus("1");
//        int result = userDao.insert(user);
//        System.out.println("结果为："+result);
        log.info("test----");
    }
}
