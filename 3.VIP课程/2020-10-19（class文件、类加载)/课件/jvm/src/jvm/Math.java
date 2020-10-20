package jvm; 


public class Math { 
	
	private final int a = 10;
	private final int b = 10;
	private int c2 = 127;
	private final Integer c3 = 128;
	private int c1 = 20;
	private float c = 11f;
	private float d = 11f;
	private float e = 11f;

	
	public static void main(String[] args) {
//		int a = 1;
//		int b = 2;
//		int c = (a + b) * 10;


		Integer i = 15;

		Integer i1 = 127;
		Integer i2 = 127;

		System.out.println(i1.equals(i2));

		Integer i3 = 128;
		Integer i4 = 128;

		System.out.println(i3.equals(i4));


	}
}
