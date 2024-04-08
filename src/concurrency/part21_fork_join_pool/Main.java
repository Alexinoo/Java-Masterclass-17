package concurrency.part21_fork_join_pool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class Main {
    /*
     * ForkJoinPool
     * ............
     * A ForkJoinPool is a Java's implementation of the Work Stealing Pool
     * It's based on the fork-join or divide and conquer algorithm of computing
     * This algorithm
     *  - breaks down a complex task into smaller subtasks
     *  - processes them independently and in parallel
     *  - combines the result to solve the original problem
     *
     * Update ExecutorService to ForkJoinPool and cast the result with a ForkJoinPool also
     * This is because we want to use ()s that are specific to this class
     * Then print some info after invoking the tasks
     *  - level of Parallelism
     *  - pool size
     *  - Steal count
     *
     * Running this:
     *  - Parallel is 4 which matches what we set up
     *  - Steal count is the same as the no of tasks submitted
     *
     * Now switch to the common pool
     *  - comment on   ForkJoinPool threadPool = (ForkJoinPool) Executors.newWorkStealingPool(4);
     *  - Call commonPool() on the left side
     *  - No need for casting since the result is of ForkJoinPool type
     *
     * Running this:
     *  - Parallelism is now 3
     *  - value for pool size and steal count changes by 1 or 2 each time
     *
     * We can get the no of CPu in my system
     *  - We can get info about the running system from the Runtime Class including the no of available processors
     *
     * Running this:
     *  - Prints that my system has 4 CPUs
     *
     * In general, the common pool will use as many CPU's as possible but won't typically use all of them as we see here
     * The common pool starts with a small no of threads in its pool & it will add more threads as needed
     * Threads will also be removed if they are idle for too long
     * The Steal count returns an estimate of the total no of completed tasks that were executed by a thread other than
     *  the submitter
     * When parallelism is higher, there is less work stealing
     *
     * Next : How to use a RecursiveTask
     * RecursiveTask is generic & we want to return a long, the sum of all my numbers in the array
     *
     * Create a RecursiveSumTask that extends RecursiveTask
     *  - Fields
     *      - numbers : long []
     *      - start index : int
     *      - end index : int
     *      - division :int
     *          - if we pass 2 - create 2
     *          - if we pass 4 - create 4
     *  - Constructor
     *      - generate for all fields
     *  - compute()
     *      - remove return null
     *      - customize
     *
     * There are 2 main types of parallelism
     *  - Task Parallelism
     *      - divides a program into smaller tasks that get executed concurrently
     *      - each task can run on a separate thread or processor core
     *  - Data Parallelism
     *      - processes diff parts of the same data concurrently
     */

    public static void main(String[] args) throws Exception{

        int numbersLength = 100_000;
        long[] numbers = new Random().longs(numbersLength , 1,numbersLength).toArray();
        long sum = Arrays.stream(numbers).sum();


        System.out.println("sum = "+sum);

        //ForkJoinPool threadPool = (ForkJoinPool) Executors.newWorkStealingPool(4);
        ForkJoinPool threadPool = ForkJoinPool.commonPool();

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

        System.out.println("CPUs = "+Runtime.getRuntime().availableProcessors());
        System.out.println("Parallelism = "+threadPool.getParallelism());
        System.out.println("Pool size = "+threadPool.getPoolSize());
        System.out.println("Steal Count = "+threadPool.getStealCount());

        long taskSum = 0;
        for (Future<Long> future: futures ) {
            taskSum += future.get();
        }
        System.out.println("Thread Pool sum = "+ taskSum);

        RecursiveTask<Long> task = new RecursiveSumTask(numbers, 0, numbersLength,2);
        long forkJoinSum = threadPool.invoke(task);
        System.out.println("RecursiveSumTask sum is "+forkJoinSum);

        threadPool.shutdown();

        System.out.println(threadPool.getClass().getName());

    }
}
