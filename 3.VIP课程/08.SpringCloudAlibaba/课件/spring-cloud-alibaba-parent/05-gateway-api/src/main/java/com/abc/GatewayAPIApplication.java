package com.abc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication//启动引导类也是一个配置类
public class GatewayAPIApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayAPIApplication.class, args);
    }
    //配置路由规则
    @Bean
    public RouteLocator someRouteLocator(RouteLocatorBuilder builder){
        return builder.routes().route(predicateSpec -> predicateSpec
                .path("/")
                .uri("https://www.baidu.com")
                .id("baidu_route")).build();//函数式编程思想
    }
//    @Bean
//    public RouteLocator someRouteLocator(RouteLocatorBuilder builder){
//        //创建路由规则：Lambda表达式==> 面向对象对象创建过程简写
//        Function<PredicateSpec, Route.AsyncBuilder> route = new Function<PredicateSpec, Route.AsyncBuilder>() {
//            @Override
//            public Route.AsyncBuilder apply(PredicateSpec predicateSpec) {
//                return predicateSpec
//                        .path("/**")
//                        .uri("https://www.baidu.com")
//                        .id("baidu_route");
//            }
//        };
//        RouteLocatorBuilder.Builder routeLocatorBuilder = builder.routes().route(route);
//        return routeLocatorBuilder.build();
//    }

}
