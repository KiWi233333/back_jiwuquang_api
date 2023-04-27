package com.example.kiwi_community_mall_back.dto.user;
import lombok.AllArgsConstructor;
import lombok.Data;
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
public class UserCheckDTO {
    private String id;
    private String password;
    private String salt;
}
