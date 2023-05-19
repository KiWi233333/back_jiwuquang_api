package com.example.back_jiwuquang_api.pojo.event;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

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
@TableName("event")
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Event {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    private String title;

    private String details;

    private String images;

    private Integer level;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;

    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

}
