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
        String userAccount = "planet";
        String userPassword = "12345678";
        String checkPassword = "12345678";
        String planetCode = "";
        long result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        assertEquals(-1, result);

        planetCode = "1";
        result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        assertEquals(-1, result);

        planetCode = "asa";
        result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        assertEquals(-1, result);

        planetCode = "10000";
        result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        System.out.println("result = " + result);
    }
}