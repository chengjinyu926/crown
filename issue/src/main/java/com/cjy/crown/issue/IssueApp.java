package com.cjy.crown.issue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author ：JinYu
 * @date ：Created in 2022/4/11 13:58
 * @description：
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.cjy.crown")
@EnableJpaRepositories(basePackages = "com.cjy.crown")
@EntityScan(basePackages = "com.cjy.crown")
@EnableFeignClients
public class IssueApp {
    public static void main(String[] args) {
        System.setProperty("es.set.netty.runtime.available.processors","false");
        SpringApplication.run(IssueApp.class, args);
    }
}
