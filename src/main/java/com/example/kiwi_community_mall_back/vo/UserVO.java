package com.example.kiwi_community_mall_back.vo;

import com.example.kiwi_community_mall_back.enums.Gender;
import com.example.kiwi_community_mall_back.enums.UserStatus;
import com.example.kiwi_community_mall_back.pojo.user.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 描述
 *
 * @className: UserVo
 * @author: Kiwi23333
 * @description: TODO描述
 * @date: 2023/4/30 14:45
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserVO {


    /**
     * id
     */
    private String id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 性别 （男|女|保密）
     */
    private Gender gender;

    /**
     * 头像icon
     */
    private String avatar;

    /**
     * 账号创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 最后登录时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastLoginTime;

    /**
     * 登录状态：on|off
     */
    private UserStatus status;

    /**
     * 邮箱是否验证（绑定）
     */
    private Integer isEmailVerified;
    /**
     * 手机号是否验证（绑定）
     */
    private Integer isPhoneVerified;

    /**
     * 会员等级
     */
    private Integer memberLevel;


    public static UserVO formUser(User user) {
        return new UserVO()
                .setId(user.getId())
                .setUsername(user.getUsername())
                .setEmail(user.getEmail())
                .setPhone(user.getPhone())
                .setNickname(user.getNickname())
                .setAvatar(user.getAvatar())
                .setUpdateTime(user.getUpdateTime())
                .setGender(user.getGender())
                .setCreateTime(user.getCreateTime())
                .setLastLoginTime(user.getLastLoginTime())
                .setIsEmailVerified(user.getIsEmailVerified())
                .setIsPhoneVerified(user.getIsPhoneVerified())
                .setStatus(user.getStatus())
                .setMemberLevel(user.getMemberLevel());
    }

}
