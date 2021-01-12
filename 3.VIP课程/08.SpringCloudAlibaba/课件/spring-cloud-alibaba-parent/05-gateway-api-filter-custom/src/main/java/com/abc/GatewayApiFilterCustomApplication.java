package com.abc;

import com.abc.filter.AddHeaderGatewayFilter;
import com.abc.filter.OneGatewayFilter;
import com.abc.filter.ThreeGatewayFilter;
import com.abc.filter.TwoGatewayFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication//启动引导类也是一个配置类
public class GatewayApiFilterCustomApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApiFilterCustomApplication.class, args);
    }


    //配置自定义过滤器
//    @Bean
//    public RouteLocator someRouteLocator(RouteLocatorBuilder builder) {
//        return builder.routes()
//                .route(ps -> ps.path("/**")
//                        .filters(fs -> fs.filter(new AddHeaderGatewayFilter())) // 配置自定义网关
//                        .uri("http://localhost:8080")
//                        .id("custom_filter"))
//                .build();
//    }
    //配置自定义多过滤器
    @Bean
    public RouteLocator someRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(ps -> ps.path("/**")
                        .filters(fs ->
                                      fs.filter(new OneGatewayFilter())
                                        .filter(new TwoGatewayFilter())
                                        .filter(new ThreeGatewayFilter())) // 配置自定义网关
                        .uri("http://localhost:8080")
                        .id("custom_filter"))
                .build();
    }

}
