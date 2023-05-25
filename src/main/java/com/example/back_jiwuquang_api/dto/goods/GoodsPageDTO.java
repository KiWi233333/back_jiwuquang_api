package com.example.back_jiwuquang_api.dto.goods;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 分页参数
 *
 * @className: GoodsPageDTO
 * @author: Kiwi23333
 * @description: TODO描述
 * @date: 2023/5/1 22:55
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class GoodsPageDTO {


    @ApiModelProperty(value = "商品名称", required = false)
    String name;
    @ApiModelProperty(value = "分类id", required = false)
    String cid;
    @ApiModelProperty(value = "是否为新品", required = false)
    Integer isNew;
    @ApiModelProperty(value = "销量排序", notes = "0 asc, 1 desc", required = false)
    Integer saleSort;
    @ApiModelProperty(value = "价格排序", notes = "0 asc, 1 desc", required = false)
    Integer priceSort;
    @ApiModelProperty(value = "浏览量排序", notes = "0 asc, 1 desc", required = false)
    Integer viewsSort;
    @ApiModelProperty(value = "是否免邮", notes = "0 否, 1 是", required = false)
    Integer isPostage;
}
