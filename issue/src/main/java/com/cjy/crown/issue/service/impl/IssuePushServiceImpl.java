package com.cjy.crown.issue.service.impl;

import com.cjy.crown.common.dto.Result;
import com.cjy.crown.common.dto.enums.UniversalStateEnum;
import com.cjy.crown.issue.bo.IssuePushModel;
import com.cjy.crown.issue.dto.CommentView;
import com.cjy.crown.issue.dto.IssueAdditionalData;
import com.cjy.crown.issue.dto.IssueView;
import com.cjy.crown.issue.entity.*;
import com.cjy.crown.issue.entity.es.IssueEs;
import com.cjy.crown.issue.enums.IssueConfigEnum;
import com.cjy.crown.issue.enums.IssueOperateEnum;
import com.cjy.crown.issue.repository.*;
import com.cjy.crown.issue.repository.es.IssueEsRepository;
import com.cjy.crown.issue.service.IssuePushService;
import com.cjy.crown.security.repository.UserRepository;
import com.cjy.crown.security.utils.WebUtil;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author ：JinYu
 * @date ：Created in 2022/4/11 18:17
 * @description：
 */
@Service
public class IssuePushServiceImpl implements IssuePushService {

    public static final String ISSUE_USER_SEEING = "issue_user:";

    @Autowired
    UserVisitIssueRepository userVisitIssueRepository;

    @Autowired
    IssueRelationRepository issueRelationRepository;

    @Autowired
    IssueRepository issueRepository;

    @Autowired
    RelationRepository relationRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    IssueConfigRepository issueConfigRepository;

    @Autowired
    IssueManagerRepository issueManagerRepository;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    IssueEsRepository issueEsRepository;


    /**
     * 根据token里面的用户ID去获取推送元信息，影响推送元信息的数据主要有浏览的议题所拥有的关键词和时间
     *
     * @return
     */
    @Override
    public Result getUserPushModel() {
        String userId = WebUtil.getUserId();
        IssuePushModel issuePushModel = new IssuePushModel();
        List<UserVisitIssue> userVisitIssues = userVisitIssueRepository.findByUserIdAndState(userId, UniversalStateEnum.INUSE.getCode(), PageRequest.of(0, 300));
        if (userVisitIssues == null || userVisitIssues.size() == 0) {
            issuePushModel.setPushModel(new HashMap<>());
            issuePushModel.getPushModel().put(IssuePushModel.DEFAULT_KEY, new IssuePushModel.Scope(0, 100, "默认"));
            issuePushModel.setSumRelations(100);
        } else {
            List<IssueRelation> issueRelations = new ArrayList<>();
            userVisitIssues.forEach((bean) -> {
                issueRelations.addAll(issueRelationRepository.findByIssueId(bean.getIssueId()));
            });
            Map<String, Integer> sumRelationMap = new HashMap<>();
            issueRelations.forEach((bean) -> {
                sumRelationMap.put(bean.getRelationId(), sumRelationMap.getOrDefault(bean.getRelationId(), 0) + 1);
            });
            issuePushModel.setPushModel(new HashMap<>());
            issuePushModel.setSumRelations(0);
            sumRelationMap.forEach((key, value) -> {
                issuePushModel.getPushModel().put(key, new IssuePushModel.Scope(issuePushModel.getSumRelations(), issuePushModel.getSumRelations() + value - 1));
                issuePushModel.setSumRelations(issuePushModel.getSumRelations() + value);
            });
            int defaultNum = (int) (issuePushModel.getSumRelations() * 0.3);
            if (defaultNum < 1) {
                defaultNum = 10;
            }
            issuePushModel.getPushModel().put(IssuePushModel.DEFAULT_KEY, new IssuePushModel.Scope(issuePushModel.getSumRelations(), issuePushModel.getSumRelations() + defaultNum - 1, "默认"));
            issuePushModel.setSumRelations(issuePushModel.getSumRelations() + defaultNum);
            issuePushModel.getPushModel().forEach((x, y) -> {
                if (x != IssuePushModel.DEFAULT_KEY) {
                    y.setName(relationRepository.findById(x).get().getName());
                }
            });
        }
        Result result = new Result();
        result.addData("issuePushModel", issuePushModel);
        return result;
    }

