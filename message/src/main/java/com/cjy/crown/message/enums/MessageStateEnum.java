package com.cjy.crown.message.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ：JinYu
 * @date ：Created in 2022/4/16 19:49
 * @description：
 */
@AllArgsConstructor
@Getter
public enum MessageStateEnum {
    NO_DEAL(0, "未处理"),
    HAVE_DEAL(1, "已处理"),
    ;
    private Integer code;
    private String descrption;
}
