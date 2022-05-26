package com.cjy.crown.issue.service;

import com.cjy.crown.common.dto.Result;
import com.cjy.crown.issue.entity.IssueConfig;

import java.util.List;

/**
 * @author ：JinYu
 * @date ：Created in 2022/4/20 14:51
 * @description：
 */
public interface IssueConfigService {
    Result update(IssueConfig issueConfig);
    Result getManagerAndFriend(String issueId);
    Result setManagers(String issueId, List<String> managers);
    Result setIssueState(String id, int state);
}
