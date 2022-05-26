package com.cjy.crown.message.repository;

import com.cjy.crown.message.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ：JinYu
 * @date ：Created in 2022/4/18 14:48
 * @description：
 */
@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage,String> {
    List<ChatMessage> findBySenderInAndReceiverInOrderByCreateTimeDesc(List<String> senders,List<String> receivers);
}
