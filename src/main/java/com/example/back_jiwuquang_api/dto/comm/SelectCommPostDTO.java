package com.example.back_jiwuquang_api.dto.comm;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 查询帖子dto
 * @className: SelectCommPostDTO
 * @author: Kiwi23333
 * @description:
 * @date: 2023/6/9 2:23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class SelectCommPostDTO {

    @ApiModelProperty(value = "帖子关键字", required = false)
    String name;
    @ApiModelProperty(value = "是否精华", required = false)
    Integer isEssence;
    @ApiModelProperty(value = "是否最新", required = false)
    Integer isNew;
    @ApiModelProperty(value = "浏览量排序", notes = "0 asc, 1 desc", required = false)
    Integer viewsSort;
    @ApiModelProperty(value = "评论数排序", notes = "0 asc, 1 desc", required = false)
    Integer commentSort;
}
