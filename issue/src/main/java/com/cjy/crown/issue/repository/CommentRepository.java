package com.cjy.crown.issue.repository;

import com.cjy.crown.issue.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ：JinYu
 * @date ：Created in 2022/4/13 23:37
 * @description：
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment,String> {
    List<Comment> findByIssueId(String issueId);
    List<Comment> findByIssueIdAndState(String issueId,int state);
}
