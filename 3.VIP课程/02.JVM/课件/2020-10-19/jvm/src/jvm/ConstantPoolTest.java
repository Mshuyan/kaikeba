package jvm;

public class ConstantPoolTest {
	// private boolean boo1 = true;
	// private final boolean boo2 = true;
	// private static boolean boo3 = true;
	// private final static boolean boo4 = false;
	//
	// private short sh1 = 10;
	// private final short sh2 = 20;
	// private static short sh3 = 30;
	// private final static short sh4 = 40;
	//
	// private byte b1 = 10;
	// private final byte b2 = 20;
	// private static byte b3 = 30;
	// private final static byte b4 = 40;
	//
	// private int a = 1000;
	// private final int b = 2000;
	// private static int c = 3000;
	// private final static int d = 4000;
	//
	// private float f1 = 10.0f;
	// private final float f2 = 11.0f;
	// private static float f3 = 12.0f;
	// private static final float f4 = 13.0f;
	//
	// private long l1 = 100;
	// private final long l2 = 200;
	// private static long l3 = 300;
	// private final static long l4 = 400;
	//
	// private double d1 = 100.0d;
	// private final double d2 = 110.0d;
	// private static double d3 = 120.0d;
	// private static final double d4 = 130.0d;
	//
	// private String s1 = "kkb1";
	// private final String s2 = "kkb2";
	// private static String s3 = "kkb3";
	// private static final String s4 = "kkb4";
	//
	// private User u0 ;
	// private User u1 = new User();
	// private final User u2 = new User();
	// private static User u3 = new User();
	// private static final User u4 = new User();

	private int int_num = 110;
	private char char_num = 'a';
	private short short_num = 120;
	private float float_num = 130.0f;
	private double double_num = 140.0;
	private byte byte_num = 111;
	private long long_num = 3333L;
	private long long_delay_num;
	private boolean boolean_flage = true;

	public void init() {
		this.long_delay_num = 5555L;
	}

	// public static void main(String[] args) {
	//
	// }
	//
	// public void m1() {
	// System.out.println("m1");
	// }
}

class User {

}
