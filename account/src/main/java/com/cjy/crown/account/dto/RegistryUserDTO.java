package com.cjy.crown.account.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author ：JinYu
 * @date ：Created in 2022/3/29 18:49
 * @description：
 */
@Data
public class RegistryUserDTO {
    private String loginame;
    private String username;
    private String password;
    private String phone;
    private String email;
    private Date birthday;
    private Integer sex;
}
