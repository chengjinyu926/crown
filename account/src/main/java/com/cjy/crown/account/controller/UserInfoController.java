package com.cjy.crown.account.controller;

import com.cjy.crown.account.service.UserInfoService;
import com.cjy.crown.common.dto.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：JinYu
 * @date ：Created in 2022/4/15 20:36
 * @description：
 */
@CrossOrigin
@RestController
@RequestMapping("account/info")
public class UserInfoController {

    @Autowired
    UserInfoService userInfoService;

    @GetMapping("getUserInfo")
    public Result getUserInfo() {
        return userInfoService.getUserInfo();
    }
}
