package com.example.kiwi_community_mall_back.util;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum Gender {

    BOY(0, "男"),
    GIRL(1, "女"),
    DEFAULT(2, "保密");

    @EnumValue
    private final Integer key;
    private final String display;
    Gender(Integer key, String display) {
        this.key = key;
        this.display = display;
    }

}