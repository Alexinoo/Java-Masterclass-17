package concurrency.part7_concurrent_thread_concepts;

import java.util.concurrent.TimeUnit;

public class Main {
    /*
     * Concurrent Thread Concepts
     * ..........................
     * Initially, when the loop variable (i) was a local variable, the threads had no conflict with each other
     * They all successfully counted down from some initial starting point
     * When we used an instance field for the loop variable, the concurrent application fell apart
     *
     *
     * Interference
     * ............
     * A conceptual model of the countDown() simplified to show the for loop block
     * Specifically, showing the for-loop initialization statement as well as the printf statement
     * For loop
     *   i = unitCount
     *   i >= 0
     *   i = [i--] [i-1]
     * PrintF
     *  Substitution
     *  Concatenation
     *  Output to console
     *
     * Each box is a unit of work & they can also be nested in 1 another
     * Only the smallest block might be atomic
     * Think of having these blocks as a door, through which many threads can enter at any single time.
     * There's no queue or line, so all threads can show up & enter at each of these boundaries, at the exact same time
     * A thread can be halfway through the work in any of these blocks, when its time slice expires, then it pauses or
     *  suspend execution, to allow other threads to wake up and execute
     * This means another active thread has an open door, to that same unit of work, where the paused thread is only partially done
     * When threads start and pause, in the same blocks as other threads, this is called interleaving
     *
     * One result of interleaving is, that a shared resources's state may change, while a thread is paused in the middle
     *  of its work
     * In this case, one thread interferes with the work of another
     *
     * Interleaving
     * ............
     * When multiple threads run concurrently, their instructions can overlap or interleave in time
     * This is because the execution of multiple threads happens in an arbitrary order
     * The order in which the threads execute their instructions can't be guaranteed
     *
     * Atomic Actions
     * ..............
     * In programming, an atomic action is one that effectively happens all at once
     * An atomic action can't be stopped in the middle, it either happens completely, or it doesn't at all
     * Side effects of an atomic action are never visible until the action completes
     * Even the simplest operations may not be atomic, even decrements and increments, aren't atomic, nor are all
     *  primitive assignments
     *
     *
     * Now that we know what Interleaving is , how can we prevent it ?
     *  - First , be careful about shared obj(s)
     *      - In this code, it doesn't make sense to have all threads sharing a single stop watch instance
     *      - A lot of things could go wrong, if your threads are sharing obj(s) not meant to be shared
     *
     * Create 3 stop watch instances
     *  - Copy and rename to greenWatch,purpleWatch,redWatch
     *  - Replace with the instance that matches the thread name in the method references
     *
     * Running this:-
     *  - Output looks a lot better
     * By giving each thread its own instance of StopWatch, we've ensured, we'll almost not have trouble with interleaving
     *  threads
     * We say almost because we could have made that field i static - and this would result in the same behaviour
     * This is because a static variable is shared by all its instances
     *
     * What if we had designed the StopWatch class to be an immutable class, certainly, the field i would never be used
     *  as a loop variable
     * Using immutable obj(s) when dealing with concurrent threads, simplifies techniques we might need to use otherwise,
     *  and eliminates some bad programming practices
     * AN immutable obj is said to be thread safe for this reason
     *
     * Thread Safe
     * An obj or a block of code is thread-safe if it is not compromised, by the execution of concurrent threads
     * This means the correctness and consistency of the program's output or it's visible state, is unaffected by
     *  other threads
     * Atomic operations and immutable obj(s) are examples of thread-safe code
     *
     * In real-life, there are shared resources, which have to be available to multiple concurrent threads in near real time
     * For these, we need to control access to the resource, to protect it from the effects of interleaving threads
     * You can think of these techniques as forcing threads to wait in line, until one thread has finished a full unit
     *  of work, and not just an atomic operation
     *
     *
     *
     * demo similar problem with CachedData
     * Create a new class called Cached data with a main() in this package

     */

    public static void main(String[] args) {

        StopWatch greenWatch = new StopWatch(TimeUnit.SECONDS);
        StopWatch purpleWatch = new StopWatch(TimeUnit.SECONDS);
        StopWatch redWatch = new StopWatch(TimeUnit.SECONDS);


        Thread green = new Thread(greenWatch::countDown , ThreadColor.ANSI_GREEN.name());
        Thread purple = new Thread(()-> purpleWatch.countDown(7), ThreadColor.ANSI_PURPLE.name());
        Thread red = new Thread(redWatch::countDown , ThreadColor.ANSI_RED.name());

        green.start();
        purple.start();
        red.start();

    }

}


class StopWatch {
    private TimeUnit timeUnit;
    private int i;

    public StopWatch(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }

    public void countDown(){
        countDown(5);
    }

    public void countDown(int unitCount){
        String threadName = Thread.currentThread().getName();
        ThreadColor threadColor = ThreadColor.ANSI_RESET;
        try{
            threadColor = ThreadColor.valueOf(threadName);
        }catch (IllegalArgumentException ignore){
            // User may pass a bad color name, Will just ignore this error
        }
        String color = threadColor.color();

        for (i = unitCount; i > 0 ; i--) {
            try{
                timeUnit.sleep(1);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            System.out.printf("%s%s Thread : i = %d%n",color,threadName , i);
        }
    }
}