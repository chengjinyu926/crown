package com.cjy.crown.issue.controller;

import com.cjy.crown.common.dto.Result;
import com.cjy.crown.issue.dto.IssueAndManagerListModel;
import com.cjy.crown.issue.entity.IssueConfig;
import com.cjy.crown.issue.service.IssueConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author ：JinYu
 * @date ：Created in 2022/4/20 14:49
 * @description：
 */
@RestController
@CrossOrigin
@RequestMapping("/issue/config")
public class IssueConfigController {

    @Autowired
    IssueConfigService issueConfigService;

    @PostMapping("/update")
    public Result update(@RequestBody IssueConfig issueConfig){
        return issueConfigService.update(issueConfig);
    }
    @GetMapping("/getManagerAndFriend")
    public Result getManagerAndFriend(String issueId){
        return issueConfigService.getManagerAndFriend(issueId);
    }
    @PostMapping("/setManagers")
    public Result setManagers(@RequestBody IssueAndManagerListModel model){
        return issueConfigService.setManagers(model.getIssueId(), model.getManagers());
    }
    @GetMapping("/setState")
    public Result setIssueState(String id,int state){
        return issueConfigService.setIssueState(id,state);
    }
}
