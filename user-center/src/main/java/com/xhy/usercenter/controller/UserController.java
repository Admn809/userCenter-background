package com.xhy.usercenter.controller;

import com.xhy.usercenter.common.ErrorCode;
import com.xhy.usercenter.exception.BusinessException;
import com.xhy.usercenter.model.domain.User;
import com.xhy.usercenter.model.request.UserLoginRequest;
import com.xhy.usercenter.model.request.UserRegistRequest;
import com.xhy.usercenter.common.Result;
import com.xhy.usercenter.common.ResultUtils;
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
    public Result<Long> userRegist(@RequestBody UserRegistRequest registRequest) {
        if (registRequest == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }

        String userAccount = registRequest.getUserAccount();
        String userPassword = registRequest.getUserPassword();
        String checkPassword = registRequest.getCheckPassword();
        String planetCode = registRequest.getPlanetCode();

        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword, planetCode)) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }

        long resultCode = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);

        Result result = ResultUtils.success(resultCode);

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
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }

        String userAccount = loginRequest.getUserAccount();
        String userPassword = loginRequest.getUserPassword();

        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }

        Result result = ResultUtils.success(userService.userLogin(userAccount, userPassword, request));

        return result;
    }

    /**
     *
     * 用户注销
     *
     * @param request 登录态
     * @return
     */
    @PostMapping("logout")
    public Result userLogout(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        return ResultUtils.success(userService.userLogout(request));
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
            throw new BusinessException(ErrorCode.NO_AUTH, "非管理员用户！");
        }
        Result result = ResultUtils.success(userService.queryUserList(username));
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
            throw new BusinessException(ErrorCode.NO_AUTH, "非管理员用户！");
        }

        if (id <= 0) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }

        /**
         * mybatis-plus开启逻辑删除配置之后会自动将删除变成更新
         */
        Result result = ResultUtils.success(userService.deleteUser(id));
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
            throw new BusinessException(ErrorCode.NULL_ERROR);
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
