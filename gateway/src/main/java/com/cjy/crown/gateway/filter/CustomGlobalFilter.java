package com.cjy.crown.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author ：JinYu
 * @date ：Created in 2022/4/9 20:50
 * @description：
 */
@Component
@Order(0)
public class CustomGlobalFilter implements GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        exchange.getResponse().getHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponse().getHeaders().add("Access-Control-Allow-Credentials", "true");
        exchange.getResponse().getHeaders().add("Access-Control-Allow-Methods", "*");
        exchange.getResponse().getHeaders().add("Access-Control-Allow-Headers", "Content-Type,Access-Token,Authorization");
        exchange.getResponse().getHeaders().add("Access-Control-Expose-Headers", "*");
        return chain.filter(exchange);
    }
}
