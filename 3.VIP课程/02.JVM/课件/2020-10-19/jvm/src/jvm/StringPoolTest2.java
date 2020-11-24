package jvm;

import java.util.Random;

public class StringPoolTest2 {

	static final int MAX = 1000 * 10000;
	static final String[] arr = new String[MAX];

	public static void main(String[] args) throws Exception {
		Integer[] DB_DATA = new Integer[10];
		Random random = new Random(10 * 10000);
		for (int i = 0; i < DB_DATA.length; i++) {
			DB_DATA[i] = random.nextInt();
			System.out.println(DB_DATA[i]);
		}
		for (int i = 0; i < MAX; i++) {
			arr[i] = new String(String.valueOf(DB_DATA[i % DB_DATA.length])).intern();
			// arr[i] = new String(String.valueOf(DB_DATA[i % DB_DATA.length]));
			// arr[i] = String.valueOf(DB_DATA[i % DB_DATA.length]).intern();
			// arr[i] = String.valueOf(DB_DATA[i % DB_DATA.length]);
		}
		System.out.println("等待中。。。。。");
		System.in.read();
	}
}
