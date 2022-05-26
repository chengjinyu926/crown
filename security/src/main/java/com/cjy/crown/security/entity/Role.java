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
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(generator = "system.uuid")
    @GenericGenerator(name = "system.uuid",strategy = "uuid.hex")
    private String id;
    private String name;
    private Date createTime;
    private Date updateTime;
    private String createId;
    private String updateId;
    private Integer state;
}
