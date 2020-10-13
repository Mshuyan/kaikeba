package template.father;

/**
 * 模板设计模式
 * 
 * 抽取一个抽象模板类，同时定义模板方法 对于模板方法的实现，在子类中去实现
 * 
 * @author 怡吾宇
 *
 */
public abstract class GetTimeTemplate {

	// 固定流程方法
	public long getTime() {
		// 获取起始时间
		long t1 = System.currentTimeMillis();

		// 模板方法
		code();

		// 获取结束时间
		long t2 = System.currentTimeMillis();
		return t2 - t1;
	}

	// 钩子方法
	public abstract void code();
}
