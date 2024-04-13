package concurrency.part28_starvation_fair_locks;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

    /*
     * Starvation and Fair Locks
     *
     * Starvation occurs when 1 thread is unable to obtain the resource it needs ,to execute
     * This is usually caused by other concurrent threads being greedy
     * Some threads are able to make progress but others can't
     * This means your application may still keep running, and some of the work is getting done, but not all of it
     *
     *
     * Example
     * Use an explicit lock as a static field
     *   Typed Lock - an instance of the ReentrantLock
     *
     * main()
     *  - Set up a Callable to use with executor service to fire off several concurrent threads at once
     *      - returns a boolean
     *      - get thread name
     *      - get thread no from Java's generated name
     *          - setup a variable which will use Integer.parseInt
     *              - pass it a thread name but use a regex which will replace everything but the thread no with an empty string
     *          - generated name consists of the format
     *              - first or the pool of group name
     *              - a dash
     *              - the pool or group number
     *              - a dash/hyphen
     *              - literal text thread
     *      - Initialize a boolean keepGoing to true
     *      - Use a while loop tht runs as long as keepGoing is true
     *          - continue to process and acquire the lock if it can or block here
     *          - if it get's the lock - simulate some consuming work
     *              - add a try {} block
     *                  - prints which thread has acquired the lock with a unique threadno
     *                  - hang on for 1 sec
     *              - catch the InterruptedException
     *                  - print the thread is shutting down
     *                  - reassert the interruption
     *                      - the executor is going to force these threads to shut down because they are in this while loop
     *                          therefore shutting down smoothly
     *                  - return false and exit the loop
     *              - add a finally clause
     *                  - release the lock when the work is done by calling unlock()
     *
     *      - return true
     *
     * We are not going to use this true/false values
     *
     * We've set this up as a Callable rather than a Runnable that returns no value, because we plan to take the advantage
     *  of the invokeAll() on an Executor Service
     * invokeAll() only takes a collection of Callables
     *
     * Use a FixedThreadPool with 3 threads running concurrently
     *  - add a try {}
     *      - store the List<Future> that we get back by calling invokeAll() **overloaded version**  on the executor and
     *          - 1st arg - pass a List , using the List.of() and simply pass 3 threads
     *          - 2nd arg - pass a Timeout
     *              (after the time elapses ,the executor will timeout, waiting for these threads to complete)
     *              - This is crucial as these threads are in a n infinite loop and the invokeAll(), without a timeout
     *                 would wait for them to complete normally and would wait indefinitely if we don't do this
     *  - catch an InterruptedException that invokeAll might throw
     *      - throw it as a run time exception
     * Call shutdown on executor
     *  - use shutdownNow() instead
     *
     * shutdown() and shutdownNow()
     * shutdown() attempts an orderly shutdown meaning it will wait your threads to finish processing
     *  - in our case, the threads will not finish normally and so we have 2 options
     *      - use Array of futures and manually cancel each thread, by calling cancel on the future
     *      - use shutdownNow() which can do that for us
     *  - shutdownNow() will attempt to stop all actively running threads
     *
     * Running this :
     *  - Watch it for 10 sec while it's processing
     *  - Each thread holds the lock for a full second
     *      - prints the threadNo and the thread has acquired a lock
     *  - After running this several times
     *      - We are able to see a pattern here
     *      - The thread that manages to acquire the lock, becomes a greedy thread, meaning it monopolizes the shared resource
     *      - The other thread are politely waiting, but the greedy thread has a better chance of re-acquiring the lock
     *  - That hardly seems unfair to the other threads, but in java locking isn't simply implicitly fair
     *
     *  - We can try to mess around with the priority of the threads, but remember priority is just a suggestion
     *
     * So what options do we have to try and have a more even distribution of access, to the resource ?
     *
     * With an explicit lock, we can actually change the fairness policy of the locking, by passing true to the
     *   constructor of the ReentrantLock instance
     *
     * Add true to the constructor argument
     *
     * Re-running this code :
     *  - Each thread is acquiring the lock and it's a pretty even distribution over the 10 sec that we try this
     *
     * This is another advantage of explicit locking, meaning using a Lock implementation like the ReentrantLock
     *
     * You can't set the fairness policy manually of a monitor lock, i.e. the lock used when you use the
     *  synchronized keyword
     *
     * Fair Locks
     * ..........
     *
     * A fair lock guarantees that all threads waiting to acquire the lock will be given an equal chance of acquiring it.
     * This is in contrast to an unfair lock, which doesn't make any guarantees
     * Remember, the monitor lock is unfair
     *
     * A Reentrant lock can be fair or unfair as we've jsut seen
     *
     * How Fair Locks Work
     * ...................
     * When a thread requests access to a fair lock, it gets added to a FIFO queue
     * The lock is then granted to the thread at the head of the queue, or the first in
     *
     * Should you use a fair lock ?
     * ............................
     * Benefits :
     *  - Fair locks can help to prevent thread starvation
     *  - May improve overall performance of a system, by ensuring that all threads get a chance of accessing resources
     *  - They can make a system more predictable and easier to debug
     *
     * Drawbacks:
     *  - Fair locks might have a negative impact on performance, especially in systems where threads are frequently
     *    competing for locks
     *  - Fair locks can be more difficult to implement
     *
     * We looked at an example of a starvation situation, where 2 threads were usually unable to acquire a lock on the
     *  resource, because the one thread was able to continually acquire it
     *
     * To prevent this situation, we used a fair lock which puts waiting threads in a queue and allows acquisition of a
     *  lock in the order in which a thread was queued
     *
     */

    private static final Lock lock = new ReentrantLock(true);

    public static void main(String[] args) {

        Callable<Boolean> thread = ()-> {
            String threadName = Thread.currentThread().getName();
            int threadNo = Integer.parseInt(threadName.replaceAll(".*-[1-9]*-.*-",""));
            boolean keepGoing = true;
            while (keepGoing){
                lock.lock();
                try{
                    System.out.printf("%d acquired the lock. %n",threadNo);
                    TimeUnit.SECONDS.sleep(1);
                }catch (InterruptedException e){
                    System.out.printf("Shutting down %d%n",threadNo);
                    Thread.currentThread().interrupt();
                    return false;
                }finally {
                    lock.unlock();
                }
            }
            return true;
        };

        ExecutorService executor  = Executors.newFixedThreadPool(3);

        try {
            var futures = executor.invokeAll(
                    List.of(thread, thread , thread), 10 , TimeUnit.SECONDS);

        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }

        executor.shutdownNow();

    }
}
