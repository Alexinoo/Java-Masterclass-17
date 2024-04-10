package concurrency.part25_concurrent_collections_lists_queue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Stream;

public class VisitorList {
    /* Thread-safe Lists and Queues, ArrayBlockingQueue
     * ................................................
     *
     * Case Study
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
     *  - Execute task every second starting immediately with 0 sec delay, in other words for the first task
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
     *
     * //////////////////  ArrayBlockingQueue, Consumer Tasks ///////////////////////////////
     *
     * Create a master list of visitors
     *  - represents the park system's currently registered visitors
     *  - Our visitor's center only wants to give out discounts to new visitors
     * Use
     *   static final masterList : CopyOnWriteArrayList<Person>
     *
     * This class is also thread safe & it's not a fixed size and it never blocks
     *
     * Next
     *  - populate this list with a static initializer block
     *  - since its final, its the only place, other than the declaration statement itself, to assign a value
     *  - also lets us know one way you can collect a stream of elements, into this concurrent class
     *      - generate random persons
     *      - distinct - each customer is unique by combination of name and age (limit the no of new visitors we encounter)
     *      - limit to 2500
     *      - use collect terminal operation and pass 3 args
     *          - supplier first - () reference for a new CopyOnWriteArrayList
     *          - accumulator - () reference to add an element to this array list
     *          - combiner  - () reference to addAll
     *
     * Next : Set up a Consumer runnable just after the producer runnable
     *  - Call it consumer
     *  - get the thread name
     *  - print the thread name and the queue size
     *  - return the first visitor at the head of the queue using poll()
     *  - if the visitor is not null
     *      - print which thread is processing which visitor
     *          - check if the curr visitor is in the masterList
     *              - if they aren't, in the master list, add them
     *                 - also prints they are new and should get a coupon
     *  - Print the newVisitors queue size again, after this process
     *
     * - Since we want consumer threads running, create a thread pool
     *     - Needs to be scheduled
     *     - Create a new Scheduled thread pool with 3 threads at the start
     * - Since we want to schedule 3 tasks, wrap this in a for loop
     *     - use scheduleAtFixedRate and pass the consumer runnable, have the first task start after 6 sec have elapsed
     *        then have them scheduled at every 3 sec thereafter
     * - Need to shut down the consumer executor at some point
     *     - copy the while loop and the shutdown statements and paste a copy at the end of this ()
     *     - update from producerExecutor to consumerPool
     *     - update the timeout in the awaitTermination () from 20 sec to 3 sec
     *
     * - Change the timeout in the awaitTermination () call from producerExecutor from 20 sec to 10 sec
     * - Delete the DrainedQueue.txt first
     *
     * Will change the print statements in the producer runnable , so that the output will be easier to understand
     *  - change from adding to queueing
     *  - comment out on the code that prints the entire queue
     *
     *
     * OK, Looking at this code, we should get 5 visitors queued up, before any consumer threads get started
     * While the 6th visitor is waiting to be queued in the offer(), for that 5 seconds, the consumer threads will
     *  start polling the queue and the 6th visitor shd be successfully added to the queue
     * This means the code won't be writing to the DrainingQueue.txt file
     *
     * 3 consumer threads will work on the queue at fixed intervals, starting up every 3 sec after an initial delay of
     *  6 sec
     *
     * This will give the single threaded produces the chance to add 3 more visitors to the queue
     *
     * Running this:-
     *  - visitors queue up every second
     *  - pauses at the 6th visitor ( call to the offer() has 5 sec timeout - waits up to 5 sec to succeed)
     *  - meanwhile, the consumer threads start polling the queue
     *  - Each finds a full queue
     *      - each will get a visitor at the head of the queue
     *  - Notice that each got a different visitor
     *
     * This means the ArrayBlockingQueue is doing its job, managing the head of the queue efficiently in a
     *  multi-threaded environment
     *
     * After processing, the queue size is 3, that's because the producer was able to add that 6th visitor while these
     *  threads were processing their element
     *
     * This continues and we can see statements that 1 or 2 visitors added to the master list and getting a coupon
     *
     * Notice also that the poll() doesn't block if the queue is empty
     *  - just returns a null value
     *
     * Running again, we see 1 or 2 visitors are added out of a total of 9 or 10 queued.
     *
     * Adding visitors to the master list is an infrequent occurrence, which is the reason why we chose
     *  CopyOnWriteList
     *
     * CopyOnWriteList
     * ...............
     * The name "CopyOnWrite" is important
     * Whenever this list is modified, by adding,updating, or removing elements, a new copy of the underlying array
     *  is created
     * The modification is performed on the new copy, allowing concurrent read operations to use the original
     *  unmodified []
     * This ensures that reader threads aren't blocked by writers
     * Since changes are made to a separate copy of the [], there aren't any synchronization issues between the reading
     *  and writing threads
     * This is ordinarily too costly but may be more efficient than alternatives when traversal operations, vastly
     *  outnumber mutations
     *
     *
     * Removing Single Element From the ArrayBlockingQueue
     * ...................................................
     * The ArrayBlockingQueue has different ()s to ge an element from the queue
     * Most of these will get the element at the head of the queue, or the first in
     * If the queue is empty, a () will return null or block as described as
     * The instructor has include the peek() since it's going to return an element from the queue, but it doesn't
     *  actually remove it from the queue
     * peek() can be used to check the queue, before attempting a blocking ()
     *
     * Others include :
     *  - peek()
     *  - poll()
     *  - poll(long timeout,TimeUnit unit)
     *  - remove()
     *  - remove(Object obj)
     *  - take()
     *
     *
     * Change the poll() to include a timeout.
     *  - This makes this code more efficient, forcing a thread to hang out and wait rather than disposing a thread
     *     and creating one, when there's no work
     *  - Go to consumer runnable and add timeout args to the poll()
     *      - make it 5 sec , so a thread will wait up to 5 sec for a queued element to be added
     *      - handle checked exc - InterruptedException use try-catch
     *
     * Change the rate the producer adds a visitor from 1 sec to say 3 seconds,
     *  - so that the queue will be empty more often
     *
     * Change the fixed rate
     *  - so that the consumer threads run more often every second after the first delay of 6 sec
     *
     *
     * Running this :
     *  - 3 visitors are added by the time our 3 consumer threads are kicked off and those threads can do their work
     *  - Then we see the 3 threads polling again, and getting an empty queue with size 0
     *  - Then the producer adds a fourth visitor, who's immediately processed by one of the waiting threads
     *  - Once the 10 sec elapses, the application ends
     *
     * You might have a good idea to waiting a little bit to wait for the elements to be added to the queue, instead
     *  of trying then immediately failing
     *
     * Use take() instead of poll(5, TimeUnit.SECONDS)
     *  - Replace poll() with take()
     *
     * Running this :-
     *  - This code runs as it did before, until the 2nd round of consumer threads, encounter an empty queue
     *  - Then the app hangs and never shuts down
     *
     * There might be very valid reasons for blocking indefinitely, and having these threads waiting here
     *
     * We used Consumer-Producer example with 2 more thread-safe classes
     *
     * ArrayBlockingQueue
     *  - gives us a lot of different options, to control and manage the shared data being processed
     *
     * CopyOnWriteArrayList
     *  -  useful when the shared data is mostly going to be accessed for reading
     *  - An expensive copy of the array list is made, if adds, or updates are made to the list, so make
     *    sure you know how your shared list will be accessed before making a decision to use this type
     *
     * The alternative to CopyOnWriteArrayList is the ConcurrentLinkedQueue which we discussed in the previous slide
     *
     */

