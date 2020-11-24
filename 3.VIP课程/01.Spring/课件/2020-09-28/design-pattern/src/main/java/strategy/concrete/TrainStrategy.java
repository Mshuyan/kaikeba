package strategy.concrete;

import strategy.abs.TravelStrategy;

/***
 * 具体策略类(ConcreteStrategy)
 * 
 * @author think
 *
 */
public class TrainStrategy implements TravelStrategy {

	@Override
	public void travelWay() {
		System.out.println("旅游方式选择：乘坐火车");
	}

	@Override
	public boolean isOK(int type) {
		if (type > 200 && type < 800) {
			return true;
		}
		return false;
	}

}
