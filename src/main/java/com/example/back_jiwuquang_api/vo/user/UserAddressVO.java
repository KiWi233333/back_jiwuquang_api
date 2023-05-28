package com.example.back_jiwuquang_api.vo.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;

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
}
