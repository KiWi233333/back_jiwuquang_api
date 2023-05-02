package com.example.kiwi_community_mall_back.pojo.sys;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 角色表实体类
 *
 * @className: Role
 * @author: Kiwi23333
 * @description: 角色表实体类
 * @date: 2023/5/2 10:22
 */
@Data
@TableName("sys_role")
public class Role {

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
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date createTime;

        /**
         * 修改时间
         */
        @TableField(fill = FieldFill.INSERT_UPDATE)
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date updateTime;

        @TableField(exist = false)
        private List<Permission> permissions;
        @TableField(exist = false)
        private List<User> users;
}
