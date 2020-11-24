package com.kkb.spring.test.v18;


import com.kkb.spring.dao.UserDaoImpl;
import com.kkb.spring.ioc.BeanDefinition;
import com.kkb.spring.ioc.PropertyValue;
import com.kkb.spring.ioc.RuntimeBeanReference;
import com.kkb.spring.ioc.TypedStringValue;
import com.kkb.spring.po.User;
import com.kkb.spring.service.UserService;
import com.kkb.spring.service.UserServiceImpl;
import org.apache.commons.dbcp.BasicDataSource;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 使用面向过程思维和配置文件的方式去实现容器化管理Bean
 */
public class TestSpringV1802 {

    // 存储bean的定义信息的集合
    private Map<String, BeanDefinition> beanDefinitions = new HashMap<>();

    // 存储所有的单例Bean实例
    private Map<String,Object> singletonObjects = new HashMap<>();

    @Before
    public void before(){
        //加载XML配置信息，放入Map集合
        // TODO XML解析
        //完成XML解析，其实就是完成BeanDefinition的注册
        // XML解析，解析的结果，放入beanDefinitions中
        String location = "beans.xml";
        // 获取流对象
        InputStream inputStream = getInputStream(location);
        // 创建文档对象
        Document document = createDocument(inputStream);

        // 按照spring定义的标签语义去解析Document文档
        registerBeanDefinitions(document.getRootElement());
    }

    // A 同学
    @Test
    public void test() throws Exception{
//        UserService userService = getUserService();
        UserService userService = (UserService) getBean("userService");

        Map<String,Object> map = new HashMap<>();
        map.put("username","千年老亚瑟");
        List<User> users = userService.queryUsers(map);
        System.out.println(users);
    }

    // B同学
    private UserService getUserService() {
        UserServiceImpl userService = new UserServiceImpl();
        UserDaoImpl userDao = new UserDaoImpl();
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://47.113.81.149:3306/kkb?characterEncoding=utf8");
        dataSource.setUsername("root");
        dataSource.setPassword("kkb0826");

        userDao.setDataSource(dataSource);
        userService.setUserDao(userDao);
        return userService;
    }

    // C同学
    // 问题：1、扩展性不好 2、类的职责很重
//    private Object getBean(String beanName){
//        if ("userService".equals(beanName)){
//            UserServiceImpl userService = new UserServiceImpl();
//            UserDaoImpl userDao = new UserDaoImpl();
//            BasicDataSource dataSource = new BasicDataSource();
//            dataSource.setDriverClassName("com.mysql.jdbc.Driver");
//            dataSource.setUrl("jdbc:mysql://47.113.81.149:3306/kkb?characterEncoding=utf8");
//            dataSource.setUsername("root");
//            dataSource.setPassword("kkb0826");
//
//            userDao.setDataSource(dataSource);
//            userService.setUserDao(userDao);
//            return userService;
//        }else if("itemService".equals(beanName)) {
//            // ......
//        }
//
//        return null;
//    }

