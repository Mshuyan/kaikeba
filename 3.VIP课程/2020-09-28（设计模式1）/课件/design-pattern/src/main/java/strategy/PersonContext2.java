package strategy;

import strategy.abs.TravelStrategy;

/**
 * 环境类(Context)
 * 
 * @author think
 *
 */
public class PersonContext2 {

	// 拥有一个出行策略引用
	private TravelStrategy travelStrategy;
	
	public PersonContext2(TravelStrategy travelStrategy) {
		this.travelStrategy = travelStrategy;
	}

	public void travel() {
		travelStrategy.travelWay();
	}
}
