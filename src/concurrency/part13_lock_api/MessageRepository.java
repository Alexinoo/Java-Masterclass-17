package concurrency.part13_lock_api;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MessageRepository {

    private String message;

    private boolean hasMessage = false;

    private final Lock lock = new ReentrantLock();

    public String read(){
       if(lock.tryLock()) {
           try {
               while (!hasMessage) {
                   try {
                       Thread.sleep(500);
                   } catch (InterruptedException e) {
                       throw new RuntimeException(e);
                   }
               }
               hasMessage = false;
           } finally {
               lock.unlock();
           }
       }else{
           System.out.println("** read was blocked "+lock);
           hasMessage = false;
       }

        return message;
    }

    public synchronized void write(String message){
        try {
            if (lock.tryLock(3, TimeUnit.SECONDS)) {
                try {
                    while (hasMessage) {
                        //if there is a message
                        // wait for consumption
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    hasMessage = true;
                } finally {
                    lock.unlock();
                }
            } else {
                System.out.println("** write blocked "+lock);
                hasMessage = true;
            }
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }
        this.message = message;
    }
}
