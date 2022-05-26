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
 * @date ：Created in 2022/4/18 14:45
 * @description：
 */
@Data
@Entity
@Table(name = "chat_message")
public class ChatMessage {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
    private String id;
    private String content;
    private String sender;
    private String receiver;
    private Date createTime;
    private Integer state;
}
