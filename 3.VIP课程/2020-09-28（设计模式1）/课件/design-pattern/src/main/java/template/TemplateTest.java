package template;

import template.father.GetTimeTemplate;
import template.son.CopyFileDemo;
import template.son.ForDemo;

public class TemplateTest {

	public static void main(String[] args) {
		/*GetTime time = new GetTime();
		System.out.println("耗时 "+time.getTime()+" 毫秒");*/
		
		GetTimeTemplate time = new ForDemo();
		System.out.println("耗时 "+time.getTime()+" 毫秒");
		
		GetTimeTemplate time2 = new CopyFileDemo();
		System.out.println("耗时 "+time2.getTime()+" 毫秒");
		
	}

}
