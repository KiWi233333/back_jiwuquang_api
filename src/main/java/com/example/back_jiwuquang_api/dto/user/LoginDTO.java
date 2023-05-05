package com.example.back_jiwuquang_api.dto.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 描述
 *
 * @className: LoginDto
 * @author: Kiwi23333
 * @description: TODO描述
 * @date: 2023/4/21 15:23
 */
@Data
public class LoginDTO {
    @ApiModelProperty("用户名/手机号/邮箱")
    @NotBlank(message = "用户名/手机号/邮箱不能为空")
    String username;

    @ApiModelProperty("密码")
    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 20, message = "密码长度必须在6-20个字符之间")
    String password;

}
