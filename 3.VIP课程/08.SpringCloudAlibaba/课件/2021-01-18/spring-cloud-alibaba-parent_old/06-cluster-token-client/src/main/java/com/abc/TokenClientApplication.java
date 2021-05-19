package com.abc;

import com.alibaba.csp.sentinel.cluster.ClusterStateManager;
import com.alibaba.csp.sentinel.cluster.client.config.ClusterClientAssignConfig;
import com.alibaba.csp.sentinel.cluster.client.config.ClusterClientConfig;
import com.alibaba.csp.sentinel.cluster.client.config.ClusterClientConfigManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class TokenClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(TokenClientApplication.class, args);
        loadClusterClientConfig();//装载集群客户端配置
    }
    private static void loadClusterClientConfig() {
        //设置客户端状态
        ClusterStateManager.applyState(ClusterStateManager.CLUSTER_CLIENT);
        //配置服务端host和port
        ClusterClientAssignConfig assignConfig = new ClusterClientAssignConfig();
        assignConfig.setServerHost("localhost");
        assignConfig.setServerPort(9999);
        //应用配置
        ClusterClientConfigManager.applyNewAssignConfig(assignConfig);
        //创建集群客户端对象，设置请求超时时间
        ClusterClientConfig clientConfig = new ClusterClientConfig();
        clientConfig.setRequestTimeout(200);
        //应用集群客户端配置
        ClusterClientConfigManager.applyNewConfig(clientConfig);
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
