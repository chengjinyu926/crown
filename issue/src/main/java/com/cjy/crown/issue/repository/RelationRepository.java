package com.cjy.crown.issue.repository;

import com.cjy.crown.issue.entity.Relation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ：JinYu
 * @date ：Created in 2022/4/11 15:18
 * @description：
 */
@Repository
public interface RelationRepository extends JpaRepository<Relation, String> {
    @Query(nativeQuery = true, value = "select * from relation r where r.id in (select ir.relation_id from issue_relation ir where issue_id = ?1)")
    List<Relation> queryByIssueId(String issueId);

    Relation findByName(String name);
}
