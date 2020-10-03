package proxy.target;

import java.lang.reflect.InvocationHandler;

public class $Proxy11 implements UserService {

	// private UserService service = new UserServiceImpl();
	private InvocationHandler h;

	@Override
	public void saveUser() {
		// 调用增强功能
		// 调用目标对象的目标方法
		// this.h.invoke(this, "saveUser对应的Method方法", null);
		// service.saveUser();
		// h.invoke(proxy, method, args);
	}

	public void saveUser1() {
		// 调用增强功能
		// 调用目标对象的目标方法
		// this.h.invoke(this, "saveUser1对应的Method方法", args);
		// service.saveUser();
		// h.invoke(proxy, method, args);
	}

	public void saveUser2() {
		// 调用增强功能
		// 调用目标对象的目标方法
		// this.h.invoke(this, "saveUser2对应的Method方法", args);
		// service.saveUser();
		// h.invoke(proxy, method, args);
	}

}
