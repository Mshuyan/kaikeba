package com.abc.filter;

import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 目标：自定义全局过滤器，模拟登陆权限校验
 */
@Component//注意：必须注入Spring容器，否则不能生效
public class URLValidateFilter implements GlobalFilter, Ordered {//Ordered 排序相关：过滤器是chain

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        //1.获取请求参数token
        String token = exchange.getRequest().getQueryParams().getFirst("token");

        //2.判断token是否存在
        //如果不存在则拦截，提示用户未授权
        if (StringUtils.isEmpty(token)){
            //设置提示用户未授权
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            //完结请求
            return exchange.getResponse().setComplete();
        }
        //如果存在，则放行拦截器
        return chain.filter(exchange);
    }


    @Override
    public int getOrder() {
        //优先级是int的整数，数字越大，优先级越高的
        return Ordered.HIGHEST_PRECEDENCE;//最高优先级
    }
}
