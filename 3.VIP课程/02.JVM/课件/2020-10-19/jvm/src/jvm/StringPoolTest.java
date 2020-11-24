package jvm;

public class StringPoolTest {

	public static void main(String[] args) {
		String str1 = "abc";
		String str2 = new String("abc");
		System.out.println(str1 == str2);

		String str3 = new String("abc");
		System.out.println(str3 == str2);

		String str4 = "a" + "b";
		System.out.println(str4 == "ab");

		final String s = "a";
		String str5 = s + "b";
		System.out.println(str5 == "ab");

		String s1 = "a";
		String s2 = "b";
		String str6 = s1 + s2;
		System.out.println(str6 == "ab");

		String str7 = "abc".substring(0, 2);
		System.out.println(str7 == "ab");

		String str8 = "abc".toUpperCase();
		System.out.println(str8 == "ABC");

		String s3 = "ab";
		String s4 = "ab" + getString();
		System.out.println(s3 == s4);

		String s5 = "a";
		String s6 = "abc";
		String s7 = s5 + "bc";
		System.out.println(s6 == s7.intern());

		// 为了方便查看堆内存中对象个数，线程sleep
		try {
			Thread.sleep(100000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}

	private static String getString() {
		return "c";
	}
}
