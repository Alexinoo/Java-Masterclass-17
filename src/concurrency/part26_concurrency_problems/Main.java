package concurrency.part26_concurrency_problems;

import java.io.File;

public class Main {
    /*
     * Common Problems in a Multi-threaded Application
     * ...............................................
     *
     * Deadlock
     *  - 2 or more threads are blocked, waiting for each other to release a resource
     *
     * Livelock
     *  - 2 or more threads are continuously looping , each waiting for the other thread to take action
     * 
     * Starvation
     *  - A thread is not ale to obtain the resources it needs to execute
     *
     * Classic Example of a Deadlock
     * .............................
     * 2 threads are trying to acquire the lock on 2 different shared resources at the same time
     *
     * Let's imagine we have 2 files , a csv and a json file
     *  - resourceA - csv file
     *  - resourceB - json file
     *
     * We will have 2 threads:
     * The first process reads data from the csv then dumping it to a json file
     *  - Create threadA and pass it a runnable
     *      - get thread name
     *      - print thread name is attempting to lock resourceA (csv)
     *      - set up a synchronized block - synchronizing on resource A - csv
     *          - if successful - print thread acquired lock on resourceA
     *          - sleep for like 1 second - rep some kind of work like trying to read data from the file
     *              - print the thread is attempting to get a lock on resourceB but still has a lock on resource A
     *              - synchronize resourceB
     *                  - if successful, print thread has a lock on resourceB (json file)
     *              - after synchronize resourceB completes, print that the lock was released on resourceB
     *          - After the block completes - print thread has release resourceA
     *  - Pass custom Thread name as the 2nd argument
     *
     *
     * Do something similar for the 2nd thread
     *  - Create threadB and pass it a runnable
     *      - get thread name
     *      - print thread name is attempting to lock resourceB (json) - wants access to the same resource but in
     *          reverse order of the first thread
     *      - set up a synchronized block - synchronizing on resource B - json
     *          - if successful - print thread acquired lock on resourceB
     *          - sleep for like 1 second - simulate some kind of work whichever that might be
     *              - print the thread is attempting to get a lock on resourceA but still has a lock on resource B
     *              - synchronize resourceA
     *                  - if successful, print thread has a lock on resourceA (csv file)
     *              - after synchronize resourceA completes, print that the lock was released on resourceA
     *      - After the block completes - print thread has release lock on resourceB
     *  - Pass custom Thread name "THREAD_B" as the 2nd argument
     *
     *
     * Start both threadA and threadB
     *
     * Wait till the thread completes using join on threadA and on threadB
     *  - handle checked InterruptedException
     *
     *
     * Running this:-
     *  - The 2 threads are running concurrently
     *      - THREAD-B attempts to lock resourceB and acquires the lock
     *      - THREAD-A attempts to lock resourceA and acquires the lock
     *  - Both threads attempt to lock the next resource in the process
     *      - THREAD-B wants to access resourceA, but it's locked by THREAD-A
     *      - THREAD-A wants to access resourceB, but it's locked by THREAD-B
     *  - Neither of the threads is able to continue which means they are blocked inside a synchronized block
     *  - This is bad
     *  - It means each thread will keep the lock indefinitely on the resource they already have, so we've got the classic
     *     deadlock
     *  - Need to stop this application manually
     *
     *
     *
     * Preventing Deadlock
     * ......................
     * There are many ways to avoid this situation
     *  1) Organize your locks into a hierarchy and ensure all threads acquire locks in the same order to avoid circular
     *     waiting, which is a common cause of deadlocks
     *    This establishes a global lock order that all threads must follow
     *  2) Instead of using traditional synchronized blocks or ()s, you can use the tryLock() on the Lock Interface
     *    This allows you to attempt to acquire a lock. If it fails, you can handle the situation without causing a deadlock
     *
     *
     *
     * Implementing Solution 1
     *
     * Consider THREAD-B for a minute
     * We made this code a bit vague, but let's just say THREAD-B is processing data and really just reading from
     *  both files
     * It doesn't actually need to read from the json file first
     * If we organize our locks into a hierarchy as recommended,we'd set a rule that says all code has to get locks
     *  in the same order,
     *  - first getting the lock on csv file
     *  - then getting the lock on json file
     * Reverse the code in THREAD-B to follow this rule
     *  - Delete the code in THREAD-B (IN MY CASE , I WILL COMMENT IT OUT !!!!)
     *  - Copy the code in THREAD-A and paste it in THREAD-B
     *
     *
     * Running this code again :-
     *  - Both threads are able to get locks, processing whatever they need to do, and completing
     *
     * You should always be careful when you are using nested synchronized blocks
     * If you have to do a nested locking as we've done here, and you feel like you can't follow the hierarchical
     *  order, then be sure to use Lock implementations instead of the built-in monitor lock
     *
     * In other words, don't use a synchronized block as your nested locking mechanism
     * Use explicit locks as they give you more options
     *
     */

    public static void main(String[] args) {

        File resourceA = new File("inputData.csv");
        File resourceB = new File("outputData.json");

        Thread threadA = new Thread(()->{
            String threadName = Thread.currentThread().getName();
            System.out.println( threadName+ " attempting to lock resourceA (csv)");
            synchronized (resourceA){
                System.out.println(threadName + " has lock on resourceA (csv)");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(threadName +" NEXT attempting to lock resourceB (json), "+
                        "still has a lock on resourceA (csv) ");
                synchronized (resourceB){
                    System.out.println(threadName + " acquires lock on resourceB (json)");
                }
                    System.out.println(threadName + " has released lock on resourceB (json)");
            }
            System.out.println(threadName + " has released lock on resourceA (csv)");

        },"THREAD-A");

        Thread threadB = new Thread(()->{
//            String threadName = Thread.currentThread().getName();
//            System.out.println( threadName+ " attempting to lock resourceB (json)");
//            synchronized (resourceB){
//                System.out.println(threadName + " has lock on resourceB (json)");
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println(threadName +" NEXT attempting to lock resourceA (csv), "+
//                        "still has a lock on resourceB (json) ");
//                synchronized (resourceA){
//                    System.out.println(threadName + " acquires lock on resourceA (csv)");
//                }
//                System.out.println(threadName + " has released lock on resourceA (csv)");
//            }
//            System.out.println(threadName + " has released lock on resourceB (json)");


            // CODE FROM THREAD-A
            String threadName = Thread.currentThread().getName();
            System.out.println( threadName+ " attempting to lock resourceA (csv)");
            synchronized (resourceA){
                System.out.println(threadName + " has lock on resourceA (csv)");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(threadName +" NEXT attempting to lock resourceB (json), "+
                        "still has a lock on resourceA (csv) ");
                synchronized (resourceB){
                    System.out.println(threadName + " acquires lock on resourceB (json)");
                }
                System.out.println(threadName + " has released lock on resourceB (json)");
            }
            System.out.println(threadName + " has released lock on resourceA (csv)");

        },"THREAD-B");

        threadA.start();
        threadB.start();

        try {
            threadA.join();
            threadB.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