    /**
     * 根据推送元数据去获取推送的议题
     *
     * @param model
     * @return
     */
    @Override
    public Result pushByPushModel(IssuePushModel model) {
        int issueNumber = 10;
        Map<String, Integer> queryNumberMap = new HashMap<>();
        List<Issue> issues = new ArrayList<>();
        for (int i = 0; i < issueNumber; i++) {
            int randomNumber = RandomUtils.nextInt(0, model.getSumRelations());
            model.getPushModel().forEach((key, value) -> {
                if (randomNumber >= value.getStart() && randomNumber <= value.getEnd()) {
                    queryNumberMap.put(key, queryNumberMap.getOrDefault(key, 0) + 1);
                    return;
                }
            });
        }
        int defaultNum = queryNumberMap.getOrDefault(IssuePushModel.DEFAULT_KEY, 0);
        if (defaultNum != 0) {
            issues.addAll(issueRepository.findByIdNotIn(model.getExistIssueIdList(), PageRequest.of(0, defaultNum)));
        }
        issues.forEach(issue -> {
            model.getExistIssueIdList().add(issue.getId());
        });
        queryNumberMap.forEach((key, value) -> {
            Pageable pageable = PageRequest.of(0, value);
            List<String> issueIds = new ArrayList<>();
            issueRelationRepository.findByIssueIdNotInAndRelationId(model.getExistIssueIdList(), key, pageable).forEach(issueRelation -> {
                issueIds.add(issueRelation.getIssueId());
                issues.addAll(issueRepository.findAllById(issueIds));
                model.getExistIssueIdList().addAll(issueIds);
            });
        });
        issues.forEach(x -> {
            x.setMainText(x.getMainText().substring(0, x.getMainText().length() >= 80 ? 80 : x.getMainText().length()));
            x.setMarkdown("");
        });
        Result result = new Result();
        result.addData("pushModel", model);
        result.addData("issueList", issues);
        return result;
    }

    @Override
    public Result visitIssue(String issueId, IssuePushModel issuePushModel) {
        String userId = WebUtil.getUserId();
        Date now = new Date();
        Result result = new Result();
        recordVisit(issueId, userId, now);
        IssueAdditionalData issueAdditionalData = new IssueAdditionalData();
        issueAdditionalData.getRelationList().addAll(relationRepository.queryByIssueId(issueId));
        issueAdditionalData.setUsername(userRepository.findById(issueRepository.findById(issueId).get().getHolder()).get().getUsername());
        List<Comment> commentList = commentRepository.findByIssueIdAndState(issueId, UniversalStateEnum.INUSE.getCode());
        List<CommentView> commentViewList = new ArrayList<>();
        commentList.forEach(comment -> {
            CommentView commentView = new CommentView();
            BeanUtils.copyProperties(comment, commentView);
            commentView.setUsername(userRepository.findById(comment.getUserId()).get().getUsername());
            commentViewList.add(commentView);
        });
        result.addData("commentList", commentViewList);
        result.addData("issueAddData", issueAdditionalData);
        return result;
    }

    private void recordVisit(String issueId, String userId, Date now) {
        UserVisitIssue userVisitIssue = new UserVisitIssue();
        userVisitIssue.setIssueId(issueId);
        userVisitIssue.setCreateTime(now);
        userVisitIssue.setUserId(userId);
        userVisitIssue.setType(IssueOperateEnum.VISIT.getCode());
        userVisitIssue.setState(UniversalStateEnum.INUSE.getCode());
        userVisitIssueRepository.save(userVisitIssue);
    }

    @Override
    public Result getChildIssue(String id) {
        Result result = new Result();
        if (id == null) {
            return result;
        }
        result.addData("childIssueList", issueRepository.findByFatherId(id));
        return result;
    }

