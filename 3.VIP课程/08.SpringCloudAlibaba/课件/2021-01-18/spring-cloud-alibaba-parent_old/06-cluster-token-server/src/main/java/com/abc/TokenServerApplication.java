package com.abc;

import com.alibaba.csp.sentinel.cluster.server.ClusterTokenServer;
import com.alibaba.csp.sentinel.cluster.server.SentinelDefaultTokenServer;
import com.alibaba.csp.sentinel.cluster.server.config.ClusterServerConfigManager;
import com.alibaba.csp.sentinel.cluster.server.config.ServerTransportConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;

@SpringBootApplication//启动引导类也是一个配置类
public class TokenServerApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(TokenServerApplication.class, args);
        //配置及其服务
        ClusterServerConfigManager.loadGlobalTransportConfig(new ServerTransportConfig()
                .setIdleSeconds(600)
                .setPort(9999));
        //设置客户端名称
        String clientName = "msc-depart-consumer";
        ClusterServerConfigManager.loadServerNamespaceSet(Collections.singleton(clientName));
        //创建集群TokenServer服务对象
        ClusterTokenServer tokenServer = new SentinelDefaultTokenServer();
        tokenServer.start();//启动集群的TokenServer服务
    }
}
