package com.example.back_jiwuquang_api.dto.comm;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;

/**
 * 描述
 *
 * @className: SelectCommUserDTO
 * @author: Kiwi23333
 * @description: TODO描述
 * @date: 2023/6/24 1:50
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class SelectCommUserDTO {
    @ApiModelProperty(value = "昵称", required = false)
    String nickname;
    @ApiModelProperty(value = "邮箱", required = false)
    @Email(message = "邮箱格式不正确！")
    String email;
    @ApiModelProperty(value = "性别", notes = "男 女 保密", required = false)
    String gender;
    @ApiModelProperty(value = "最新排序", notes = "0 asc, 1 desc", required = false)
    Integer timeSort;
    @ApiModelProperty(value = "距离排序", notes = "0 asc, 1 desc", required = false)
    Integer distanceSort;
}
