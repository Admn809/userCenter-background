package com.xhy.usercenter;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

@SpringBootTest
class UserCenterApplicationTests {

    @Test
    void testDigest() {
        String result = DigestUtils.md5DigestAsHex("12356".getBytes());
        System.out.println("result = " + result);
    }

    @Test
    void contextLoads() {
    }

}
