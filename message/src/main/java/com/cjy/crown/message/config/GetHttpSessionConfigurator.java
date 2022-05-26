package com.cjy.crown.message.config;

import com.cjy.crown.security.utils.WebUtil;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

/**
 * @author ：JinYu
 * @date ：Created in 2022/4/15 15:40
 * @description：
 */
public class GetHttpSessionConfigurator extends ServerEndpointConfig.Configurator {
    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        String userId =  WebUtil.getUserId();
        sec.getUserProperties().put("userId",userId);
    }
}
