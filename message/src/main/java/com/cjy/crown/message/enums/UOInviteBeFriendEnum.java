package com.cjy.crown.message.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ：JinYu
 * @date ：Created in 2022/4/16 1:07
 * @description：
 */
@Getter
@AllArgsConstructor
public enum UOInviteBeFriendEnum {
    NO_DEAL_BE_FRIEND(0, "暂未处理"),
    ACCEPT_BE_FRIEND(1, "接受"),
    REFUSED_BE_FRIEND(2, "拒绝"),
    ;
    private Integer code;
    private String descrption;
}
