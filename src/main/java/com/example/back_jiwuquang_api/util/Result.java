package com.example.back_jiwuquang_api.util;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class Result {
    /**
     * @code 状态码：
     * 20000操作成功
     * <p>
     * 20001添加或已经存在， 增
     * 20002被删或不存在， 删
     * 20003查询不到，    查
     * 20004修改失败，    改
     * 20005链接元素不存在
     * 40001不能为空，
     * 40002认证失败
     * 40004阻塞或被占用 繁忙
     */
    public static final int SUCCESS = 20000;
    public static final int INSERT_ERR = 20001;
    public static final int DELETE_ERR = 20002;
    public static final int SELECT_ERR = 20003;
    public static final int UPDATE_ERR = 20004;
    public static final int LINK_NULL_ERR = 20005;
    public static final int NULL_ERR = 40001;
    public static final int TOKEN_ERR = 40002;
    public static final int BUSY_ERR = 40004;

    @ApiModelProperty(value = "状态码", notes = "20001添加或已经存在， 增" +
            "20002被删或不存在， 删" +
            "20003查询不到，    查" +
            "20004修改失败，    改" +
            "20005链接元素不存在" +
            "40001不能为空" +
            "40002认证失败" +
            "40004阻塞或被占用 繁忙")
    private Integer code;
    @ApiModelProperty("描述信息")
    private String message;
    @ApiModelProperty("返回数据")
    private Object data;


    /**
     * 成功
     **/
    // 成功请求--普通
    public static Result ok() {
        return new Result(SUCCESS, "操作成功!", null);
    }

    // 成功请求--传入数据
    public static Result ok(Object data) {
        return new Result(SUCCESS, "获取成功!", data);
    }

    // 成功请求--传入消息和数据
    public static Result ok(String message, Object data) {
        return new Result(SUCCESS, message, data);
    }

    /**
     * 失败
     **/
    // 失败请求--普通
    public static Result fail() {
        return new Result(20002, "操作失败！", null);
    }

    // 失败请求--传入消息
    public static Result fail(String message) {
        return new Result(20002, message, null);
    }

    // 失败请求--传入消息和数据
    public static Result fail(int code, String message) {
        return new Result(code, message, null);
    }


}
