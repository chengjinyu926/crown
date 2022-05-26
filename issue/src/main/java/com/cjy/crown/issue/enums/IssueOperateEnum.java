package com.cjy.crown.issue.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author ：JinYu
 * @date ：Created in 2022/4/13 17:04
 * @description：
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum IssueOperateEnum {
    VISIT(1, "访问"),
    LIKE(2, "点赞"),
    NO_LIKE(3, "取消点赞"),
    STAR(4, "收藏"),
    NO_STAR(5, "取消收藏"),
    ;
    private Integer code;
    private String description;
}
