package com.example.back_jiwuquang_api.dto.event;

import com.example.back_jiwuquang_api.pojo.event.Event;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * 活动实体类
 *
 * @className: Event
 * @author: Kiwi23333
 * @description: 活动实体类
 * @date: 2023/5/17 16:29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class EventDTO {

    @ApiModelProperty(value = "活动名称", required = true)
    @NotBlank(message = "活动名称不能为空")
    private String title;

    @ApiModelProperty(value = "详情md")
    private String details;

    @ApiModelProperty(value = "活动图片集合")
    private String images;

    @ApiModelProperty(value = "权重")
    @Range(min = 0, max = 9, message = "权重值为0-")
    private Integer level;

    @ApiModelProperty(value = "开始时间", required = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @NotNull(message = "开始时间不能为空")
    private Date startTime;

    @ApiModelProperty(value = "结束时间", required = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @NotNull(message = "结束时间不能为空")
    private Date endTime;

    @ApiModelProperty(value = "活动状态", notes = "0还未开始 1正在进行 2已结束")
    @Range(min = 0, max = 1, message = "权重值为0-2")
    private Integer status;

    // 对象转换
    public static Event toEventGoods(EventDTO p) {
        return new Event()
                .setTitle(p.getTitle())
                .setStartTime(p.getStartTime())
                .setEndTime(p.getEndTime())
                .setDetails(p.getDetails())
                .setImages(p.getImages())
                .setStatus(p.getStatus())
                .setLevel(p.getLevel());
    }

    // 校验日期
    public static boolean startEndTimeValid(EventDTO eventDTO) {
        return eventDTO.getStartTime().getTime() - eventDTO.getEndTime().getTime() > 1000 * 30;
    }
}