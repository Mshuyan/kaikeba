package com.abc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@SpringBootApplication//启动引导类也是一个配置类
public class GatewayAPIApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayAPIApplication.class, args);
    }
    //配置路由规则
//    @Bean
//    public RouteLocator someRouteLocator(RouteLocatorBuilder builder){
//        return builder.routes().route(predicateSpec -> predicateSpec
//                .path("/")
//                .uri("https://www.baidu.com")
//                .id("baidu_route")).build();//函数式编程思想
//    }
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
    //Path配置路由规则： 可以配置多个路径的拦截规则
//    @Bean
//    public RouteLocator someRouteLocator(RouteLocatorBuilder builder) {
//        return builder.routes()
//                .route(ps -> ps.path("/provider/**")
//                        .uri("http://localhost:8081")
//                        .id("path_provider_route"))
//                .route(ps -> ps.path("/consumer/**")
//                        .uri("http://localhost:8080")
//                        .id("path_consumer_route"))
//                .build();
//    }
    //Before配置路由规则
    @Bean
    public RouteLocator someRouteLocator(RouteLocatorBuilder builder) {
        //ISO-8601日历系统中带有时区的日期时间
        ZonedDateTime dateTime = LocalDateTime.now().minusDays(5).atZone(ZoneId.systemDefault());

        return builder.routes()
                .route(ps -> ps.before(dateTime)//设置before路由断言
                        .uri("https://www.baidu.com")
                        .id("before_route"))
                .build();
    }


}
