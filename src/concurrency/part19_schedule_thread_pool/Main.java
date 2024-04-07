package concurrency.part19_schedule_thread_pool;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

public class Main {
    /*
     * Schedule Tasks
     * You schedule tasks with a special type of ExecutorService, the ScheduledExecutorService
     * Since we are using a scheduler, deal with date time data
     *
     * main()
     *  - Create a date time formatter variable for a local date time instance
     *      - use ofLocalizedDateTime() factory ()
     *      - print the medium format style for a date
     *      - print the time in the long style
     *  - Set up a Callable variable that returns a ZonedDateTime named "waitThenDoIt"
     *      - Create a ZonedDateTime variable "zdt" and initialize to null
     *      - Reassign "zdt" to ZonedDateTime.now() after sleeping for 2 sec
     *      - Catch InterruptedException
     *      - return "zdt"
     *
     *  - Create a FixedThreadPool and pass 4 threads - so that 4 tasks can run concurrently at 1 time using this pool
     *
     *  - Set up a List of Callable and get a collection of 4 of these using nCopies passing it 4, and the Callable
     *    variable waitThenDoIt
     *
     *  - Kick off the tasks in a try-catch
     *      - Print curr date time with the formatter - using ZonedDateTime.now() and pass formatter to format()
     *      - Call invokeAll on the threadPool passing it the task list
     *          - this returns a list of futures
     *              - each future will contain a zoned date time passed back from the task
     *                  - loop through the futureList and use get() on current result
     *                      - catch Execution and TimeOutException in a multi-line
     *  - Catch Interrupted Exception and throw a runtime exception
     *  - Add a finally clause and call shutdown() on the threadPool ExecutorService
     *
     * Running this:-
     *  - Prints local date time
     *  - After about 2 seconds, we get the dates from the other 4 tasks, when those tasks were executed
     *  - Each of the tasks seems to have executed 2 sec after the first statement
     *
     * Updating threads from 4 to 2
     *
     * Running again:-
     *  - Notice that all four are printed at the same time, the first 2 threads were recorded 2 sec after the initial date time
     *
     * Even though you think you might be scheduling your threads 2 sec after they get invoked, this is really not upto you
     * Your thread pool manager and the OS, will determine how to best manage a series of threads
     * IF you really need to manage exactly when things run, you'll want to use a scheduled executor service
     *
     *
     * ScheduledExecutorService Example
     * ................................
     * Print current local date
     * Set up an executor variable using a specialized Interface, ScheduledExecutorService
     *  - Then call newScheduledExecutorService() on Executors Class
     *  - Then call schedule() on this instance and pass a lambda expression that prints
     *      - a formatted date time
     *      - delay time
     *      - Time unit
     *  - Then call shutdown() on the executor
     *
     * Running this:-
     *  - The first 4 tasks are run from the previous code
     *  - Then another print statement with an arrow
     *  - Then after a 2 sec delay, another date time is printed from the scheduled task
     *  - This time the delay was managed by the executor which is 2 sec after the previous statement
     *
     */
    public static void main(String[] args) {

        var dtf = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM,FormatStyle.LONG);

        Callable<ZonedDateTime> waitThenDoIt = ()-> {
            ZonedDateTime zdt = null;
            try{
                TimeUnit.SECONDS.sleep(2);
                zdt = ZonedDateTime.now();
            }catch (InterruptedException e){
                throw new RuntimeException(e);
            }
            return zdt;
        };

        ExecutorService threadPool = Executors.newFixedThreadPool(2);

        List<Callable<ZonedDateTime>> list = Collections.nCopies(4,waitThenDoIt);

        try{
            System.out.println("---> "+ ZonedDateTime.now().format(dtf));
            List<Future<ZonedDateTime>> futureList = threadPool.invokeAll(list);
            for (var result :futureList) {
                try{
                    System.out.println(result.get(1, TimeUnit.SECONDS).format(dtf));
                }catch (ExecutionException | TimeoutException e){
                    e.printStackTrace();
                }
            }

        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }finally {
            threadPool.shutdown();
        }


