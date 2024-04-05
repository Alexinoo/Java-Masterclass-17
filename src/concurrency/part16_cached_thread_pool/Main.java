package concurrency.part16_cached_thread_pool;


import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    /*
     * Additional Thread Pools, Callable, Submit and the Future
     *
     * main()
     * use type inference and create a multiExecutor var using newCachedThreadPool() with no args
     *
     * Add tasks
     * Wrap in a try {}
     *  - sum all the numbers from 1 to 10, incrementing by 1 each time - prints in red
     *  - sum all the numbers from 10 to 100, incrementing by 10 each time - prints in blue
     *  - sum all the numbers between 2 and 20, incrementing by 2 each time - prints in green
     * Add Finally {}
     *  - shutdown multiExecutor
     *  - considered best practice to do it here
     *
     * A thread pool will stay in existence until something tells it to stop running, and that's what shutdown() does
     *
     * Running this:
     *  - Get 3 statements printed in no specific order
     *      - Red - 55
     *      - Green - 10
     *      - Blue - 550
     *
     * Means we have 3 distinct thread names, so this pool had 3 threads executing even though we never specified a thread count for
     *  this executor service
     *
     * Copy the 3 tasks and paste
     *  - change color to yellow, cyan and purple
     *
     * Running this:
     *  - Get 6 statements printed in diff colors and in no specific order
     *      - Red - 55
     *      - Green - 110
     *      - Blue - 550
     *      - Purple - 110
     *      - Cyan - 550
     *      - yellow 55
     *  - The thread names are still unique, so the thread pool is growing as tasks increases
     *
     * Before shutting down
     *  - Hang out for a sec
     *  - Then execute few more tasks
     *      - call sleep before the shutdown()
     *      - print "Next Tasks will get Executed"
     *      - Loop through the colors provided explicitly
     *          call execute and pass sum()
     *
     * Running this:-
     *  - 6 tasks complete
     *  - We get a statement "Next Tasks will get Executed"
     *  - Prints statement for the final for
     *  - The pool appears to be reusing existing threads, at this point in the pool
     *
     * Will go through how a cached pool, creates and destroy threads in just a minute
     *
     * Add more colors to the array
     *
     * Re-running this :-
     *  - Print 7 uniquely named threads - the thread is growing
     *
     * newCachedThreadPool()
     * Ctrl+click on newCachedThreadPool()
     *  - Creates a new ThreadPoolExecutor()
     *  - This () takes the following args:
     *      - corePoolSize
     *          - starts at 0
     *          - means this won't create threads when it's instantiated
     *      - maximum pool size
     *          - is the MAX VALUE of Integer (around 2.14 billion)
     *      - keepAliveTime
     *          - set to 60 seconds
     *          - means after 60 sec, cached threads will be dropped
     *      - TimeUnit
     *          - unit of time to be used
     */

    public static void main(String[] args) {
        var multiExecutor = Executors.newCachedThreadPool();

        try{
            multiExecutor.execute(()-> Main.sum(1,10,1,"red"));
            multiExecutor.execute(()-> Main.sum(10,100,10,"blue"));
            multiExecutor.execute(()-> Main.sum(2,20,2,"green"));


            multiExecutor.execute(()-> Main.sum(1,10,1,"yellow"));
            multiExecutor.execute(()-> Main.sum(10,100,10,"cyan"));
            multiExecutor.execute(()-> Main.sum(2,20,2,"purple"));

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(" === Next Tasks will get Executed ====");
            for (var color : new String[]{"red","blue","green","yellow","purple","cyan","black"}){
                multiExecutor.execute(()-> Main.sum(1,10,1,color));
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
    private static void sum(int start , int end , int delta , String colorString){
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
    }
}
