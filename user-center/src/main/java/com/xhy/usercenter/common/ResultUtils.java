package com.xhy.usercenter.common;

/**
 * @Author: 席浩原
 * @CreateTime: 2024-03-16
 * @Description: 返回工具类
 */

@SuppressWarnings("all")
public class ResultUtils {

    /**
     * 成功
     * @param data 传递成功的数据信息
     * @return 封装类
     * @param <T>
     */
    public static <T> Result<T> success(T data) {
        return new Result(ErrorCode.SUCCESS.getCode(), ErrorCode.SUCCESS.getMessage(), data, ErrorCode.SUCCESS.getDescription());
    }

    /**
     * 失败
     * @param errorCode 失败错误代码类型
     * @return
     */
    public static Result error(ErrorCode errorCode) {
        return new Result<>(errorCode);
    }

    /**
     * 失败
     * @param errorCode 错误代码类型
     * @param description 自定义的错误信息描述
     * @return
     */
    public static Result error(ErrorCode errorCode, String description) {
        return new Result<>(errorCode, description);
    }

    /**
     * 失败
     * @param code 错误代码
     * @param message 错误信息
     * @param description 错误描述
     * @return
     */
    public static Result error(int code, String message, String description) {
        return new Result<>(code, message, description);
    }

    /**
     * 失败
     * @param errorCode 错误代码类型
     * @param message 错误信息
     * @param description 错误描述
     * @return
     */
    public static Result error(ErrorCode errorCode, String message, String description) {
        return new Result<>(errorCode.getCode(), message, description);
    }
}


