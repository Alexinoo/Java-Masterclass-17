package concurrency.part10_producer_consumer_app;

public class MessageRepository {

    private String message;

    private boolean hasMessage = false;

    public synchronized String read(){
        while(!hasMessage){
            // if there is no message to read
            // wait
        }
        hasMessage = false;
        return message;
    }

    public synchronized void write(String message){
        while (hasMessage){
            //if there is a message
            // wait for consumption
        }
        hasMessage = true;
        this.message = message;
    }
}
