package com.example.back_jiwuquang_api.dto.goods;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * 商品分类VO
 *
 * @className: GoodsCategoryVO
 * @author: Kiwi23333
 * @description: 商品分类VO
 * @date: 2023/5/1 17:08
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class GoodsCategoryDTO {

    @ApiModelProperty(value = "分类名称",required = true)
    @NotBlank(message = "分类名称不能为空！")
    String name;

    @ApiModelProperty(value = "分类 父id",required = false)
    String parentId;

    @ApiModelProperty(value = "分类图标",required = false)
    String icon;

    @ApiModelProperty(value = "权重",notes = "值越大权重越低,0默认",required = false)
    private Integer sortOrder;

    @ApiModelProperty(value = "是否展示",notes="默认0不展示",required = false)
    private Integer isShow;

}
