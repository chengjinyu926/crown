package com.cjy.crown.message.ws;

import com.cjy.crown.security.utils.JwtUtil;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ：JinYu
 * @date ：Created in 2022/4/18 14:50
 * @description：
 */
@ServerEndpoint(value = "/message/friend/chatMessage/{token}")
@Component
@Data
public class ChatMessageEndPoint {

    private static final String USER_ID = "userId";

    public static Map<String, ChatMessageEndPoint> onlineUsers = new ConcurrentHashMap<>();

    private String token = "";

    private Session session;


    @OnOpen
    public void onOpen(Session session, @PathParam("token") String token) {
        this.session = session;
        String userId = JwtUtil.getUserId(token);
        onlineUsers.put(userId, this);
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
