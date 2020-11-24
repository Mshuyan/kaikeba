package com.kkb.spring.reader;

import com.kkb.spring.factory.support.DefaultListableBeanFactory;
import com.kkb.spring.registry.BeanDefinitionRegistry;
import com.kkb.spring.utils.DocumentUtils;
import org.dom4j.Document;

import java.io.InputStream;

/**
 * 它主要是针对流对象进行读取
 */
public class XmlBeanDefinitionReader {
    private BeanDefinitionRegistry registry;

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    public void loadBeanDefinitions(InputStream inputStream) {
        // 创建文档对象
        Document document = DocumentUtils.getDocument(inputStream);
        XmlBeanDefinitionDocumentReader documentReader = new XmlBeanDefinitionDocumentReader(registry);
        documentReader.registerBeanDefinitions(document.getRootElement());
    }
}
