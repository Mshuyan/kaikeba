package thread;

import entity.User;

import java.util.concurrent.atomic.AtomicReference;

public class AtomicReferenceDemo {
    public static void main(String[] args) {
        User user1 = new User("Jack",25);
        User user2 = new User("Tom",21);

        AtomicReference<User> atomicReference = new AtomicReference<>();

        atomicReference.set(user1);

        System.out.println(atomicReference.compareAndSet(user1,user2)+"\t"+atomicReference.get()); // true
        System.out.println(atomicReference.compareAndSet(user1,user2)+"\t"+atomicReference.get()); //false
    }
}
