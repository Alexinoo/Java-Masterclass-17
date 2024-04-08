package concurrency.part20_work_stealing_pool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {
    /*
     * Set up a variable to set the nos to be processed
     * Generate an array of randomly generated longs using the numbers length to limit the array to 100k as well as limit
     *  the nos to be between 1 and 100k
     * Use Stream to calculate the sum and assign the result to a long
     * Print the sum
     * 
     * Running this:
     *  - get a value that is diff because of the random values used
     *
     * WorkStealingPool
     * .................
     * Is used for parallelism, and concurrent execution of tasks
     * Each worker thread has its own task queue
     * When a worker finishes its own tasks and its queue is empty, it can 'steal' tasks from the back of other worker
     *  thread's queues
     * This helps to balance the workload among threads, reduces idle time and optimizes resource usage
     *
     *
     * Create a WorkStealingPool by calling newWorkStealingPool() on Executors Class
     * Pass 4 which is technically not the no of threads but the level of parallelism that we want
     *  - usually set to the no of CPUs available on your system or less
     *
     * How do we split up the single task of adding 100k into smaller tasks
     * Use work stealing pool to distribute the work
     * First, set up a List<Callable<Long>> task each returning a long value
     *  - We've used the Long wrapper type, since we can't use primitives with generics & instantiate to a new ArrayList
     * Then create 10 subtask
     *  - Each subtask will sum a 10th of the randomized array
     *      - Initialize the no of tasks to 10
     *      - Initialize a variable splitCount that will set the max that each array can hold - 10k elements (100000/10)
     *      - Split with the use of a for loop and add tasks in each sub-tasks
     *  - Submit tasks to the thread pool and wait for all of them to complete (invokeAll)
     *
     *  - Loop through  List<Future> and get the sum for each subtask(future)
     *      - add cumulatively with initialized taskSum
     *
     *  - Print taskSum
     *
     * Running this:
     *  - We get the same sum as we got with streams
     *
     * Print the class name of this pool instance
     * Running this:-
     *  - Prints a ForkJoinPool
     *
     * ForkJoinPool
     * ............
     * A ForkJoinPool is a Java's implementation of the Work Stealing Pool
     * It's based on the fork-join or divide and conquer algorithm of computing
     * This algorithm
     *  - breaks down a complex task into smaller subtasks
     *  - processes them independently and in parallel
     *  - combines the result to solve the original problem
     */

    public static void main(String[] args) throws Exception{

        int numbersLength = 100_000;
        long[] numbers = new Random().longs(numbersLength , 1,numbersLength).toArray();
        long sum = Arrays.stream(numbers).sum();

        System.out.println("sum = "+sum);

        ExecutorService threadPool = Executors.newWorkStealingPool(4);

        List<Callable<Long>> tasks = new ArrayList<>();

        int taskNo = 10;
        int splitCount  = numbersLength / taskNo;

        for (int i = 0; i < taskNo; i++) {
            int start = i * splitCount;
            int end = start + splitCount;
            tasks.add(()->{
                long taskSum = 0;
                for (int j = start; j < end; j++) {
                    taskSum += (long) numbers[j];
                }
                return taskSum;
            });
        }

        List<Future<Long>> futures = threadPool.invokeAll(tasks);

        long taskSum = 0;
        for (Future<Long> future: futures ) {
            taskSum += future.get();
        }
        System.out.println("Thread Pool sum = "+ taskSum);

        threadPool.shutdown();

        System.out.println(threadPool.getClass().getName());

    }
}
