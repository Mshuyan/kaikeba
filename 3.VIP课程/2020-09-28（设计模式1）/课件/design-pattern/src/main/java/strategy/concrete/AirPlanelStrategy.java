package strategy.concrete;

import strategy.abs.TravelStrategy;

/***
 * 具体策略类(ConcreteStrategy)
 * @author think
 *
 */
public class AirPlanelStrategy implements TravelStrategy {

	@Override
	public void travelWay() {
		System.out.println("旅游方式选择：乘坐飞机");
	}
	
	@Override
	public boolean isOK(int km) {
		if (km <= 3000 && km >800) {
			return true;
		}
		return false;
	}

}
