package com.example.back_jiwuquang_api.dto.sys;

import com.example.back_jiwuquang_api.domain.annotation.Phone;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * 修改用户邮箱参数类
 *
 * @className: UpdatePhoneDTO
 * @author: Kiwi23333
 * @description: 修改用户邮箱参数类
 * @date: 2023/5/10 16:29
 */
@Data
public class UpdateEmailDTO {


    @ApiModelProperty(value = "新邮箱", required = true)
    @Email
    @NotBlank(message = "新邮箱不能为空！")
    private String newEmail;

    @ApiModelProperty(value = "新邮箱-验证码", required = true)
    private String code;
}