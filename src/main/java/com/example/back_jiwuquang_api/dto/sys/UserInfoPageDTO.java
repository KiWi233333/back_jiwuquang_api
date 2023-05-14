package com.example.back_jiwuquang_api.dto.sys;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 用户列表搜索参数类
 *
 * @className: UserListDTO
 * @author: Kiwi23333
 * @description: 用户列表搜索参数嘞
 * @date: 2023/5/14 12:37
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserInfoPageDTO {

    @ApiModelProperty(value = "用户id", required = false)
    String userId;
    @ApiModelProperty(value = "用户名、昵称、手机号、邮箱", required = false)
    String keyWord;
    @ApiModelProperty(value = "是否禁用", notes = "0否 1是", required = false)
    Integer status;
    @ApiModelProperty(value = "创建时间排序", notes = "0asc, 1desc", required = false)
    Integer createTimeSort;
}
