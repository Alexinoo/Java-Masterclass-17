package concurrency.part29_concurrency_extras;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TimerExample {

    /* Timer Class
     * ...........
     * Set up a local variable typed Timer
     *  - create a no args constructor to create an instance of a Timer class
     * When a timer is created, a new background thread is created
     *
     * Instead of a thread, or a Callable or Runnable, we need to create a TimerTask
     *  - We have to override the TimerTask's run() which has the signature "public void run()"
     *      - @Override run()
     *      - get the thread name
     *      - create a date time formatter and pass a specific format
     *      - print the thread name and the time that task was executed as the current local date time
     *  - Call scheduledAtFixedRate() on the timer instance
     *      - takes a timer task
     *      - and other 2 long values both rep time in ms
     *          - delay
     *          - period
     *      - we want the task to run immediately , then every 2 seconds after
     *
     * This code will run at this point and after every 2 seconds & will run until we cancel it through the timer
     *  instance
     *
     * Need a try block so that the thread can sleep for 12 seconds giving it some time to run
     *
     * Finally, cancel the timer instance
     *
     * Running this :
     *  - Thread name appears diff (Timer-0) from the executor's service default thread names
     *  - The task gets executed every 2 sec
     *
     * Should you use this or the Scheduled Executor Service?
     *  - seems simpler on the first look but let's do the same thing with a ScheduledSingleThread Executor
     *  - comment out 2 line
     *      - timer.scheduleAtFixedRate(task , 0 ,2000);
     *      - timer.cancel();
     *  - And now lets use an executor service
     *      - use newSingleThreadScheduledExecutor
     *      - call scheduleAtFixedRate on the executor
     *          - we have tiny flexibility as we can specify the time unit
     *          - notice we can also pass timer task as the Runnable
     *              - That's because the TimerTask actually implements the Runnable interface
     *          - You can't use a Runnable though in the timer instances ()
     *      - call shutdown() on the executor
     *
     * Running this :
     *  - We get the same no of messages, printed every 2 sec
     *  - see the familiar thread name, pool-1-thread-1
     *  - However, the application is not exiting smoothly
     *      - is because we have created a Timer instance as the 1st statement in the main(), and all timer
     *          constructors start a timer thread
     *      - the app won't shutdown unless we execute Cancel manually
     *  - Will comment out the first line instead since we are not using Timer anymore
     *      - i.e. Timer timer = new Timer();
     *
     * Running this :
     *  - The code prints the statements and executes smoothly
     *
     * If you need multiple threads running tasks at fixed rates, then you'd want to use ScheduledThreadPoolExecutor
     *
     * Timer might be useful/simplest route if you just want to schedule a one-off task
     */

    public static void main(String[] args) {
        //Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                String threadName = Thread.currentThread().getName();
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                System.out.println(threadName + " Timer task was executed at : "+ dtf.format(LocalDateTime.now()));
            }
        };

//        timer.scheduleAtFixedRate(task , 0 ,2000);
        var executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(task , 0,2, TimeUnit.SECONDS);

        try {
            Thread.sleep(12000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

//        timer.cancel();
        executor.shutdown();
    }
}
