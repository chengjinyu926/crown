package com.cjy.crown.sms.controller;

import com.cjy.crown.sms.service.CodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @author ：JinYu
 * @date ：Created in 2022/3/28 23:02
 * @description：
 */
@RestController
@RequestMapping("sms")
public class MessageController {

    @Autowired
    CodeService codeService;

    @GetMapping("sendLoginCode")
    public String sendLoginCode(@RequestParam(value = "phone") String phone) {
        return codeService.sendLoginCode(phone);
    }

    @GetMapping("validateLoginCode")
    public boolean validateLoginCode(@RequestParam(value = "phone") String phone, @RequestParam(value = "code") String code) {
        return codeService.validateLoginCode(phone, code);
    }
}