    private static final CopyOnWriteArrayList<Person> masterList;

    static {
        masterList = Stream.generate(Person::new)
                .distinct()
                .limit(2500)
                .collect(CopyOnWriteArrayList::new,CopyOnWriteArrayList::add, CopyOnWriteArrayList::addAll);
    }

    private static final ArrayBlockingQueue<Person> newVisitors = new ArrayBlockingQueue<>(5);
    public static void main(String[] args) {


        Runnable producer = ()-> {
            Person visitor = new Person();
            System.out.println("Queueing "+ visitor);
            boolean isQueued = false;
            try{
                isQueued = newVisitors.offer(visitor,5, TimeUnit.SECONDS);
            }catch (InterruptedException e){
                System.out.println("Illegal State Exception!");
            }
            if (isQueued) {
                //System.out.println(newVisitors);
            }else{
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

        Runnable consumer = ()-> {
          String threadName = Thread.currentThread().getName();
            System.out.println(threadName + "Polling queue "+ newVisitors.size());
            Person visitor = null;
            try {
                visitor = newVisitors.take();
               // visitor = newVisitors.poll(5, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (visitor != null){
                System.out.println(threadName + " "+ visitor);
                if (!masterList.contains(visitor)){
                    masterList.add(visitor);
                    System.out.println("--> New Visitor gets Coupon! : "+visitor);
                }
            }
            System.out.println(threadName + " done "+ newVisitors.size());
        };

        ScheduledExecutorService producerExecutor = Executors.newSingleThreadScheduledExecutor();
        producerExecutor.scheduleWithFixedDelay(producer , 0, 3, TimeUnit.SECONDS);

        ScheduledExecutorService consumerPool = Executors.newScheduledThreadPool(3);
        for (int i = 0; i < 3; i++) {
            consumerPool.scheduleAtFixedRate(consumer,6,1 , TimeUnit.SECONDS);
        }

        while (true){
            try {
                if(!producerExecutor.awaitTermination(10, TimeUnit.SECONDS))
                    break;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        producerExecutor.shutdown();

        while (true){
            try {
                if(!consumerPool.awaitTermination(3, TimeUnit.SECONDS))
                    break;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        consumerPool.shutdown();
    }
}
