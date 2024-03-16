package com.xhy.usercenter.exception;

import com.xhy.usercenter.common.ErrorCode;
import com.xhy.usercenter.common.Result;
import com.xhy.usercenter.common.ResultUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author: 席浩原
 * @CreateTime: 2024-03-16
 * @Description: 全局异常处理器
 */

@SuppressWarnings("all")
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    /**
     * 捕获自定义的一个异常类
     * @param e 自定义的异常类
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    public Result businessException(BusinessException e) {
        log.error("businessException" + e.getMessage(), e);
        return ResultUtils.error(e.getCode(), e.getMessage(), e.getDescription());
    }


    /**
     * 捕捉运行时异常
     * @param e 运行时异常类
     * @return
     */
    @ExceptionHandler(RuntimeException.class)
    public Result runtimeException(RuntimeException e) {
        log.error("runtimeException", e);
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR, "系统异常！");
    }
}
