package com.example.back_jiwuquang_api.dto.sys;

import com.example.back_jiwuquang_api.domain.annotation.Phone;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 修改用户手机参数类
 *
 * @className: UpdatePhoneDTO
 * @author: Kiwi23333
 * @description: 修改用户手机参数类
 * @date: 2023/5/10 16:29
 */
@Data
public class UpdatePhoneDTO {


    @ApiModelProperty(value = "新手机号", required = true)
    @Phone
    @NotBlank(message = "新手机号不能为空！")
    private String newPhone;

    @ApiModelProperty(value = "新手机-验证码", required = true)
    private String code;
}