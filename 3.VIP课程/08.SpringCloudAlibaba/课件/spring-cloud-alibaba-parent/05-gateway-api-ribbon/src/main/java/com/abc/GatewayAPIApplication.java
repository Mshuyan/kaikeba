package com.abc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

/**
 * bean.xml配置文件
 * 进阶
 * @Configuration
 * 进阶
 * yaml配置文件
 *
 */
@SpringBootApplication//启动引导类也是一个配置类
public class GatewayAPIApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayAPIApplication.class, args);
    }
    //配置路由规则
    @Bean
    public RouteLocator someRouteLocator(RouteLocatorBuilder builder){
        //路由构建器对象，构建一个路由规则
        return builder.routes().route(predicateSpec -> predicateSpec
                .path("/provider/depart/**")
                .uri("lb://abcmsc-provider-depart")//动态路由方式
                .id("ribbon_route")).build();
    }
}
