package com.example.back_jiwuquang_api.dto.event;

import com.baomidou.mybatisplus.annotation.*;
import com.example.back_jiwuquang_api.pojo.event.EventGoods;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 活动商品关联表实体类
 *
 * @className: Event
 * @author: Kiwi23333
 * @description: 活动商品关联表实体类
 * @date: 2023/5/17 16:29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class EventGoodsDTO {

    @ApiModelProperty(value = "关联id", required = true)
    @NotBlank(message = "id不能为空")
    private String id;

    @ApiModelProperty(value = "活动id", required = true)
    @NotBlank(message = "活动id不能为空")
    
    private String eventId;
    @ApiModelProperty(value = "商品id", required = true)
    @NotBlank(message = "商品id不能为空")
    private String goodsId;

    @ApiModelProperty(value = "活动价格", required = true)
    @NotNull(message = "活动价格不能为空")
    @Range(min = 0,message = "活动价格不能为负数")
    private BigDecimal eventPrice;



    // 对象转换
    public static EventGoods toEventGoods(EventGoodsDTO p) {
        return new EventGoods()
                .setEventId(p.getId())
                .setGoodsId(p.getGoodsId())
                .setEventPrice(p.getEventPrice());
    }

}