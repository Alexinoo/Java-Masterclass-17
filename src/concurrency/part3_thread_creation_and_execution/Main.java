package concurrency.part3_thread_creation_and_execution;

import java.util.concurrent.TimeUnit;

public class Main {
    /* Java Threads - Creation and Execution
     * .....................................
     * Threads are the fundamental building blocks, to support concurrency, in a java application
     * They are essential because they allow us to perform multiple tasks simultaneously within a single process
     * We've really been using a thread all along because the JVM fires a main thread , when you execute any app
     *  using the main()
     *
     * We can get info about the main thread that's running, or any running thread by executing static ()s on the
     *  Thread Class
     *  - Thread.currentThread() returns an instance of the currently running thread
     *
     * Running this :-
     *  - Prints "Thread[main,5,main]" - Prints default rep of a Thread
     *  - Calls toString()
     *
     * Let's also print the class name
     *  - Prints "java.lang.Thread"
     *
     * What is java.util.Thread Class
     * This class implements Runnable interface, which has a single abstract (), the run()
     *  - This () has no parameters and doesn't return any data
     *  - It simply executes some code without parameterized input/output
     * Each instance of a Thread has some state
     *
     * The fields displayed ina Thread class are all encapsulated, so they're private
     * They include :
     *  - id : int
     *  - name : String
     *  - group : ThreadGroup
     *  - priority : int
     *  - target : Runnable
     *  - threadStatus : int
     *
     * Thread class has 2 constructors among others
     *  - Thread() - A thread can be constructed with no arguments
     *  - Thread(Runnable target) - A thread can be constructed by passing a Runnable instance to it
     *
     * Thread Class has 2 common ()s that are important in the execution of thread
     *  - run() - Needs to be overridden since it's declared abstract on the Runnable interface
     *  - start()
     *
     * Print the attributes here on the Thread Class
     *  - You can retrieve these attributes using the accessor, or getter () on a Thread instance
     *  - Use printThreadState(Thread thread)
     *
     * Running this prints all the info
     *
     * We can also modify some of these fields with setters
     *  - update name to MainGuy
     *  - Priority - need to enter between 1 - 10 where 10 is the highest priority
     *      - Rather than entering no 10, there are constants that rep these no's
     *      - Will call a constant on Thread which has the value of 10
     *  - Print again after we make the changes
     *  - Running this updates
     *      - Thread Name : ShakeAndBake
     *      - Thread Priority : 10
     *
     * Thread Priority
     * ...............
     * Thread priority is a value between 1 and 10
     * The Thread Class has 3 pre-defined priorities, included as constants
     *  - Thread.MIN_PRIORITY = 1 (low)
     *  - Thread.NORM_PRIORITY = 5 (default)
     *  - Thread.MAX_PRIORITY = 10 (high)
     * Higher priority threads have a higher chance of being scheduled, by a thread scheduler, over the lower priority
     *  threads
     * However, priority behavior can vary across diff OS and JVM implementations
     *
     * There are additional constructors that lets you pass in a name, or a group, when you create a thread this way
     *
     * Let's create and execute 1 running thread
     * As mentioned before, there are multiple ways to create Thread instances
     *  - Extend the Thread class and create an instance of this new subclass
     *  - Create a new instance of Thread,and pass it any instance that implements Runnable interface
     *      - This includes passing a lambda expression
     *  - Use an Executor, to create 1 or more threads
     *
     *
     *
     * 1) Extend the Thread class and create an instance of this new subclass
     *  - Create a new Class in this package named CustomThread
     *  - Notice we don't have any errors, The run() on Thread Class isn't abstract & we are not required to override it
     *      - If we don't override it though, when creating a Thread subclass, the thread isn't going to do anything
     *  - Ctrl + O to override run()
     *  - Add logic to run() - Output 1 at certain intervals using a for loop
     *      - Print 1 in each iteration after 1 second
     *      - Use Thread.sleep()
     *          - tells the current thread to sleep or do nothing, for a certain defined time
     *          - takes a long value rep no of milliseconds to sleep
     *          - If we add 1000 - we are adding a 1-second delay between each count
     *          - need to catch Interrupted Exc - checked exception
     *              - if it does get interrupted - print the stack trace instead of throwing a RuntimeException
     *
     * Next,
     * Execute as a concurrent thread, to the main thread
     * Create an instance of CustomThread class
     * Then call start() on the CustomThread instance
     *  - So start and not the run() ?
     *      -
     *
     *
     * Running this :-
     *  - As it's running the no 1 is printed after every second
     *  - The main thread didn't exit until the thread completed
     *
     * But how do you really know that this thread is running concurrently, to the main thread ?
     * We can test this by setting another for-loop for the main thread that will do something similar
     * This time will loop 3 iterations and print a 0 (zero) for this main thread instead of a 1 for each iteration
     * Use try-catch for any InterruptedException
     *  - Another way to make a thread sleep is using an enum TimeUnit with one of its values which are all units of time
     *     e.g, NANOSECONDS, SECONDS, MINUTES, HOURS etc
     *  - Use SECONDS and pass 1.5 sec
     *      e.g TimeUnit.SECONDS.sleep(1)
     *  - This code calls Thread.sleep under the covers and does the math to figure out how many milliseconds this is
     *  - Catch InterruptedException and print stack trace
     *
     * Running this :-
     *  - Notice , the numbers 0 and 1 are printed out and they're alternating
     * This means that the threads are running concurrently, each doing its own thing & not waiting foreach other
     *
     * So back to the question ?
     *
     * Why did we call start() instead of run ?
     * Updating start() to run()
     *
     * Running this:-
     *  - We get all 5 - 1's printed first and then 3 -0's next
     *
     * When you execute the run(), it gets done synchronously, in the current thread
     * The main thread run this () like any other () on a class , and waits for it to complete before continuing
     *  with its next tasks
     *
     * Difference between start() and run()
     * There's a big diff between start() and run()
     * If you execute the run(), it's executed synchronously by the running thread it's invoked from
     * If you want your code to be run asynchronously, you must call the start()
     *
     * The native modifier on a ()
     * The native modifier indicates that a ()'s source code isn't written in Java.
     * It's written in another language, such as C or C++
     * The code in this example is part of a native lib such as a .dll file
     *
     * Why would you use a native library ()
     *  - To access system-level functionality that's platform specific
     *  - To interface with hardware
     *  - To optimize performance for tasks that might be computationally-intensive
     *
     * In truth, creating a thread is an expensive operation,
     * Later, we'll talk about why you would want to use thread pools, to reduce the work needed to create a thread on your OS
     * In this case, Java is handing off to a native library, to do the thread creation here, to both access system
     *  level functionality and to optimize performance
     *
     * Therefore, calling the run() directly from your thread instance isn't creating a new thread at all, it's just
     *  simply executing that code directly
     *
     * The start() is the method that will do the work of creating the new thread, and on that new thread, executing
     *  the code in your run()
     *
     *
     * Let's look at the 2nd way of creating a new thread
     *
     * 2) Implementing the Runnable Interface
     * Runnable is a Functional Interface
     * It's functional (), or its Single Access Method (SAM) is the run()
     * This means, anywhere you see a Runnable type, it's a target for a lambda expression
     * You can have any class implement the Runnable interface, and then pass it to the thread constructor
     *  to run asynchronously
     *
     * Will insert this code before the main thread - where we have the main thread printing 0-3 times
     * i.e. before the last for-loop in the main()
     *
     * We want this code to run concurrently both with the CustomThread,and the main thread
     *  - Create a Runnable variable , using a lambda expression
     *  - Don't forget that the code we write inside the ()->{} rep the code that goes in the run() of Runnable
     *  - Then define the task, that you want the thread to perform, when it gets executed
     *  - You can think of this as your thread's task
     *      - add a for-loop that prints number 2 for my 2nd concurrent thread on each iteration
     *      - Use milliseconds on the TimeUnit and pass 250ms
     *      - Catch InterruptedException and print stack
     *  - That's the lambda expression containing the code we want to run
     * But how do we actually use it in a thread ?
     * Well, we first need to create a Thread instance and pass this lambda expression, which is ultimately just
     *  Runnable , to the thread constructor, that takes a Runnable as an arg
     * Then call start() on the Thread instance
     *
     * Running this :-
     *  - We can see all 3 threads running at the same time
     *  - The main thread prints the 0's
     *  - The custom thread prints the 1's
     *  - The Runnable thread prints the 2's
     *
     * So, what is really the diff between the 2 approaches ? - refer to the slides
     */