    // d同学
    // 解决扩展性和职责太重的方案：
    // 1、使用配置文件（XML）解决扩展性问题，比如要创建的对象，配置对应的信息即可（类的全路径、类的属性信息）
    //   a）类的全路径信息（要创建一个对象，就要对应一个信息）
    //    <bean id="唯一标识" class="要创建的对象的类的全路径信息"></bean>
    //   b）配置类的属性信息
    //    <bean id="唯一标识" class="要创建的对象的类的全路径信息">
    //          <property name="属性名称" value="简单类型的值"></property>
    //          <property name="属性名称" ref="另一个要创建的对象的唯一标识"></property>
    //    </bean>
    // 2、加载XML配置信息
    // 3、创建对应的对象
    private Object getBean(String beanName)throws Exception{
        // 暗号：小鸡炖蘑菇

        // 1、先从单例Bean的map集合中查找
        Object singletonObject = this.singletonObjects.get(beanName);

        // 2、如果存在，则直接返回该bean
        if (singletonObject != null){
            return singletonObject;
        }

        // 3、不存在，则从Bean的定义信息集合中查找对应的BeanDefinition
        BeanDefinition bd = this.beanDefinitions.get(beanName);
        if (bd == null){
            return null;
        }

        // 4、判断该Bean是单例还是多例
//        String scope = bd.getScope();
//        if ("singleton".equals(scope)){
//
//        }else if("prototype".equals(scope)){
//
//        }

        // 5、创建Bean
        if (bd.isSingleton()){
            singletonObject = createBean(bd);
            // 6、创建完Bean，加入单例Bean的集合
            this.singletonObjects.put(beanName,singletonObject);
        }else if (bd.isPrototype()){
            singletonObject = createBean(bd);
        }

        return singletonObject;
    }

    private Object createBean(BeanDefinition bd) throws Exception{
        // 5.1 Bean的实例化（new）
        Object bean = createBeanInstance(bd);
        // 5.2 Bean的属性填充（依赖注入）
        populateBean(bean,bd);
        // 5.3 Bean的初始化（调用初始化方法，完成初始化操作）
        initializeBean(bean,bd);
        return bean;
    }

    private void initializeBean(Object bean, BeanDefinition bd) throws Exception{

        // TODO 处理Aware接口
        // TODO 处理InitializingBean接口的初始化操作

        // 处理init-method标签属性对应的初始化方法
        invokeInitMethod(bean,bd);

    }

    private void invokeInitMethod(Object bean, BeanDefinition bd) throws Exception{
        String initMethod = bd.getInitMethod();
        if (initMethod == null){
            return;
        }
        Class<?> clazzType = bd.getClazzType();
        Method method = clazzType.getDeclaredMethod(initMethod);

        // 完成了初始化方法的调用
        method.invoke(bean);
    }

    private void populateBean(Object bean, BeanDefinition bd) throws Exception{
        List<PropertyValue> propertyValues = bd.getPropertyValues();
        for (PropertyValue pv : propertyValues) {
            String name = pv.getName();
            Object value = pv.getValue();

            // 处理之后的value值
            Object valueToUse = resolveValue(value);

            setProperty(bean,name,valueToUse,bd);
        }
    }

    private void setProperty(Object bean, String name, Object valueToUse, BeanDefinition bd)throws Exception {
        Class<?> clazzType = bd.getClazzType();
        Field field = clazzType.getDeclaredField(name);
        field.setAccessible(true);
        field.set(bean,valueToUse);
    }

    private Object resolveValue(Object value) throws Exception{
        Object valueToUse = null;
        if (value instanceof TypedStringValue){
            TypedStringValue typedStringValue = (TypedStringValue) value;
            String stringValue = typedStringValue.getValue();
            Class<?> targetType = typedStringValue.getTargetType();

            valueToUse = handleType(stringValue,targetType);
        }else if(value instanceof RuntimeBeanReference){
            RuntimeBeanReference beanReference = (RuntimeBeanReference) value;
            String ref = beanReference.getRef();
            // 此处有可能会发生循环依赖问题
            valueToUse = getBean(ref);
        }
        return valueToUse;
    }

    private Object handleType(String stringValue, Class<?> targetType) {
        if (targetType == Integer.class){
            return Integer.parseInt(stringValue);
        }else if (targetType == String.class){
            return stringValue;
        }// ......
        return null;
    }

    private Object createBeanInstance(BeanDefinition bd) throws Exception{
        // TODO 1、通过静态工厂方法去创建Bean的实例
        // TODO 2、通过对象工厂去创建Bean的实例
        // 3、通过构造方法去创建Bean的实例
        Class<?> clazzType = bd.getClazzType();
        // 获取默认的无参构造
        Constructor<?> constructor = clazzType.getDeclaredConstructor();
        Object bean = constructor.newInstance();

        return bean;
    }

