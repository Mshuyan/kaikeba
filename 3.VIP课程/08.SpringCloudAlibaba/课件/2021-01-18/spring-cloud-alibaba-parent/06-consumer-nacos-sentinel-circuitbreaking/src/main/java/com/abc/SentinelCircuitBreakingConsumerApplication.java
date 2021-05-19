package com.abc;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@EnableFeignClients//配置开启Feign的客户端支持
@SpringBootApplication
public class SentinelCircuitBreakingConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SentinelCircuitBreakingConsumerApplication.class, args);
        //初始化熔断策略
        SentinelCircuitBreakingConsumerApplication.initRule();
    }
    public static void initRule() {
        List<DegradeRule> rules = new ArrayList<>();
        // 获取定义的规则
        DegradeRule rule = SentinelCircuitBreakingConsumerApplication.slowRequestDegradeRule();
        rules.add(rule);
        DegradeRuleManager.loadRules(rules);//Sentinel降级的策略管理器，装载熔断规则
    }
    //慢调用比例 熔断降级规则
    public static DegradeRule slowRequestDegradeRule(){
        //创建一个降级规则实例：熔断规则
        DegradeRule rule = new DegradeRule();
        //设置配置熔断规则的资源名称
        rule.setResource("getDepartById");
        //熔断策略：慢调用比例、异常比例、异常数
        rule.setGrade(RuleConstant.DEGRADE_GRADE_RT);
        //设置阈值：RT的时间，单位毫秒。若一个请求获取到响应的时间超出该值，则会将该请求统计为“慢调用”
        rule.setCount(200);//Count拥有很多含义：最大RT、异常比例阈值、异常数...
        //熔断恢复时间窗口，单位秒
        rule.setTimeWindow(30);
        //可触发熔断的最小请求数，默认是5个
        rule.setMinRequestAmount(5);
        // 设置发生慢调用的比例
        rule.setSlowRatioThreshold(0.5);
        return rule;
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
