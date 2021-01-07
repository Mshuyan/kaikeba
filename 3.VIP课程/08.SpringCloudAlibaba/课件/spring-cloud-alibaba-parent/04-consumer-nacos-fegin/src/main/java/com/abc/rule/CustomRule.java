package com.abc.rule;

import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.Server;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * 自定义负载均衡算法
 */
public class CustomRule implements IRule {
    private ILoadBalancer lb;
    private List<Integer> excludePorts;

    public CustomRule() {
    }

    public CustomRule(List<Integer> excludePorts) {
        this.excludePorts = excludePorts;
    }

    @Override
    public void setLoadBalancer(ILoadBalancer lb) {
        this.lb = lb;
    }

    @Override
    public ILoadBalancer getLoadBalancer() {
        return lb;
    }

    /**
     * 目标：自定义负载均衡策略：从所有可用的provider中排除掉指定端口号的provider，剩余provider进行随机选择
     * 实现步骤：
     * 1.获取到所有Server
     * 2.从所有Server中排除掉指定端口的Server后，剩余的Server
     * 3.从剩余Server中随机选择一个Server
     */
    @Override
    public Server choose(Object key) {
        // 1.获取到所有Server
        List<Server> servers = lb.getReachableServers();
        // 2.从所有Server中排除掉指定端口的Server后，剩余的Server
        List<Server> availableServers = this.getAvailableServers(servers);
        // 3.从剩余Server中随机选择一个Server
        return this.getAvailableRandomServers(availableServers);
    }

    private List<Server> getAvailableServers(List<Server> servers) {
        // 若没有指定要排除的port，则返回所有Server
        if(excludePorts == null || excludePorts.size() == 0) {
            return servers;
        }
        List<Server> aservers = servers.stream()
                // filter()
                // noneMatch() 只有当流中所有元素都没有匹配上时，才返回true，只要有一个匹配上了，则返回false
                .filter(server -> excludePorts.stream().noneMatch(port -> server.getPort() == port))
                .collect(Collectors.toList());

        return aservers;
    }

    private Server getAvailableRandomServers(List<Server> availableServers) {
        // 获取一个[0,availableServers.size())的随机数
        int index = new Random().nextInt(availableServers.size());
        return availableServers.get(index);
    }
}
