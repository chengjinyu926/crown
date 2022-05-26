package com.cjy.crown.message;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author ：JinYu
 * @date ：Created in 2022/4/15 13:50
 * @description：
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = "com.cjy.crown")
@EnableJpaRepositories(basePackages = "com.cjy.crown")
@EntityScan(basePackages = "com.cjy.crown")
public class MessageApp {
    public static void main(String[] args) {
        SpringApplication.run(MessageApp.class, args);
    }
}
