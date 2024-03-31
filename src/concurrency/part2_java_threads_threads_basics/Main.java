package concurrency.part2_java_threads_threads_basics;

public class Main {
    /* Java Threads
     * ............
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
     */

    public static void main(String[] args) {

        Thread currentThread = Thread.currentThread();
        System.out.println(currentThread);
        System.out.println(currentThread.getClass().getName());

        printThreadState(currentThread);

        currentThread.setName("ShakeAndBake");
        currentThread.setPriority(Thread.MAX_PRIORITY);
        printThreadState(currentThread);

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
