package com.example.back_jiwuquang_api.pojo.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 描述
 *
 * @className: UserAddress
 * @author: Kiwi23333
 * @description: TODO描述
 * @date: 2023/5/16 2:31
 */
@Data
@Accessors(chain = true)
@TableName("user_address")
public class UserAddress {


    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 收货人
     */
    @TableField(value = "name")
    private String name;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    private String userId;

    /**
     * 省份
     */
    @TableField(value = "province")
    private String province;

    /**
     * 城市
     */
    @TableField(value = "city")
    private String city;

    /**
     * 区/县
     */
    @TableField(value = "county")
    private String county;

    /**
     * 详细地址
     */
    @TableField(value = "address")
    private String address;

    /**
     * 邮编
     */
    @TableField(value = "postal_code")
    private String postalCode;

    /**
     * 手机号
     */
    @TableField(value = "phone")
    private String phone;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private Date updateTime;
}