    @Override
    public Result getIssueById(String issueId) {
        Issue issue = issueRepository.findById(issueId).get();
        String userId = WebUtil.getUserId();
        Result result = new Result();
        Date now = new Date();
        recordVisit(issueId, userId, now);
        IssueView issueView = new IssueView(issue);
        issueView.setUsername(userRepository.findById(issueRepository.findById(issueId).get().getHolder()).get().getUsername());
        IssueConfig issueConfig = issueConfigRepository.findByIssueId(issueId);
        if (issueConfig == null) {
            issueConfig = initIssueConfig(issue);
        }
        issueView.setIssueConfig(issueConfig);
        issueView.setIfHolder(issueView.getHolder().equals(userId) ? true : false);
        issueView.getRelationList().addAll(relationRepository.queryByIssueId(issueId));
        setCanComment(issueId, issue, userId, issueView, issueConfig);
        setCanCreateChild(issueId, issue, userId, issueView, issueConfig);
        setCanDeleteComment(issueId, issue, userId, issueView, issueConfig);
        setCanUnlineIssue(issueId, issue, userId, issueView, issueConfig);
        redisTemplate.opsForValue().set(ISSUE_USER_SEEING + userId, issueView);
        issueView.setChildList(issueRepository.findByFatherId(issueId));
        if (!issueView.getHolder().equals(userId) && issueView.getState() == UniversalStateEnum.FORBIDDEN.getCode()) {
            issueView.setMainText("下线整改中，有疑问请联系持有人...");
            issueView.setMarkdown("# 下线整改中，有疑问请联系持有人...");
        }
        UserVisitIssue likeRecord = userVisitIssueRepository.findTopByIssueIdAndUserIdAndTypeInOrderByCreateTimeDesc(issueId, userId, IssueOperateEnum.LIKE.getCode(), IssueOperateEnum.NO_LIKE.getCode());
        if (likeRecord != null) {
            issueView.setIfLike(likeRecord.getType() == IssueOperateEnum.LIKE.getCode() ? true : false);
        } else {
            issueView.setIfLike(false);
        }
        UserVisitIssue starRecord = userVisitIssueRepository.findTopByIssueIdAndUserIdAndTypeInOrderByCreateTimeDesc(issueId, userId, IssueOperateEnum.STAR.getCode(), IssueOperateEnum.NO_STAR.getCode());
        if (starRecord != null) {
            issueView.setIfStar(starRecord.getType() == IssueOperateEnum.STAR.getCode() ? true : false);
        } else {
            issueView.setIfStar(false);
        }
        setComments(issueView);
        getSeeAndLikeAndStar(issueView);
        result.addData("issue", issueView);
        return result;
    }

    @Override
    public Result getUserPush(String id) {
        if (id == null || id.equals("")) {
            id = WebUtil.getUserId();
        }
        List<Issue> issueList = issueRepository.findByHolder(id);
        Result result = new Result();
        result.addData("issueList", issueList);
        return result;
    }

    @Override
    public Result getManageIssue(String id) {
        if (id == null || id.equals("")) {
            id = WebUtil.getUserId();
        }
        List<IssueManager> issueManagers = issueManagerRepository.findByManager(id);
        List<Issue> issues = issueRepository.findAllById(issueManagers.stream().collect(() -> new ArrayList<String>(), (list, x) -> list.add(x.getIssueId()), (list1, list2) -> list1.addAll(list2)));
        Result result = new Result();
        result.addData("issueList", issues);
        return result;
    }

    @Override
    public Result starIssue(String issueId) {
        String userId = WebUtil.getUserId();
        Date now = new Date();
        UserVisitIssue userVisitIssue = new UserVisitIssue();
        userVisitIssue.setState(UniversalStateEnum.INUSE.getCode());
        userVisitIssue.setUserId(userId);
        userVisitIssue.setIssueId(issueId);
        userVisitIssue.setCreateTime(now);
        userVisitIssue.setType(IssueOperateEnum.STAR.getCode());
        userVisitIssueRepository.save(userVisitIssue);
        return new Result();
    }

