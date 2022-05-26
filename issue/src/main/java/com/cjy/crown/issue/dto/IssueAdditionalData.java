package com.cjy.crown.issue.dto;

import com.cjy.crown.issue.entity.Relation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：JinYu
 * @date ：Created in 2022/4/13 17:03
 * @description：
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class IssueAdditionalData {
    private String username;
    private List<Relation> relationList = new ArrayList<>();
}
