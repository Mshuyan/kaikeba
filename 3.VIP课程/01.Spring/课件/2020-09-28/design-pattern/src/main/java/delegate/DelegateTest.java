package delegate;

import org.junit.Test;

public class DelegateTest {

	@Test
	public void test() {
		// 成绩是委托人的，干活的是受托人的
		// 对于客户端来说，是感受不到受托人的
		ProjectManager pm = new ProjectManager();
		
		pm.doSomething();
	}
}
