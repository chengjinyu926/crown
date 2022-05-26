package com.cjy.crown.security.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author ：JinYu
 * @date ：Created in 2022/3/29 17:40
 * @description：
 */
@Data
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid.hex")
    private String id;
    private String username;
    private String loginame;
    private String password;
    private Integer sex;
    private String email;
    private String phone;
    private Date birthday;
    private Date lastLoginTime;
    private Date createTime;
    private Date updateTime;
    private String createId;
    private String updateId;
    private Integer state;
}
