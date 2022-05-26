package com.cjy.crown.message.entity;

import com.cjy.crown.message.enums.MessageTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ：JinYu
 * @date ：Created in 2022/4/15 16:09
 * @description：
 */
@Data
@Table(name = "message")
@Entity
public class Message {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
    private String id;
    private String userId;
    private Integer type;
    private Integer state;
    private Date createTime;
    private Integer dealway;
    private Date dealTime;
    private String url;
    private String title;
    private String content;
}
