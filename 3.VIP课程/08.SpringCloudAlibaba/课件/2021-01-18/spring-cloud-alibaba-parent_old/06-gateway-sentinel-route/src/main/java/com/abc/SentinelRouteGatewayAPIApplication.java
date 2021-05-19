package com.abc;

import com.alibaba.csp.sentinel.adapter.gateway.common.SentinelGatewayConstants;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@SpringBootApplication//启动引导类也是一个配置类
public class SentinelRouteGatewayAPIApplication {

    public static void main(String[] args) {
        SpringApplication.run(SentinelRouteGatewayAPIApplication.class, args);
        initRule();//配置初始化网关的流控规则

        initBlockHandlers();//初始化路由限流降级处理方法
    }

    private static void initRule() {
        Set<GatewayFlowRule> rules = new HashSet<>();
        GatewayFlowRule rule = SentinelRouteGatewayAPIApplication.gatewayFlowRule();
        rules.add(rule);
        GatewayRuleManager.loadRules(rules);
    }

    // 对名称为staff_route的路由规则进行限流
    private static GatewayFlowRule gatewayFlowRule() {
        // 定义一个Gateway限流规则实例
        GatewayFlowRule rule = new GatewayFlowRule();
        // 指定规则模式是route限流，其为默认值
        rule.setResourceMode(SentinelGatewayConstants.RESOURCE_MODE_ROUTE_ID);
        // 指定sentienl资源名称为 路由规则id
        rule.setResource("staff_route");
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule.setCount(2);
        return rule;
    }

    // 路由限流降级
    private static void initBlockHandlers() {
        GatewayCallbackManager.setBlockHandler((exchange, th) -> {
            // 从请求中获取uri
            URI uri = exchange.getRequest().getURI();
            // 将响应数据写入到map
            Map<String, Object> map = new HashMap<>();
            map.put("uri", uri);
            map.put("msg", "访问量过大，请稍候重试");
            return ServerResponse.status(HttpStatus.TOO_MANY_REQUESTS)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(map));
        });
    }
}
