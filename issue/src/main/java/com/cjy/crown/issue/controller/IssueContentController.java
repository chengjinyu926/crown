package com.cjy.crown.issue.controller;

import com.cjy.crown.common.dto.Result;
import com.cjy.crown.issue.dto.AddIssueModel;
import com.cjy.crown.issue.dto.EditIssueMainTextModel;
import com.cjy.crown.issue.service.IssueContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author ：JinYu
 * @date ：Created in 2022/4/11 13:56
 * @description：
 */
@CrossOrigin
@RestController
@RequestMapping("/issue/content")
public class IssueContentController {

    @Autowired
    IssueContentService issueContentService;

    @PostMapping("/addOneIssue")
    public Result addOneIssue(@RequestBody AddIssueModel model) {
        return issueContentService.addOneIssue(model);
    }

    @GetMapping("/editTitle")
    public Result editTitle(String id, String title){
        return issueContentService.editTitle(id,title);
    }
    @GetMapping("/deleteComment")
    public Result deleteComment(String id){
        return issueContentService.deleteComment(id);
    }

    @PostMapping("/editMainText")
    public Result editMainText(@RequestBody EditIssueMainTextModel model){
        return issueContentService.editMainText(model);
    }

    @PostMapping("/editMarkdown")
    public Result editMarkdown(@RequestBody EditIssueMainTextModel model){
        return issueContentService.editMarkdown(model);
    }
}
