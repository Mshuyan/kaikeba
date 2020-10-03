package com.kkb.spring.resource;

import java.io.InputStream;

public class ClasspathResource implements Resource{
    // 封装classpath下的路径
    private String location;

    public ClasspathResource(String location) {
        this.location = location;
    }

    @Override
    public InputStream getResource() {
        if (location.startsWith("classpath:")){
            location = location.substring(10);
        }
        return this.getClass().getClassLoader().getResourceAsStream(location);
    }
}
