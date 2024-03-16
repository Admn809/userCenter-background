package com.xhy.usercenter.exception;

import com.xhy.usercenter.common.ErrorCode;

/**
 * @Author: 席浩原
 * @CreateTime: 2024-03-16
 * @Description: 自定义业务异常处理类
 */

@SuppressWarnings("all")
public class BusinessException extends RuntimeException{
    /**
     * 自定义的错误码
     */
    private final int code;

    /**
     * 自定义描述信息
     */
    private final String description;

    public BusinessException(String message, int code, String description) {
        super(message);
        this.code = code;
        this.description = description;
    }

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.description = errorCode.getDescription();
    }

    public BusinessException(ErrorCode errorCode, String description) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
