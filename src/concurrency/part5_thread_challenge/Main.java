package concurrency.part5_thread_challenge;

public class Main {

    /*
     * Thread Challenge
     * ................
     *
     * Create and manage threads, using 2 approaches
     * 1) Sub classing the Thread Class
     *      - should have a run()
     *      - Print 5 even numbers
     * 2) Implementing the Runnable Interface
     *      - This can be any class that implements Runnable, or a lambda expression
     *      - Print 5 odd no's
     *
     * Execute them asynchronously, calling that start() on each, in 2 consecutive statements
     * Have your main() after these threads interrupt 1 or both of these threads
     * Your application should not crash, meaning the threads should handle an InterruptedException
     *
     * Solution
     * ..........
     * Create the 2 classes :- OddThread and EvenThread
     *  - Implement run() in both
     *
     * Create an instance of the OddThread
     * Create an instance of the Thread class and pass a new instance of the EvenThread to this constructor
     *
     * Next, put the main thread to sleep before interrupting one of the threads
     * This lets them do a little work first
     *
     * Interrupt the oddThread, which means it will stop executing at some point but EvenThread should continue
     *  running
     *
     * Running this:-
     *  - Prints
     *      oddThread: 1
            evenThread: 2
            oddThread: 3
            evenThread: 4
            Odd Thread was interrupted
            evenThread: 6
            evenThread: 8
            evenThread: 10
     *  - As you can see the program did not crash when OddThread was interrupted, but rather handled the exception,
     *      printed a message, while EvenThread kept executing
     *
     * /// BONUS ////
     * Replace the EvenThread with a Runnable class inside the main ()
     * Pass runnable to the constructor of the evenThread
     *
     *
     * Running this:-
     *  - And we get the same results
     *  - Whether you use a lambda expression or a class that implements Runnable is a matter of style
     *  - If you need additional helper ()s, or some state for your running (), then obviously using a class
     *    makes more sense
     *
     */

    public static void main(String[] args) {

        OddThread oddThread = new OddThread();
        oddThread.start();

       // Thread evenThread = new Thread(new EvenThread());
       // evenThread.start();

        Runnable runnable = ()->{
            for (int i = 2; i <= 10; i+=2) {
                System.out.println("fromRunnable: "+ i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("from Runnable was interrupted");
                    break;
                }
            }
        };

        Thread evenThread = new Thread(runnable);
        evenThread.start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        oddThread.interrupt();
    }
}
