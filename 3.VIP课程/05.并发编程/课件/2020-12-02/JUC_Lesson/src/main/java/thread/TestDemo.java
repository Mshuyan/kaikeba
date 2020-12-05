package thread;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author shuyan
 */
@Data
@AllArgsConstructor
class TestEntity {
    private Integer a;
    private Integer b;
}

@SuppressWarnings("AlibabaAvoidManuallyCreateThread")
public class TestDemo {
    public static void main(String[] args) {
        AtomicReference<TestEntity> atomicReference = new AtomicReference<>(new TestEntity(0,0));

        for (int i=0; i<100; i++){
            new Thread(() ->{
                TestEntity expectValue;
                TestEntity updateValue;
                for(int j=0; j<10000; j++) {
                    do {
                        expectValue = atomicReference.get();
                        updateValue = new TestEntity(expectValue.getA() + 1, expectValue.getB() + 1);
                    } while (!atomicReference.compareAndSet(expectValue, updateValue));
                    expectValue = atomicReference.get();
                    int atomicAValue = expectValue.getA();
                    int atomicBValue = expectValue.getB();
                    if(atomicAValue != atomicBValue) {
                        System.out.println("atomicA : " + atomicAValue + " ; atomicB : " + atomicBValue);
                    }
                }
            },"AtomicIntegerThread " + i).start();
        }

    }

    public void test1(){
        AtomicInteger atomicA = new AtomicInteger(0);
        AtomicInteger atomicB = new AtomicInteger(0);

        for (int i=0; i<100; i++){
            new Thread(() ->{
                int expectValue;
                for(int j=0; j<10000; j++) {
                    do {
                        expectValue = atomicA.get();
                    } while (!atomicA.compareAndSet(expectValue, expectValue + 1));
                    do {
                        expectValue = atomicB.get();
                    } while (!atomicB.compareAndSet(expectValue, expectValue + 1));
                    int atomicAValue = atomicA.get();
                    int atomicBValue = atomicB.get();
                    if(atomicAValue != atomicBValue) {
                        System.out.println("atomicA : " + atomicAValue + " ; atomicB : " + atomicBValue);
                    }
                }
            },"AtomicIntegerThread " + i).start();
        }
    }
}
