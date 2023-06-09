package com.example.back_jiwuquang_api.dto.sys;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * UserTokenDTO
 *
 * @className: UserTokenDTO
 * @author: Kiwi23333
 * @description: UserTokenDTO
 * @date: 2023/5/3 1:38
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UserTokenDTO {

    /**
     * 用户id
     */
    private String id;


    /**
     * 角色集合
     */
    private String[] roleCodeList;
    private Integer[] roleTypeList;
    /**
     * 权限集合
     */
    private String[] permissionUrlList;
    private Integer[] permissionTypeList;


//    public static User toSecurityUser(UserTokenDTO userTokenDTO) {
//        return new User(userTokenDTO.getId(),null,null);
//    }

}
