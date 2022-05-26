package com.cjy.crown.message.service;

import com.cjy.crown.common.dto.Result;

/**
 * @author ：JinYu
 * @date ：Created in 2022/4/15 19:15
 * @description：
 */
public interface MessageService {
    Result sendFriendInvite(String invitedId);
    Result initMessage();
}
