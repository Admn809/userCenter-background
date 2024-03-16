package com.xhy.usercenter.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: 席浩原
 * @CreateTime: 2024-03-08
 * @Description: 返回给web一个JSON格式的数据
 */

@SuppressWarnings("all")
@Data
public class Result<T> implements Serializable {
    private int code; //状态码

    private String message; //返回信息

    private T data;  //返回具体数据

    private String description;

    public Result(int code, String message, T data, String description) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.description = description;
    }


    public Result(int code, String message, T data) {
        this(code, message, data, "");
    }

    public Result(int code, T data) {
        this(code, "", data);
    }

    public Result(ErrorCode errorCode) {
        this(errorCode.getCode(), errorCode.getMessage(), null, errorCode.getDescription());
    }

    public Result(ErrorCode errorCode, String description) {
        this(errorCode.getCode(), errorCode.getMessage(), null, description);
    }

    public Result(int code, String message, String description) {
        this(code, message, null, description);
    }
}

