package com.cjy.crown.issue.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author ：JinYu
 * @date ：Created in 2022/4/18 19:50
 * @description：
 */
@Entity
@Data
@Table(name = "issue_config")
@AllArgsConstructor
@NoArgsConstructor
public class IssueConfig {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid",strategy = "uuid.hex")
    private String id;
    private String issueId;
    private Integer commentLevel;
    private Integer createChildLevel;
    private boolean managerDeleteComment;
    private boolean managerUnlineIssue;
    private Date createTime;
    private Date updateTime;
    private String creater;
    private String updater;
    private Integer state;
}
