/**
 * @author shuyan
 */
public class GcTest {
    private static final int _1MB = 1024 * 1024;
    public static void main(String[] args) {
        byte[] b1 = new byte[(int)(2.6 * _1MB)];
        byte[] b4 = new byte[(int)(76 * _1MB)];
        b4 = null;
        byte[] b2 = new byte[(int)(10 * _1MB)];
        b4 = new byte[(int)(76 * _1MB)];
//        b4 = null;
//        byte[] b3 = new byte[(int)(2.6 * _1MB)];
//        b4 = new byte[(int)(76 * _1MB)];
//        b4 = null;
//        byte[] b5 = new byte[(int)(2.6 * _1MB)];
//        b4 = new byte[(int)(76 * _1MB)];
        //byte[] b2 = new byte[(int)(0.4 * _1MB)];
        //byte[] b4 = new byte[(int)(7 * _1MB)];
    }
}
