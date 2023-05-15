package com.example.back_jiwuquang_api.dto.sys;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.example.back_jiwuquang_api.enums.Gender;
import com.example.back_jiwuquang_api.pojo.sys.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 用户基本信息修改参数DTO(管理员)
 *
 * @className: UpdateUserInfoDTO
 * @author: Kiwi23333
 * @description: 用户基本信息修改参数DTO
 * @date: 2023/5/9 3:06
 */

@Data
public class UpdateUserAllInfoDTO {


    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("性别（男|女|保密）")
    private Gender gender;

    @ApiModelProperty("状态（0|1）")
    private Integer status;

    @ApiModelProperty("状态（0|1）0前台 1后台")
    private Integer userType;

    @ApiModelProperty("生日")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date birthday;

    @ApiModelProperty("创建日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    public static User toUser(UpdateUserAllInfoDTO u) {
        return new User()
                .setUsername(u.getUsername())
                .setStatus(u.getStatus())
                .setCreateTime(u.getCreateTime())
                .setNickname(u.getNickname())
                .setGender(u.getGender())
                .setBirthday(u.getBirthday());
    }
}