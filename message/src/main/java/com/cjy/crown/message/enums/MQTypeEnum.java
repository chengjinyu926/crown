package com.cjy.crown.message.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ：JinYu
 * @date ：Created in 2022/4/15 19:21
 * @description：
 */
@AllArgsConstructor
@Getter
public enum MQTypeEnum {
    INVITE_BE_FRIEND("inviteBeFriend")
    ;
    private String value;
}
