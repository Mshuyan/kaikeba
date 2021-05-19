package com.abc.controller;

import com.abc.bean.Depart;
import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * 消费者Controller
 */
@RestController
@RequestMapping("/consumer/depart")
public class DepartController {
    //注入RestTemplate模板对象
    @Autowired
    private RestTemplate restTemplate;

    //发送http请求的，http://localhost:8081地址写死，去访问提供者行不行呢？
    //行、不行【】
    //原因1：如果我服务提供者集群部署：无法访问集群
    //原因2：如果服务发布上线，或者后期服务迁移，需要经常性修改配置文件【1万个服务呢】
    //原因：....负载均衡、熔断

    //配置请求地址
    //解决url地址写死的问题：
    // private static final String SERVICE_PROVIDER = "http://localhost:8081";
    //通过注册中心的地址去访问对应的服务：注册中心的机制：注册列表。http://{服务名称}
    private static final String SERVICE_PROVIDER = "http://msc-provider-depart";


    //跨服务新增
    @PostMapping("/save")
    public boolean saveHandle(@RequestBody Depart depart) {
        String url = SERVICE_PROVIDER + "/provider/depart/save";
        //问题：消费者直连提供者行不行？
        Boolean result = restTemplate.postForObject(url, depart, Boolean.class);
        return result;
    }
    //跨服务根据id删除
    @DeleteMapping("/del/{id}")
    public void deleteHandle(@PathVariable("id") int id) {
        String url = SERVICE_PROVIDER + "/provider/depart/del/" + id;
        restTemplate.delete(url);
    }
    //跨服务修改
    @PutMapping("/update")
    public void updateHandle(@RequestBody Depart depart) {
        String url = SERVICE_PROVIDER + "/provider/depart/update";
        restTemplate.put(url, depart, Boolean.class);
    }

//    @GetMapping("/get/{id}")
//    public Depart getHandle(@PathVariable("id") int id) {
//        Entry entry = null;
//        try {
//            entry = SphU.entry("qpsFlowRule");
//
//            String url = SERVICE_PROVIDER + "/provider/depart/get/" + id;
//            return restTemplate.getForObject(url, Depart.class);
//        } catch (BlockException e) {
//            Depart depart = new Depart();
//            depart.setName("我是被限流处理的结果！");
//            return depart;
//        } finally {
//            if (entry != null) {
//                entry.exit();
//            }
//        }
//    }

    //跨服务根据id查询
    //该注解表明当前方法是一个由Sentinel管理的资源，value属性用于指定该资源的名称
    @SentinelResource(value = "qpsFlowRule"
            ,blockHandler = "getHandlerBlock"
            , fallback = "getHandlerFallback")
    @GetMapping("/get/{id}")
    public Depart getHandle(@PathVariable("id") int id) {
        String url = SERVICE_PROVIDER + "/provider/depart/get/" + id;
        Depart depart = restTemplate.getForObject(url, Depart.class);
        return depart;
    }
    //定义限流阻断处理方法：方法的参数和返回值与被降级方法保持一致，参数列表中多加一个BlockException异常参数
    public Depart getHandlerBlock(int id, BlockException be) {
        Depart depart = new Depart();
        depart.setId(id);
        depart.setName("flow-control-" + id);
        return depart;
    }
    //降级处理方法：方法的参数和返回值与被降级方法保持一致
    public Depart getHandlerFallback(int id) {
        Depart depart = new Depart();
        depart.setId(id);
        depart.setName("degrade-method-" + id);
        return depart;
    }


    //跨服务根据列表查询
    @SentinelResource(value = "qpsFlowRule_list")
    @GetMapping("/list")
    public List<Depart> listHandle() {
        String url = SERVICE_PROVIDER + "/provider/depart/list/";
        List list = restTemplate.getForObject(url, List.class);
        return list;
    }
}
