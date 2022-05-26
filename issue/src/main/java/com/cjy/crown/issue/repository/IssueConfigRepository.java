package com.cjy.crown.issue.repository;

import com.cjy.crown.issue.entity.IssueConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author ：JinYu
 * @date ：Created in 2022/4/19 10:27
 * @description：
 */
@Repository
public interface IssueConfigRepository extends JpaRepository<IssueConfig,String> {
    IssueConfig findByIssueId(String issueId);
}
