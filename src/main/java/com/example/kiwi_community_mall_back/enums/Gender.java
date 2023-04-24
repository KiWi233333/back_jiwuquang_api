package com.example.kiwi_community_mall_back.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum Gender {
    男,女,保密;
//    DEFAULT(0, "保密"),
//    BOY(1, "男"),
//    GIRL(2, "女");
//
//    private final Integer key;
//    @EnumValue // 将注解所标识的属性的值存储到数据库中
//    private final String value;
//    Gender(Integer key, String value) {
//        this.key = key;
//        this.value = value;
//    }

}