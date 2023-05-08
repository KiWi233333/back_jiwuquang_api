package com.example.back_jiwuquang_api.dto.sys;

import com.example.back_jiwuquang_api.domain.annotation.Phone;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @className: UpdatePwdDTO
 * @author: Kiwi23333
 * @description: TODO描述
 * @date: 2023/5/9 2:30
 */
@Data
public class UpdatePwdDTO {

    @ApiModelProperty("旧密码")
    @NotBlank(message = "旧密码不能为空")
    @Size(min = 6, max = 20, message = "密码长度必须在6-20个字符之间")
    String oldPassword;


    @ApiModelProperty("新密码")
    @NotBlank(message = "新密码不能为空")
    @Size(min = 6, max = 20, message = "密码长度必须在6-20个字符之间")
    String newPassword;

}
