package com.example.back_jiwuquang_api.dto.sys;

import com.baomidou.mybatisplus.annotation.*;
import com.example.back_jiwuquang_api.domain.annotation.Phone;
import com.example.back_jiwuquang_api.pojo.sys.UserAddress;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 描述
 *
 * @className: UserAddressDTO
 * @author: Kiwi23333
 * @description: TODO描述
 * @date: 2023/5/16 13:39
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserAddressDTO {


    @ApiModelProperty(value = "收件人", required = true)
    @NotBlank(message = "收件人不能为空！")
    @Size(min = 2, max = 20, message = "收件人长度2-20字符！")
    private String name;

    @ApiModelProperty(value = "手机号", required = true)
    @NotBlank(message = "手机号不能为空！")
    @Phone
    private String phone;

    @ApiModelProperty(value = "是否默认", required = true)
    @NotNull(message = "是否默认不能为空！")
    private Integer isDefault;

    @ApiModelProperty(value = "省份", required = true)
    @NotBlank(message = "省份不能为空！")
    private String province;

    @ApiModelProperty(value = "城市", required = true)
    @NotBlank(message = "城市不能为空！")
    private String city;

    @ApiModelProperty(value = "区/县", required = true)
    @NotBlank(message = "区/县不能为空！")
    private String county;

    @ApiModelProperty(value = "详细地址", required = true)
    @NotBlank(message = "详细地址不能为空！")
    private String address;

    @ApiModelProperty(value = "邮政编码")
    private String postalCode;

    public static UserAddress toUserAddress(UserAddressDTO p) {
        return new UserAddress()
                .setName(p.getName())
                .setPhone(p.getPhone())
                .setProvince(p.getProvince())
                .setCity(p.getCity())
                .setCounty(p.getCounty())
                .setIsDefault(p.getIsDefault())
                .setPostalCode(p.getPostalCode())
                .setAddress(p.getAddress());
    }

}

