package com.cjy.crown.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author ：JinYu
 * @date ：Created in 2022/3/29 20:57
 * @description：
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.cjy.crown")
@EnableJpaRepositories(basePackages = "com.cjy.crown")
@EntityScan(basePackages = "com.cjy.crown")
@EnableFeignClients
public class AccountApp {
    public static void main(String[] args) {
        SpringApplication.run(AccountApp.class,args);
    }
}
