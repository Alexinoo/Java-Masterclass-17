package concurrency.part18_executor_service_challenge;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    /* The ExecutorService Challenge - ShoeWarehouse Fulfillment
     *
     * Use ShoeFulfillment Example from Synchronization Challenge
     * Use ExecutorService Classes i.e. SingleThreadExecutor, FixedThreadPool, or CachedThreadPool , or a combination
     *  of these
     *
     * Tasks
     *  - Add an ExecutorService in the main() to send orders to the warehouse
     *  - Add an ExecutorService to the ShoeWarehouse class , which will fulfill orders
     * You should create and fulfill 15 shoe orders
     *
     *
     * Task 1 - Add an ExecutorService in the main() to send orders to the warehouse
     * main()
     * Create an instance of the ShoeWarehouse Class
     * Create an ExecutorService using CachedThreadPool
     * Set up a variable next which is a Callable and will process an Order
     *  - Call it orderingTask 0 set it as a multiline lambda
     *      - call generateOrder and store it in a newOrder variable
     *      - sleep between half a second and 2 sec so that the orders are not received all at once
     *      - call receiveOrder() and pass new order
     *      - Notice what we don't have here - and that's a for loop in the task
     *          - This is because we can use ExecutorService to fire of multiple tasks
     *
     *  - Create an Array of Tasks
     *      - Create a List of Callable typed Order named "tasks" and create it by calling nCopies() on Collections
     *      - This creates a list of 15 ordering tasks
     *      - Call invokeAll on the orderingService instance passing the tasks
     *          - Surround with try-catch since invokeAll() throws InterruptedException
     *      - Call shutdown on orderingService
     *      - Then wait for all the threads to terminate but not longer than 6 seconds - use awaitTermination
     *          - Surround with try-catch since invokeAll() throws InterruptedException
     *
     * Task 2 -  Add an ExecutorService to the ShoeWarehouse class , which will fulfill orders
     * GoTo ShoeWarehouse Class
     *  - Add Field
     *      - private final fulfillmentService:ExecutorService
     *  - Constructor
     *      - Assign this field an instance that we get back from the newFixedThreadPool() on Executors Class
     *          - pass 3 meaning this service will only have a max of 3 threads in the pool
     *  - shutDown(): void
     *      - Shut down fulfillmentService smoothly
     *
     *  - receiveOrder()
     *      - Include threadName in the print statement to see which thread is processing which task
     *      - Call submit() on fulfillmentService passing a () reference to consumeOrder()
     *
     *
     * Now we have both sides of Consumer and Producer code both using ExecutorService
     * Finally call shutDown() from the main() to shut the fulfillmentService ExecutorService
     *
     *
     *
     * Running this :-
     *  - Prints 30 statements
     *      - 15 from the threads that are doing the ordering
     *      - 15 from the threads fulfilling those orders
     *  - Producer pool is producing incoming statements
     *      - goes from thread-1 all the way to thread-15
     *  - Consumer pool of threads are the ones that print "Consumed"
     *      - goes from thread-1 to thread-3
     *      - because these threads are reused for tasks
     *
     * In this example, the runnable code tried to include a delay , between half a sec and 5 sec
     * Let's re-write this a little bit
     * Instead of including the delay in the run() or as part of the executing task, instead include the delay,
     *  when submitting the task to the executor service which is more realistic
     *
     * Comment the code that creates a list of 15 tasks as well as the invokeAll()
     *
     * Instead, set a loop to submit tasks
     *  - use for loop to submit task separately
     *  - use a one-liner lambda rather than use a Runnable created earlier to exclude additional sleep
     *  - call submit() on orderingService and pass a Runnable that calls receiveOrder as a lambda
     *
     * Running this:
     *  - Looks similar to the previous output with 15 individually named threads in the Producer's pool
     *  - Waits for 2 sec and then firing off 15 tasks all at once, so the cached pool increases to the same size as the tasks
     *
     * Let's move sleep statement inside the for loop
     *
     * Running this:-
     *  - There's a delay between each order being received
     *  - But more importantly, we don't see 15 unique threads in this case - maybe see 1 or 2
     *  - Remember that a cached thread pool will also re-use threads like a fixed thread pool does
     *
     * In this case, tasks are completing before other tasks get queued up & before the thread expires, within 60 sec,
     *  so the thread can be re-used
     */

    private static final Random random = new Random();

    public static void main(String[] args) {

        ShoeWarehouse warehouse = new ShoeWarehouse();
        ExecutorService orderingService = Executors.newCachedThreadPool();

        Callable<Order> orderingTask = ()->{
                Order newOrder = generateOrder();
                try{
                    Thread.sleep(random.nextInt(500,5000));
                    warehouse.receiveOrder(newOrder);
                }catch (InterruptedException e){
                    throw new RuntimeException(e);
                }
                return newOrder;
        };

//        List<Callable<Order>> tasks = Collections.nCopies(15 , orderingTask);
//        try {
//            orderingService.invokeAll(tasks);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }

        try{
            for (int i = 0; i < 15; i++) {
                Thread.sleep(random.nextInt(500,2000));
                Order newOrder = generateOrder();
                orderingService.submit(()->warehouse.receiveOrder(newOrder));
            }
        }catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        orderingService.shutdown();

        try {
            orderingService.awaitTermination(6, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        warehouse.shutDown();


    }

    /* generateOrder()
     * Generate and returns a new order
     *
     */

    private static Order generateOrder(){
        return new Order(
                random.nextLong(10000,99999),
                ShoeWarehouse.Products[random.nextInt(0, 5)],
                random.nextInt(1,4)
        );
    }
}













