package com.example.back_jiwuquang_api.dto.orders;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;


/**
 * 添加订单评论 DTO
 *
 * @className: SelectOrderDTO
 * @author: Kiwi23333
 * @description: 添加订单评论 DTO
 * @date: 2023/5/22 19:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class InsertOrderCommentDTO {

    @ApiModelProperty(value = "订单单项id", required = true)
    @NotBlank(message = "订单单项不能为空！")
    String orderItemId;


    @ApiModelProperty(value = "内容", required = true)
    private String content;
    
    @ApiModelProperty(value = "内容")
    private String images;

    @ApiModelProperty(value = "星级（0-5）", required = true)
    private Integer rate;

    @ApiModelProperty(value = "是否匿名评论")
    private Integer isAnonymous;

    @ApiModelProperty(value = "是否推荐该商品")
    private Integer isRecommend;


}
