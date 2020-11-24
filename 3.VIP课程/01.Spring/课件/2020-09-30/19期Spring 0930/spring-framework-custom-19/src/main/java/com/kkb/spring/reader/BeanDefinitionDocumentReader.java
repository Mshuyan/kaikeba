package com.kkb.spring.reader;

import org.dom4j.Element;

public interface BeanDefinitionDocumentReader {
    void registerBeanDefinitions(Element rootElement);
}
