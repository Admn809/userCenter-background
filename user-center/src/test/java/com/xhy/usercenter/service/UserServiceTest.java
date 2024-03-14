package com.xhy.usercenter.service;
import java.util.Date;

import com.xhy.usercenter.model.domain.User;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {

    @Resource
    private UserService userService;

    @Test
    void userRegister() {
//        检验非空
        String userAccount = "HaoYuan";
        String userPassword = "";
        String checkPassword = "12345678";
        long result = userService.userRegister(userAccount, userPassword, checkPassword);
        assertEquals(-1, result);

//        检验账户长度
        userAccount = "Hao";
        userPassword = "12345678";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        assertEquals(-1, result);

//        检验密码长度
        userAccount = "HaoYuan";
        userPassword = "1234";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        assertEquals(-1, result);

//        不包含特殊字符
        userPassword = "12345678";
        userAccount = "Hao Yuan";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        assertEquals(-1, result);

//        账户不重复
        userAccount = "testXhy";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        assertEquals(-1, result);

//        密码与校验密码相同
        checkPassword = "123123123";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        assertEquals(-1, result);

//        成功
        userAccount = "HaoYuan";
        checkPassword = "12345678";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        assertEquals(-1, result);

    }
}