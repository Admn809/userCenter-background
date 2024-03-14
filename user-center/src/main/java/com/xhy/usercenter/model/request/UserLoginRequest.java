package com.xhy.usercenter.model.request;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @Author: 席浩原
 * @CreateTime: 2024-03-08
 * @Description: 用户登录请求体
 */

@SuppressWarnings("all")
@Data
public class UserLoginRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = -4442420587539652187L;

    private String userAccount;
    private String userPassword;
}
