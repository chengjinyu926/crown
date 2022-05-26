package com.cjy.crown.message.service.impl;

import com.alibaba.fastjson.JSON;
import com.cjy.crown.common.dto.Result;
import com.cjy.crown.common.dto.enums.ResultEnum;
import com.cjy.crown.common.dto.enums.UniversalStateEnum;
import com.cjy.crown.message.bo.MessageQueueLoad;
import com.cjy.crown.message.dto.MessageView;
import com.cjy.crown.message.entity.Message;
import com.cjy.crown.message.entity.UsersRelation;
import com.cjy.crown.message.enums.MQTypeEnum;
import com.cjy.crown.message.enums.MessageStateEnum;
import com.cjy.crown.message.enums.UOInviteBeFriendEnum;
import com.cjy.crown.message.enums.UserRelationOperateEnum;
import com.cjy.crown.message.repository.MessageRepository;
import com.cjy.crown.message.repository.UserRelationRepository;
import com.cjy.crown.message.service.MessageService;
import com.cjy.crown.security.utils.WebUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author ：JinYu
 * @date ：Created in 2022/4/15 19:16
 * @description：
 */
@Service
public class MessageServiceImpl implements MessageService {

    public static final String INVITED_BE_FRIEND_ID = "invitedId";
    public static final String INVITER_BE_FRIEND_ID = "inviterId";
    public static final String USERS_RELATION_ID ="usersRelationId";

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    UserRelationRepository userRelationRepository;

    @Autowired
    MessageRepository messageRepository;

    /**
     * 发送进行好友申请的消息，在进行校验后，校验成功后会将申请信息推送给RabbitMq
     * @param invitedId
     * @return
     */
    @Override
    public Result sendFriendInvite(String invitedId) {
        //判断是不是已经成为了好友
        Result result = null;
        String inviter = WebUtil.getUserId();
        UsersRelation usersRelation = userRelationRepository.findFirstByOperaterAndBeOperaterAndTypeOrderByCreatetimeDesc(inviter, invitedId, UserRelationOperateEnum.INVITE_BE_FRIEND.getCode());
        if (usersRelation != null) {
            if (usersRelation.getDealway() == UOInviteBeFriendEnum.NO_DEAL_BE_FRIEND.getCode()) {
                return new Result(ResultEnum.NO_DEAL_INVITE);
            } else if (usersRelation.getDealway() == UOInviteBeFriendEnum.ACCEPT_BE_FRIEND.getCode()) {
                return new Result(ResultEnum.HAVE_BE_FRIEND);
            }
        }
        usersRelation = userRelationRepository.findFirstByOperaterAndBeOperaterAndTypeOrderByCreatetimeDesc(invitedId, inviter, UserRelationOperateEnum.INVITE_BE_FRIEND.getCode());
        if (usersRelation != null) {
            if (usersRelation.getDealway() == UOInviteBeFriendEnum.NO_DEAL_BE_FRIEND.getCode()) {
                // 立马成为好友
                usersRelation.setDealway(UOInviteBeFriendEnum.ACCEPT_BE_FRIEND.getCode());
            } else if (usersRelation.getDealway() == UOInviteBeFriendEnum.ACCEPT_BE_FRIEND.getCode()) {
                return new Result(ResultEnum.HAVE_BE_FRIEND);
            }
        }

        UsersRelation newUsersRelation = new UsersRelation();
        newUsersRelation.setOperater(inviter);
        newUsersRelation.setBeOperater(invitedId);
        newUsersRelation.setType(UserRelationOperateEnum.INVITE_BE_FRIEND.getCode());
        newUsersRelation.setDealway(UOInviteBeFriendEnum.NO_DEAL_BE_FRIEND.getCode());
        newUsersRelation.setCreatetime(new Date());
        newUsersRelation.setState(UniversalStateEnum.INUSE.getCode());
        newUsersRelation = userRelationRepository.save(newUsersRelation);
        MessageQueueLoad load = new MessageQueueLoad();
        load.addData(INVITED_BE_FRIEND_ID, invitedId);
        load.addData(INVITER_BE_FRIEND_ID, inviter);
        load.addData(USERS_RELATION_ID,newUsersRelation.getId());
        rabbitTemplate.convertAndSend(MQTypeEnum.INVITE_BE_FRIEND.getValue(), JSON.toJSONString(load));
        result = new Result(ResultEnum.SEND_INVITE_FRIEND_SUCCESS);
        return result;
    }

    @Override
    public Result initMessage() {
        String userId = WebUtil.getUserId();
        List<Message> messages = messageRepository.findByUserIdAndDealway(userId, MessageStateEnum.NO_DEAL.getCode());
        List<MessageView> messageViewList = new ArrayList<>();
        messages.forEach(message -> {
            MessageView messageView = new MessageView(message);
            messageViewList.add(messageView);
        });
        Result result = new Result();
        result.addData("messageList", messageViewList);
        return result;
    }
}
