package com.cjy.crown.issue.service;

import com.cjy.crown.common.dto.Result;

/**
 * @author ：JinYu
 * @date ：Created in 2022/4/13 23:40
 * @description：
 */
public interface CommentService {

    Result pubComment(String comment, String issueId);
}
