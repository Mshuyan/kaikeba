package strategy;

import java.util.ArrayList;
import java.util.List;

import strategy.abs.TravelStrategy;
import strategy.concrete.AirPlanelStrategy;
import strategy.concrete.BicycleStrategy;
import strategy.concrete.CarStrategy;
import strategy.concrete.TrainStrategy;

/**
 * 环境类(Context)
 * 
 * @author think
 *
 */
public class PersonContext {

	// 拥有一个出行策略引用
	private List<TravelStrategy> strategylist;

	public PersonContext() {
		this.strategylist = new ArrayList<>();
		strategylist.add(new AirPlanelStrategy());
		strategylist.add(new TrainStrategy());
		strategylist.add(new CarStrategy());
		strategylist.add(new BicycleStrategy());
	}


	public void travel(int km) {
		// 根据具体策略类，执行对应的出行策略
		/*for (TravelStrategy travelStrategy : strategylist) {
			if (travelStrategy.isOK(type)) {
				travelStrategy.travelWay();
				break;
			}
		}*/
		for (TravelStrategy travelStrategy : strategylist) {
			if (travelStrategy.isOK(km)) {
				travelStrategy.travelWay();
				break;
			}
		}
	}
}
