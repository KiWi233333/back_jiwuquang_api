package com.example.back_jiwuquang_api.dto.pay;

import com.example.back_jiwuquang_api.pojo.pay.RechargeCombo;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;


/**
 * 充值套餐参数类
 *
 * @className: RechargeComboDTO
 * @author: Kiwi23333
 * @description: RechargeComboDTO
 * @date: 2023/5/5 14:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class RechargeComboDTO {

    @ApiParam("套餐名称")
    @NotBlank(message = "套餐名称不能为空")
    private String name;

    @ApiParam(value = "折扣", required = false, defaultValue = "1.0")
    private Float discount;

    @ApiParam(value = "套餐额度", required = true)
    private BigDecimal amount;

    @ApiParam(value = "送积分额度", required = true)
    private Long points;
    public static RechargeCombo toRechargeCombo(RechargeComboDTO re) {
        return new RechargeCombo(null, re.getName(),
                re.getDiscount() == null ? 1.00f : re.getDiscount(),
                re.getAmount(),
                re.getPoints(),
                null,
                null);
    }
}