package com.xhy.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xhy.usercenter.service.UserService;
import com.xhy.usercenter.model.domain.User;
import com.xhy.usercenter.mapper.UserMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.xhy.usercenter.constant.UserConstant.USER_LOGIN_SESSION;

/**
* @author Lenovo
* @description 针对表【user】的数据库操作Service实现
* @createDate 2024-03-07 16:33:22
*/
@Slf4j
@Service
@SuppressWarnings("all")
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

//    加密用户的密码
    private static final String SALT = "HaoYuan";


    @Resource
    private UserMapper userMapper;

    /**
     * 对用户注册的信息进行校验
     *
     * @param userAccount 用户账号
     * @param userPassword 用户密码
     * @param checkPassword 校验密码（二次确认）
     * @return 用户的id
     */
    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword) {
        // 1.校验
        // 三者不为空 -> StringUtils.isAnyBlank() -> commons-lang3
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            return -1;
        }
        // 账号密码的长度
        if (userAccount.length() < 4 ) {
            return -1;
        }
        if (userPassword.length() < 8 || checkPassword.length() < 8) {
            return -1;
        }
        // 检验账户的特殊字符
        String validPattern = "[\\n`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。， 、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()) {
            return -1;
        }
        // 校验密码和检验密码
        if (!(checkPassword.equals(userPassword))) {
            return -1;
        }
        // 检验账户不重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        long count = userMapper.selectCount(queryWrapper);
        if (count > 0) { // 大于0说明有重复的account
            return -1;
        }

        // 2.加密 -> DigestUtils工具库的方法将密码转换成一个16进制
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());

        // 3.将数据加入数据库
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        boolean result = this.save(user);
        if (!result) {
            return -1;
        }

        return user.getId();
    }

    /**
     *
     * 登录逻辑
     *
     * @param userAccount  用户账户
     * @param userPassword 用户密码
     * @param request 记录登录信息
     * @return 用户信息
     */
    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        // 1.校验
        // 检查非空
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return null;
        }
        
        // 账户密码的长度
        if (userAccount.length() < 4 || userPassword.length() < 8) {
            return null;
        }
        
        // 不包含特殊字符
        String validPattern = "[\\n`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。， 、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()) {
            return null;
        }
        
        // 2.校验账户密码是否正确
        String result = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("userAccount", userAccount);
        wrapper.eq("userPassword", result);
        User user = userMapper.selectOne(wrapper);
        // 用户不存在
        if (user == null) {
            log.info("Login filed, userAccount connot match userPassword...");
            return null;
        }

        // 3.用户脱敏
        User safetyUser = getSafetyUser(user);

        // 4.记录登录态
        request.getSession().setAttribute(USER_LOGIN_SESSION, safetyUser);

        return safetyUser;
    }

    @Override
    public List<User> queryUserList(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(username)) {
            queryWrapper.like("username", username);
        }

        List<User> userList = userMapper.selectList(queryWrapper);
        /**
         * 这段Java代码使用了Java 8引入的Stream API来处理一个名为userList的列表。我会为你逐步分析这段代码：
         *
         * userList.stream():
         * 这将userList转换为一个流（Stream）。流是Java 8中引入的一个新特性，允许你以声明性方式处理数据（如集合）。
         *
         * .map(user -> { return getSafetyUser(user); }):
         * map是一个中间操作，用于对流中的每个元素应用一个函数。
         * 在这里，对userList中的每个user对象，都调用了getSafetyUser(user)方法。
         * getSafetyUser可能是一个自定义方法，它的目的是返回某种“安全”或“处理过”的用户对象。
         *
         * user -> { return getSafetyUser(user); }
         * 是一个lambda表达式，表示一个函数。在这个例子中，这个函数接受一个user作为参数，并返回getSafetyUser(user)的结果。
         *
         * .collect(Collectors.toList()):
         * collect是一个终端操作，用于将流中的元素聚合成某种结果。
         * Collectors.toList()是一个收集器，它将流中的元素收集到一个新的列表中。
         * 因此，整个操作的结果是一个新的列表，该列表包含userList中每个用户对象经过getSafetyUser方法处理后的结果。
         * 总结：这段代码的目的是将userList中的每个用户对象转换为另一个对象（可能是处理或安全版本的用户），并将这些新对象收集到一个新的列表中。
         */
        return userList.stream().map(user -> {
            return getSafetyUser(user);
        }).collect(Collectors.toList());
    }


    @Override
    public int deleteUser(Long id) {

        // 先检验
        if (id <= 0) {
            return 0;
        }


        // 检验是否存在
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id);

        User user = userMapper.selectOne(wrapper);
        // 用户不存在
        if (user == null) {
            log.info("Not find, this user may not exist!");
            return 0;
        }

        return userMapper.deleteById(id);
    }

    /**
     * 用户脱敏
     * @param originalUser 原始用户数据
     * @return 返回脱敏后的用户数据
     */
    @Override
    public User getSafetyUser(User originalUser) {
        if (originalUser == null) {
            return null;
        }
        User safetyUser = new User();
        safetyUser.setId(originalUser.getId());
        safetyUser.setUsername(originalUser.getUsername());
        safetyUser.setUserAccount(originalUser.getUserAccount());
        safetyUser.setAvatarUrl(originalUser.getAvatarUrl());
        safetyUser.setGender(originalUser.getGender());
        safetyUser.setPhone(originalUser.getPhone());
        safetyUser.setEmail(originalUser.getEmail());
        safetyUser.setCreateTime(originalUser.getCreateTime());
        safetyUser.setUpdateTime(originalUser.getUpdateTime());
        safetyUser.setUserRole(originalUser.getUserRole());

        return safetyUser;
    }
}




