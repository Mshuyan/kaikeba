package adapter.adapter;

import adapter.adaptee.DBSocket;
import adapter.target.GuoJiSocket;

/**
 * 适配器模式
 * 
 * 		主要作用：将一个类的接口转换成另外一个客户希望的接口
 * 
 * 这个类就相当于实际案例中的电源转换头
 * 
 * @author 怡吾宇
 *
 */
//DBSocket 适配的目标接口
public class DBSocketGuojiSocketAdapter implements GuoJiSocket{

	//被适配的接口
	private  DBSocket dbSocket;
	
	public DBSocketGuojiSocketAdapter(DBSocket dbSocket) {
		this.dbSocket = dbSocket;
	}
	
	
	// 适配之后的方法
	@Override
	public void method() {
		// 实质上调用德国标准
		dbSocket.method();
	}
}
