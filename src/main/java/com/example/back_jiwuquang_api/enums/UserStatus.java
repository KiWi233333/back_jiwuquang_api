package com.example.back_jiwuquang_api.enums;

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
    ON(1, "正常"),
    OFF(0, "禁用");
    private final Integer key;
    private final String val;

    private UserStatus(Integer key, String val) {
        this.key = key;
        this.val = val;
    }

}
