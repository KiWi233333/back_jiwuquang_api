package com.example.back_jiwuquang_api.pojo.sys;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 权限表
 *
 * @className: Permission
 * @author: Kiwi23333
 * @description: TODO描述
 * @date: 2023/5/2 13:00
 */
@Data
@TableName("sys_permission")
public class Permission {


    /**
     * 权限ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 所属父级权限ID
     */
    private String parentId;

    /**
     * 权限唯一CODE代码
     */
    private String code;

    /**
     * 权限名称
     */
    private String name;

    /**
     * 权限介绍
     */
    private String intro;

    /**
     * 权限类别(0-9)
     */
    private Integer type;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableField(exist = false)
    private List<Role> roles;
    @TableField(exist = false)
    private List<User> users;
}
