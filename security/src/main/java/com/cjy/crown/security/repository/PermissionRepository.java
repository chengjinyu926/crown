package com.cjy.crown.security.repository;

import com.cjy.crown.security.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author ：JinYu
 * @date ：Created in 2022/3/29 18:15
 * @description：
 */
public interface PermissionRepository extends JpaRepository<Permission, String> {
}
