package concurrency.part13_lock_api;

public class Main {
    /*
     * Purpose of a Lock
     * .................
     *
     * The purpose of a lock is to control access to a shared resource by multiple threads
     *
     * Limitations of the Monitor Lock
     * ...............................
     *
     * The monitor lock is pretty easy to use, but it does have limitations
     *  - No way to test if the intrinsic lock has already been acquired
     *  - No way to interrupt a blocked thread
     *  - Not an easy way to debug, or examine the intrinsic lock
     *  - Intrinsic lock is an exclusive lock - 1 thread unconditionally acquires a lock & excludes all other threads from acquiring any other kind of lock
     *
     *
     * java.util.concurrent.locks package
     * ..................................
     * Java introduced additional locking feature, to try to address some of these concerns
     * JDK 5 gave us the java.util.concurrent package
     * This provided developers with some additional solutions to prevent problems in a multi-threading environment
     * Included as part of this is the locks package
     * The Lock Interface,and some of the provided implementations can give us a bit more control and flexibility over locking and when and how to
     *  block threads
     * Any class that implements this interface, is required to override, and provide code for the 6 abstract ()s
     *
     *
     * Implementation
     * ..............
     *
     * Use MessageRepository Example , let's see how we'd start implementing this, with an explicit locking mechanism using a Class
     *  that implements Lock
     * Add a Lock to the MessageRepository Class asa private final field
     *  - Its type is the Interface type, so simply lock there
     *  - Initialized a new instance of ReentrantLock
     *
     * This lock is Reentrant and mutually exclusive, so it has the basic behavior as the intrinsic lock
     * Remove synchronized keywords from read()
     *
     * Running this:-
     *  - Throws an exc from the MessageReader Class originating from the read() where we call the wait() on this obj
     *  - Since this () isn't synchronized, the thread that's reading the msg , never acquires the monitor lock
     *  - You can't call wait() or notifyAll() without exceptions being thrown here, if your curr thread doesn't have that lock or is the owner of the lock
     *  - The wait and notifyAll ()s are only used for the intrinsic lock
     *  - There's another problem since after throwing an error, the code is still running which is a diff problem altogether
     *      - The reader thread has had an exception and has actually been shut down because of it
     *      - The writer thread is hanging out in its while loop waiting for a reader thread to flip the flag which ain't going happen
     *      - Let's look at a way to deal with this situation, when 1 thread gets an exception
     *          (really not related to explicit locks though it's important to know what to do here)
     *      - The Thread Class has a () on it called setUncaughtExceptionHandler
     *
     * setUncaughtExceptionHandler
     * ....................
     * This () takes a single argument, an interface type, that has a single abstract ()
     * This means we can pass a lambda expression
     * This () has 2 parameters, the curr thread and the exc that was thrown
     * Will insert this code btwn Thread declaration and the lines where we call start() on each thread
     *  - First, call this () on writer and pass it an inline multi-line lambda expression
     *      - pass 2 params (thread and exc)
     *      - print that Writer got an exc and what it is
     *      - Next, check the reader thread if it is still active
     *      - Print that you are going to interrupt it
     *      - Then call interrupt() on the reader thread
     *  - Second , do something similar for reader - setting uncaught exception handler on reader
     *
     *
     * Running this:-
     *  - We get the same exception, but now the thread is going to shut down the writer before it exists
     *  - The writer thread prints it had an interrupted exception, and shuts down smoothly
     *  - The code doesn't hang
     *
     * Fixing the read() now
     *  - Remove wait() and notifyAll() calls
     *  - Replace wait() with Thread.sleep() - so we're polling the hasMessage flag every half a second or so
     *  - Now when we use explicit lock, we have to both lock and unlock the lock instance
     *  - Similar, to using a synchronized statement, we can pick and choose where to do this
     *  - We can start out, like the synchronized () and acquire the lock at the start of this ()
     *      - Add lock.lock
     *      - Then wrap the code beneath in a try {}
     *      - instead of catch use finally clause
     *  - Any time we lock an explicit lock, it's now up to me to explicitly unlock it
     *  - For a monitor lock, this was done for us - Here it is done in the finally clause
     *  - This means, that if the code executes fine, or gets an exception, this finally block is always going to be executed
     *  - This is exactly, where you want your unlock code to be and is considered best practice
     *
     * In essence, what we've done is the same thing we did, when we had this setup as a synchronized ()
     * Like this is what we had before we included wait and notifyAll ()s
     *
     * Running this:-
     *  - We get a deadlock as before
     *  - This means using an explicit lock isn't going to fix all your deadlocks
     *  - But unlike, the implicit lock, this type of lock gives us some options to choose from before a thread acquires the lock and blocks
     */
    public static void main(String[] args) {

        MessageRepository messageRepository = new MessageRepository();

        Thread reader = new Thread(new MessageReader(messageRepository));
        Thread writer = new Thread(new MessageWriter(messageRepository));

        writer.setUncaughtExceptionHandler((thread , exc)->{
            System.out.println("Writer had an exception: "+exc);
            if(reader.isAlive()){
                System.out.println("Going to interrupt the reader ");
                reader.interrupt();
            }
        });

         reader.setUncaughtExceptionHandler((thread , exc)->{
                    System.out.println("Reader had an exception: "+exc);
                    if(writer.isAlive()){
                        System.out.println("Going to interrupt the writer ");
                        writer.interrupt();
                    }
                });

        reader.start();
        writer.start();

    }
}
