package com.cjy.crown.issue.repository;

import com.cjy.crown.issue.entity.IssueManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ：JinYu
 * @date ：Created in 2022/4/19 10:26
 * @description：
 */
@Repository
public interface IssueManagerRepository extends JpaRepository<IssueManager,String> {
    IssueManager findByIssueIdAndManagerAndState(String issueId,String userId,Integer State);
    List<IssueManager> findByManager(String id);
    List<IssueManager> findByIssueId(String issueId);
    List<IssueManager> findByIssueIdAndState(String issueId,Integer state);
    List<IssueManager> findByIssueIdAndManagerIn(String issueId,List<String> managerId);
}
