package thread;

import entity.User;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author shuyan
 */
public class TestDemo {
    public static void main(String[] args) {
        AtomicStampedReference<User> userAtomicStampedReference = new AtomicStampedReference<>(new User("jack",11),1);
        User reference = userAtomicStampedReference.getReference();
        reference.setAge(100);
        System.out.println(userAtomicStampedReference.getReference());
//        User lileilei = new User("lileilei", 33);
//        boolean compareAndSet = userAtomicStampedReference.compareAndSet(reference, lileilei, 1, 2);
//        System.out.println(compareAndSet); // true
//        System.out.println(userAtomicStampedReference.getReference());
    }
}
