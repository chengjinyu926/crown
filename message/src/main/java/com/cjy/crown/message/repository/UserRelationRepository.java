package com.cjy.crown.message.repository;

import com.cjy.crown.message.entity.UsersRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ：JinYu
 * @date ：Created in 2022/4/16 0:55
 * @description：
 */
@Repository
public interface UserRelationRepository extends JpaRepository<UsersRelation,String> {
    UsersRelation findFirstByOperaterAndBeOperaterAndTypeOrderByCreatetimeDesc(String operater,String beOperater,int type);
//    List<UsersRelation> findByOperaterOrBeOperaterAndDealway(String operater,String beOperater,Integer dealway);
    List<UsersRelation> findByOperaterAndDealway(String id,Integer dealway);
    List<UsersRelation> findByOperaterAndDealwayAndType(String id,Integer dealway,Integer type);
    List<UsersRelation> findByBeOperaterAndDealway(String id,Integer dealway);
    List<UsersRelation> findByBeOperaterAndDealwayAndType(String id,Integer dealway,Integer type);
    UsersRelation findTopByOperaterAndBeOperaterAndTypeOrderByCreatetimeDesc(String operater,String beOperater,int Type);
}
