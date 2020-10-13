package reflect.case7;

import java.lang.reflect.Field;

public class ReflectTools {

	private ReflectTools(){	}
	
	public static void setProperty(Object obj,String fieldName,Object value) {
		try {
			//先去获取Class对象
			Class c = obj.getClass();
			//使用反射获取指定属性
			Field field = c.getDeclaredField(fieldName);

			//暴力访问
			field.setAccessible(true);
			//修改属性值
			field.set(obj, value);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