    @Override
    public Result unStarIssue(String issueId) {
        String userId = WebUtil.getUserId();
        Date now = new Date();
        UserVisitIssue userVisitIssue = new UserVisitIssue();
        userVisitIssue.setState(UniversalStateEnum.INUSE.getCode());
        userVisitIssue.setUserId(userId);
        userVisitIssue.setIssueId(issueId);
        userVisitIssue.setCreateTime(now);
        userVisitIssue.setType(IssueOperateEnum.NO_STAR.getCode());
        userVisitIssueRepository.save(userVisitIssue);
        return new Result();
    }

    @Override
    public Result likeIssue(String issueId) {
        String userId = WebUtil.getUserId();
        Date now = new Date();
        UserVisitIssue userVisitIssue = new UserVisitIssue();
        userVisitIssue.setState(UniversalStateEnum.INUSE.getCode());
        userVisitIssue.setUserId(userId);
        userVisitIssue.setIssueId(issueId);
        userVisitIssue.setCreateTime(now);
        userVisitIssue.setType(IssueOperateEnum.LIKE.getCode());
        userVisitIssueRepository.save(userVisitIssue);
        return new Result();
    }

    @Override
    public Result unLikeIssue(String issueId) {
        String userId = WebUtil.getUserId();
        Date now = new Date();
        UserVisitIssue userVisitIssue = new UserVisitIssue();
        userVisitIssue.setState(UniversalStateEnum.INUSE.getCode());
        userVisitIssue.setUserId(userId);
        userVisitIssue.setIssueId(issueId);
        userVisitIssue.setCreateTime(now);
        userVisitIssue.setType(IssueOperateEnum.NO_LIKE.getCode());
        userVisitIssueRepository.save(userVisitIssue);
        return new Result();
    }

    @Override
    public Result getStarIssue(String id) {
        if (id == null || id.equals("")) {
            id = WebUtil.getUserId();
        }
        List<Issue> issueList = issueRepository.customFindStarIssueByUserId(id);
        issueList.forEach(x -> {
            x.setMainText(x.getMainText().substring(0, x.getMainText().length() >= 80 ? 80 : x.getMainText().length()));
        });
        Result result = new Result();
        result.addData("issueList", issueList);
        return result;
    }

    /**
     * 根据输入的值和页码书去Elasticsearch查询议题数据，查询后将结果加工返回给客户端
     *
     * @param input
     * @param page
     * @return
     */
    @Override
    public Result searchIssue(String input, int page) {
        List<IssueEs> issueEsList = issueEsRepository.findByTitleContainingOrderByCreateTime(input, PageRequest.of(page, 10));
        issueEsList.forEach(x -> {
            x.setMainText(x.getMainText().substring(0, x.getMainText().length() >= 80 ? 80 : x.getMainText().length()));
        });
        Result result = new Result();
        result.addData("issueList", issueEsList);
        return result;
    }

    private IssueConfig initIssueConfig(Issue issue) {
        IssueConfig issueConfig;
        issueConfig = new IssueConfig();
        issueConfig.setIssueId(issue.getId());
        issueConfig.setState(UniversalStateEnum.INUSE.getCode());
        issueConfig.setCreater(issue.getCreater());
        issueConfig.setCreateTime(issue.getCreateTime());
        issueConfig.setManagerUnlineIssue(true);
        issueConfig.setManagerDeleteComment(true);
        issueConfig.setCommentLevel(IssueConfigEnum.COMMENT_LEVEL.ALL_CAN.getCode());
        issueConfig.setCreateChildLevel(IssueConfigEnum.CREATE_CHILD_LEVEL.ALL_CAN.getCode());
        issueConfigRepository.save(issueConfig);
        return issueConfig;
    }

    private void setComments(IssueView issueView) {
        List<Comment> commentList = commentRepository.findByIssueIdAndState(issueView.getId(), UniversalStateEnum.INUSE.getCode());
        List<CommentView> commentViewList = new ArrayList<>();
        commentList.forEach(comment -> {
            CommentView commentView = new CommentView();
            BeanUtils.copyProperties(comment, commentView);
            commentView.setUsername(userRepository.findById(comment.getUserId()).get().getUsername());
            commentViewList.add(commentView);
        });
        issueView.setCommentList(commentViewList);
    }

