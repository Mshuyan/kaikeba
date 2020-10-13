package strategy.concrete;

import strategy.abs.TravelStrategy;

/***
 * 具体策略类(ConcreteStrategy)
 * 
 * @author think
 *
 */
public class CarStrategy implements TravelStrategy {

	@Override
	public void travelWay() {
		System.out.println("旅游方式选择：乘坐汽车");
	}

	@Override
	public boolean isOK(int type) {
		if (type >= 20 && type < 200) {
			return true;
		}
		return false;
	}

}
