package singleton;

/**
 * 枚举单例
 * 
 * @author 灭霸詹
 *
 */
public enum EnumSingleton {
	INSTANCE;

	public void talk() {
		System.out.println("This is an EnumSingleton " + this.hashCode());
	}
}
