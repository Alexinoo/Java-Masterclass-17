package concurrency.part7_concurrent_thread_concepts;

import java.util.concurrent.TimeUnit;

public class CachedData {

    /*
     * set up a flag - boolean - initialize it to false
     * Add a () to toggle this flag toggleFlag()
     *  - gives the opposite each time this () is called
     * Then call the accessor () isReady
     *  - returns the flag value
     */
    private volatile boolean flag = false;

    public void toggleFlag(){
        flag = !flag;
    }

    public boolean isReady(){
        return flag;
    }

    /*
     * Then create an instance of this class
     * Then set up a thread that sets up the flag value to the opposite after 1 sec
     * Then print out the value of the flag at that point
     *
     * The second thread is another task that needs to wait, until the first thread is ready
     * Will call it a readerThread
     *  - let's set it to keep looping while the ready flag is false
     *  - Once ready - print statement
     *
     * Start both threads..
     *
     * Running this:-
     *  - The first statement gets printed, indicating that obj(s) flag has been set to true
     *  - However, the code in the 2nd thread seems to be stuck in the loop
     *
     * So what's happened here..?
     * This code is a demonstration of something called Memory inconsistency
     *
     * Memory Consistency Errors
     * ....................
     * The OS may read from heap variables and make a copy of the value, in each thread's own storage cache
     * Each thread has its own small and fast memory storage, that holds its own copy of a shared resource's value
     * One thread can modify a shared variable, but this change might not be immediately reflected or visible on
     *  the heap
     * Instead, its first updated in the thread's local cache
     * When multiple threads are accessing the same data,its possible for operations of one thread to not be visible
     *  to the other thread immediately
     * This is because the OS may not flush or push the 1st thread's changes to the heap, until the thread has finished executing
     * This is called Memory Inconsistency and leads to these kind of errors we are seeing here
     *
     * Looking at this code here, the writerThread sets the flag to true, after a delay of 1 sec using cached.toggleFlag()
     * Meanwhile, the readerThread is actively waiting in it's busy-wait loop
     * The readerThread's local cache isn't getting updated with the modified value of the flag variable after the
     *  writerThread toggled it
     * As a result the readerThread is stuck in it's loop indefinitely, waiting for the flag to become true even though
     *  the writer has set it to true
     *
     * To fix, this situation, we can add a keyword, a modifier, to the flag's declaration statement
     * This modifier is volatile
     *
     * Adding it after the access modifier or after the return type
     *
     * Running this code now with this change :-
     *  - Both statements are printed and the app terminates naturally
     *
     * volatile keyword
     * ................
     * is used as a modifier for class variables
     * It's an indicator that this variable's value may be changed by multiple threads
     * This modifier ensures that the variable is always read from, and written to the main memory, rather than
     *  from any thread-specific caches
     * This provides memory consistency for this variable's value across threads
     * Volatile has limited usage though
     * It does not guarantee atomicity, so with our previous example , with our interleaving colorized threads,
     *  volatile wouldn't have helped
     * There are specific scenarios when you'll want to use volatile
     *  - When a variable is used to track the state of a shared resource such as a counter or a flag
     *  - When a variable is used to communicate between threads
     *
     * You should never use volatile,
     *  - When a variable is only used by a single thread
     *  - When a variable is used to store a large amount of data
     *
     *
     *
     */

    public static void main(String[] args) {
        CachedData cached = new CachedData();
        Thread writerThread = new Thread(()->{
            try{
                TimeUnit.SECONDS.sleep(1);

            }catch (InterruptedException e){
                e.printStackTrace();
            }
            cached.toggleFlag();
            System.out.println("A. Flag set to "+cached.isReady());
        });

        Thread readerThread = new Thread(()->{
            while (!cached.isReady()){
                //Busy wait until flag becomes true
            }
            System.out.println("B. Flag is "+cached.isReady());
        });

        writerThread.start();
        readerThread.start();
    }
}
