package com.mp.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
/**
 * 雄哥按：生产环境使用需经过详细的测试
 * @author itXiongGe
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.mp.demo.dao"}) //扫描DAO
@EnableScheduling
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
