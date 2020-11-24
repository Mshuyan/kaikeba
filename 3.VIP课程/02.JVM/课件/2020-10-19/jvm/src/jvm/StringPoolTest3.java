package jvm;

public class StringPoolTest3 {

	public static void main(String[] args) throws Exception {
		String s1 = new String("aa") + new String("bb");
		String s3 = s1.intern();
		String s2 = new String("aa") + new String("bb");
		String s4 = s2.intern();
		System.out.println(s1 == s2);
		System.out.println(s1 == s3);
		System.out.println(s2 == s4);
		System.out.println(s3 == s4);
		System.in.read();
		
	}
}
