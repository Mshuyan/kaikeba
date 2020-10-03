package com.kkb.spring.test.v19;


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
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TestSpringV1902 {

    // 存储单例Bean的集合（多例或者叫原型Bean不存储到集合）
    private Map<String, Object> singletonObjects = new HashMap<>();

    // 存储配置文件中的bean的定义信息
    private Map<String, BeanDefinition> beanDefinitions = new HashMap<>();

    @Before
    public void before() {
        // 配置文件的解析，把配置文件中的内容，封装到一个Java对象中保存（BeanDefinition）
        String location="beans.xml";
        // 得到对应文件的流对象
        InputStream inputStream = getResourceAsStream(location);
        // 得到对象XML文件的Document对象（
        Document document = getDocument(inputStream);
        // 按照spring语义解析文档
        loadBeanDefinitions(document.getRootElement());
    }
    // A同学
    @Test
    public void test() {

        // 找B同学写的代码要对象
//        UserService userService = getUserService();
//        UserService userService = (UserService) getObject("userService");

        UserService userService = (UserService) getBean("userService");
        Map<String, Object> map = new HashMap<>();
        map.put("username", "千年老亚瑟");
        List<User> users = userService.queryUsers(map);
        System.out.println(users);
    }

    /**
     *
     * @param rootElement <beans></beans>
     */
    private void loadBeanDefinitions(Element rootElement) {
        List<Element> elements = rootElement.elements();
        for (Element element : elements) {
            if (element.getName().equals("bean")){
                parseDefaultElement(element);
            }else {
                parseCustom(element);
            }
        }
    }

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
    private Class resoleType(String className) {
        try {
            return Class.forName(className);
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    private void parseCustom(Element element) {

    }

    private Document getDocument(InputStream inputStream) {
        try {
            SAXReader reader = new SAXReader();
            return reader.read(inputStream);
        }catch (Exception e){
            e.printStackTrace();
        }
        return  null;
    }

    private InputStream getResourceAsStream(String location) {
        return this.getClass().getClassLoader().getResourceAsStream(location);
    }



    // D同学
    // 通过配置（XML）的方式去解放硬编码和扩展性差的问题
    // XML配置（一个new）
    // <bean id="唯一标识" class="类的全路径">
    //   <property name="属性名称" value="属性值"/>
    // </bean>
    // 思考：配置文件是否是每次getBean都需要解析一次呢？不是
    // 所以说getBean和配置文件的解析工作是两个时机
    private Object getBean(String beanName) {

        // new对象可以通过反射来new
        // new对象---> class.newInstance();
        // 得到class对象 ---> Class.forName("类的全路径");
        // 思考：如何获取某个类的全路径（唯一标识）

        // setter方法--->class.getXXField("属性名称");
        // field.set(对象，属性值)

        // getBean的步骤应该是如何的？
        // 1、从缓存中获取对应的bean实例（map结构）---beanname为key
        Object bean = this.singletonObjects.get(beanName);
        if (bean != null) {
            return bean;
        }
        // 2、如果没有对应的bean实例，此时需要xml解析出来的对应的信息（map结构中的BeanDefinition）--beanname为key
        BeanDefinition bd = this.beanDefinitions.get(beanName);
        if (bd == null) {
            return null;
        }
        // 3、根据BeanDefinition的信息去创建Bean实例
        // 判断要创建的是单例的bean还是多例的bean
//        if ("singleton".equals(bd.getScope())){
        if (bd.isSingleton()) {
            bean = doCreateBean(bd);
            // 细化创建Bean的流程
            // 4、将创建出来的bean实例放入缓存
            this.singletonObjects.put(beanName, bean);
//        }else if ("prototype".equals(bd.getScope())){
        } else if (bd.isPrototype()) {
            bean = doCreateBean(bd);
        }
        return bean;
    }

    private Object doCreateBean(BeanDefinition bd) {

        // 第一步：对象实例化（new）
        Object bean = createInstanceBean(bd);
        // 第二步：依赖注入（属性填充setter）
        populateBean(bean, bd);
        // 第三步：对象初始化（调用初始化方法）
        initializeBean(bean, bd);

        return bean;
    }

    private void initializeBean(Object bean, BeanDefinition bd) {
        //TODO Aware接口（标记接口，BeanFactoryAware-->对带有标记的bean注入一个BeanFactory）


        // 初始化的方式有两种：init-method标签属性指定的方法、InitializingBean接口
        invokeInitMethod(bean, bd);
        // AOP产生代理对象，就是在初始化方法中产生的
    }

    private void invokeInitMethod(Object bean, BeanDefinition bd) {
        String initMethod = bd.getInitMethod();
        if (initMethod == null || "".equals(initMethod)) {
            return;
        }
        try {
            Class<?> clazzType = bd.getClazzType();
            Method method = clazzType.getDeclaredMethod(initMethod);
            method.invoke(bean);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void populateBean(Object bean, BeanDefinition bd) {
        // 思考：spring的依赖注入是通过属性注入的，还是setter方法注入的？
        List<PropertyValue> propertyValues = bd.getPropertyValues();
        for (PropertyValue pv : propertyValues) {
            String name = pv.getName();
            Object value = pv.getValue();// 此时value是TypeStringValue或者RuntimeBeanReference

            // 获取可以完成依赖注入的值
            Object valueToUse = resoleValue(value);

            // 完成属性注入
            setProperty(bean, name, valueToUse);
        }
    }

    private void setProperty(Object bean, String name, Object valueToUse) {
        try {
            Class<?> aClass = bean.getClass();
            Field field = aClass.getDeclaredField(name);
            field.setAccessible(true);
            field.set(bean,valueToUse);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private Object resoleValue(Object value) {
        if (value instanceof TypedStringValue){
            TypedStringValue typedStringValue = (TypedStringValue) value;
            String stringValue = typedStringValue.getValue();
            Class<?> targetType = typedStringValue.getTargetType();
            if (targetType != null){
                // 根据类型做类型处理(使用策略模式优化)
                if (targetType == Integer.class){
                    return Integer.parseInt(stringValue);
                }else if (targetType == String.class){
                    return stringValue;
                }
            }
            return stringValue;
        }else  if (value instanceof RuntimeBeanReference){
            RuntimeBeanReference beanReference = (RuntimeBeanReference) value;
            String ref = beanReference.getRef();
            // 此处会发生循环依赖问题（后面会去讲）
            return getBean(ref);
        }// Map类型、Set类型、List类型
        return null;
    }

    private Object createInstanceBean(BeanDefinition bd) {
        // TODO 可以从实例工厂中获取一个Bean

        // TODO 可以从静态工厂方法中获取一个Bean
        try {
            // 通过构造器去new一个Bean(无参构造，思考：如何实现有参数构造)
            Class<?> clazzType = bd.getClazzType();
            Constructor<?> constructor = clazzType.getDeclaredConstructor();
            return constructor.newInstance();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // B同学
    private UserService getUserService() {
        UserServiceImpl userService = new UserServiceImpl();
        // 第一步：发现了userService不能正常使用，需要注入userDao
        UserDaoImpl userDao = new UserDaoImpl();
        // 第二步：发现了userDao不能正常使用，需要注入dataSource
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://47.113.81.149:3306/kkb?characterEncoding=utf8");
        dataSource.setUsername("root");
        dataSource.setPassword("kkb0826");

        userDao.setDataSource(dataSource);
        userService.setUserDao(userDao);

        return userService;
    }

    // C同学(代码违背了开闭原则)
    private Object getObject(String beanName) {
        if ("userService".equals(beanName)) {
            UserServiceImpl userService = new UserServiceImpl();
            // 第一步：发现了userService不能正常使用，需要注入userDao
            UserDaoImpl userDao = new UserDaoImpl();
            // 第二步：发现了userDao不能正常使用，需要注入dataSource
            BasicDataSource dataSource = new BasicDataSource();
            dataSource.setDriverClassName("com.mysql.jdbc.Driver");
            dataSource.setUrl("jdbc:mysql://47.113.81.149:3306/kkb?characterEncoding=utf8");
            dataSource.setUsername("root");
            dataSource.setPassword("kkb0826");

            userDao.setDataSource(dataSource);
            userService.setUserDao(userDao);

            return userService;
        } else if ("".equals(beanName)) {
            //....
        }
        return null;
    }

}