package com.cjy.crown.security.repository;

import com.cjy.crown.security.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author ：JinYu
 * @date ：Created in 2022/3/29 18:15
 * @description：
 */
public interface RoleRepository extends JpaRepository<Role, String> {
}
