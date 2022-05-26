package com.cjy.crown.issue.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author ：JinYu
 * @date ：Created in 2022/4/11 18:08
 * @description：
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IssuePushModel {

    public static String DEFAULT_KEY = "default";

    //关键词数量
    private Integer sumRelations;
    //关键词-关键词的比例
    private Map<String, Scope> pushModel;
    //已经推送过的议题ID
    private List<String> existIssueIdList = new ArrayList<String>() {
        {
            add("");
        }
    };

    public IssuePushModel(Integer sumRelations, Map<String, Scope> pushModel) {
        this.sumRelations = sumRelations;
        this.pushModel = pushModel;
    }


    /**
     * 内部类 Scope 用于关键词比例的开始和结束
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Scope {
        private Integer start;
        private Integer end;
        private String name;

        public Scope(Integer start, Integer end) {
            this.start = start;
            this.end = end;
        }
    }
}
