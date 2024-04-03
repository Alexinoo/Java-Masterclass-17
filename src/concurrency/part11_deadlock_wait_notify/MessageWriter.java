package concurrency.part11_deadlock_wait_notify;

import java.util.Random;

public class MessageWriter implements Runnable{

    private MessageRepository outgoingMessage;
    private final String text = """
                Humpty Dumpty sat on a wall.
                Humpty Dumpty had a great fall.
                All the king’s horses and all the king’s men.
                Couldn’t put Humpty together again.
                """;

    public MessageWriter(MessageRepository outgoingMessage) {
        this.outgoingMessage = outgoingMessage;
    }

    @Override
    public void run() {
        Random random = new Random();
        String[] lines = text.split("\n");

        for (int i = 0; i < lines.length; i++) {
            outgoingMessage.write(lines[i]);

            try {
                Thread.sleep(random.nextInt(500,2000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        outgoingMessage.write("Finished");

    }
}
