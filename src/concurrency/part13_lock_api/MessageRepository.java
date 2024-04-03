package concurrency.part13_lock_api;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MessageRepository {

    private String message;

    private boolean hasMessage = false;

    private final Lock lock = new ReentrantLock();

    public String read(){
        lock.lock();
        try {
            while(!hasMessage){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            hasMessage = false;
        }finally {
            lock.unlock();
        }

        return message;
    }

    public synchronized void write(String message){
        while (hasMessage){
            //if there is a message
            // wait for consumption
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        hasMessage = true;
        notifyAll();
        this.message = message;
    }
}
