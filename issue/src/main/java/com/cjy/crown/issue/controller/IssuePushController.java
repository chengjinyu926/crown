package com.cjy.crown.issue.controller;

import com.cjy.crown.common.dto.Result;
import com.cjy.crown.issue.bo.IssuePushModel;
import com.cjy.crown.issue.service.CommentService;
import com.cjy.crown.issue.service.IssuePushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ：JinYu
 * @date ：Created in 2022/4/11 18:07
 * @description：
 */
@RestController
@CrossOrigin
@RequestMapping("/issue/push")
public class IssuePushController {

    @Autowired
    IssuePushService issuePushService;

    @Autowired
    CommentService commentService;

    /**
     * 获取推送元信息模板
     * @return
     */
    @GetMapping("/getIssuePushModel")
    public Result getIssuePushModel() {
        return issuePushService.getUserPushModel();
    }

    /**
     * 根据推送元信息去获取议题
     * @param issuePushModel
     * @return
     */
    @PostMapping("/getIssueByPushModel")
    public Result getIssueByPushModel(@RequestBody(required = false) IssuePushModel issuePushModel) {
        return issuePushService.pushByPushModel(issuePushModel);
    }

    @PostMapping("/visitIssue/{id}")
    public Result visitIssue(@PathVariable String id, IssuePushModel pushModel) {
        return issuePushService.visitIssue(id, pushModel);
    }

    @GetMapping("/pubComment")
    public Result pubComment(String comment, String issueId) {
        return commentService.pubComment(comment, issueId);
    }

    @GetMapping("/getChildIssue")
    public Result getChildIssue(String id) {
        return issuePushService.getChildIssue(id);
    }

    @GetMapping("/getIssueById")
    public Result getIssueById(String id) {
        return issuePushService.getIssueById(id);
    }

    @GetMapping("/getUserPush")
    public Result getUserPush(@RequestParam(required = false) String id) {
        return issuePushService.getUserPush(id);
    }

    @GetMapping("/getManageIssue")
    public Result getManageIssue(@RequestParam(required = false) String id) {
        return issuePushService.getManageIssue(id);
    }

    @GetMapping("/searchIssue")
    public Result searchIssue(String input,int page){
        return issuePushService.searchIssue(input,page);
    }
    @GetMapping("/starIssue")
    public Result starIssue(String issueId) {
        return issuePushService.starIssue(issueId);
    }
    @GetMapping("/unStarIssue")
    public Result unStarIssue(String issueId) {
        return issuePushService.unStarIssue(issueId);
    }
    @GetMapping("/likeIssue")
    public Result likeIssue(String issueId) {
        return issuePushService.likeIssue(issueId);
    }
    @GetMapping("/unLikeIssue")
    public Result unLikeIssue(String issueId) {
        return issuePushService.unLikeIssue(issueId);
    }
    @GetMapping("/getStarIssue")
    public Result getStarIssue(@RequestParam(required = false) String id){
        return  issuePushService.getStarIssue(id);
    }
}
