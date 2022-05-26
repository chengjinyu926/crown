package com.cjy.crown.issue.service.impl;

import com.cjy.crown.common.dto.Result;
import com.cjy.crown.common.dto.enums.ResultEnum;
import com.cjy.crown.common.dto.enums.UniversalStateEnum;
import com.cjy.crown.issue.dto.AddIssueModel;
import com.cjy.crown.issue.dto.EditIssueMainTextModel;
import com.cjy.crown.issue.dto.IssueView;
import com.cjy.crown.issue.entity.*;
import com.cjy.crown.issue.entity.es.IssueEs;
import com.cjy.crown.issue.repository.*;
import com.cjy.crown.issue.repository.es.IssueEsRepository;
import com.cjy.crown.issue.service.IssueContentService;
import com.cjy.crown.security.utils.WebUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author ：JinYu
 * @date ：Created in 2022/4/11 13:56
 * @description：
 */
@Service
public class IssueContentServiceImpl implements IssueContentService {

    @Autowired
    IssueRepository issueRepository;

    @Autowired
    RelationRepository relationRepository;

    @Autowired
    IssueRelationRepository issueRelationRepository;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    CommentRepository commentRepository;


    @Autowired
    IssueEsRepository issueEsRepository;

    /**
     * 新增一个议题，将议题存储值至Mysql和Elasticsearch
     * @param model
     * @return
     */
    @Override
    public Result addOneIssue(AddIssueModel model) {
//        for (int i = 0; i < 500; i++) {
//            List<String> contens = new ArrayList<>();
//            for (int j = 0; j < 3; j++) {
//                contens.add(RandomStringUtils.random(1, "ABCDEFGHIJKLMNOPQRST"));
//            }
//            model.setTitle(contens.get(0) + contens.get(1) + contens.get(2));
//            model.setMainText("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
//            model.setRelation(contens);
//        }
        Date now = new Date();
        String userId = WebUtil.getUserId();
        Issue issue = addIssue2DB(model, now);
        addIssueRelation(model, userId, now, issue.getId());
        Result result = new Result(ResultEnum.ADD_ONE_ISSUE_SUCCESS);
        result.addData("issue", issue);
        return result;
    }

    @Override
    public Result editTitle(String id, String title) {
        Result result = new Result();
        String userId = WebUtil.getUserId();
        IssueView issueView = (IssueView) redisTemplate.opsForValue().get(IssuePushServiceImpl.ISSUE_USER_SEEING + userId);
        if (issueView != null && issueView.getHolder().equals(userId)) {
            Issue issue = issueRepository.findById(id).get();
            issue.setTitle(title);
            issueRepository.save(issue);
            IssueEs issueEs = new IssueEs();
            BeanUtils.copyProperties(issue, issueEs);
            issueEsRepository.save(issueEs);
        }
        return result;
    }

    /**
     * 删除一条评论
     * @param commentId
     * @return
     */
    @Override
    public Result deleteComment(String commentId) {
        String userId = WebUtil.getUserId();
        Date now = new Date();
        Comment comment = commentRepository.findById(commentId).get();
        comment.setState(UniversalStateEnum.FORBIDDEN.getCode());
        commentRepository.save(comment);
        return new Result();
    }

    @Override
    public Result editMainText(EditIssueMainTextModel model) {
        String userId = WebUtil.getUserId();
        Issue issue = issueRepository.findById(model.getIssueId()).get();
        if (!issue.getHolder().equals(userId)) {
            return new Result();
        }
        issue.setMainText(model.getMainText());
        issue.setUpdateTime(new Date());
        issue.setUpdater(userId);
        issueRepository.save(issue);
        IssueEs issueEs = new IssueEs();
        BeanUtils.copyProperties(issue, issueEs);
        issueEsRepository.save(issueEs);
        return new Result();
    }

    @Override
    public Result editMarkdown(EditIssueMainTextModel model) {
        String userId = WebUtil.getUserId();
        Issue issue = issueRepository.findById(model.getIssueId()).get();
        if (!issue.getHolder().equals(userId)) {
            return new Result();
        }
        issue.setMarkdown(model.getMarkdown());
        issue.setUpdateTime(new Date());
        issue.setUpdater(userId);
        issueRepository.save(issue);
        IssueEs issueEs = new IssueEs();
        BeanUtils.copyProperties(issue, issueEs);
        issueEsRepository.save(issueEs);
        return new Result();
    }


    /**
     * 将数据议题存储在Mysql和ElasticSearch
     * @param model
     * @param userId
     * @param now
     * @param issueId
     */
    private void addIssueRelation(AddIssueModel model, String userId, Date now, String issueId) {
        for (String relationName : model.getRelation()) {
            Relation relation = relationRepository.findByName(relationName);
            if (relation == null) {
                relation = new Relation();
                relation.setName(relationName);
                relation.setCreater(userId);
                relation.setTemperature(0);
                relation.setCreateTime(now);
                relation.setState(UniversalStateEnum.INUSE.getCode());
                relation = relationRepository.save(relation);
            }
            IssueRelation issueRelation = new IssueRelation();
            issueRelation.setIssueId(issueId);
            issueRelation.setRelationId(relation.getId());
            issueRelation.setState(UniversalStateEnum.INUSE.getCode());
            issueRelation.setCreater(userId);
            issueRelation.setCreaterTime(now);
            issueRelationRepository.save(issueRelation);
        }
    }

    private Issue addIssue2DB(AddIssueModel model, Date now) {
        Issue issue = new Issue();
        BeanUtils.copyProperties(model, issue);
        String userId = WebUtil.getUserId();
        issue.setCreater(userId);
        issue.setHolder(userId);
        issue.setTier(0);
        issue.setState(UniversalStateEnum.INUSE.getCode());
        issue.setCreateTime(now);
        issueRepository.save(issue);
        IssueEs issueEs = new IssueEs();
        BeanUtils.copyProperties(issue, issueEs);
        issueEsRepository.save(issueEs);
        return issue;
    }
}
