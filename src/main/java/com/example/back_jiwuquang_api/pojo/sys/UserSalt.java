package com.example.back_jiwuquang_api.pojo.sys;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_user_salt")
public class UserSalt {

    @TableId(value = "user_id", type = IdType.ASSIGN_ID)
    private String userId;

    @TableField("salt")
    private String salt;
}