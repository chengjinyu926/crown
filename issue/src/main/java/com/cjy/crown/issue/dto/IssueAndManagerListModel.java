package com.cjy.crown.issue.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author ：JinYu
 * @date ：Created in 2022/4/22 1:43
 * @description：
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IssueAndManagerListModel {
    private String issueId;
    private List<String> managers;
}
