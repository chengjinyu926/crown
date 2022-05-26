package com.cjy.crown.account.service.rest;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author ：JinYu
 * @date ：Created in 2022/4/9 22:02
 * @description：
 */
@FeignClient(value = "sms")
@Component
public interface SmsService {

    @GetMapping("/sms/sendLoginCode")
    public String sendLoginCode(@RequestParam(value = "phone") String phone);

    @GetMapping("/sms/validateLoginCode")
    public boolean validateLoginCode(@RequestParam(value = "phone") String phone,@RequestParam("code") String code);
}
