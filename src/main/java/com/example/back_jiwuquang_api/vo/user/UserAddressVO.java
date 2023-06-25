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
@Data
@AllArgsConstructor
@Accessors(chain = true)
@NoArgsConstructor
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
