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
 * @date ：Created in 2022/4/11 18:23
 * @description：
 */
@Entity
@Table(name = "user_visit_issue")
@Data
public class UserVisitIssue {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
    private String id;
    private Integer type;
    private String userId;
    private String issueId;
    private Date createTime;
    private Integer state;
}
