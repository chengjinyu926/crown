package com.cjy.crown.issue.config;

import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;

/**
 * @author ：JinYu
 * @date ：Created in 2022/4/23 20:04
 * @description：
 */
//@Configuration
public class ElasticsearchConfig {
    @Bean
    RestHighLevelClient elasticsearchClient(){
        ClientConfiguration configuration = ClientConfiguration.builder()
                .connectedTo("192.168.17.131:9300").build();
        RestHighLevelClient client = RestClients.create(configuration).rest();
        return client;
    }
}
