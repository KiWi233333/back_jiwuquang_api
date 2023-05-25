package com.example.back_jiwuquang_api.dto.orders;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.util.List;


/**
 * 提交订单 DTO
 *
 * @className: SelectOrderDTO
 * @author: Kiwi23333
 * @description: 提交订单 DTO
 * @date: 2023/5/22 19:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class InsertOrderDTO {

    @ApiModelProperty(value = "地址id", required = true)
    @NotBlank(message = "地址不能为空！")
    String addressId;

    @ApiModelProperty(value = "备注")
    String remark;

    @ApiModelProperty(value = "购买项列表",required = true)
    List<InsertOrderItemDTO> items;
}
