package concurrency.part17_thread_pool_methods_pt2;


import concurrency.part16_cached_thread_pool.ThreadColor;

import java.util.List;
import java.util.concurrent.*;

public class Main {
    /*
     * Additional Thread Pools, Callable, Submit and the Future
     *
     * invokeAll()
     * The ExecutorService implementations hava a () named invokeAll()
     *
     * main()
     * Create a multiExecutor from newCachedThreadPool() on Executors Class
     * Set a list of Callable tasks
     *  - Create a List of callables which will return integers i.e. List<Callable<Integer>>
     *  - Tasks will be the same as used previously and will just set them individually
     *
     * Then
     *  - Call invokeAll() on multiExecutor instance
     *  - invokeAll() takes a Collection of Callables, so we can pass the taskList
     *  - Returns a List<Future> which will wrap integer value results
     *  - Loop through the results,
     *      - printing out the result of each callable, waiting up to half a second before retrieving it
     *  - Handle three exceptions from get()
     *      - create 1 multi exception catch for all three exceptions
     *
     * Running this code:-
     *  - We get the same results as the same as when we called submit on each task individually
     *
     * The diff here is , we were able to create a Collection of tasks up front or before we needed them, then passed them all to the
     *  ExecutorService's invokeAll()
     * And we can get the result in a single collection, rather than having to get each result individually
     *
     *
     * invokeAny()
     * ...........
     *  - invoke invokeAny() on multiExecutor instance and pass a Collection of Callables : assign to "resultFromInvokeAny"
     *  - Returns an integer and not a future instance type
     *
     * Running this :-
     *  - Returns an arbitrary result
     * The result we get back is the result from the task that completes first
     * It returns only that one completed task results
     *
     * invokeAny() vs invokeAll()
     * ..........................
     *
     * invokeAny()
     * ...........
     *  - Tasks Executed : At least one,the first to complete
     *  - Result : Result of the first to complete, not a Future
     *  - Use Cases : Use this () when you need a quick response back from one of several tasks, and don't care if some will fail
     *
     *
     * * invokeAll()
     * ............
     *  - Tasks Executed : All tasks get executed
     *  - Result : Result a list of results, as futures, for all the tasks, once they have all completed
     *  - Use Cases : Use this () when you want all the tasks to be executed concurrently, and all tasks must complete before proceeding
     *
     * */

    public static void main(String[] args) {
        var multiExecutor = Executors.newCachedThreadPool();
        List<Callable<Integer>> taskList = List.of(
                () -> Main.sum(1,10,1, "red"),
                () -> Main.sum(10,100,10, "blue"),
                () -> Main.sum(2,20,2, "green")
        );

        try{
            var results = multiExecutor.invokeAll(taskList);

            var resultFromInvokeAny = multiExecutor.invokeAny(taskList);
            System.out.println("_____invokeAll()___________");
            for (var result : results){
                System.out.println(result.get(500 , TimeUnit.MILLISECONDS));
            }

            System.out.println("_____invokeAny()___________");
            System.out.println(resultFromInvokeAny);

        }catch (InterruptedException | TimeoutException | ExecutionException e){
            throw new RuntimeException(e);
        }finally {
            multiExecutor.shutdown();
        }
    }

    /*
     * Adds a series of numbers
     * Return type : void
     * Takes 3 args :-
     *  - starting number : int
     *  - ending number :int
     *  - delta :int (amount each iteration will increase by)
     *  - color :String  (The color the thread should be printed in)
     *
     * Validate Color passed
     *  - Initialize to ANSI_RESET
     *  - Check for color validity and ignore the exception
     * Loop
     *  - Use a for loop to sum from start to end incrementing with a delta value
     *  - Summing up the delta values
     * Print
     *  - color
     *  - current thread
     *  - colorString
     *  - sum
     *
     * Call this () from the main()
     *
     *
     */
    private static int sum(int start , int end , int delta , String colorString){
        var threadColor = ThreadColor.ANSI_RESET;
        try {
            threadColor = ThreadColor.valueOf("ANSI_"+colorString.toUpperCase());
        }catch (IllegalArgumentException ignore){
            // User may pass a bad color name, Will just ignore this error
        }
        String color = threadColor.color();
        int sum = 0;
        for (int i = start; i <= end; i+= delta) {
            sum += i;
        }
        System.out.println(color + Thread.currentThread().getName() + ", "+ colorString+ " "+sum);
        return sum;
    }
}
