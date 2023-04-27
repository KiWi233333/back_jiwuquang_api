package com.example.kiwi_community_mall_back.dto.user;

import com.example.kiwi_community_mall_back.util.interfaces.Phone;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 邮箱登录 入参类
 *
 * @className: LoginEmailCodeDTO
 * @author: Kiwi23333
 * @description: 邮箱登录
 * @date: 2023/4/21 15:23
 */
@Data
public class LoginEmailCodeDTO {

    @ApiModelProperty("邮箱")
    @Email
    @NotBlank(message = "邮箱不能为空")
    String email;


    @ApiModelProperty("验证码")
    @Size(min = 6,max = 6,message = "验证码长度为6个字符")
    @NotBlank(message = "验证码不能为空")
    String code;

}
