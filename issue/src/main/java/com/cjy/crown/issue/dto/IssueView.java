package com.cjy.crown.issue.dto;

import com.cjy.crown.issue.entity.Issue;
import com.cjy.crown.issue.entity.IssueConfig;
import com.cjy.crown.issue.entity.Relation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author ：JinYu
 * @date ：Created in 2022/4/18 21:38
 * @description：
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IssueView {
    private String id;
    private String title;
    private String mainText;
    private String holder;
    private String rootId;
    private String fatherId;
    private Integer tier;
    private String creater;
    private String updater;
    private String markdown;
    private Date createTime;
    private Date updateTime;
    private Integer State;
    private String username;
    private List<Relation> relationList = new ArrayList<>();
    private IssueConfig issueConfig;
    private List<CommentView> commentList;
    private List<Issue> childList;
    private boolean canComment;
    private boolean canCreateChild;
    private boolean canUnlineIssue;
    private boolean canDeleteComment;
    private boolean ifHolder;
    private boolean ifStar;
    private boolean ifLike;
    private Integer like;
    private Integer star;
    private Integer see;
    public IssueView (Issue issue){
        BeanUtils.copyProperties(issue,this);
    }
}
