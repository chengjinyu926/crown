package com.cjy.crown.issue.repository.es;

import com.cjy.crown.issue.entity.es.IssueEs;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ：JinYu
 * @date ：Created in 2022/4/24 2:08
 * @description：
 */
@Repository
public interface IssueEsRepository extends ElasticsearchRepository<IssueEs,String> {
    List<IssueEs> findByTitleContainingOrderByCreateTime(String title, Pageable pageable);
}
