package com.example.back_jiwuquang_api.dto.sys;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 校验密码登录参数类
 *
 * @className: UserTokenDto
 * @author: Kiwi2333
 * @description: 密码登录密码+盐和后端的比对
 * @date: 2023/4/13 1:22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCheckDTO {
    private String id;
    private String password;
    private String salt;
}
