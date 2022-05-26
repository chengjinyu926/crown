package com.cjy.crown.issue.service;

import com.cjy.crown.common.dto.Result;
import com.cjy.crown.issue.dto.AddIssueModel;
import com.cjy.crown.issue.dto.EditIssueMainTextModel;

/**
 * @author ：JinYu
 * @date ：Created in 2022/4/11 13:55
 * @description：
 */
public interface IssueContentService {
    Result addOneIssue(AddIssueModel model);

    Result editTitle(String id, String title);

    Result deleteComment(String commentId);

    Result editMainText(EditIssueMainTextModel model);

    Result editMarkdown(EditIssueMainTextModel model);
}
