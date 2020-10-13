package decorate;
/**
 * 目标类
 * @author think
 *
 */
public class HuaWeiPhone implements PhoneInterface {
	@Override
	public void call() {
		System.out.println("使用华为手机打电话");
	}

}
