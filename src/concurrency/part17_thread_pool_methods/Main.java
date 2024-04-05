package concurrency.part17_thread_pool_methods;


import concurrency.part16_cached_thread_pool.ThreadColor;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    /*
     * Additional Thread Pools, Callable, Submit and the Future
     *
     * submit()
     * These classes have a submit()
     * Replace execute() with submit()
     *
     * Running this:-
     *  - We get the same output similar with what we have with execute()
     *
     * So what's the diff here ?
     * .........................
     * For this example, execute and submit do the same thing
     * The () we are executing does not return a value and so submit() knows we are executing a Runnable
     * The submit() though unlike the execute() can also run a Callable
     *
     * Diff between Runnable and Callable
     * ..................................
     * Runnable Functional () : void run()
     *  - You can have 2 ways of communication when using submit
     *
     * Callable Functional () : V call() throws Exception
     *  - returns a value
     *  - means you can get data back from your running threads
     *
     * Method Signatures for both execute() and submit()
     *  execute()
     *      - void execute(Runnable command)
     *
     * submit()
     *      - Future<?> submit(Runnable task)
     *      - <T>Future<T> submit(Runnable task, T result)
     *      - <T>Future<T> submit(Callable<T> task)
     * The submit() has 3 overloaded versions
     * In all cases, each returns a result, an instance of something called a Future
     *
     * The submit() in this code is an example of  [ Future<?> submit(Runnable task) ] which executed a runnable
     * In this case, we simply ignore the result coming back
     *
     *
     * The Future Interface
     * ....................
     * A Future represents a result , of an asynchronous computation
     * It's a generic type, a placeholder for a result instance
     * It has the following ()s
     *  - cancel()
     *      - Retrieves the result, or check if the computation was completed or cancelled
     *
     * - get()
     *      - Returns the result
     *      - Can only be called when the computation is complete
     *      - Otherwise, the call will block, until it does complete
     *      - The overloaded version allows you to specify a wait time, rather than blocking
     *
     *
     * Update sum()
     *  - Change the return type from void to int
     *  - return sum
     *
     * If we go back to the main(), the code still compiles:-
     *  - The submit() doesn't really care if you do anything with the result of its call
     *  - Hovering over submit shows that we're calling a diff overloaded version of submit() now which takes a Callable, as its argument
     *      - use type inference and assign each of the submit calls to a variable
     *  - In each case, the result is a Future with a type parameter of Integer
     *
     * Print each result by calling the get() that lets us specify a timeout
     * Wrap all of these calls in a single try-catch and just catch any exc since there's a variety of exc types that could be thrown here
     * Then invoke get() on each tasks result with 500ms i.e. redValue, blueValue and greenValue
     * Use a more generic class Exception and rethrow the exception, if you get one
     *
     * Running this :-
     *  - Prints the sum of each task
     *
     * The future is a way for a task to communicate its result, back to the calling code
     *
     * */

    public static void main(String[] args) {
        var multiExecutor = Executors.newCachedThreadPool();

        try{

          var redValue =  multiExecutor.submit(()-> Main.sum(1,10,1,"red"));
          var blueValue =  multiExecutor.submit(()-> Main.sum(10,100,10,"blue"));
          var greenValue =  multiExecutor.submit(()-> Main.sum(2,20,2,"green"));

          try{

            System.out.println(redValue.get(500, TimeUnit.MILLISECONDS));
            System.out.println(blueValue.get(500, TimeUnit.MILLISECONDS));
            System.out.println(greenValue.get(500, TimeUnit.MILLISECONDS));
          }catch (Exception e){
            throw new RuntimeException(e);
          }

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
        var threadColor = concurrency.part16_cached_thread_pool.ThreadColor.ANSI_RESET;
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
