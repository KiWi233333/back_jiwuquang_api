package com.example.back_jiwuquang_api.dto.comm;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 筛选帖子评论dto
 * @className: SelectCommPostCommentDTO
 * @author: Kiwi23333
 * @description:
 * @date: 2023/6/9 2:23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class SelectCommPostCommentDTO {

    @ApiModelProperty(value = "是否最新", required = false)
    Integer isNew;
    @ApiModelProperty(value = "是否最热", required = false)
    Integer isHost;
}
