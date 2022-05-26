package com.cjy.crown.issue.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ：JinYu
 * @date ：Created in 2022/4/23 2:21
 * @description：
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditIssueMainTextModel {
    private  String issueId;
    private  String mainText;
    private String markdown;
}
