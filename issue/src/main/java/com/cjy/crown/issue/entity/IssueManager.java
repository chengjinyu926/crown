package com.cjy.crown.issue.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author ：JinYu
 * @date ：Created in 2022/4/18 19:57
 * @description：
 */
@Data
@Entity
@Table(name = "issue_manager")
public class IssueManager {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid",strategy = "uuid.hex")
    private String id;
    private String manager;
    private String issueId;
    private Date createTime;
    private Date updateTime;
    private Integer state;
}
