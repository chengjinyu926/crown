package com.cjy.crown.message.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ：JinYu
 * @date ：Created in 2022/4/16 0:52
 * @description：
 */
@Getter
@AllArgsConstructor
public enum UserRelationOperateEnum {
    INVITE_BE_FRIEND(1, "申请成为好友");
    private Integer code;
    private String descrption;
}
