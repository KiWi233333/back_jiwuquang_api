package com.example.kiwi_community_mall_back.util;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 接口返回结果模板
 *
 * @className: Result
 * @author: Author作者
 * @description: 接口返回模板
 * @date: 2023/4/7 21:35
 */
@Data
public class Result {
    /**
     * @code 状态码：
     *  20000操作成功
     *
     *  20010添加或已经存在， 增
     *  20011被删或不存在， 删
     *  20012查询不到，    查
     *  20013修改失败，    改
     *  20014不能为空，
     *  20020阻塞或被占用
     */
    @ApiModelProperty("状态码：20000成功，20010添加已经存在，20011被删或不存在，20012查询不到，20013修改失败，20014不能为空，20020常规性错误")
    private Integer code;
    @ApiModelProperty("描述信息")
    private String message;
    @ApiModelProperty("返回数据")
    private Object data;


    public Result(Integer code, String message,Object data) {
        this.code = code;
        this.message = message;
        // 防止传空
        if (data==null) {
            this.data="";
        } else {
            this.data = data;
        }
    }
    /**
     * 成功
     **/
    // 成功请求--普通
    public static Result ok() {
        return new Result(20000, "操作成功!", null);
    }

    // 成功请求--传入数据
    public static Result ok(Object data) {
        return new Result(20000, "获取成功!", data);
    }

    // 成功请求--传入消息和数据
    public static Result ok(String message, Object data) {
        return new Result(20000, message, data);
    }

    /**
     * 失败
     **/
    // 失败请求--普通
    public static Result fail() {
        return new Result(20020, "操作失败！", null);
    }

    // 失败请求--传入消息
    public static Result fail(String message) {
        return new Result(20020, message,null);
    }

    // 失败请求--传入消息和数据
    public static Result fail(int code, String message) {
        return new Result(code, message, null);
    }



}
