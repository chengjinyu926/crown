package com.cjy.crown.message.ws;

import com.cjy.crown.security.utils.JwtUtil;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ：JinYu
 * @date ：Created in 2022/4/15 13:53
 * @description：
 */
@ServerEndpoint(value = "/message/createSocket/{token}")
@Component
@Data
public class MessageEndPoint {

    private static final String USER_ID = "userId";

    public static Map<String, MessageEndPoint> onlineUsers = new ConcurrentHashMap<>();

    private String token = "";

    private Session session;


    @OnOpen
    public void onOpen(Session session, @PathParam("token") String token) {
        this.session = session;
        String userId = JwtUtil.getUserId(token);
        onlineUsers.put(userId, this);
        System.out.println("建立链接成功："+userId+"|"+new Date());
//        MessageEndPoint messageEndPoint = onlineUsers.get(userId);
//        Message message = new Message();
//        message.setType(MessageTypeEnum.SYS_MESSAGE.getCode());
//        try {
//            this.session.getBasicRemote().sendText(JSON.toJSONString(message));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("ONMESSAGE");

    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("ONCLOSE");
    }
}
