package concurrency.part15_fixed_thread_pool;

import java.util.concurrent.Executors;

public class Main {

    /*
     * Advantages of using an ExecutorService
     *  - Simplifies the job of managing threads
     *      - You get a static ()s on Executors Class
     *      - You pass it a lambda expression that rep a task that you want to be run
     *  - Then call execute() on that service
     *  - Then call shutdown(), otherwise the executor service will continue to stay up and running
     *  - Maybe, you'll want to include a call to the awaitTermination(), if you have something pending or that needs to wait, on the completion
     *     of that task
     *
     * ExecutorService implementations let you stay focused on tasks that need to be run, rather than thread creation and management
     * This will become more apparent when we use an ExecutorService that can handle multiple threads
     *
     * Use ThreadColor enum Class
     *
     *
     * main()
     *  - Add a count variable and initialize it to 3 (rep the number of threads we want to run)
     *  - Set up a multiExecutor variable with type inference and will start with Executors Class and then dot (.) so that intelliJ lists ()s available
     *     - check all the ()s that ends with pool
     *          - newCachedThreadPool
     *          - newFixedThreadPool
     *          - newScheduledThreadPool
     *          - newStealingThreadPool
     *      - These all return ExecutorService implementations that manage a pool, or a group of threads
     *  - Select newFixedThreadPool(int nThreads ,  ThreadFactory) - one that takes 2 arg
     *      - Pass count as the 1st arg
     *      - Pass an instance of the ColorThreadFactory Class with no params
     *
     * - Update ColorThreadFactory
     *    - to be a bit more flexible and to generate a color thread with having to actually specify a color
     *      - Add field :
     *          - colorValue which rep the ordinal value of the last color used for a thread name and text color
     *          - assign it to 1, since we don't want to use the default color value at 0, which is the ANSI_DEFAULT
     *      - Add no args constructor
     *      - Update newThread()
     *          - Add a local String variable for the revised thread name
     *          - If thread name is null, and we don't want to break the existing code, which uses the single arg constructor to pass a specific thread name value
     *          - Name will only be null when the no args constructor on this class is used
     *              - We can use the values on the enum, in conjunction with the color value, to get a color by it's ordinal or numeric value
     *          - Then increment the colorValue and check whether it's greater than the no of colors set up in the enum
     *              - If it is, set the color value back to 1
     *                  - Means the code will cycle through all the non default ANSI colors as new threads are created
     *          - Set the name of the thread to the new variable "name" rather than thread name
     *
     * Main()
     *  - Now the code executes
     * Need to execute the multiExecutor, passing it the task that we want it to run - which is the countDown() on this class
     * Use for loop and iterate with count to determine how many tasks to create
     *  - Call execute on multiExecutor and pass countDown as a () reference
     *
     * Running this:
     *  - Get a variety of statements in colored text in black, blue and white countdowns
     *
     * With this implementation, we can easily dial up the no of threads
     * Update count to 6
     *
     * Running again :-
     *  - This adds green , purple and cyan to the mix (previous list)
     *
     * Using an executor is much easier than the alternative
     * Without service, we'd have to create manually instantiate 3 new thread variables , start each and write some code, to have the main thread
     *  wait appropriately if needed
     * This code is cleaner, easier to read and scalable - we can quickly change the number and use a lot more threads to run tasks
     *
     * What happens if we pass 3 as the count to the Fixed pool and leave the rest of the code with the count variable as 6 ?
     *
     * Running this :-
     *  - Generate only 3 colors in the output
     *  - You might think that only 3 diff tasks were run, and not six but there actually appears tob 2 sets of blue,blue and white countdowns
     *
     * Did our code fail, No it didn't fail at all
     * Only 3 threads were created and pooled, in this scenario
     * When 6 tasks were submitted, the 3 threads were used to execute the first 3 tasks and then reused to execute the second 3 tasks
     * This is where the FixedThreadPool gets it's name
     * It will ever create at a maximum, the no of threads we specify, regardless of the number of tasks submitted
     *
     * Creating Threads is Expensive
     * .............................
     * Creating threads, destroying threads and then creating them again is expensive
     * A thread pool mitigates the cost, by keeping a set of threads around in a pool, for current and future work
     * Threads, once they complete 1 task, can be reassigned to another task, without the expense of destroying that thread and creating a new one
     *
     * Mechanics of a Thread Pool
     * ..........................
     * A thread pool consists of 3 components :-
     *  - Worker Threads
     *      - Are available in a pool to execute tasks
     *      - They're pre-created and kept alive, throughout the lifetime of the application
     *  - Submitted Tasks
     *      - Are placed in a FIFO queue
     *      - Threads pop tasks from the queue, and execute them , so they're executed in the order they're submitted
     *  - Thread Pool Manager
     *      - Allocates tasks to threads and ensures proper thread synchronization
     *
     * Java's Thread Pool Classes
     *  - FixedThreadPool
     *      - Has a fixed no of threads
     *      - Executor () is newFixedThreadPool()
     *      - Threads are reused when the no of tasks exceed the no of threads
     *
     *  - CachedThreadPool
     *      - Has a variable no of threads - dynamically create as needed
     *      - Executor () is newCachedThreadPool()
     *      - Grow and shrink in size as the no of task changes
     *      - SIze is managed by the Thread Pool Manager
     *
     *  - ScheduledThreadPool
     *      - is a special type of cached thread pool, with mechanisms for providing a schedule for any submitted tasks
     *      - Executor () is newScheduledThreadPool()
     *
     *  - WorkStealingPool
     *      - breaks tasks into subtasks and executes the subtasks in parallel in a divide and conquer way
     *      - Executor () is newWorkStealingPool()
     *
     * - FolkJoinPool
     *      - is a type of work stealing pool, that provides the means for describing how to divide and conquer a single large task
     *      - Does this by allowing us to control how to fork a task into parallel sub-tasks and then how to join the sub-tasks together
     *      - Executor () is N/A
     *
     */

    public static void main(String[] args) {

        int count = 6;
        var multiExecutor = Executors.newFixedThreadPool(3 , new ColorThreadFactory());

        for (int i = 0; i < count; i++) {
            multiExecutor.execute(Main::countDown);
        }
        multiExecutor.shutdown();

    }

    private static void countDown(){
        String threadName = Thread.currentThread().getName();
        var threadColor = ThreadColor.ANSI_RESET;
        try{
            threadColor = ThreadColor.valueOf(threadName.toUpperCase());
        }catch (IllegalArgumentException ignore){
            // ignore if user passes bad color
        }
        String color = threadColor.color();

        for (int i = 20; i > 0 ; i--) {
            System.out.println( color + " "+ threadName.replace("ANSI_","") + " "+i);
        }
    }
}


















