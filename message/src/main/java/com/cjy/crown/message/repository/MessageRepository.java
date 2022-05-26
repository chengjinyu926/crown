package com.cjy.crown.message.repository;

import com.cjy.crown.message.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ：JinYu
 * @date ：Created in 2022/4/16 19:55
 * @description：
 */
@Repository
public interface MessageRepository extends JpaRepository<Message,String> {
    List<Message> findByUserId(String userId);
    List<Message> findByUserIdAndDealway(String userId , Integer dealwat);
}
