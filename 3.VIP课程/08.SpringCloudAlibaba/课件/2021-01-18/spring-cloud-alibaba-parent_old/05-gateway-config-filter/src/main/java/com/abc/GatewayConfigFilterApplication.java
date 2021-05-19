package com.abc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class GatewayConfigFilterApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayConfigFilterApplication.class, args);
    }

    //配置令牌桶限流算法的key：将hostName作为key返回回去
    @Bean
    public KeyResolver keyResolver(){
        return exchange -> Mono.just(
                exchange.getRequest()
                        .getRemoteAddress()
                        .getHostName()
        );
    }
}
