package com.example.kiwi_community_mall_back.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 接口返回结果模板
 *
 * @className: Result
 * @author: Author作者
 * @description: 接口返回模板
 * @date: 2023/4/7 21:35
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    /**
     * @code 20011成功  20010失败
     */
    private int code;
    private String message;
    private Object data;

    // 成功请求--普通
    private static Result ok() {
        return new Result(20011,"操作成功!",null);
    }

    // 成功请求--传入数据
    private static Result ok(Object data) {
        return new Result(20011,"查询成功!",data);
    }
    // 成功请求--传入消息和数据
    private static Result ok(String message,Object data) {
        return new Result(20011,message,data);
    }

    // 失败请求--普通
    private static Result fail() {
        return new Result(20010,"操作失败！",null);
    }
    // 失败请求--传入消息
    private static Result fail(String message) {
        return new Result(20010,message,null);
    }
    // 失败请求--传入消息和数据
    private static Result fail(int code,String message) {
        return new Result(code,message,null);
    }
}
