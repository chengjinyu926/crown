package com.cjy.crown.message.dto;

import com.cjy.crown.message.entity.Message;
import com.cjy.crown.message.enums.MessageTypeEnum;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ：JinYu
 * @date ：Created in 2022/4/16 20:10
 * @description：
 */
@Data
public class MessageView {
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
    private Map<Integer, String> option;
    private boolean ifHaveOptions;

    public MessageView(Message message) {
        BeanUtils.copyProperties(message, this);
        MessageTypeEnum messageTypeEnum = MessageTypeEnum.getByCode(message.getType());
        setIfHaveOptions(messageTypeEnum.isInput());
        if (messageTypeEnum.getOptions() != null) {
            setOption(messageTypeEnum.getOptions());
        }
    }
}
