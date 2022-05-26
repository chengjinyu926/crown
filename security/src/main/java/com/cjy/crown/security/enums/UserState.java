package com.cjy.crown.security.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ：JinYu
 * @date ：Created in 2022/4/9 16:12
 * @description：
 */
@AllArgsConstructor
@Getter
public enum UserState {
    FORBIDDEN(0, "FORBIDDEN"),
    OFFLINE(1, "OFFLINE"),
    ONLINE(2, "ONLINE");
    private Integer code;
    private String name;

}
