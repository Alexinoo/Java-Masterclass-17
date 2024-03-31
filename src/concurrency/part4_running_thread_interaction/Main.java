package concurrency.part4_running_thread_interaction;

public class Main {

    /*
     * Interacting with threads
     * We've looked at the Thread Class and 2 different ways to create a thread and to run them concurrently with the
     *  main thread
     * Check ()s on both manipulate and communicate with running threads
     * Thread.sleep(Long long) - allow you to pause the execution of a thread for a specified amount of time
     * It temporarily stops the execution of a current thread
     * You can do this in the main thread or in any concurrent threads you create
     *
     * Print the message that "Main Thread is Running"
     *  - Next call Thread.sleep for 1 sec
     *  - Print something before sleep for 1 sec
     *  - Also prints something after sleep for 1 sec
     *
     * Running this:-
     *  - Prints the "Main Thread Running",
     *      then the code pauses for 1 sec
     *          & finishes by printing "Main thread would continue here"
     *
     * Now to create a concurrent thread, will create a new instance of Thread & pass it a runnable lambda expression
     * We can define this directly in the constructor or all in one statement
     *  - Get the thread name
     *  - this code will execute in a new thread, will return that thread's name
     *  - Print a msg "threadName shd take 10 dots to run"
     *  - Then print a dot (.) every half sec for 10 iterations
     *      - start a for loop to loop 10 times
     *      - print a dot in each iteration
     *      - delay for half a second after printing each iteration
     *          - catch InterruptedExc and print "Whoops, threadName was interrupted"
     *          - then exit the loop
     *      - After the loop print "threadName completed"
     *  - Finally, print starting the thread and will include Thread.getName()
     *  - Lastly, executing the thread - starting the thread
     *
     * Running this :-
     *  - We get that the new thread's name is Thread-0, and is starting
     *  - Notice that the "Main thread would continue here" was printed before any dots were printed
     *  - We get the msg "Thread-0 should print 10 dots"
     *  - Then 10 dots are printed
     *  - We get the msg "Thread-0 completed"
     *
     * Now that we've got a running thread, that catches an interruption, lets interrupt this thread from the main
     *  thread
     * First, we want to give it time to run
     *  - Have the main thread sleep for 2 sec
     *  - Catch InterruptedException
     *
     * Then interrupt the new thread by calling interrupt() on the thread variable
     *
     * Running this :-
     *  - The output starts the same, until we get to the 4 dots, which if you think about it is actually 2 sec
     *  - Then we get the msg that "Whoops! Thread-0 was interrupted"
     *
     * That's how you would manually interrupt a running thread from the code that started the thread
     *
     * Let's change the code a little bit and only interrupt the thread if its taking longer than 3 seconds
     * Comment out on the code after thread.start()
     *
     * Then change the code in the concurrent thread
     *  - add some print statements that will print the state of the running thread
     *  - print the current thread's state after each sleep() using A. as placement indicator
     *  - print the state when the interrupted exception is thrown
     *      - use A1 - to know that this coming from the catch clause
     *
     * Next get the current time in milliseconds, elapsed from the epoch time
     *   - Use System.currentMillis()
     *   - Will use it to determine how much time has elapsed
     *   - Use a while loop that loops as long as the isAlive() returns true on the asynchronous thread
     *   - Then print that i'm waiting for the thread to complete
     *   - sleep for 1 second and print the state of the asynchronous thread
     *
     * Interrupt thread after 2 seconds
     *
     * Then print state of the thread variable
     *  Running this :- prints diff values on enum declared on the Thread Class called Thread.State
     *
     * Thread States :-
     *  - NEW - A thread that has not yet started is in this state
     *  - RUNNABLE - A thread executing in the JVM is in this state
     *  - BLOCKED - A thread that is blocked waiting for a monitor lock is in this state
     *  - WAITING - A thread that is waiting indefinitely for another thread to perform a particular action is in this state
     *  - TIMED_WAITING - A thread that is waiting for another thread to perform an action to a specified time is in this state
     *  - TERMINATED - A thread that has exited is in this state
     *
     * Thread.join()
     * Lets you create task dependencies
     * Suppose our current thread is downloading an installation package
     * When it completes, we want to start a separate installation thread, but only if the download actually completed
     * Will create the installation thread nex but first remove the statements that output the thread states
     *
     * Now will create a new Thread immediately after the 1st thread variable
     *  - Call installThread variable
     *  - Start with a new instance of thread and pass a lambda
     *  - Loop 3 times sleeping 250 ms each time
     *  - Then print installation set completed for each iteration
     *  - Catch the interrupted exc and print stack trace
     *  - Will pass the 2nd arg which lets us set up the name of the thread and call it "InstallThread"
     *
     * And since we don't want this thread to run until the 1s one has completed successfully
     * Will call thread.join(), which joins this thread to the current thread which in this case is the main thread
     * This means the main thread will wait here until this thread completes
     *
     * After the thread completes, check if it was interrupted - using isInterrupted() on the thread variable and if
     *  it wasn't, then kick off the installThread
     * Otherwise, print that the previous thread was interrupted and print's that it can't run
     *
     * Comment out on the while loop for a moment
     *
     * Running this:-
     *  - 10 dots are printed and then the message that "Thread-0 has completed"
     *  - Only after this , we see installation steps in the install thread be executed
     *
     * The join() allowed the main thread to wait here, until that other concurrent task completed and before we
     *  proceeded with the dependent task
     *
     * Let's create a 3rd thread that interrupts the first thread if it takes longer than 3 seconds
     * UnComment out on the while loop that we had commented out -
     * Will wrap the while code with a Thread instance and make it part of the lambda expression of this thread
     * Will call it threadMonitor
     *  - remove sout that prints "waiting for thread to complete"
     * We want to start this thread at the same time as the original first thread
     *
     * start threadMonitor after the first thread
     *
     * Java recommends that any () that catches an interrupted exception and is not prepared to deal with it immediately
     *  should reassert the exception
     * They recommend reasserting it rather than rethrowing it, because sometimes you can't rethrow it
     * The thread has to re-interrupt itself, or has to call interrupt on itself

     */

    public static void main(String[] args) {

        System.out.println("Main Thread is Running");

        try {
            System.out.println("Main thread paused for 1 sec");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread thread = new Thread(()->{
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName + " task should take 10 dots to run.");
            for (int i = 0; i < 10; i++) {
                System.out.print(". ");
                try {
                    Thread.sleep(500);

                } catch (InterruptedException e) {
                    System.out.println("\n Whoops! "+ threadName+ " was interrupted");
                    Thread.currentThread().interrupt();
                    return;
                }
            }
            System.out.println("\n"+ threadName+ " completed");
        });

        Thread installThread = new Thread(()->{
            try {
                for (int i = 0; i < 3; i++) {
                    Thread.sleep(250);
                    System.out.println("Installation Step " + (i + 1) + " is completed");
                }
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        },"InstallThread");


        Thread threadMonitor = new Thread(()->{
            long now = System.currentTimeMillis();
            while (thread.isAlive()){
                try {
                    Thread.sleep(1000);
                    if( System.currentTimeMillis() - now > 8000){
                        thread.interrupt();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        });


        System.out.println(thread.getName() +" starting");
        thread.start();
        threadMonitor.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if(!thread.isInterrupted()){
            installThread.start();
        }else{
            System.out.println("Previous thread was interrupted, "+ installThread.getName() + " can't run.");
        }

        /*
        System.out.println("Main thread would continue here");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread.interrupt();
        */
    }
}
