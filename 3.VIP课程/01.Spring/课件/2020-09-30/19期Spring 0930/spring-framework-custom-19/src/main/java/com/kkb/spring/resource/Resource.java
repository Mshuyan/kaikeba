package com.kkb.spring.resource;

import java.io.InputStream;

/**
 * 定义访问资源的统一接口
 */
public interface Resource {

    InputStream getInputStream();
}
