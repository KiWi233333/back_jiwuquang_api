package com.example.back_jiwuquang_api.dto.orders;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.util.List;


/**
 * 修改订单 DTO
 *
 * @className: SelectOrderDTO
 * @author: Kiwi23333
 * @description: 修改订单 DTO
 * @date: 2023/5/22 19:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UpdateOrderDTO {

    @ApiModelProperty(value = "地址id")
    String addressId;

    @ApiModelProperty(value = "备注")
    String remark;

}
