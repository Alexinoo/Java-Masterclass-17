package concurrency.part11_deadlock_wait_notify;

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
     *
     *
     *
     *
     *
     * Avoid DeadLocks with wait() and notify()
     * This happens on a 2 synchronized ()s on a shared resource
     * One process is waiting for another process to do something, but it can't because the first process won't release the monitor lock
     *
     * Object's Class wait(), notify(), and notifyALl()
     * wait(), notify(), and notifyALl()
     *  - used to manage monitor lock situations, to prevent threads from blocking indefinitely
     *  - since these ()s are on Object class, any instance of any class, can execute these ()s, from within a synchronized () or statement
     *
     * wait()
     *  - The wait() will put the current thread in a wait queue, for the obj it might have a lock on.
     *  - This frees up the obj(s) lock to be acquired by other threads, while that thread is in the waiting state or set
     *  - The thread which calls wait will lie dormant, until something happens
     *  - The thread that had the lock relinquishes the lock when it calls wait, but only until it gets woken up by some outside event
     *      - events include being notified by other thread's that something's happened when those threads broadcast either notify() or notifyAll()
     *      - a thread could be Interrupted
     *      - an overloaded version of wait(), which if it times out, will wake up this thread
     *      - if the thread is awakened spuriously
     *
     * notify()
     *  - Wakes up a single thread
     * But how does it know which one ?
     * The choice is arbitrary
     * If a thread broadcasts notify(), and there are 2 or 3 threads in the wait set, any of those threads might get notified, but only just 1 of them
     * So it might not be the one you really want to notify at all
     * It could be another thread completely unrelated to the process at hand
     * There are some good use cases fro this notify(), but it's not what we want in our case
     *
     * notifyAll()
     *  - Wakes up all threads that are waiting on the obj monitor
     *  - They can't acquire the lock , before the notifying thread releases the lock
     *
     *
     * Applying to our Producer-Consumer application
     * Any code in a synchronized (), that's sitting in a loop, and waiting for something to change should be called in a waiting ()
     * This will suspend its synchronization claims on the obj, while the thread waits for some conditions to be met
     * In other words, it won't block other threads
     *
     * This needs to be done in the MessageRepository Class , in both the read and write ()s where we have the while loops
     *
     * read()
     *  - add wait() inside the while
     *  - catch possible InterruptedExc
     *  - add notifyAll() outside the loop and after the code toggles the Message flag
     *      - is the trigger another thread is waiting on
     * write()
     *  - do something similar for this ()
     *
     * It's conventional to use notifyALl
     *
     * Running this:-
     *  - And now we can see all the line sin the nursery rhymes followed by the "Finished" message which is a good sign
     *  - Takes a second or 2 to process each msg & we are consistently getting the Finished msg, and the app exits smoothly
     *
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
