package strategy.concrete;

import strategy.abs.TravelStrategy;

/***
 * 具体策略类(ConcreteStrategy)
 * 
 * @author think
 *
 */
public class BicycleStrategy implements TravelStrategy {

	@Override
	public void travelWay() {
		System.out.println("旅游方式选择：骑自行车");
	}

	@Override
	public boolean isOK(int km) {
		if (km <= 20) {
			return true;
		}
		return false;
	}

}
