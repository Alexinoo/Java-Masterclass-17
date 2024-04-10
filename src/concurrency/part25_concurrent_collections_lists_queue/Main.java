package concurrency.part25_concurrent_collections_lists_queue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {

    /*
     * Concurrent Collections
     * .........................
     * LinkedList and ArrayList, as well as TreeSet and HashSet, are also NOT thread-safe
     * Each of these can be used with a synchronized wrapper, which you can get from the Collections helper class
     * They are also available for these types as well,especially if you find yourself maintaining or altering some
     *  legacy code, in a multithreaded application
     * The synchronized wrapper provide a thread-safe option for you, with less impact on the design, if you need to
     *  make existing code work concurrently
     * If you're starting with new code though, the instructor recommends using concurrent collections
     *
     * Concurrent Collections for Arrays and Lists
     * ...........................................
     * For lists, there are 2 concurrent collection choices, depending on the type of work which needs to be done in
     *  parallel
     *  - ConcurrentLinkedQueue
     *      - use it when you have frequent insertions and removals, such as producer-consumer scenarios or task scheduling
     *  - CopyOnWriteArrayList
     *      - use it when you have a read-heavy workload with infrequent modifications
     *      - useful for scenarios like configuration management, or read-only views of data
     *
     * For an array, you can use one of the concurrent list options above Or Use
     *  - ArrayBlockingQueue
     *      - is a fixed queue that blocks under 2 circumstances
     *          - If you try to poll or remove an element from an empty queue
     *          - If you try to offer, or add an element to a full queue
     *      - This is designed as a FIFO (First In First Out)
     *
     *
     * ArrayBlockingQueue Class
     * ........................
     * Use the Person obj to generate random persons
     * Switch away from parallel streams and set up a producer-consumer example which will use an ArrayBlockingQueue
     *
     * Create a Class : VisitorList
     *  Case Study
     *    - Imagine we work at a park & we have a kiosk in the visitor's center
     *    - Visitors can enter their data & have an electric coupon credited to their record if they aren't in the existing list
     *    - A visitor can use this coupon in the visitor center's store or be applied to their next entry fee
     *
     * - Create a producer task, to add visitors record to a temporary queue
     * - The producer code will add the visitor data, to an interim queue to be processed by consumer code later
     * - Fields
     *    static final newVisitors : ArrayBlockingQueue
     * - The ArrayBlockingQueue takes a capacity as its argument
     *      - start with only 5 visitors at a max in the queue
     * - You use an ArrayBlockingQueue when you want to control the number of elements being processed in some fashion
     *
     * main()
     * - Create a Producer Runnable - with no args lambda
     *      - Create a new visitor (leverage our person record)
     *          - print out we are adding visitor here
     *      - queued : boolean  (initialize it to false)
     *      - Add new visitor to newVisitors queue
     *          - the add() returns a boolean (added successfully or not)
     *      - If visitor was added successfully
     *          - print the newVisitors list after a successful add
     *      - Otherwise
     *          - print the queue is full
     *
     * To Execute this code
     *  - Create a ScheduledExecutor "producerExecutor" and assign that a new single threaded scheduled executor
     *  - Execute task every second starting immediately with o sec delay, in other words for the first task
     *
     * Stop at some point, but need to run for 10 sec first by using the awaitTermination() with a timeout
     *
     * Use a while loop
     *  - Continue adding visitors until either
     *      - Queue is full
     *      - 10 seconds elapses
     *
     * Running this:
     *  - Visitors are added with each subsequent visitor being added after a 1 sec delay
     *  - the new visitors queue keeps growing
     *
     * Right now there's no consumer threads to do anything with this information
     *  - Then on the 6th visitor, the app seems to be hanging and finally quits because of the timeout we have specified
     *     on the awaitTermination code
     *  - The 6th visitor never gets added to the queue, because the producer thread actually errored out, on that
     *     add()
     * When using ArrayBlockingQueue, there are several ()s to choose from to add an element
     *  - Each is slightly different
     *
     * Adding an Element to the ArrayBlockingQueue
     *  - add(E e)
     *      - which we have used here
     *      - throws an unchecked exception in a thread when the queue is full but our code seems not to be monitoring it
     *      - We only get the info "customer wasn't added because the queue was full at the time"
     *
     *  - offer(E e) , offer(E e , long timeout, TimeUnit unit)
     *      - Adds element to the tail of the queue
     *      - offer(E e) doesn't block or throw an InterruptedException and returns false if the queue is full and the
     *          element can't be added
     *      - offer(E e , long timeout, TimeUnit unit)
     *          - takes a timeout
     *          - blocks temporarily during that timeout, then return false if the queue is full, or at capacity
     *          - need to catch or specify the InterruptedException
     *
     *  - put(E e)
     *      - is a blocking () - blocks indefinitely if the queue is full
     *      - returns a void
     *
     * Wrap the add() ith a try-catch
     *  - The add() throws an IllegalStateException, and so we'll catch that and print something to that effect
     *
     * Running this :-
     *  - We now have more info that the queue is full
     *  - IllegalStateException is printed regularly
     *
     * This doesn't fix the problem, since we keep getting this exception until something starts emptying the queue
     * Meanwhile, we are not adding visitors to the queue & they wouldn't be getting their coupons
     *
     * Suppose we swap add() with put() - lets do that
     *  - put() unlike the others doesn't return a boolean, so won't be assigning to any variable
     *  - Update the exception from IllegalStateException to InterruptedException
     *  - set queue to true after calling the put()
     *
     * Running this :-
     *  - 5 visitors are added just fine, but the code is definitely blocked , or hanging
     *
     * Without consumer code or some code draining the queue, we're stuck here, until we kill the app
     *
     * There's still another option with this class : offer(E e , long timeout , TimeUnit unit)
     *  - comment out on the 2 statements in the try {} and retain the InterruptedException since offer(E e) still throws it
     *  - use the overloaded version and wait for 5 sec - if anything doesn't happen , quit
     *
     *
     * Running this :-
     *  - Adds 5 visitors
     *  - When adding the sixth one, the code sits here for 5 sec
     *  - Then we get the msg from the producer thread, the queue is full and that the visitor wasn't added
     *
     * If this were real code, we'd probably want to send off some alarm bells somehow, letting the outside
     *  world knows there is a problem here
     *
     * Or I might try to recover from this situation by having the producer thread kick off some consumer threads
     *  maybe
     * At least for now, this code won't hang or block, while trying to add a visitor to the queue
     *
     * For our use case, this just means some visitors won't get coupons.
     *
     * Actually. let's do this
     *  - log off all visitors to a file, as well as the visitor waiting to be added to the queue
     *
     * There's a () on the ArrayBlockingQueue that lets you drain the queue to another collection
     *  - print "Draining the queue and writing to a data file" after the queue gets full
     *  - set up a temporary list - an array list of Persons
     *  - call drainTo() on the newVisitors queue, passing it the temp list
     *      - will populate the temp list with all the visitors in the queue as well as empty it
     *  - create an array list of Strings next
     *  - populate that from the temp list elements
     *  - also adds the current visitor's data to this strings list
     *  - Then write to a file
     *      - need a try block
     *      - use Files.write()
     *          - pass a path for a file which does not exist named "DrainedQueue.txt"
     *          - pass what we want to be printed, the visitors data strings
     *          - pass the var args of both CREATE if it doesn't exist or APPEND if exist from the StandardOpenOptions enum
     *      - throw IOException as a RuntimeException
     *
     * Update timeout in the awaitTermination to 20 sec
     *
     * That's a rough attempt to try to handle the situation, while the consumer threads are missing in action
     * And of course we don't have any consumer threads.
     * But you can imagine there could be situations, where something like this could happen, if they did exist
     *
     * Running this:
     *  - get to the sixth visitor and wait for about 5 seconds
     *  - see a text queue is full and that's its draining and being written to a file
     *  - then more 5 visitors are added successfully and once again on the 12th one, the process repeat itself
     *  - Then the 20 seconds timeout completes
     *  - Outputs all 12 of the visitors listed in the "DrainedQueue.txt"
     *
     * This list could be consulted, if there is any question about a missing coupon, or a customer service rep could
     *  add the coupons manually
     *
     * Ok let's add some consumer code to consume this queue, and look at the other side, getting data out of this thread
     *  safe queue
     *
     */

    private static final ArrayBlockingQueue<Person> newVisitors = new ArrayBlockingQueue<>(5);
    public static void main(String[] args) {

        Runnable producer = ()-> {
            Person visitor = new Person();
            System.out.println("Adding "+ visitor);
            boolean isQueued = false;
            try{
                //isQueued = newVisitors.add(visitor);
                //newVisitors.put(visitor);
                //isQueued = true;
                isQueued = newVisitors.offer(visitor,5,TimeUnit.SECONDS);
            }catch (InterruptedException e){
                System.out.println("Illegal State Exception!");
            }
            if (isQueued)
                System.out.println(newVisitors);
            else{
                System.out.println("Queue is Full, cannot add "+visitor);
                System.out.println("Draining Queue and writing data to a file");

                List<Person> tempList = new ArrayList<>();
                newVisitors.drainTo(tempList);

                List<String> lines = new ArrayList<>();
                tempList.forEach( singleVisitor -> lines.add(singleVisitor.toString()));
                lines.add(visitor.toString());

                try{
                    Files.write(Path.of("DrainedQueue.txt"),lines, StandardOpenOption.CREATE ,StandardOpenOption.APPEND);

                }catch (IOException e){
                    throw new RuntimeException(e);

                }
            }
        };

        ScheduledExecutorService producerExecutor = Executors.newSingleThreadScheduledExecutor();
        producerExecutor.scheduleWithFixedDelay(producer , 0, 1, TimeUnit.SECONDS);

        while (true){
            try {
                if(!producerExecutor.awaitTermination(20, TimeUnit.SECONDS))
                    break;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        producerExecutor.shutdown();

    }
}
