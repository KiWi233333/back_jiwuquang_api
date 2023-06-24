package com.example.back_jiwuquang_api.vo.user;

import com.example.back_jiwuquang_api.enums.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 描述
 *
 * @className: UserAddressVO
 * @author: Kiwi23333
 * @description: TODO描述
 * @date: 2023/5/27 15:08
 */
public class UserAddressVO {


    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("收货人")
    private String name;

    @ApiModelProperty("是否默认")
    private Integer isDefault;

    @ApiModelProperty("省份")
    private String province;

    @ApiModelProperty("城市")
    private String city;

    @ApiModelProperty("区/县")
    private String county;

    @ApiModelProperty("详细地址")
    private String address;

    @ApiModelProperty("邮编")
    private String postalCode;

    @ApiModelProperty("手机号")
    private String phone;

    /**
     * 修改用户基本信息Vo
     *
     * @className: UserInfoVo
     * @author: Kiwi23333
     * @description: TODO描述
     * @date: 2023/5/1 2:13
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Accessors(chain = true)
    public static class UserInfoVO {



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
         * 生日
         */
        private Date birthday;

        /**
         * 账号创建时间
         */
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
        private Date createTime;

        /**
         * 更新时间
         */
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
        private Date updateTime;

        /**
         * 最后登录时间
         */
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
        private Date lastLoginTime;

        /**
         * 登录状态：on|off
         */
        private Integer status;

        /**
         * 邮箱是否验证（绑定）
         */
        private Integer isEmailVerified;
        /**
         * 手机号是否验证（绑定）
         */
        private Integer isPhoneVerified;

        /**
         *
         */


    }
}
