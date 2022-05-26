package com.cjy.crown.issue.service.impl;

import com.cjy.crown.common.dto.Result;
import com.cjy.crown.common.dto.enums.UniversalStateEnum;
import com.cjy.crown.issue.dto.CommentView;
import com.cjy.crown.issue.entity.Comment;
import com.cjy.crown.issue.repository.CommentRepository;
import com.cjy.crown.issue.service.CommentService;
import com.cjy.crown.security.repository.UserRepository;
import com.cjy.crown.security.utils.WebUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author ：JinYu
 * @date ：Created in 2022/4/13 23:40
 * @description：
 */
@Service
public class CommentServiceImpl implements CommentService {


    @Autowired
    CommentRepository commentRepository;


    @Autowired
    UserRepository userRepository;

    /**
     * 发布一条新的评论
     * @param commentText
     * @param issueId
     * @return
     */
    @Override
    public Result pubComment(String commentText, String issueId) {
        String userId = WebUtil.getUserId();
        Date now = new Date();
        Comment comment = new Comment();
        comment.setContent(commentText);
        comment.setUserId(userId);
        comment.setIssueId(issueId);
        comment.setCreateTime(now);
        comment.setState(UniversalStateEnum.INUSE.getCode());
        commentRepository.save(comment);
        CommentView commentView = new CommentView();
        BeanUtils.copyProperties(comment, commentView);
        commentView.setUsername(userRepository.findById(comment.getUserId()).get().getUsername());
        Result result = new Result();
        result.addData("comment", commentView);
        return result;
    }
}
