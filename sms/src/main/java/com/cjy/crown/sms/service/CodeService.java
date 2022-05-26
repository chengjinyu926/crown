package com.cjy.crown.sms.service;

/**
 * @author ：JinYu
 * @date ：Created in 2022/4/9 21:09
 * @description：
 */
public interface CodeService {
    String sendLoginCode(String phone);
    boolean validateLoginCode(String phone,String code);
}
