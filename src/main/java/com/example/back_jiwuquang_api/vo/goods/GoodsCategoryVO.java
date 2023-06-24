package com.example.back_jiwuquang_api.vo.goods;

import com.example.back_jiwuquang_api.pojo.goods.GoodsCategory;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Objects;

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
public class GoodsCategoryVO {

    @ApiModelProperty(value = "分类id")
    String id;
    @ApiModelProperty(value = "分类名称")
    String name;

    @ApiModelProperty(value = "分类 父id")
    String parentId;

    @ApiModelProperty(value = "分类图标")
    String icon;

    @ApiModelProperty(value = "子树")
    List<GoodsCategory> children;

    public static List<GoodsCategory> buildTree(List<GoodsCategory> list, String parentId) {
        // 下一级目录
        for (GoodsCategory category : list) {
            // id 相等
            if (Objects.equals(category.getParentId(), parentId)) {
                List<GoodsCategory> children = buildTree(list, category.getId());
                category.setChildren(children);
            }
        }
        return list;
    }

    public GoodsCategoryVO toGoodsCategoryVO(GoodsCategory goodsCategory) {
        return new GoodsCategoryVO()
                .setId(goodsCategory.getId())
                .setName(goodsCategory.getName())
                .setParentId(goodsCategory.getParentId())
                .setIcon(goodsCategory.getIcon())
                .setChildren(goodsCategory.getChildren());
    }


}
