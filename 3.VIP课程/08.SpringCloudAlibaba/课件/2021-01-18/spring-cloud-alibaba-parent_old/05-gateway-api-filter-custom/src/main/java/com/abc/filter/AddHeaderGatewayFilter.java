package com.abc.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 目标：自定义filter，拦截请求对象，并且设置参数
 * 1.获取请求对象，并且自定义参数
 * 2.将请求对象设置到交换机exchange中
 * 3.放行请求
 * 4.在05-showinfo中，打印request的参数打印一下
 */
public class AddHeaderGatewayFilter implements GatewayFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //1.获取请求对象，并且自定义参数
        ServerHttpRequest request = exchange.getRequest().mutate().header("x-request-red", "blue").build();
        //2.将请求对象设置到交换机exchange中
        ServerWebExchange webExchange = exchange.mutate().request(request).build();
        //3.放行请求
        return chain.filter(webExchange);

    }
}
