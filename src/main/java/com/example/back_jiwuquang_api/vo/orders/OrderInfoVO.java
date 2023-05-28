package com.example.back_jiwuquang_api.vo.orders;

import com.baomidou.mybatisplus.annotation.TableField;
import com.example.back_jiwuquang_api.pojo.sys.UserAddress;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 订单列表VO
 *
 * @className: OrderInfoVO
 * @author: Kiwi23333
 * @description: 订单列表VO
 * @date: 2023/5/17 16:51
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@FieldNameConstants
public class OrderInfoVO {

    @ApiModelProperty("订单id")
    private String id;

    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty("地址信息")
    private UserAddress userAddress;

    @ApiModelProperty(value = "状态", notes = "0:待付款,1:已付款,2:已发货,3:待收货,4:已收货,5:已评价,6:已取消,7:已超时取消,8:发起退款,9:退款成功并取消")
    private Integer status;

    @ApiModelProperty("备注")
    private String remark;

    private BigDecimal spendPrice;

    private BigDecimal totalPrice;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date paidTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    @TableField(exist = false)
    private List<OrderItemVO> ordersItems;

}