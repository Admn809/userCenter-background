package com.xhy.usercenter.service;

import com.xhy.usercenter.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Lenovo
* @description 针对表【user】的数据库操作Service
* @createDate 2024-03-07 16:33:22
*/
@Service
public interface UserService extends IService<User> {

    /**
     *
     * 用户注册
     *
     * @param userAccount 用户账号
     * @param userPassword 用户密码
     * @param checkPassword 校验密码（二次确认）
     * @param planetCode 星球id
     * @return 用户的id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword, String planetCode);

    /**
     * 用户登录
     *
     * @param userAccount  用户账户
     * @param userPassword 用户密码
     * @param request 记录登录状态
     * @return 用户的信息
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 根据用户名查询
     *
     * @return 用户信息
     */
    List<User> queryUserList(String username);


    /**
     * 删除用户信息 ->（逻辑删除）
     *
     * @param id 用户id
     * @return 影响行数
     */
    int deleteUser(Long id);

    /**
     *
     * 用户脱敏
     *
     * @param originalUser 原始用户数据
     * @return 脱敏后的用户数据
     */
    User getSafetyUser(User originalUser);

    /**
     * 用户注销
     *
     * @param request 拿取用户的登录态
     */
    boolean userLogout(HttpServletRequest request);

}
