package com.xhy.usercenter.constant;

/**
 * @Author: 席浩原
 * @CreateTime: 2024-03-08
 * @Description: 常量信息
 */

@SuppressWarnings("all")
public interface UserConstant {
    /**
     * 用于记录用户的登录态
     */
    String USER_LOGIN_SESSION = "userLogin";

    /**
     * 普通用户
     */
    int DEFAULT_ROLE = 0;

    /**
     * 管理员用户
     */
    int ADMIN_ROLE = 1;

}
