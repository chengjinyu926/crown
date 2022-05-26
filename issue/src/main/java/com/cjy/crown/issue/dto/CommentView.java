package com.cjy.crown.issue.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author ：JinYu
 * @date ：Created in 2022/4/13 23:56
 * @description：
 */
@Data
public class CommentView {
    private String id;
    private String content;
    private String username;
    private String issueId;
    private String userId;
    private Date createTime;
}
