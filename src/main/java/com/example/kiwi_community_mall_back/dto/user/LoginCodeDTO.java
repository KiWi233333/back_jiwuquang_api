package com.example.kiwi_community_mall_back.dto.user;

import com.example.kiwi_community_mall_back.util.interfaces.Phone;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
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
public class LoginCodeDTO {

    @ApiModelProperty("手机号")
    @Phone
    @NotBlank(message = "手机号不能为空")
    String phone;

    @ApiModelProperty("密码")
    @Size(min = 6,max = 20,message = "密码长度为6-20个字符")
    @NotBlank(message = "密码不能为空")
    String code;

}
