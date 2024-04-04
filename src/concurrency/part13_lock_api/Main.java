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
     *  - But unlike, the implicit lock, this type of lock gives us some options to choose from before a
     *    thread acquires the lock and blocks
     *
     * java.util.concurrent.locks CONTINUED......
     * You remember we included a lock an unlock call in the read() removing the synchronized keyword from its declaration
     * Also removed wait and notify ()s call since they can't be used outside of a synchronized () or statement
     * Running the code though, simply produced a deadlock
     * And so we might be asking ourselves, what is the advantage of using explicit lock then
     *
     * Instead of the lock(), we also have some other ()s available and one of them is tryLock()
     *
     * tryLock()
     * ........
     * Returns a boolean
     * use it on read()
     * Will wrap the try {} in an if statement
     * Tests if another thread has already got this lock
     * If another thread doesn't, then this () will acquire the lock and returns true back
     * If another thread does have the lock, it will return false
     * Then on the else statement
     *  - If we can't acquire the lock then we need to figure out something else to do
     *  - Will just print that "read was blocked"
     *  - Also set hasMessage flag to false
     *
     * use it on write()
     *  - Do something similar
     *
     *
     * Running this :-
     *  - We see that sometimes or Producer and Consumer code couldn't get a lock, but still manages to come out OK
     *  - May not always be true depending on the timing of the reads and the writes but the code doesn't lock the app
     *  - Still, this might not be an acceptable solution, if messages do eventually get skipped
     *
     * Another Option
     * Is to use an overloaded version of the tryLock()
     * This takes a no and a timeUnit value as arg
     * Will do this in the write()
     *  - Pass 3 as the 1st arg
     *  - Pass TimeUnit.SECONDS as the 2nd arg
     * This means , the code will wait up until 3 sec have passed, attempting to acquire
     *  the lock from the Consumer code
     * Unlike, the tryLock() with no args, the overloaded one throws an exception - so will wrap it in a try-catch
     *  - Catch InterruptedExc and throw it as a RuntimeException
     *
     * But why does this throw an exception anyway?
     * Well unlike the simple tryLock() , it's possible to interrupt this thread, before it times out
     *
     * Running this :-
     *  - You might notice there is a bit of a wait, before the 1st or 2nd msg because the reader got the lock before
     *     the writer
     *  - The writer is going to wait up to 3 sec to attempt to acquire that lock
     *
     *
     * In addition, to these ()s, the Reentrant lock can offer invaluable info about the lock
     * To illustrate this,
     *  - Go to main() and pass thread names to both of the thread constructors
     *  - Set them as "Reader" and "Writer"
     *  - Then go to the read() in the MessageRepository
     *      - After the finally clause in the print statement - include the lock itself
     *  - Do the same thing, in the write()
     *
     *
     * Running this:-
     *  - Now in addition to knowing that the lock has already been acquired, you can see exactly which thread
     *    actually does have the lock, which of course is just the opposite in this case
     *  - This would be useful info, if you were managing many threads for example
     *
     * Lock Hold Count
     * ...............
     * The hold count of a lock counts the no of times that a single thread, the owner of the lock has acquired the lock
     *  - When a thread acquires the lock for the first time, the lock hold's count is set to 1
     *  - If a lock is reentrant, and a thread, reacquires the same lock, the lock's hold count will get incremented
     *  - When a thread release a lock, the lock's hold count is decremented
     *  - A lock is only released when it's hold count becomes 0
     * Because of this, it's really important to include a call to the unlock() in a finally clause, of any code that
     *  will acquire a lock, even if it's reentrant
     *
     * Advantages of using Lock Implementations
     * .........................................
     * - Explicit Control - Have control over when to acquire and release locks, making it easier to avoid deadlocks
     *                      and manage other concurrency challenges
     * - Timeouts - Allows you to attempt to acquire a lock without blocking indefinitely
     * - Along with timeouts, Interruption Locking lets you handle interruptions during acquisition more gracefully
     * - Improved debugging ()s let you query the no of waiting threads, and check if a thread holds a lock
     *
     * In many cases, the monitor lock is sufficient, and easier to use for basic synchronization needs
     * However, when dealing with more complex concurrency scenarios, you may need fine-grained control, and
     *  explicit locks can be another tool, to ensure thread safety
     * In addition to locks, the java.util.concurrent package offers us concurrent collections and classes for managing
     *  a group of concurrent threads
     *
     * We'll go through those classes for managing threads.
     *
     * These implement an Interface appropriately called the Executor
     *
     */
    public static void main(String[] args) {

        MessageRepository messageRepository = new MessageRepository();

        Thread reader = new Thread(new MessageReader(messageRepository),"Reader");
        Thread writer = new Thread(new MessageWriter(messageRepository),"Writer");

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
