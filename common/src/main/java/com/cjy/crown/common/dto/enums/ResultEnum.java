package com.cjy.crown.common.dto.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author ：JinYu
 * @date ：Created in 2022/3/30 14:16
 * @description：
 */
@AllArgsConstructor
@Getter
@ToString
public enum ResultEnum {
    DENY_LOGINAME_PWD("WARNING","warning","用户名或密码错误"),
    REGISTRY_SUCCESS("SUCCESS","success","注册成功"),
    ADD_ONE_ISSUE_SUCCESS("SUCCESS","success","新建议题成功"),
    LOGIN_SUCCESS("SUCCESS","success","登录成功"),
    HAVE_NO_PWD_OR_LOGINAME("WARNING","warning","用户名或密码未填写"),
    REPEAT_LOGINAME("WARNING","warning","用户名已注册"),
    REPEAT_PHONE("WARNING","warning","手机号已注册"),
    NO_REGISTRY_PHONE("WARNING","warning","手机号未注册"),
    REPEAT_EMAIL("WARNING","warning","邮箱已注册"),
    SEND_LOGIG_CODE_FAILED("WARNING","warning","验证码发送失败"),
    SEND_LOGIG_CODE_SUCCESS("SUCCESS","success","验证码发送成功"),
    VALIDATE_LOGIG_CODE_SUCCESS("SUCCESS","success","登录成功"),
    VALIDATE_LOGIG_CODE_FAILED("WARNING","warning","验证码验证失败"),
    NO_DEAL_INVITE("WARNING","warning","已发送过一次好友申请"),
    HAVE_BE_FRIEND("SUCCESS","success","对方已是您的好友"),
    SEND_INVITE_FRIEND_SUCCESS("SUCCESS","success","发送好友申请成功，请耐心等待"),
    MESSAGE_EXCUTE_SUCCESS("SUCCESS","success","执行成功"),
    ;
    private String title;
    private String state;
    private String message;
}
