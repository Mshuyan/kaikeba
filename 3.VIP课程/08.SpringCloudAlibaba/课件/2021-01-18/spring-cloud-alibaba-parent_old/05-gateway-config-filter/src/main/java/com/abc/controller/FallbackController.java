package com.abc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 网关的服务降级处理类
 */
@RestController
public class FallbackController {

    //服务降级处理方法
    @GetMapping("/fallback")
    public String fallback(){
        return "This is gateway fallback method~~";
    }
}
