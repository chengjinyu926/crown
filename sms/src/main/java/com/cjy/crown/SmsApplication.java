package com.cjy.crown;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author ：JinYu
 * @date ：Created in 2022/3/27 13:51
 * @description：
 */
@EnableDiscoveryClient
@SpringBootApplication
public class SmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(SmsApplication.class,args);
    }
}
