package com.cjy.crown.common.dto;

import com.cjy.crown.common.dto.enums.ResultEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ：JinYu
 * @date ：Created in 2022/3/30 14:12
 * @description：
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private String title;
    private String state;
    private String message;
    private Map<String, Object> data;

    public Result(ResultEnum resultEnum) {
        this.title = resultEnum.getTitle();
        this.state = resultEnum.getState();
        this.message = resultEnum.getMessage();
    }

    public void addData(String key, Object value) {
        if (data == null) {
            data = new HashMap<>();
        }
        data.put(key, value);
    }
}