    private InputStream getInputStream(String location) {
        return this.getClass().getClassLoader().getResourceAsStream(location);
    }
    private Document createDocument(InputStream inputStream) {
        Document document = null;
        try {
            SAXReader reader = new SAXReader();
            document = reader.read(inputStream);
            return document;
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void registerBeanDefinitions(Element rootElement) {
        // 获取<bean>和自定义标签（比如mvc:interceptors）
        List<Element> elements = rootElement.elements();
        for (Element element : elements) {
            // 获取标签名称
            String name = element.getName();
            if (name.equals("bean")) {
                // 解析默认标签，其实也就是bean标签
                parseDefaultElement(element);
            } else {
                // 解析自定义标签，比如aop:aspect标签
                parseCustomElement(element);
            }
        }
    }

    private void parseCustomElement(Element element) {

    }

    @SuppressWarnings("unchecked")
    private void parseDefaultElement(Element beanElement) {
        try {
            if (beanElement == null) {
                return;
            }
            // 获取id属性
            String id = beanElement.attributeValue("id");

            // 获取name属性
            String name = beanElement.attributeValue("name");

            // 获取class属性
            String clazzName = beanElement.attributeValue("class");
            if (clazzName == null || "".equals(clazzName)) {
                return;
            }

            // 获取init-method属性
            String initMethod = beanElement.attributeValue("init-method");
            // 获取scope属性
            String scope = beanElement.attributeValue("scope");
            scope = scope != null && !scope.equals("") ? scope : "singleton";

            // 获取beanName
            String beanName = id == null ? name : id;
            Class<?> clazzType = Class.forName(clazzName);
            beanName = beanName == null ? clazzType.getSimpleName() : beanName;
            // 创建BeanDefinition对象
            // 此次可以使用构建者模式进行优化
            BeanDefinition beanDefinition = new BeanDefinition(clazzName, beanName);
            beanDefinition.setInitMethod(initMethod);
            beanDefinition.setScope(scope);
            // 获取property子标签集合
            List<Element> propertyElements = beanElement.elements();
            for (Element propertyElement : propertyElements) {
                parsePropertyElement(beanDefinition, propertyElement);
            }

            // 注册BeanDefinition信息
            this.beanDefinitions.put(beanName, beanDefinition);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void parsePropertyElement(BeanDefinition beanDefination, Element propertyElement) {
        if (propertyElement == null)
            return;

        // 获取name属性
        String name = propertyElement.attributeValue("name");
        // 获取value属性
        String value = propertyElement.attributeValue("value");
        // 获取ref属性
        String ref = propertyElement.attributeValue("ref");

        // 如果value和ref都有值，则返回
        if (value != null && !value.equals("") && ref != null && !ref.equals("")) {
            return;
        }

        /**
         * PropertyValue就封装着一个property标签的信息
         */
        PropertyValue pv = null;

        if (value != null && !value.equals("")) {
            // 因为spring配置文件中的value是String类型，而对象中的属性值是各种各样的，所以需要存储类型
            TypedStringValue typeStringValue = new TypedStringValue(value);

            Class<?> targetType = getTypeByFieldName(beanDefination.getClazzName(), name);
            typeStringValue.setTargetType(targetType);

            pv = new PropertyValue(name, typeStringValue);
            beanDefination.addPropertyValue(pv);
        } else if (ref != null && !ref.equals("")) {

            RuntimeBeanReference reference = new RuntimeBeanReference(ref);
            pv = new PropertyValue(name, reference);
            beanDefination.addPropertyValue(pv);
        } else {
            return;
        }
    }
    private Class<?> getTypeByFieldName(String beanClassName, String name) {
        try {
            Class<?> clazz = Class.forName(beanClassName);
            Field field = clazz.getDeclaredField(name);
            return field.getType();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
