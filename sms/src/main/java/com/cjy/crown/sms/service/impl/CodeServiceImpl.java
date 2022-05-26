package com.cjy.crown.sms.service.impl;

import com.cjy.crown.sms.cosntants.RedisConstant;
import com.cjy.crown.sms.service.CodeService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author ：JinYu
 * @date ：Created in 2022/4/9 21:10
 * @description：
 */
@Service
public class CodeServiceImpl implements CodeService {

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public String sendLoginCode(String phone) {
        String code = RandomStringUtils.random(6, false, true);
        redisTemplate.opsForValue().set(RedisConstant.LOGIN_CODE + phone, code, 120, TimeUnit.SECONDS);
        System.out.println(redisTemplate.opsForValue().get(RedisConstant.LOGIN_CODE + phone));
        return code;
    }

    /**
     *
     * @param phone
     * @param code
     * @return
     */
    @Override
    public boolean validateLoginCode(String phone, String code) {
        String key = RedisConstant.LOGIN_CODE + phone;
        String trueCode = (String) redisTemplate.opsForValue().get(key);
        if (trueCode == null || trueCode == "") {
            return false;
        } else if (trueCode.equals(code)) {
            redisTemplate.delete(key);
            return true;
        }
        return false;
    }
}
