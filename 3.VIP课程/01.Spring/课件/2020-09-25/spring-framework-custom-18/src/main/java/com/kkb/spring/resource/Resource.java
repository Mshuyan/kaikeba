package com.kkb.spring.resource;

import java.io.InputStream;

/**
 * 提供对资源的操作功能（资源可能存在于磁盘的默认文件路径中，或者网络中，或者classpath下）
 */
public interface Resource {

    InputStream getResource();
}
