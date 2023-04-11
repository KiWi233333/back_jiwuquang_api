package com.example.kiwi_community_mall_back.util;

import lombok.Getter;

/**
 * 描述
 *
 * @className: UserStatus
 * @author: Author作者
 * @description: TODO描述
 * @date: 2023/4/11 16:12
 */
@Getter
public enum UserStatus {
    DEFAULT("on", "正常"),
    OFF("off", "禁用");
    private final String name;
    private final String val;

    private UserStatus(String name, String val) {
        this.name = name;
        this.val = val;
    }
}
