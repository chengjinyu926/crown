package com.cjy.crown.message.controller;

import com.cjy.crown.common.dto.Result;
import com.cjy.crown.message.entity.ChatMessage;
import com.cjy.crown.message.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author ：JinYu
 * @date ：Created in 2022/4/17 17:27
 * @description：
 */
@CrossOrigin
@RestController
@RequestMapping("/message/friend")
public class FriendController {

    @Autowired
    FriendService friendService;

    @GetMapping("/acceptFriend")
    public Result acceptFriend(String usersRealtionId, Integer dealway, String messageId) {
        return friendService.acceptFriend(usersRealtionId, dealway, messageId);
    }

    @GetMapping("/getFriendList")
    public Result getFriendList() {
        return friendService.getFriendList();
    }

    @PostMapping("/sendChatMessage")
    public Result sendCahtMessage(@RequestBody ChatMessage message){
        return  friendService.sendChatMessage(message);
    }

    @GetMapping("/getChatMessage")
    public Result getChatMessage(String id){
        return friendService.getChatMessage(id);
    }
    @GetMapping("/getFriendIdAndUsernameByUserId")
    public Result getFriendIdAndUsernameByUserId(String userId){
        return friendService.getFriendIdAndUsernameByUserId(userId);
    }

    @GetMapping("/getOtherUesrInfo")
    public Result getOtherUesrInfo(String userId){
        return friendService.getOtherUesrInfo(userId);
    }
}
