package com.cjy.crown.issue.dto;

import lombok.Data;

import java.util.List;

/**
 * @author ：JinYu
 * @date ：Created in 2022/4/11 14:00
 * @description：
 */
@Data
public class AddIssueModel {
    //父议题ID
    private String fatherId;
    //根议题ID
    private String rootId;
    //标题
    private String title;
    //正文
    private String mainText;
    //关键字
    private List<String> relation;

    private String markdown = "";
}
