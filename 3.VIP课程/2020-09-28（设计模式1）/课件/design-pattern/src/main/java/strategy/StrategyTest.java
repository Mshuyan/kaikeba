package strategy;

import org.junit.Test;

import strategy.concrete.AirPlanelStrategy;
import strategy.concrete.BicycleStrategy;
import strategy.concrete.TrainStrategy;

/**
 * 测试类
 * 
 * @author think
 *
 */
public class StrategyTest {

	@Test
	public void test() {
		// 环境类
		PersonContext xiaozhu = new PersonContext();

		// 太远了，需要做飞机
		xiaozhu.travel(1000);

		// 不太远，飞机太贵，选择火车
		xiaozhu.travel(300);

		// 很近，直接选择自行车
		xiaozhu.travel(5);
	}

	@Test
	public void test2() {
		// 环境类
		AirPlanelStrategy airPlanelStrategy = new AirPlanelStrategy();
		
		PersonContext2 xiaozhu = new PersonContext2(airPlanelStrategy);
		// 太远了，需要做飞机
		xiaozhu.travel();

		// 环境类
		TrainStrategy trainStrategy = new TrainStrategy();
		xiaozhu = new PersonContext2(trainStrategy);
		// 太远了，需要做飞机
		xiaozhu.travel();

		// 环境类
		BicycleStrategy bicycleStrategy = new BicycleStrategy();
		xiaozhu = new PersonContext2(bicycleStrategy);
		// 太远了，需要做飞机
		xiaozhu.travel();

	}
}
