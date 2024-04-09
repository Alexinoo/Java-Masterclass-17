package concurrency.part22_parallel_streams;

import java.util.Arrays;
import java.util.Random;

public class Main {
    /* Parallel Streams
     * Introduced in Java 8 along with along with serial streams
     * Allows you to perform operations on collections in parallel, thus potentially speeding up data processing
     * The key advantages of parallel streams are:
     *  - Improved performance on multi-core CPU's
     *  - Simplified code for concurrent processing
     *  - Automatic workload distribution among available threads
     *
     * We used ForkJoinPool, to break up one operation into many smaller ones running in parallel.
     * We did this manually, first by creating a List<Callable> tasks and then by creating a Recursive Task, that we had
     *  the ForkJoinPool invoke
     * Parallel streams do this work implicitly for us, making them simpler to use
     *
     * main()
     *  Set up a variable to set the nos to be processed
     * Generate an array of randomly generated longs using the numbers length to limit the array to 100k as well as limit
     *  the nos to be between 1 and 100k
     * Will take a snapshot of UTC time using nanoTime()
     * Then execute the average operation, a terminal operation , on a stream of the numbers[]
     *  - use Optional's orElseThrow() to get that from the optional instance
     *  - this () throws an exception if for some reason, the stream doesn't return the average
     * Get the time elapsed  by subtracting start from System.nanoTime()
     * Print a formatted string with the average of the [] values and the elapsed time in nano seconds
     *
     * Running this:
     *  - get the average value and the elapsed time in nanoseconds
     *  - values will be diff each time, but the average shd be about half of numbers length - about 50000
     * Add millisecond to the print out - easier to read - divide elapsedSerial by a million(make it a double)
     *
     * There are 2 ways to make a stream parallel
     *  1) Include the parallel intermediate operation
     *      - copy and update variables to averageParallel and elapsedParallel
     *      - add parallel() after we get the stream
     * Java will now perform this operation in parallel, using ForkJoinPool's common pool implicitly here
     *
     * Running this:
     *  - The parallel operation isn't always faster acc to the timings here
     *
     * Let's explore this a little further and execute each of these multiple times, and get an average elapsed time diff
     * Declare a no of variables before doing this calculations
     *   delta : long - track the time diff between the serial execution and the parallel execution - initialize to 0
     *   iterations : int - no of times we want to execute these tasks - initialize to 100
     * Set a for loop around the two existing tests
     *  - execute 100 times to start with
     *  - remove both print statements
     * Include calculation of the difference btwn the serial elapsed time and the parallel elapsed time
     *  - keep a running total of the diff, in the delta field
     * Print out the average delta
     *  - compare the parallel to the serial both in nanos and ms
     *  - get the delta divided by the loop iterations for nano and for ms divide by million
     *
     * So this code will loop 100 times for the same array and get the average each time using a serial stream, then
     *  get the average again using parallel
     * Will calculate the diff in elapsed time , subtracting parallel time from serial time which we might assume is higher
     *  because you'd expect the serial stream to be slower
     * Then print the average diff after the loop
     *
     * Running this:
     *  - We get a -ve value for both cases meaning the parallel isn't faster than the processing at all
     *
     * Will change the value of the array from 100k to 100m
     * Then change the loop iterations from 100 to 25 because these calc will take longer
     *
     * Running this:
     *  - Parallel processing is faster than the serial
     *  - Run a couple of times to confirm this remains true over several tests - benchmark testing
     *  - Both times we run it we get similar result
     *
     * Why was serial faster in the case of a smaller array and parallel faster in larger array ?
     *  - Parallel streams introduce some overhead, such as the need to create and manage multiple threads
     *    - this overhead can be significant for small arrays
     *  - Parallel streams need to synchronize their operations to ensure that the results are correct. This
     *    synchronization can also add overhead, especially for small []s
     *
     * The cautionary tale here is, don't assume a parallel stream will always improve performance
     *
     * There are times, though you specify you want to use a parallel stream, Java's optimization may not even use one
     * If your collection of data needs to be ordered, his too may affect, how well the parallel operations perform
     *
     */
    public static void main(String[] args) {

        int numbersLength = 100_000_000;
        long[] numbers = new Random().longs(numbersLength , 1,numbersLength).toArray();

        long delta = 0;
        int iterations = 25;

        for (int i = 0; i < iterations; i++) {
            long start = System.nanoTime();
            double averageSerial = Arrays.stream(numbers).average().orElseThrow();
            long elapsedSerial = System.nanoTime() - start;

            start = System.nanoTime();
            double averageParallel = Arrays.stream(numbers).parallel().average().orElseThrow();
            long elapsedParallel = System.nanoTime() - start;
            delta += (elapsedSerial - elapsedParallel);

        }

        System.out.printf("Parallel is [%d] nanos or [%.2f] ms faster on average %n",
                    delta/iterations ,
                    delta / 1_000_000.0 / iterations);
    }
}
