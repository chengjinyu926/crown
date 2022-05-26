package com.cjy.crown.security.repository;

import com.cjy.crown.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ：JinYu
 * @date ：Created in 2022/3/29 18:14
 * @description：
 */
public interface UserRepository extends JpaRepository<User, String> {
//    List<User> findByLoginameOrPhoneOrEmail(String loginame, String phone, String Email);
    User findByLoginame(String loginame);
    User findByPhone(String phone);
    User findByEmail(String Email);
}