    private void getSeeAndLikeAndStar(IssueView issueView) {
        issueView.setSee(userVisitIssueRepository.countByIssueIdAndTypeAndState(issueView.getId(), IssueOperateEnum.VISIT.getCode(), UniversalStateEnum.INUSE.getCode()));
        issueView.setLike(userVisitIssueRepository.countByIssueIdAndTypeAndState(issueView.getId(), IssueOperateEnum.LIKE.getCode(), UniversalStateEnum.INUSE.getCode()));
        issueView.setStar(userVisitIssueRepository.countByIssueIdAndTypeAndState(issueView.getId(), IssueOperateEnum.STAR.getCode(), UniversalStateEnum.INUSE.getCode()));
    }

    private void setCanDeleteComment(String issueId, Issue issue, String userId, IssueView issueView, IssueConfig issueConfig) {
        if (issue.getHolder().equals(userId)) {
            issueView.setCanDeleteComment(true);
            return;
        }
        if (!issueConfig.isManagerDeleteComment()) {
            issueView.setCanDeleteComment(false);
            return;
        }
        IssueManager issueManager = issueManagerRepository.findByIssueIdAndManagerAndState(issueId, userId, UniversalStateEnum.INUSE.getCode());
        if (issueManager == null) {
            issueView.setCanDeleteComment(false);
            return;
        }
        issueView.setCanDeleteComment(true);
    }

    private void setCanUnlineIssue(String issueId, Issue issue, String userId, IssueView issueView, IssueConfig issueConfig) {
        if (issue.getHolder().equals(userId)) {
            issueView.setCanUnlineIssue(true);
            return;
        }
        if (!issueConfig.isManagerUnlineIssue()) {
            issueView.setCanUnlineIssue(false);
            return;
        }
        IssueManager issueManager = issueManagerRepository.findByIssueIdAndManagerAndState(issueId, userId, UniversalStateEnum.INUSE.getCode());
        if (issueManager == null) {
            issueView.setCanUnlineIssue(false);
            return;
        }
        issueView.setCanUnlineIssue(true);
    }

    private void setCanCreateChild(String issueId, Issue issue, String userId, IssueView issueView, IssueConfig issueConfig) {
        issueView.setCanCreateChild(false);
        if (issueConfig.getCreateChildLevel() == IssueConfigEnum.CREATE_CHILD_LEVEL.ALL_CAN.getCode()) {
            issueView.setCanCreateChild(true);
        } else if (issueConfig.getCommentLevel() == IssueConfigEnum.CREATE_CHILD_LEVEL.MANAGER_CAN.getCode()) {
            if (issue.getHolder() == userId) {
                issueView.setCanCreateChild(true);
            } else {
                IssueManager issueManager = issueManagerRepository.findByIssueIdAndManagerAndState(issueId, userId, UniversalStateEnum.INUSE.getCode());
                if (issueManager != null) {
                    issueView.setCanCreateChild(true);
                }
            }
        } else if (issue.getHolder() == userId) {
            issueView.setCanCreateChild(true);
        }
    }

    private void setCanComment(String issueId, Issue issue, String userId, IssueView issueView, IssueConfig issueConfig) {
        issueView.setCanComment(false);
        if (issueConfig.getCommentLevel() == IssueConfigEnum.COMMENT_LEVEL.ALL_CAN.getCode()) {
            issueView.setCanComment(true);
        } else if (issueConfig.getCommentLevel() == IssueConfigEnum.COMMENT_LEVEL.MANAGER_CAN.getCode()) {
            if (issue.getHolder() == userId) {
                issueView.setCanComment(true);
            } else {
                IssueManager issueManager = issueManagerRepository.findByIssueIdAndManagerAndState(issueId, userId, UniversalStateEnum.INUSE.getCode());
                if (issueManager != null) {
                    issueView.setCanComment(true);
                }
            }
        } else if (issue.getHolder() == userId) {
            issueView.setCanComment(true);
        }
    }
}