    public static void main(String[] args) {

        Thread currentThread = Thread.currentThread();
        System.out.println(currentThread);
        System.out.println(currentThread.getClass().getName());

        printThreadState(currentThread);

        currentThread.setName("ShakeAndBake");
        currentThread.setPriority(Thread.MAX_PRIORITY);
        printThreadState(currentThread);

        CustomThread customThread = new CustomThread();
        customThread.start();

        Runnable myRunnable = () -> {
            for (int i = 1; i <= 8 ; i++) {
                System.out.print(" 2 ");
                try {
                    TimeUnit.MILLISECONDS.sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread myThread = new Thread(myRunnable);
        myThread.start();

        for (int i = 1; i <= 3; i++) {
            System.out.print(" 0 ");
            try {
                 TimeUnit.SECONDS.sleep(1);
                //Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    /* static void printThreadState(Thread thread)
     * Will not return any data but will take a Thread instance and print the relevant info
     * Call printThreadState(Thread thread) from the main()
     */

    public static void printThreadState(Thread thread){
        System.out.println("_".repeat(50));
        System.out.println("Thread Id : "+thread.getId());
        System.out.println("Thread name : "+thread.getName());
        System.out.println("Thread priority : "+thread.getPriority());
        System.out.println("Thread state : "+thread.getState());
        System.out.println("Thread Group : "+thread.getThreadGroup());
        System.out.println("Thread Is Alive : "+thread.isAlive());
        System.out.println("_".repeat(50));
    }
}
