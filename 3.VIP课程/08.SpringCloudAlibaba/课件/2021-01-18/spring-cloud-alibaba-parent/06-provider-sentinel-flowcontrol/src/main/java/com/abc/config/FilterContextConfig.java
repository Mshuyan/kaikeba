package com.abc.config;

import com.alibaba.csp.sentinel.adapter.servlet.CommonFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterContextConfig {
    @Bean
    public FilterRegistrationBean sentinelFilterRegistration() {
        //过滤器注册对象
        FilterRegistrationBean registration = new FilterRegistrationBean();
        //与Sentinel过滤器
        registration.setFilter(new CommonFilter());
        //注册了一个Sentinel的过滤器
        registration.addUrlPatterns("/*");
        registration.addInitParameter(CommonFilter.WEB_CONTEXT_UNIFY, "false");
        registration.setName("sentinelFilter");
        registration.setOrder(1);
        return registration;
    }
}
