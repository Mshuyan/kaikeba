package strategy.abs;
/**
 * 抽象策略类
 * @author think
 */
public interface TravelStrategy {
	
	//出行方式
	void travelWay();
	
	boolean isOK(int km);
}
