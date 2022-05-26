package com.cjy.crown.message.consumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cjy.crown.message.bo.MessageQueueLoad;
import com.cjy.crown.message.dto.MessageView;
import com.cjy.crown.message.entity.Message;
import com.cjy.crown.message.enums.MQTypeEnum;
import com.cjy.crown.message.enums.MessageStateEnum;
import com.cjy.crown.message.enums.MessageTypeEnum;
import com.cjy.crown.message.enums.UOInviteBeFriendEnum;
import com.cjy.crown.message.repository.MessageRepository;
import com.cjy.crown.message.service.impl.MessageServiceImpl;
import com.cjy.crown.message.ws.MessageEndPoint;
import com.cjy.crown.security.repository.UserRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ：JinYu
 * @date ：Created in 2022/4/15 19:31
 * @description：
 */
@Component
public class MessageQueueConsumer {

    @Autowired
    UserRepository userRepository;

    @Autowired
    MessageRepository messageRepository;

    /**
     * 处理好友申请，在进行消息的持久化的同时，若被申请者在线，到被申请者的消息列表将被推送申请信息，
     * @param msg
     */
    @RabbitListener(queues = {"inviteBeFriend"})
    public void inviteBeFriend(String msg) {
        try {
            MessageQueueLoad load = JSON.parseObject(msg, MessageQueueLoad.class);
            String invited = (String) load.getData().get("invitedId");
            String inviter = (String) load.getData().get("inviterId");
            String usersRelationId = (String) load.getData().get(MessageServiceImpl.USERS_RELATION_ID);
            Message message = new Message();
            message.setType(MessageTypeEnum.INVITE_BE_FRIEND.getCode());
            message.setTitle(MessageTypeEnum.INVITE_BE_FRIEND.getDescription());
            String content = userRepository.findById(inviter).get().getUsername() + "向你发送了好友申请";
            message.setContent(content);
            message.setDealway(UOInviteBeFriendEnum.NO_DEAL_BE_FRIEND.getCode());
            message.setUserId(invited);
            message.setCreateTime(new Date());
            message.setState(MessageStateEnum.NO_DEAL.getCode());
            message.setUrl("http://crown.com/message/friend/acceptFriend?usersRealtionId=" + usersRelationId + "&dealway=");
            message = messageRepository.save(message);
            MessageEndPoint messageEndPoint = MessageEndPoint.onlineUsers.get(invited);
            if (messageEndPoint != null) {
                try {
                    MessageView messageView = new MessageView(message);
                    messageEndPoint.getSession().getBasicRemote().sendText(JSONObject.toJSONString(messageView));
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            }
        } catch (Exception e) {
            return;
        }

    }
}
