package com.abc.config;

import com.abc.rule.CustomRule;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * feign配置类
 */
@Configuration
public class FeignConfiguration {
    /**
     * 配置随机的负载均衡策略
     * 特点：对所有的服务都生效
     */
    //@Bean
    //public IRule loadBalancedRule() {
    //    return new RandomRule();
    //}
    @Bean
    public IRule loadBalancedRule() {
        List<Integer> list = new ArrayList<>();
        list.add(8081);//排除访问端口
        return new CustomRule(list);
    }
}
