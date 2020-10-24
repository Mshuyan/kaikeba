import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author shuyan
 */
public class StringTest {

    static final Unsafe UNSAFE = getUnsafe();
    static final boolean IS64BIT = true;

    public static void main(String... args) {
        String s3 = new String("1") + new String("1");
        
        String s4 = "11";
        s3.intern();
        System.out.println(s3 == s4);

}

    public static void printAddresses(String label, Object... objects) {
        System.out.print(label + ": 0x");
        long last = 0;
        int offset = UNSAFE.arrayBaseOffset(objects.getClass());
        int scale = UNSAFE.arrayIndexScale(objects.getClass());
        switch (scale) {
            case 4:
                long factor = IS64BIT ? 8 : 1;
                final long i1 = (UNSAFE.getInt(objects, offset) & 0xFFFFFFFFL) * factor;
                System.out.print(Long.toHexString(i1));
                last = i1;
                for (int i = 1; i < objects.length; i++) {
                    final long i2 = (UNSAFE.getInt(objects, offset + i * 4) & 0xFFFFFFFFL) * factor;
                    if (i2 > last)
                        System.out.print(", +" + Long.toHexString(i2 - last));
                    else
                        System.out.print(", -" + Long.toHexString( last - i2));
                    last = i2;
                }
                break;
            case 8:
                throw new AssertionError("Not supported");
        }
        System.out.println();
    }

    private static Unsafe getUnsafe() {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            return (Unsafe) theUnsafe.get(null);
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }
}
