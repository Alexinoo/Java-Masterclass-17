package concurrency.part13_lock_api;

import java.util.Random;

public class MessageReader implements Runnable{

    private MessageRepository incomingMessage;

    public MessageReader(MessageRepository incomingMessage) {
        this.incomingMessage = incomingMessage;
    }

    @Override
    public void run() {
        Random random = new Random();
        String latestMessage = "";

        do {
            try {
                Thread.sleep(random.nextInt(500,2000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            latestMessage = incomingMessage.read();
            System.out.println(latestMessage);
        }while (!latestMessage.equals("Finished") );
    }
}
