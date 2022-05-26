package com.cjy.crown.account.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author ：JinYu
 * @date ：Created in 2022/4/15 20:40
 * @description：
 */
@Data
public class UserView {
    private String id;
    private String username;
    private String loginame;
    private Integer sex;
    private String email;
    private String phone;
    private Date birthday;
    private Date lastLoginTime;
    private Date createTime;
}
