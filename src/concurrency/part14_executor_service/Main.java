package concurrency.part14_executor_service;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    /* Managing Threads - Introduction to the ExecutorService, SingleThreadExecutorService
     *
     * These are the ExecutorService classes, and they exist to manage the creation and execution of threads
     *
     * Managing Individual Threads
     * ...........................
     * As we've seen, when using a Thread Class, you have limited control over that thread
     * You can interrupt a thread and join it to another thread
     * You can name the thread, try to prioritize it, and start each manually, one at a time
     * You can also pass it an UnCaughtExceptionHandler, to deal with exceptions that happen in a thread
     * Managing threads manually can be complex and error-prone
     * It can also lead to complex issues like resource contention, thread creation overhead, and scalability challenges
     *
     * For these reasons, you'll want to use an ExecutorService, even when working with a single thread
     *
     *
     * Benefits of Managing Threads with an Implementation of ExecutorService
     * ......................................................................
     * The ExecutorService type in Java is an interface.
     * Java provides several implementations of this type which provide the following benefits.
     *  - Simplify thread management, by abstracting execution, to the level of tasks which need to be run
     *  - They utilize resources more efficiently, through the use of Thread Pools, reducing the cost of creating new threads
     *  - They provide efficient scaling, by utilizing multiple processor cores
     *  - They come with built-in synchronization, reducing the chances of concurrency-related errors
     *  - They implement graceful shutdown, preventing resource leaks
     *  - Scheduled implementations exist to further help with management workflows
     *
     * We'll start with the single implementation of this type : SingleThreadedExecutor
     *
     *
     * Will use ThreadColor enum class
     *
     * Without Using an ExecutorService First
     *
     * static countDown() : void
     *  - Store the current thread name to available named "threadName"
     *  - Initialize "threadColor" variable to ThreadColor.ANSI_RESET
     *  - Use try-catch {}
     *      - Override the value of "threadColor" variable with the value of the currentThread
     *      - Catch an illegalBadArgumentException, if the user passes bad color - ignore it - do nothing
     *  - Assign the color of the current thread to a String variable "color"
     *  - Loop back from 20
     *      - print the color
     *      - replace String ANSI with ""
     *
     *
     * Main()
     * Set up a series of threads that prints diff color text for each thread
     * Use primary colors
     *  - Thread blue
     *      - Pass a method reference to the countDown () as the 1st arg
     *      - Pass a thread name that rep the color of the text it should print as the 2nd arg "ANSI_BLUE"
     *
     *  - Thread yellow
     *      - Pass a method reference to the countDown () as the 1st arg
     *      - Pass a thread name that rep the color of the text it should print as the 2nd arg "ANSI_YELLOW"
     *
     *  - Thread red
     *      - Pass a method reference to the countDown () as the 1st arg
     *      - Pass a thread name that rep the color of the text it should print as the 2nd arg "ANSI_RED"
     *
     * Next
     *  - Call start on each thread
     *  - Doesn't matter the order - add them in the order in which they were declared
     *  - Remember, even if we call them in that order, doesn't mean they'll be executed in that order
     *
     * Next
     *  - Call join() on all the 3 threads
     *  - Each will join to the main thread as the finish
     *  - But again, what order they occur in, is determined by the processes, executing the threads
     *  - Handle InterruptedException - on all the 3 threads
     *
     * Running this :-
     *  - Output is sporadic (irregular intervals) as you might expect
     *  - All the colors counting down from 20
     *
     * There's nothing new here, and the reason for setting this code this way, is for us to appreciate the Executor Service
     * If we wanted these to be called asynchronously, but only 1 asynchronous thread at a time, we would have to re-arrange the starts and joins
     *
     * Next
     *  - Rea-arrange
     *      - Place blue.join() directly below blue.start()
     *      - Do something similar for yellow and red
     *
     * Running this :-
     *  - Output is more of a workflow structure, where 1 process is running, allowing the main thread to continue doing something,
     *     or other threads to run, but the red and yellow threads have to wait
     *
     *
     *
     * There are several ways to do this using the ExecutorService implementations
     *
     * 1) Simplest Implementation - SingleThreadedExecutor
     * ....................................................
     *
     * First :
     *  - Rename main to notMain
     *  - The desire is to see both implementations side by side,
     *  - Therefore will create a new main() in the same class
     *
     * main()
     *  - set a local variable with type inference "blueExecutor"
     *  - Pass it a runnable (in this case a method reference to the countDown()
     *
     * Java provides a Class called Executors which contains static factory ()s , to get a wide variety of diff executor service implementations
     * All this ()s have a "new" prefix and in this case will "newSingleThreadExecutor"
     * Once we have an executor service, we can pass it a runnable (in this case a method reference to the countDown() to execute()
     *
     * Notice, what's not in this code, is barely a mention of a thread anywhere
     * We are not instantiating any threads or even starting them
     * This Executor Service "Executors.newSingleThreadExecutor()" and all executor service implementations, will create threads as needed whose run
     *  ()s implement the runnable code we pass to it
     * How it creates the threads and when, is one of the distinctions between each of the diff kinds of executor services
     *
     * Running this:-
     *  - First, we get a countdown from 20 to 0 though it's not in color
     *      - This is because we didn't give our thread the thread name, and is using default color
     *  - Second, notice that the application appears to still be running
     *      - Shut down that manually for now
     *
     * An Executor service must be shut down by calling a shutdown()
     *  - Add it below the execute()
     *
     * Running this again :-
     *  - The application now ends and exits smoothly
     *
     * First , let us talk about the thread name
     *  - The executor service is creating threads and giving them default names
     *  - In this case, we have 1 thread and its name is pool 1 thread 1 which is java's convention for naming threads in executor services
     *
     * In most cases, you wouldn't have to worry so much about the thread names, but in this case, we do care and we want to set it to the
     *  ANSI color
     *
     * How can we pass a thread name to this ?
     *  - Looks like we can't, not the way this is written
     * We can create a Class that implements something called ThreadFactory interface
     *  - Then pass ThreadFactory instance, to an overloaded version of the () Executors.newSingleThreadExecutor
     *  - By supplying our own thread factory class, we can override the standard way an executor creates a thread, and use custom
     *     functionality instead
     *
     * Create a New Class :
     *  ColorThreadFactory implements ThreadFactory
     *   Fields:
     *      - String : threadName
     *   Constructor:
     *      - Takes an enum value - ThreadColor color instance
     *      - set threadName to the enum constant
     *   Override newThread(Runnable r)
     *      - Replace null with some custom code
     *      - Create an instance of a new Thread
     *      - set its name to the threadName field
     *   Return the thread
     * This is the factory method for creating a thread
     *
     * Now that we have a factory class, we can pass it the call to get a newSingleThreadExecutor()
     *
     * Running this :-
     *  - We get the same results as before but in blue now
     *  - Though this doesn't feel like a simpler code, because of the ThreadFactory Class, would guess that 90% of your time, you won't need
     *    a ThreadFactory - still good to know how to create and use one and this code was a perfect opportunity to demo that
     *
     * Let's kick off the next 2 tasks
     *  - Copy the blueExecutor and paste twice
     *  - Replace to yellow and red executors
     *
     * Running this :-
     *  - We can see that the threads are running concurrently with each other
     *
     * A SingleThreadedExecutorService can still be used to create a multithreaded environment, by executing multiple of them as we've done
     *  with blue,yellow and red executor
     * Although we called shutdown() on the blue thread, the shutdown()s wait for all threads to finish, either with a successful completion
     *  or an exception
     * In this case, there's only 1 thread to wait for, but while it's waiting to shut down, the yellow and red threads can start
     *
     * So that's one of the Executor Service types, If we wanted to create the workflow situation which we implemented earlier , we can do this
     *  with another () on the executor service
     *
     * Will add this after blue shutdown()
     * This method is known as awaitTermination, it returns a boolean, so we'll create a local variable to assign the result
     *  - Create local variable "isDone" and initialize it to false
     *  - Call awaitTermination() on blueExecutor and assign the result to the boolean variable
     *  - Catch InterruptedException
     *
     * Next, wrap the rest of the code with an if statement
     *  - check if "isDone" is truthy,
     *      - print some statement to the user to show that it has finished
     *      - proceed with the rest of the statement
     *          - do the same for the yellowExecutor
     *              - assign isDone to awaitTermination() on yellowExecutor
     *                  - if truthy , proceed with red
     *          - do something similar with redExecutor
     *          - print "All processes completed"
     *
     * Running this:-
     *  - We now have one asynchronous thread at a time running now
     *
     * So we can use 1 executor service that operates on a single thread or task in diff ways
     * We can wire it up in ways similar to those we did when manipulating individual threads
     * The SingleThreadExecutor service makes sense to use in the second case as shown
     *
     * But if you really want these threads to fire up asynchronously, all executing the same tasks, there are several other alternatives that
     *  make more sense
     *
     */

    public static void main(String[] args) {
        var blueExecutor = Executors.newSingleThreadExecutor( new ColorThreadFactory(ThreadColor.ANSI_BLUE));
        blueExecutor.execute(Main::countDown);
        blueExecutor.shutdown();

        boolean isDone = false;
        try {
            isDone = blueExecutor.awaitTermination(500, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (isDone){

            System.out.println("Blue finished , starting Yellow");

            var yellowExecutor = Executors.newSingleThreadExecutor( new ColorThreadFactory(ThreadColor.ANSI_YELLOW));
            yellowExecutor.execute(Main::countDown);
            yellowExecutor.shutdown();

            try {
                isDone = yellowExecutor.awaitTermination(500,TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (isDone){
                System.out.println("Yellow finished , starting Red");
                var redExecutor = Executors.newSingleThreadExecutor( new ColorThreadFactory(ThreadColor.ANSI_RED));
                redExecutor.execute(Main::countDown);
                redExecutor.shutdown();

                try {
                    isDone = redExecutor.awaitTermination(500,TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                if (isDone){
                    System.out.println("All processes completed");
                }

            }

        }
    }

    public static void notmain(String[] args) {

        Thread blue = new Thread(Main::countDown , ThreadColor.ANSI_BLUE.name());
        Thread yellow = new Thread(Main::countDown , ThreadColor.ANSI_YELLOW.name());
        Thread red =  new Thread(Main::countDown , ThreadColor.ANSI_RED.name());

        blue.start();
        try {
            blue.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        yellow.start();
        try {
            yellow.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        red.start();

        try {
            red.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void countDown(){
        String threadName = Thread.currentThread().getName();
        ThreadColor threadColor = ThreadColor.ANSI_RESET;
        try{
          threadColor = ThreadColor.valueOf(threadName.toUpperCase());
        }
        catch(IllegalArgumentException ignore){
            //User may pass a bad color name, Will just ignore this error.
        }
        String color = threadColor.color();

        for (int i = 20; i > 0 ; i--) {
            System.out.println(color + " "+ threadName.replace("ANSI_","")+" " +i);
        }
    }


}











