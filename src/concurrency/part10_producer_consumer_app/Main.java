package concurrency.part10_producer_consumer_app;

public class Main {
    /*
     * Producer Consumer Application
     *
     * Has a class that produces data, so the producer
     * Has a class that reads the data, or consumes it in some way, the Consumer
     *
     * Create 3 Classes
     * MessageRepository Class (shared resource) - hold a single message
     *  - Fields
     *      - message : String
     *          - shared resource
     *      - hasMessage : boolean
     *          - indicate to both threads whether there is work for them to do
     *          - if false, the Producer can populate the shared msg
     *          - if true, the Consumer can read the msg
     *
     *  - Methods
     *      - read()
     *          - Since the message is a shared resource, synchronize the read()
     *          - When the consumer class calls this (), it will wait until there's a msg to read
     *          - Use a while loop on the msg flag
     *          - If there's no msg, it will stay on the while loop - make it an empty block
     *          - Once the msg has been successfully retrieved, set hasMessage to false
     *          - Returns the message to the consumer, who is waiting it
     *
     *      - write()
     *          - The message is a shared resource, synchronize the write()
     *          - Takes a string as an argument
     *          - If there's already a msg in the repository, it will hang and wait, presumably until the Consumer
     *              has a chance to read the msg and set this flag to false
     *          - Once there is no msg, meaning it has been consumed or retrieved, set hasMessage flag to true
     *              since this code sets a new msg
     *          - Set the message here as the final step
     *
     * That's the shared resource both the consumer and the producer are interested in
     *
     *
     * PRODUCER CLASS
     * ..............
     * MessageWriter Class - implements Runnable
     *  - Fields
     *      - outgoingMessage : MessageRepository
     *      - text : String - use  textBlock
     *  - Constructor()
     *      - takes a message repository as it's arg
     *  - implement run()
     *      - Use Random to randomize the time that the thread sleeps
     *      - Get the lines of text by splitting the text block by the new line char
     *      - loop through the lines using a for loop
     *          - write each line of text as a new msg to the outgoing msg repository instance
     *          - Wait between half and 2 sec (random interval) to write to the msg
     *              - write a msg and waits a bit before writing another one
     *              - this gives the consumer a bit of time to read it
     *          - Catch InterruptedExc
     *          - Write Finished once done writing to the message
     *
     *
     * CONSUMER CLASS
     * ..............
     * MessageReader Class - implements Runnable
     *  - Fields
     *      - incomingMessage : MessageRepository
     * - Constructor()
     *      - no args
     *  - implement run()
     *      - use Random to randomize the sleep time
     *      - set latestMessage to an empty string
     *      - use a do while loop
     *          - sleep for a sec and wait for the producer to write a message before attempting to read it
     *          - after half - 2 sec - read the incoming message abd assign it to latestMessage
     *          - print it out
     *          - keep looping until latestMessage != "Finished
     *
     * Now that we have the Producer and Consumer classes, it's time to start the up, working asynchronously
     *
     *
     * Main()
     * Create an instance of the shared obj
     *  - an instance of the MessageRepository Class
     *
     * Two threads
     *  - MessageReader
     *      - create a thread instance and pass to the constructor messageReader runnable class passing it repository instance
     *  - MessageWriter
     *      - do something similar as above
     * Both instance are working with an instance of the MessageRepository Class
     * Call start on both var(s)
     * 
     * 
     * Running this :-
     *  - Usually, get 1 or 2 lines printed and then the code hangs
     *  - You might get msgs, or have the code complete with no issues at all
     *
     * This is the maddening effect of a concurrent application, which has a problem
     * We shouldn't hae to wait more than 2 sec for something to happen, so we know we've got a problem
     *
     * There are several problems that can occur in a multithreaded application, that are pretty undesirable
     * These situations are called deadlock, live lock and starvation
     *
     * In this case, we have created an application that can produce a deadlock
     *
     *
     * Deadlock
     * ........
     * A deadlock usually occurs, when you have 2 or more threads accessing single/multiple shared resources
     * Can also occur in a scenario of a single resource with multiple synchronized ()s
     *
     */

    public static void main(String[] args) {
        MessageRepository messageRepository = new MessageRepository();

        Thread reader = new Thread(new MessageReader(messageRepository));
        Thread writer = new Thread(new MessageWriter(messageRepository));

        reader.start();
        writer.start();
    }
}
