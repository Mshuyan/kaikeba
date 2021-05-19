package com.abc;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class SentinelPersistConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SentinelPersistConsumerApplication.class, args);

    }

    /**
     * 注入RestTemplate模板对象，用来发送http请求
     * 作用相当于：<bean id='' class='org.springframework.web.client.RestTemplate'></bean>
     *
     * RestTemplate底层有：
     *  HttpClient
     *  OKHttp
     *  ...
     *@LoadBalanced//如果想要使用注册中心的注册列表访问对应的服务，必须要开启本注解。
     *  1.采用服务名称访问：如果不加注解，即便你注册了服务，也不能使用名称
     *  2.开启负载均衡
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
