package com.cjy.crown.message.service;

import com.cjy.crown.common.dto.Result;
import com.cjy.crown.message.entity.ChatMessage;

/**
 * @author ：JinYu
 * @date ：Created in 2022/4/17 17:29
 * @description：
 */
public interface FriendService {
    Result acceptFriend(String usersRelationId, Integer dealway, String messageId);
    Result getFriendList();
    Result sendChatMessage(ChatMessage message);
    Result getChatMessage(String id);
    Result getFriendIdAndUsernameByUserId(String userId);
    Result getOtherUesrInfo(String userId);
}
