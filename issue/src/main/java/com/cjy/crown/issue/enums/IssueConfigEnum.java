package com.cjy.crown.issue.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ：JinYu
 * @date ：Created in 2022/4/19 10:54
 * @description：
 */
@AllArgsConstructor
@Getter
public class IssueConfigEnum {

    @AllArgsConstructor
    @Getter
   public  enum COMMENT_LEVEL {
        ALL_CAN(0, "所有人可以"),
        MANAGER_CAN(1, "管理员及以上级别可以评论"),
        HOLDER_CAN(2, "持有者可以评论"),
        ;
        private Integer code;
        private String description;
    }


    @AllArgsConstructor
    @Getter
   public  enum CREATE_CHILD_LEVEL {
        ALL_CAN(0, "所有人可以"),
        MANAGER_CAN(1, "管理员及以上级别可以创建"),
        HOLDER_CAN(2, "持有者可以创建"),
        ;
        private Integer code;
        private String description;
    }
}
