package com.cjy.crown.issue.repository;

import com.cjy.crown.issue.entity.UserVisitIssue;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

/**
 * @author ：JinYu
 * @date ：Created in 2022/4/11 18:29
 * @description：
 */
@Repository
public interface UserVisitIssueRepository extends JpaRepository<UserVisitIssue, String> {
    List<UserVisitIssue> findByUserIdAndState(String userId, int state, Pageable pageable);
    List<UserVisitIssue> findByIssueIdAndState(String issueId,int state);
    UserVisitIssue findTopByIssueIdAndUserIdAndTypeInOrderByCreateTimeDesc(String issueId,String userId,int... type);
    int countByIssueIdAndTypeAndState(String issueId, int type, int state);
}
