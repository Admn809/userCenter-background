package com.xhy.usercenter.model.reslut;

import lombok.Data;

/**
 * @Author: 席浩原
 * @CreateTime: 2024-03-08
 * @Description: 返回给web一个JSON格式的数据
 */

@SuppressWarnings("all")
@Data
public class Result {
    private int code = 200; //200成功状态码

    private boolean flag = true; //返回状态

    private Object data;  //返回具体数据

    public static Result returnOk(Object data) {
        Result result = new Result();
        result.data = data;
        return result;
    }

    public static Result returnFail(Object data) {
        Result result = new Result();
        result.code = 500; // 错误码
        result.flag = false; // 错误状态
        result.data = data;

        return result;
    }
}
