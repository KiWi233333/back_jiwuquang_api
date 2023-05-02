package com.example.kiwi_community_mall_back.pojo.sys;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_user_salt")
public class UserSalt {

    @TableId(type = IdType.ASSIGN_ID)
    @TableField("user_id")
    private String userId;

    @TableField("salt")
    private String salt;
}