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
 * @date ：Created in 2022/4/11 14:40
 * @description：
 */
@Data
@Entity
@Table(name = "issue_relation")
public class IssueRelation {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
    private String id;
    private String relationId;
    private String issueId;
    private String creater;
    private Date createrTime;
    private Integer state;
}
