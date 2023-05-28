package com.example.back_jiwuquang_api.vo.user;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

/**
 * 退款退还VO
 *
 * @className: RefundWalletVO
 * @author: Kiwi23333
 * @description: 退款退还VO
 * @date: 2023/5/27 15:08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class RefundWalletVO {
    private BigDecimal price;
    private BigDecimal points;
}
