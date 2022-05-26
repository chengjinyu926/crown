package com.cjy.crown.issue.service.rest;

import com.cjy.crown.common.dto.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @author ：JinYu
 * @date ：Created in 2022/4/20 23:38
 * @description：
 */
@FeignClient(value = "message")
@Component
public interface MessageRestService {
    @GetMapping("/message/friend/getFriendIdAndUsernameByUserId")
    Result getFriendIdAndUsernameByUserId(@RequestParam(value = "userId") String userId, @RequestHeader Map map);
}
