package concurrency.part11_deadlock_wait_notify;

public class MessageRepository {

    private String message;

    private boolean hasMessage = false;

    public synchronized String read(){
        while(!hasMessage){
            // if there is no message to read
            // wait
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        hasMessage = false;
        notifyAll();
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
