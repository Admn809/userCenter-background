package com.xhy.usercenter.model.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @Author: 席浩原
 * @CreateTime: 2024-03-08
 * @Description: 用户注册请求封装类
 */

@SuppressWarnings("all")
@Data
public class UserRegistRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -4905625645647498741L;

    private String userAccount;
    private String userPassword;
    private String checkPassword;
}
