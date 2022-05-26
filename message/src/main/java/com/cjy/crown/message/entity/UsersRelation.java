package com.cjy.crown.message.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author ：JinYu
 * @date ：Created in 2022/4/15 21:18
 * @description：\
 */
@Data
@Entity
@Table(name = "users_relation")
public class UsersRelation {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
    private String id;
    private String operater;
    private String beOperater;
    private Date createtime;
    private Integer type;
    private Integer dealway;
    private Date dealtime;
    private Integer state;
}
