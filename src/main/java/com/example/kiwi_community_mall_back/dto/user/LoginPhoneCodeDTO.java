package com.example.kiwi_community_mall_back.dto.user;

import com.example.kiwi_community_mall_back.core.annotation.Phone;
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
public class LoginPhoneCodeDTO {

    @ApiModelProperty("手机号")
    @Phone
    @NotBlank(message = "手机号不能为空")
    String phone;


    @ApiModelProperty("验证码")
    @Size(min = 6,max = 6,message = "验证码长度为6个字符")
    @NotBlank(message = "验证码不能为空")
    String code;

}
