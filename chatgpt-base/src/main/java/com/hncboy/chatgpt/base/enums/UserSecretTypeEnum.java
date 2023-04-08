package com.hncboy.chatgpt.base.enums;


import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author hncboy
 * @date 2023/3/22 22:19
 * API 类型枚举
 */
@AllArgsConstructor
public enum UserSecretTypeEnum {


    TIMES(1, "次数"),

    /**
     * ACCESS_TOKEN
     */
    WORDS(2, "字数"),
    MONTH(3, "月卡");




    @Getter
    @EnumValue
    private final int name;

    @Getter
    @JsonValue
    private final String message;
}
