package com.kkb.spring.ioc;

/**
 * 封装<bean>标签中子标签<property>的ref属性值
 * 
 * @author 灭霸詹
 *
 */
public class RuntimeBeanReference {

	// ref的属性值
	private String ref;

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public RuntimeBeanReference(String ref) {
		super();
		this.ref = ref;
	}

}
