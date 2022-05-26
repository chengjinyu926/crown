package com.cjy.crown.message.bo;

import lombok.Data;

import java.util.HashMap;

/**
 * @author ：JinYu
 * @date ：Created in 2022/4/15 19:23
 * @description：
 */
@Data
public class MessageQueueLoad {

    private HashMap<String, Object> data = new HashMap<>();

    public void addData(String key, Object value) {
        data.put(key, value);
    }
}
