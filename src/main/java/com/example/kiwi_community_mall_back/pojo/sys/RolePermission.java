package com.example.kiwi_community_mall_back.pojo.sys;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 角色权限关系表 实体类
 *
 * @className: RolePermission
 * @author: Kiwi23333
 * @description: 角色权限关系表
 * @date: 2023/5/2 13:54
 */
@Data
@Accessors(chain = true)
@TableName("sys_role_permission")
public class RolePermission {

        /**
         * 权限ID
         */
        @TableId(value = "id", type = IdType.ASSIGN_ID)
        private String id;

        /**
         * 角色ID
         */
        private Long roleId;

        /**
         * 权限ID
         */
        private Long permissionId;

        /**
         * 权限唯一CODE代码
         */
        private String code;

        /**
         * 权限类别
         */
        private Integer type;

        /**
         * 权限名称
         */
        private String name;

        /**
         * 权限介绍
         */
        private String intro;

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

    }