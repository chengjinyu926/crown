package com.cjy.crown.issue.service;

import com.cjy.crown.common.dto.Result;
import com.cjy.crown.issue.bo.IssuePushModel;

/**
 * @author ：JinYu
 * @date ：Created in 2022/4/11 18:17
 * @description：
 */
public interface IssuePushService {
    Result getUserPushModel();
    Result pushByPushModel(IssuePushModel model);
    Result visitIssue(String issueId,IssuePushModel issuePushModel);
    Result getChildIssue(String id);
    Result getIssueById(String id);
    Result getUserPush(String id);
    Result getManageIssue(String id);
    Result starIssue(String issueId);
    Result unStarIssue(String issueId);
    Result likeIssue(String issueId);
    Result unLikeIssue(String issueId);
    Result getStarIssue(String id);
    Result searchIssue(String input,int page);
}
