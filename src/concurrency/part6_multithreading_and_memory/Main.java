package concurrency.part6_multithreading_and_memory;

import java.util.concurrent.TimeUnit;

public class Main {
    /*
     * Threads Access Memory
     * .....................
     * Each thread has its own stack for local variables and () calls
     * One thread doesn't have access to another thread's stack
     * You can think of a stack as a silos
     * Every concurrent thread has access to the process memory, or the heap
     * This is where objects, and their data reside.
     * This shared memory space allows all threads, to read and modify the same objects
     * When one thread changes an obj in the heap, these changes are visible to other threads
     *
     * This has some inherent problems associated with it, which we will look at here
     *
     *
     * Create an Enum Type called ThreadColor , which will allow us to colorize each thread's output differently,
     *  making the output easier to read
     *
     *
     *
     * Create and set up 1 thread in motion
     * First, will create a new instance of the StopWatch class which will count down in seconds
     * Next - Create a thread called green using new Thread
     *          - My runnable will call the countdown () that has no args, so I can pass a () reference for this
     *          - The second parameter is thread name, and we can use the enum, and the ANSI and the ANSI_GREEN value's name
     *             to name the thread
     * Finally start the thread running concurrently
     *
     * Running this :-
     *  - Prints a message "ANSI_GREEN Thread : i = 5" decrementing after every second for 5 iterations
     *  - Prints the text in green in the console
     *
     * Let's fire up a few more threads
     *  - purple thread - but this time use a lambda instead of a method reference and set the unit count to 7
     *                  - this will be a stop watch for 7 seconds
     *  - red thread - copy the green one
     *
     * Finally, call start() on these 2 threads [purple,red]
     *
     * Running this :-
     *  - Each stop thread is running, counting down from the () arg that it was called with
     *  - Purple was called with 7 sec - count down from 7 to 1 - 1 second at a time
     * In each of these threads, the unitCount is a () argument and is stored on each thread's stack
     * Each thread has its own version of unitCount
     *
     * What would happen though if we made the i variable in the for loop, a local variable, but a field on
     *  the StopWatch class
     *  - Add i as a local instance variable
     *  - And change the for loop to reference i
     *
     * Running this again :-
     *  - Something very strange has happened
     *  - First, it looks like they are all counting down from 7 or 5 & we only had one of our threads, the
     *    purple thread, set up to count down from 7
     *  - Second, the threads don't appear to be decrementing by 1 each second anymore - even decrementing with
     *    2's and 3's in some cases
     *
     * What happened.. ?
     *  - First, sharing a single instance stopWatch among the 3 threads - only have 1 stop watch instance setup
     *  - This wasn't a problem when the state of the StopWatch instance was unimportant to the count down operation
     *  - When we changed the variable i, to be a field on stop watch, we told all the fields, to share the i field
     *  - Since our 3 threads were kicked of at the same time, they all arrived at the countdown for loop at the same time
     *  - Each initialized or set the value of i
     *
     * This exercise demonstrate that all threads are changing the field i and those changes are visible to all threads
     *  as they progress in their work
     * This has pretty ugly consequences for our stop watch behavior
     * None of them are printing down correctly, or counting down correctly
     *
     * Time Slicing
     * Is also known as time-sharing or time division
     * It's a technique used in multi-tasking OS, to allow threads or processes to share a single CPU for execution
     * Available CPU time is sliced into small time intervals, divided out to the threads
     * Each thread get that interval, to attempt to make some progress, on the tasks it has to do
     * Whether it completes its tasks or not, in that time slice, doesn't matter to the thread management system
     * When the time is up, it ha to yield to another thread,and wait its turn again
     *
     * Unfortunately, when your threads are sharing heap memory, things can change during that wait
     *
     * Java Memory Model (JMM)
     * JMM is a specification that defines some rules and behavior for threads, to help control and manage shared access
     *  to data and operations
     * 2 problems that JMM tries to address include :-
     *  - Atomicity of Operations - Few operations are truly atomic - leads to problems with shared obj(s) because
     *    of time slicing
     *  - Synchronization - is the process of controlling threads access to shared resources

     */

    public static void main(String[] args) {

        StopWatch stopWatch = new StopWatch(TimeUnit.SECONDS);
        Thread green = new Thread(stopWatch::countDown , ThreadColor.ANSI_GREEN.name());
        Thread purple = new Thread(()-> stopWatch.countDown(7), ThreadColor.ANSI_PURPLE.name());
        Thread red = new Thread(stopWatch::countDown , ThreadColor.ANSI_RED.name());

        green.start();
        purple.start();
        red.start();

    }

}

/*
 *
 * Next create a Class called StopWatch
 * - Will have a () to count down to 0
 * - We can only use no modifier or package default
 * - The stop watch will count down by some unit of time
 * - Create a local instance variable timeUnit of type TypeUnit
 * - Generate a constructor with this field
 * - Then set up a countDown () that takes 1 param unitCount and does not return anything
 *      - Will assume the running threads will be named by their color - get threadName as a String
 *      - Initialize another local var and set it to ANSI_RESET
 *      - defensive - programming - if user passes a bad color that is not in the enum - throw illegal arg exception
 *      - Get the string value of the color using the color accessor () on the enum
 *      - Set a for loop to do a count down
 *          - Unit count might be min or sec
 *      - We are going to use timeUnit instance variable to have the stopwatch wait at certain intervals before it
 *        decrement the count
 *          - handle possible InterruptedExc
 *      - Then print statement with souf
 *          - color
 *          - Thread name
 *          - current value of i
 *
 * Add an overridden method to countDown() with no args and default unitCount with 5
 * We have included this (), so that when we create 1 of the threads, we can use method reference which this
 *  method will allow us to do
 */

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