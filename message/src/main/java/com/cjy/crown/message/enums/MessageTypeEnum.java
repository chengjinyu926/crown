package com.cjy.crown.message.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ：JinYu
 * @date ：Created in 2022/4/15 16:17
 * @description：
 */
@AllArgsConstructor
@Getter
public enum MessageTypeEnum {
    SYS_MESSAGE(0, "系统消息", false, null),
    INVITE_BE_FRIEND(1, "好友申请", true, new HashMap<Integer, String>() {
        {
            put(1, "接受");
            put(2, "拒绝");
        }
    }),
    ;


    private Integer code;
    private String description;
    private boolean input;
    private Map<Integer, String> options;

    public static MessageTypeEnum getByCode(Integer code) {
        switch (code) {
            case 0:
                return SYS_MESSAGE;
            case 1:
                return INVITE_BE_FRIEND;
        }
        return null;
    }
}
