package com.example.back_jiwuquang_api.dto.orders;

import com.example.back_jiwuquang_api.pojo.orders.OrdersComment;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;


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

    @ApiModelProperty(value = "星级（0-5）,可半星", required = true)
    private BigDecimal rate;

    @ApiModelProperty(value = "图片集合(,分割)")
    private String images;

    @ApiModelProperty(value = "视频地址")
    private String video;

    @ApiModelProperty(value = "是否推荐该商品（0否 1是）")
    private Integer isRecommend;

    @ApiModelProperty(value = "是否匿名评论（0否 1是）")
    private Integer isAnonymous;

    public static OrdersComment toOrdersComment(InsertOrderCommentDTO dto, String userId) {
        return new OrdersComment()
                .setUserId(userId)
                .setContent(dto.getContent())
                .setRate(dto.getRate())
                .setImages(dto.getImages())
                .setVideo(dto.getVideo())
                .setIsRecommend(dto.getIsRecommend())
                .setIsAnonymous(dto.getIsAnonymous());
    }


}
