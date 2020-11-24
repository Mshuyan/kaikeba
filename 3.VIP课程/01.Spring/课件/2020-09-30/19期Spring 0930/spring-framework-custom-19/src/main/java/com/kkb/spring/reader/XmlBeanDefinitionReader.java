package com.kkb.spring.reader;

import com.kkb.spring.factory.support.DefaultListableBeanFactory;
import com.kkb.spring.registry.BeanDefinitionRegistry;
import com.kkb.spring.resource.Resource;
import com.kkb.spring.utils.DocumentUtils;
import org.dom4j.Document;

import java.io.InputStream;

/**
 * 专门针对XML中定义的BeanDefinition进行阅读的阅读器（针对XML资源）
 */
public class XmlBeanDefinitionReader {

    private BeanDefinitionRegistry registry;

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    public void loadBeanDefinitions(Resource resource){
        doLoadBeanDefinitions(resource);
    }

    public void loadBeanDefinitions(String location){
        Resource resource = null;
        loadBeanDefinitions(resource);
    }

    private void doLoadBeanDefinitions(Resource resource){
        InputStream inputStream = resource.getInputStream();

        // 得到对象XML文件的Document对象（
        Document document = DocumentUtils.getDocument(inputStream);
        // 按照spring语义解析文档
        BeanDefinitionDocumentReader documentReader = new DefaultBeanDefinitionDocumentReader(registry);
        documentReader.registerBeanDefinitions(document.getRootElement());
    }
}
