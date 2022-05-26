package com.cjy.crown.common.dto.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author ：JinYu
 * @date ：Created in 2022/4/11 15:12
 * @description：
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum UniversalStateEnum {
    FORBIDDEN(0, "FORBIDDEN"),
    INUSE(1, "INUSE"),
    ;
    private Integer code;
    private String name;
}
