package com.abc.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("info")
public class ShowInfoController {
    //..//4.在05-showinfo中，打印request的参数打印一下
    @RequestMapping("header")
    public String header(HttpServletRequest request) {
        return "header  x-request-red : " + request.getHeader("x-request-red");
    }

    @RequestMapping("time")
    public String time(HttpServletRequest request) {
        return "time : " + System.currentTimeMillis();
    }
}
