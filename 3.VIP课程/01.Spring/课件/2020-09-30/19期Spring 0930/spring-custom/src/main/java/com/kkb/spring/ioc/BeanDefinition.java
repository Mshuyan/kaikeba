package com.kkb.spring.ioc;

import java.util.ArrayList;
import java.util.List;

/**
 * <bean>标签的信息
 * 
 * @author 灭霸詹
 *
 */
public class BeanDefinition {
	// bean标签的class属性
	private String clazzName;
	// bean标签的class属性对应的Class对象
	private Class<?> clazzType;
	// bean标签的id属性和name属性都会存储到该属性值，id和name属性是二选一使用的
	private String beanName;
	// 初始化方法名称
	private String initMethod;
	// 该信息是默认的配置，如果不配置就默认是singleton
	private String scope;
	/**
	 * bean中的属性信息
	 */
	private List<PropertyValue> propertyValues = new ArrayList<>();

	private static final String SCOPE_SINGLETON = "singleton";
	private static final String SCOPE_PROTOTYPE = "prototype";

	public BeanDefinition(String clazzName, String beanName) {
		this.beanName = beanName;
		this.clazzName = clazzName;
		this.clazzType = resolveClassName(clazzName);
	}

	private Class<?> resolveClassName(String clazzName) {
		try {
			return Class.forName(clazzName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public String getInitMethod() {
		return initMethod;
	}

	public void setInitMethod(String initMethod) {
		this.initMethod = initMethod;
	}

	public List<PropertyValue> getPropertyValues() {
		return propertyValues;
	}

	public void addPropertyValue(PropertyValue propertyValue) {
		this.propertyValues.add(propertyValue);
	}

	public String getClazzName() {
		return clazzName;
	}

	public void setClazzName(String clazzName) {
		this.clazzName = clazzName;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public void setPropertyValues(List<PropertyValue> propertyValues) {
		this.propertyValues = propertyValues;
	}

	public Class<?> getClazzType() {
		return clazzType;
	}

	public boolean isSingleton() {
		return SCOPE_SINGLETON.equals(this.scope);
	}

	public boolean isPrototype() {
		return SCOPE_PROTOTYPE.equals(this.scope);
	}

}
