package com.xhy.usercenter.controller;

import com.xhy.usercenter.model.domain.User;
import com.xhy.usercenter.model.request.UserLoginRequest;
import com.xhy.usercenter.model.request.UserRegistRequest;
import com.xhy.usercenter.model.reslut.Result;
import com.xhy.usercenter.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;



import static com.xhy.usercenter.constant.UserConstant.ADMIN_ROLE;
import static com.xhy.usercenter.constant.UserConstant.USER_LOGIN_SESSION;

/**
 * @Author: 席浩原
 * @CreateTime: 2024-03-08
 * @Description: 用户接口
 */

@SuppressWarnings("all")
@Slf4j
@RestController
@RequestMapping("user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 用户注册接口
     * @param registRequest 接收前端的JSON数据
     * @return 用户id
     */
    @PostMapping("register")
    public Result userRegist(@RequestBody UserRegistRequest registRequest) {
        if (registRequest == null) {
            return Result.returnFail("Error!");
        }

        String userAccount = registRequest.getUserAccount();
        String userPassword = registRequest.getUserPassword();
        String checkPassword = registRequest.getCheckPassword();

        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            return Result.returnFail("The account, password or checkpassword cannot be empty!");
        }

        Result result = Result.returnOk(userService.userRegister(userAccount, userPassword, checkPassword));

        return result;
    }

    /**
     * 用户登录接口
     * @param loginRequest 前端登录信息请求
     * @param request 客户端请求信息封装类
     * @return 用户实体类
     */
    @PostMapping("login")
    public Result userLogin(@RequestBody UserLoginRequest loginRequest, HttpServletRequest request) {
        if (loginRequest == null) {
            return Result.returnFail("Error!");
        }

        String userAccount = loginRequest.getUserAccount();
        String userPassword = loginRequest.getUserPassword();

        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return Result.returnFail("The account and password cannot be empty!");
        }

        Result result = Result.returnOk(userService.userLogin(userAccount, userPassword, request));

        return result;
    }

    /**
     * 查询所有
     * @return 用户信息的集合
     */
    @GetMapping("query")
    public Result queryUsers(String username, HttpServletRequest request) {
        /**
         * 鉴权
         */
        if (!isAdmin(request)) {
            log.info("This account does not have permissions!");
            return Result.returnFail("This account does not have permissions!");
        }
        Result result = Result.returnOk(userService.queryUserList(username));
        return result;
    }


    /**
     * 根据id删除用户信息
     * @param id
     * @return 影响的行数
     */
    @DeleteMapping
    public Result deleteUser(Long id, HttpServletRequest request) {
        /**
         * 鉴权
         */
        if (!isAdmin(request)) {
            log.info("This account does not have permissions!");
            return Result.returnFail("This account does not have permissions!");
        }

        if (id <= 0) {
            return Result.returnFail("This account does not have permissions!");
        }

        /**
         * mybatis-plus开启逻辑删除配置之后会自动将删除变成更新
         */
        Result result = Result.returnOk(userService.deleteUser(id));
        return result;
    }

    /**
     *
     * 返回客户端用户的登录信息
     *
     * @param request 记录用户的登录信息
     * @return 返回用户的信息
     */
    @GetMapping("/current")
    public User getCurrentUser(HttpServletRequest request) {
        User currentUser = (User) request.getSession().getAttribute(USER_LOGIN_SESSION);
        if (currentUser == null) {
            return null;
        }
        Long currentUserId = currentUser.getId();
        User user = userService.getById(currentUserId);

        return userService.getSafetyUser(user);
    }

    /**
     * 对用户的权限进行鉴定
     *
     * @param request 获取用户的登录信息
     * @return boolean类型的变量
     */
    private boolean isAdmin(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(USER_LOGIN_SESSION);
        if (user == null || user.getUserRole() != ADMIN_ROLE) {
            return false;
        }
        return true;
    }
}
