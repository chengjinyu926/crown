package com.cjy.crown.issue.repository;

import com.cjy.crown.issue.entity.Issue;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author ：JinYu
 * @date ：Created in 2022/4/11 13:54
 * @description：
 */
@Repository
public interface IssueRepository extends JpaRepository<Issue, String> {

    @Query(nativeQuery = true, value = "select * from issue limit ?1,?2 ")
    List<Issue> getDefaultIssueListWithPage(int page, int num);

    List<Issue> getTop10ByIdNotIn(List<String> ids);

    List<Issue> findByIdNotIn(List<String> ids, Pageable pageable);

    List<Issue> findByFatherId(String id);

    List<Issue> findByHolder(String id);

    @Query(nativeQuery = true, value = "SELECT * from issue where id in ( SELECT issue_id FROM ( SELECT * FROM user_visit_issue WHERE user_id = ?1 AND type IN ( 4, 5 ) GROUP BY issue_id ORDER BY create_time ) AS a WHERE a.type = 4)")
    List<Issue> customFindStarIssueByUserId(String userId);
}
