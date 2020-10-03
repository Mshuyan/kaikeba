package com.sugo.seckill.aop;
import java.lang.annotation.*;

/**
 * 同步锁 AOP
 * 创建者	hubin
 * 创建时间	2020年6月10号
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})    
@Retention(RetentionPolicy.RUNTIME)    
@Documented    
public  @interface Servicelock { 
	 String description()  default "";
}
