package com.kkb.spring.resource;

import java.io.InputStream;

/**
 * 封装classpath路径下保存的xml配置文件的信息
 */
public class ClasspathResource implements Resource{

    // classpath下的资源路径
    private String resource;

    public ClasspathResource(String resource) {
        this.resource = resource;
    }

    @Override
    public InputStream getInputStream() {
        return this.getClass().getClassLoader().getResourceAsStream(resource);
    }
}
