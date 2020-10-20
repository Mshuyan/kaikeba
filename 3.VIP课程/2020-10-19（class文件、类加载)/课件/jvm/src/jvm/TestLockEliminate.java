package jvm;

public class TestLockEliminate {
	public static String getString(String s1, String s2) {
		StringBuffer sb = new StringBuffer();
		sb.append(s1);
		sb.append(s2);
		return sb.toString();
	}

	public static void main(String[] args) {
		long tsStart = System.currentTimeMillis();
		for (int i = 0; i < 100000000; i++) {
			getString("TestLockEliminate ", "Suffix");
		}
		System.out.println("一共耗费：" + (System.currentTimeMillis() - tsStart) + " ms");
	//	System.gc();
	}
}
