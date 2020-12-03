package thread;

import entity.User;

import java.util.concurrent.atomic.AtomicMarkableReference;
import java.util.concurrent.atomic.AtomicReference;

public class AtomicReferenceDemo {
    public static void main(String[] args) {
        User user1 = new User("Jack",25);
        User user2 = new User("Tom",21);
        User user3 = new User("Jack",25);

        AtomicReference<User> atomicReference = new AtomicReference<>();

        atomicReference.set(user1);
        //CAS操作
        System.out.println(atomicReference.compareAndSet(user3,user2)+"\t"+atomicReference.get()); // true
        //CAS操作
        System.out.println(atomicReference.compareAndSet(user1,user2)+"\t"+atomicReference.get()); //false
    }
}
