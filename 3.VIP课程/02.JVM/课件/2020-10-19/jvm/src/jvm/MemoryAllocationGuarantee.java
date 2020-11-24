package jvm;

/**
 * 内存分配担保案例
 * 
 * @author 灭霸詹
 *
 */
public class MemoryAllocationGuarantee {

	private static final int _1MB = 1024 * 1024;

	public static void memoryAllocation() {
		byte[] allocation1, allocation2, allocation3, allocation4;
		allocation1 = new byte[2 * _1MB];
		allocation2 = new byte[2 * _1MB];
		allocation3 = new byte[2 * _1MB];
		allocation4 = new byte[3 * _1MB];
	}

	public static void main(String[] args) {
		memoryAllocation();
	}
}
