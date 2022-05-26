package com.cjy.crown.issue.repository;

import com.cjy.crown.issue.entity.IssueRelation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * @author ：JinYu
 * @date ：Created in 2022/4/11 15:18
 * @description：
 */
@Repository
public interface IssueRelationRepository extends JpaRepository<IssueRelation, String> {
    List<IssueRelation> findByIssueId(String issueId);
    List<IssueRelation> findByIssueIdNotInAndRelationId(List<String> issueIds, String relationId, Pageable pageable);
}