        /*
         * SingleThreadScheduledExecutor
         */

//        System.out.println("--> "+ ZonedDateTime.now().format(dtf));
//        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
//        executor.schedule(
//                ()-> System.out.println(ZonedDateTime.now().format(dtf)),
//                1,
//                TimeUnit.SECONDS);
//        executor.shutdown();

        /*
         * Switch to a scheduled thread pool, ScheduledThreadPool
         * This is a cached thread pool that takes a numeric arg which is the no of threads, this pool will be started
         *  up with
         * Pass 4 here, so that the pool gets created with four threads ready to work
         * Schedule 4 tasks instead of 1 task
         *  - use a traditional for loop
         *  - update delay - time gets printed every 2 sec
         *
         * Running this:-
         *  - we get a second set of four tasks
         *  - Each is executed at different delays, a multiple of 2 sec
         *      - first is printed after 2 sec
         *      - second is printed after 4 sec
         *      - third is printed after 6 sec
         *      - fourth is printed after 8 sec
         *
         */
        Runnable dateTask = ()->{
            try {
                TimeUnit.SECONDS.sleep(3);
                System.out.println(ZonedDateTime.now().format(dtf));
            }catch (InterruptedException e){
                throw new RuntimeException(e);
            }
        };

        System.out.println("--> "+ ZonedDateTime.now().format(dtf));
        ScheduledExecutorService executorPool = Executors.newScheduledThreadPool(4);
//        for (int i = 0; i < 4; i++) {
//            executorPool.schedule(
//                    ()-> System.out.println(ZonedDateTime.now().format(dtf)),
//                    2 * (i + 1),
//                    TimeUnit.SECONDS);
//        }

        /*
         * We can also use a different () on the ScheduledExecutorService to do a similar thing as above
         * scheduleWithFixedDelay()
         *  - takes 4 arg
         *  - second is a time for initial delay
         * In this case, task will run after initial delay of 2 sec and will be executed every 2 sec after that
         *
         * Running this though:-
         *  - This () shuts down the executor after currently executing tasks have completed and before future ones
         *      are started
         *  - We are shutting down the executor before the task ever has a chance to run
         *  - Need to get some data back after a few tasks have executed
         *
         * A scheduled thread executor will return an instance of a ScheduledFuture, instead of a Future which
         *   we can poll for information
         * Will create a variable named "scheduledTask" using var as the type and insert it before
         *   executorPool.scheduleWithFixedDelay()
         * We can now use this instance to check the state of the future task
         * Will insert this code before the shutdown() call on the executor
         * Will shut down the executor after a certain amount of time has elapsed
         *  - Get the current time from the system clock
         *  - Set up a while loop and continue looping as long as isDone is not true on the scheduledTask
         *      - need a try-clause wince we will execute this once every 2 sec
         *          - check that time elapsed is greater than 10 sec
         *              - subtract initialTime from System.currentTimeMillis() and divide by 1000 since we get that in ms
         *          - if so cancel any future task using the cancel() on scheduledTask
         * Running this:-
         *  - Print second series of statements and the date time is printed every 2 sec
         *  - This continues until the elapsed time is greater than 10 sec
         *
         * Create a Runnable that prints local date time after 3 sec - above
         * Replace the 1st arg in scheduleWithFixedDelay() with dateTask Runnable
         *
         * Running this:-
         *  - Get 2 dates formatted and the time appears to be every 5 seconds
         *
         * This () schedules the next task, after the first finishes, and not every 2 sec
         *
         */
        var scheduledTask =  executorPool.scheduleWithFixedDelay(
                dateTask,
                2,
                2,
                TimeUnit.SECONDS);

        long initialTime = System.currentTimeMillis();
        while (!scheduledTask.isDone()){
            try{
                TimeUnit.SECONDS.sleep(2);
                if ((System.currentTimeMillis() - initialTime) / 1000 > 10){
                    scheduledTask.cancel(true);
                }
            }catch (InterruptedException e){
                throw new RuntimeException(e);
            }
        }

        executorPool.shutdown();
    }
}
