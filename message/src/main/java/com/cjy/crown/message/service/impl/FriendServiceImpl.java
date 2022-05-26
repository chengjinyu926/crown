package com.cjy.crown.message.service.impl;

import com.alibaba.fastjson.JSON;
import com.cjy.crown.common.dto.Result;
import com.cjy.crown.common.dto.enums.ResultEnum;
import com.cjy.crown.message.dto.FriendView;
import com.cjy.crown.message.dto.OtherUserView;
import com.cjy.crown.message.entity.ChatMessage;
import com.cjy.crown.message.entity.Message;
import com.cjy.crown.message.entity.UsersRelation;
import com.cjy.crown.message.enums.MessageStateEnum;
import com.cjy.crown.message.enums.UOInviteBeFriendEnum;
import com.cjy.crown.message.enums.UserRelationOperateEnum;
import com.cjy.crown.message.repository.ChatMessageRepository;
import com.cjy.crown.message.repository.MessageRepository;
import com.cjy.crown.message.repository.UserRelationRepository;
import com.cjy.crown.message.service.FriendService;
import com.cjy.crown.message.ws.ChatMessageEndPoint;
import com.cjy.crown.security.repository.UserRepository;
import com.cjy.crown.security.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

/**
 * @author ：JinYu
 * @date ：Created in 2022/4/17 17:30
 * @description：
 */
@Service
public class FriendServiceImpl implements FriendService {

    @Autowired
    UserRelationRepository userRelationRepository;

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ChatMessageRepository chatMessageRepository;

    /**
     * 处理好友申请
     * @param usersRelationId
     * @param dealway
     * @param messageId
     * @return
     */
    @Override
    public Result acceptFriend(String usersRelationId, Integer dealway, String messageId) {
        UsersRelation usersRelation = userRelationRepository.getOne(usersRelationId);
        Message message = messageRepository.getOne(messageId);
        Date now = new Date();
        usersRelation.setDealway(dealway);
        message.setDealway(dealway);
        usersRelation.setDealtime(now);
        message.setDealTime(now);
        message.setState(MessageStateEnum.HAVE_DEAL.getCode());
        messageRepository.save(message);
        userRelationRepository.save(usersRelation);
        return new Result(ResultEnum.MESSAGE_EXCUTE_SUCCESS);
    }

    @Override
    public Result getFriendList() {
        String userId = WebUtil.getUserId();
        List<FriendView> friendViewList = new ArrayList<>();
        userRelationRepository.findByBeOperaterAndDealway(userId, UOInviteBeFriendEnum.ACCEPT_BE_FRIEND.getCode()).forEach(obj -> {
            FriendView friendView = new FriendView();
            friendView.setId(obj.getOperater());
            friendView.setUsername(userRepository.getOne(friendView.getId()).getUsername());
            friendViewList.add(friendView);
        });
        userRelationRepository.findByOperaterAndDealway(userId, UOInviteBeFriendEnum.ACCEPT_BE_FRIEND.getCode()).forEach(obj -> {
            FriendView friendView = new FriendView();
            friendView.setId(obj.getBeOperater());
            friendView.setUsername(userRepository.getOne(friendView.getId()).getUsername());
            friendViewList.add(friendView);
        });
        Result result = new Result();
        result.addData("friendList", friendViewList);
        return result;
    }

    /**
     * 向好友发送消息，若好友在线，他的websocket会收到消息并进行处理
     * @param message
     * @return
     */
    @Override
    public Result sendChatMessage(ChatMessage message) {
        String userId = WebUtil.getUserId();
        message.setSender(userId);
        message.setCreateTime(new Date());
        message = chatMessageRepository.save(message);
        ChatMessageEndPoint chatMessageEndPoint = ChatMessageEndPoint.onlineUsers.get(message.getReceiver());
        if (chatMessageEndPoint != null) {
            try {
                chatMessageEndPoint.getSession().getBasicRemote().sendText(JSON.toJSONString(message));
            } catch (Exception e) {
                Result result = new Result();
                result.addData("chat", message);
                return result;
            }
        }
        Result result = new Result();
        result.addData("chat", message);
        return result;
    }

    @Override
    public Result getChatMessage(String id) {
        String userId = WebUtil.getUserId();
        List<String> idList = new ArrayList<>();
        idList.add(id);
        idList.add(userId);
        List<ChatMessage> chatMessages = chatMessageRepository.findBySenderInAndReceiverInOrderByCreateTimeDesc(idList, idList);
        Result result = new Result();
        result.addData("chatMessageList", chatMessages);
        return result;
    }

    @Override
    public Result getFriendIdAndUsernameByUserId(String userId) {
        Result result = new Result();
        Set<String> friendIds = new HashSet<>();
        userRelationRepository.findByOperaterAndDealwayAndType(userId, UOInviteBeFriendEnum.ACCEPT_BE_FRIEND.getCode(), UserRelationOperateEnum.INVITE_BE_FRIEND.getCode()).forEach(x -> {
            friendIds.add(x.getBeOperater());
        });
        userRelationRepository.findByBeOperaterAndDealwayAndType(userId, UOInviteBeFriendEnum.ACCEPT_BE_FRIEND.getCode(), UserRelationOperateEnum.INVITE_BE_FRIEND.getCode()).forEach(x -> {
            friendIds.add(x.getOperater());
        });
        if (friendIds.size() == 0) {
            return result;
        }
        Map<String, String> friendsIdAndName = new HashMap<>();
        userRepository.findAllById(friendIds).forEach(x -> {
            friendsIdAndName.put(x.getId(), x.getUsername());
        });
        result.addData("friendsIdAndName", friendsIdAndName);
        return result;
    }

    @Override
    public Result getOtherUesrInfo(String otherId) {
        String userId = WebUtil.getUserId();
        OtherUserView otherUserView = new OtherUserView(userRepository.findById(otherId).get());
        UsersRelation usersRelation1 = userRelationRepository.findTopByOperaterAndBeOperaterAndTypeOrderByCreatetimeDesc(userId, otherId, UserRelationOperateEnum.INVITE_BE_FRIEND.getCode());
        otherUserView.setIfFriend(false);
        if (usersRelation1 != null) {
            if (usersRelation1.getDealway()==UOInviteBeFriendEnum.ACCEPT_BE_FRIEND.getCode()){
                otherUserView.setIfFriend(true);
            }
        } else {
            UsersRelation usersRelation2 = userRelationRepository.findTopByOperaterAndBeOperaterAndTypeOrderByCreatetimeDesc(otherId, userId, UserRelationOperateEnum.INVITE_BE_FRIEND.getCode());
            if (usersRelation2.getDealway()==UOInviteBeFriendEnum.ACCEPT_BE_FRIEND.getCode()){
                otherUserView.setIfFriend(true);
            }
        }
        Result result = new Result();
        result.addData("userData",otherUserView);
        return result;
    }
}
