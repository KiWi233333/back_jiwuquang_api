package com.example.kiwi_community_mall_back.pojo.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@TableName("user_role")
public class UserRole {


    @TableId(type = IdType.ASSIGN_ID)
    String id;

    String userId;

    String roleId;
}
