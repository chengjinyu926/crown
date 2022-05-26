package com.cjy.crown.message.controller;

import com.cjy.crown.common.dto.Result;
import com.cjy.crown.message.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：JinYu
 * @date ：Created in 2022/4/15 19:13
 * @description：
 */
@CrossOrigin
@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    MessageService messageService;

    @GetMapping("/sendFriendInvite")
    public Result sendFriendInvite(String id) {
        return messageService.sendFriendInvite(id);
    }

    @GetMapping("/initMessage")
    public Result initMessage(){
        return messageService.initMessage();
    }
}
