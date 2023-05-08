package com.example.back_jiwuquang_api.dto.sys;

import com.example.back_jiwuquang_api.domain.annotation.Phone;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;

/**
 *  注册字段类
 * @className: UserRegister
 * @author: Kiwi23333
 * @description: 注册的字段
 * @date: 2023/4/21 23:49
 */
@Data
public class UserRegisterDTO {
    @ApiModelProperty(value = "用户名",required = true)
    @Size(min = 6, max = 20, message = "用户名长度在6-20个字符之间")
    @NotBlank(message = "用户名不能为空")
    String username;


    @ApiModelProperty(value = "手机号",required = false)
    @Phone
    String phone;

    @ApiModelProperty(value = "邮箱",required = false)
    @Email(message = "邮箱不合法")
    String email;

    @ApiModelProperty(value = "密码",required = true)
    @NotBlank(message = "密码不能为空")
    String password;

    @ApiModelProperty(value = "注册类型",notes = "0 手机号，1 邮箱")
    Integer type ;


    @ApiModelProperty(value = "手机|邮箱 验证码")
    @NotBlank(message = "验证码不能为空")
    @Size(min = 6,max = 6,message = "验证码为6位组成")
    String code;

}
