package com.abc;

import com.alibaba.csp.sentinel.adapter.gateway.common.SentinelGatewayConstants;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiDefinition;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiPathPredicateItem;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiPredicateItem;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.GatewayApiDefinitionManager;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.net.URI;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@SpringBootApplication//启动引导类也是一个配置类
public class SentinelApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(SentinelApiGatewayApplication.class, args);
        initCustomizedApis();//初始化自定义api
        initRule();//配置初始化网关的流控规则
        initBlockHandlers();//初始化路由限流降级处理方法
    }
    private static void initRule() {
        Set<GatewayFlowRule> rules = new HashSet<>();
        // 在这里指定了对哪些路由api进行限流
        // 这里仅对/consumer/depart/get/**形式的请求进行限流
        GatewayFlowRule departRule = SentinelApiGatewayApplication.gatewayFlowRule("depart_api", 1);
        // 这里仅对/consumer/staff/get/2与/consumer/staff/get/3这两个请求进行限流
        GatewayFlowRule staffRule = SentinelApiGatewayApplication.gatewayFlowRule("staff_api", 2);
        rules.add(departRule);
        rules.add(staffRule);
        GatewayRuleManager.loadRules(rules);
    }
    private static void initCustomizedApis() {
        // 定义一个名称为 depart_api 的路由api
        ApiDefinition departApi = new ApiDefinition("depart_api")
                .setPredicateItems(new HashSet<ApiPredicateItem>() {{
                    add(new ApiPathPredicateItem().setPattern("/consumer/depart/get/**")
                            // 指定该路由api对于请求的匹配策略为 前辍匹配
                            .setMatchStrategy(SentinelGatewayConstants.URL_MATCH_STRATEGY_PREFIX));
                }});

        // 定义一个名称为 staff_api 的路由api
        ApiDefinition staffApi = new ApiDefinition("staff_api")
                .setPredicateItems(new HashSet<ApiPredicateItem>() {{
                    add(new ApiPathPredicateItem().setPattern("/consumer/staff/get/2"));
                    add(new ApiPathPredicateItem().setPattern("/consumer/staff/get/3")
                            // 指定该路由api对于请求的匹配策略为 精确匹配
                            .setMatchStrategy(SentinelGatewayConstants.URL_MATCH_STRATEGY_EXACT));
                }});

        Set<ApiDefinition> definitions = new HashSet<>();
        definitions.add(departApi);
        definitions.add(staffApi);
        GatewayApiDefinitionManager.loadApiDefinitions(definitions);
    }


    //定义网关限流规则
    private static GatewayFlowRule gatewayFlowRule(String apiName, int count) {
        GatewayFlowRule rule = new GatewayFlowRule();
        // 指定规则模式为路由api限流
        rule.setResourceMode(SentinelGatewayConstants.RESOURCE_MODE_CUSTOM_API_NAME);
        rule.setResource(apiName);//api名称
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule.setCount(count);
        return rule;
    }

    // 降级处理方法
    private static void initBlockHandlers() {
        GatewayCallbackManager.setBlockHandler((exchange, th) -> {
            URI uri = exchange.getRequest().getURI();
            Map<String, Object> map = new HashMap<>();
            map.put("uri", uri);
            map.put("msg", "访问量过大，请稍候重试");
            return ServerResponse.status(HttpStatus.TOO_MANY_REQUESTS)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(map));
        });
    }
}
