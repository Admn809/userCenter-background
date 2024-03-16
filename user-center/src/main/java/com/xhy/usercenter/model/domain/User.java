package com.xhy.usercenter.model.domain;



import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;

import java.util.Date;

/**
* 
* @TableName user
*/
@Data
@TableName(value = "user")
public class User implements Serializable {

    /**
    * 用户id
    */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
    * 用户昵称
    */
    private String username;

    /**
    * 用户账号
    */
    private String userAccount;

    /**
    * 用户头像
    */
    private String avatarUrl;

    /**
    * 性别
    */
    private Integer gender;

    /**
    * 用户密码
    */
    private String userPassword;

    /**
    * 电话
    */
    private String phone;

    /**
    * 邮箱
    */
    private String email;

    /**
    * 用户状态 0-正常
    */
    private Integer userStatus;


    /**
    * 创建时间
    */
    private Date createTime;

    /**
    * 更新时间
    */
    private Date updateTime;

    /**
    * 是否删除
    */
    @TableLogic
    private Integer isDelete;


    /**
     * 用户状态 0-普通用户 1-管理员
     */
    private Integer userRole;

    /**
     * 星球用户的id
     */
    private String planetCode;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}
