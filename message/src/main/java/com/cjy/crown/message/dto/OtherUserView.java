package com.cjy.crown.message.dto;

import com.cjy.crown.security.entity.User;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * @author ：JinYu
 * @date ：Created in 2022/4/23 4:38
 * @description：
 */
@Data
public class OtherUserView {
    private String id;
    private String username;
    private Integer sex;
    private String email;
    private String phone;
    private Date birthday;
    private Date lastLoginTime;
    private Integer state;
    private boolean ifFriend;

    public OtherUserView(User user) {
        BeanUtils.copyProperties(this,user);
    }
}
