package com.example.back_jiwuquang_api.dto.sys;

import com.example.back_jiwuquang_api.enums.Gender;
import com.example.back_jiwuquang_api.pojo.sys.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 用户基本信息修改参数DTO
 *
 * @className: UpdateUserInfoDTO
 * @author: Kiwi23333
 * @description: 用户基本信息修改参数DTO
 * @date: 2023/5/9 3:06
 */

@Data
public class UpdateUserInfoDTO {


    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("性别（男|女|保密）")
    private Gender gender;

    @ApiModelProperty("生日")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date birthday;


    public static User toUser(UpdateUserInfoDTO u) {
        return new User()
                .setNickname(u.getNickname())
                .setGender(u.getGender())
                .setBirthday(u.getBirthday());
    }
}
