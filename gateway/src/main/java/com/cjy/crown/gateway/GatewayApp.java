package com.cjy.crown.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author ：JinYu
 * @date ：Created in 2022/3/30 22:33
 * @description：
 */
@SpringBootApplication
@EnableDiscoveryClient
public class GatewayApp {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApp.class,args);
    }
}
