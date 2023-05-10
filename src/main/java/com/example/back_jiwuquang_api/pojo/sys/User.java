package com.example.back_jiwuquang_api.pojo.sys;

import com.baomidou.mybatisplus.annotation.*;
import com.example.back_jiwuquang_api.enums.Gender;
import com.example.back_jiwuquang_api.enums.UserStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_user")
public class User {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    private String username;

    private String password;

    private String email;

    private String phone;

    private String nickname;

    /**
     * 性别 （男|女|保密）
     */
    private Gender gender;

    private String avatar;

    /**
     * 用户类型 0前台 1后台
     */
    private Integer userType;


    /**
     * 登录的ip
     */
    @TableField("last_login_ip")
    private String lastLoginIp;

    /**
     * 状态：on|off
     */
    @TableField("status")
    private UserStatus status;

    @TableField("is_email_verified")
    private Integer isEmailVerified;

    @TableField("is_phone_verified")
    private Integer isPhoneVerified;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date birthday;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("last_login_time")
    private Date lastLoginTime;

    @TableField(exist = false)
    private List<Role> roles;
    @TableField(exist = false)
    private List<Permission> permissions;
}