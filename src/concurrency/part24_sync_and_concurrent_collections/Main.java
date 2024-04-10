package concurrency.part24_sync_and_concurrent_collections;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    /* Synchronization and Concurrent Collections
     *...........................................
     *
     * Start by printing the class name of the Map instance
     *
     * Running this :
     *  - we get back java.util.HashMap
     *
     * The HashMap and other common implementations like ArrayList, LinkedList, HashSet and TreeSet aren't thread safe
     *
     * Why the HashMap isn't thread-safe ?
     *  - Lacks synchronization - does not provide any internal synchronization mechanisms, to ensure thread safety by concurrent processes
     *      - As a result, 2 or more threads modifying the same HashMap, can interfere with each other leading to issues like lost updates, infinite
     *           loops, or inconsistent data
     *  - No guarantees of memory consistency , while iterating
     *      - When 1 thread is iterating over a HashMap while another thread modifies it, there is no guarantee that the iterator will reflect the most
     *         up-to-date state of the map
     *      - This can lead to ConcurrentModificationException, or unpredictable behavior during the iteration process
     *
     * Although the HashMap isn't thread safe, each parallel thread will have its own copy of a map instance, and won't be contending with another thread
     *  for access to a single copy
     * When the threads complete, the code operates by merging 2 maps together, by key
     * This type of merge is computationally expensive and therefore its recommended for maps , you don't code your parallel streams this way
     *
     * Change groupingBy() to use groupingByConcurrent() which is more efficient for parallel streams
     *
     * Running this:
     *  - we now get java.util.ConcurrentHashMap instead of a HashMap
     *
     * This is a class defined in java.util.concurrent package
     */

    public static void main(String[] args) {

        Map<String,Long> lastNameCounts =
                Stream.generate(Person::new)
                        .limit(10000)
                        .collect(Collectors.groupingByConcurrent(Person::lastName, Collectors.counting() ));

        long total = 0;
        for (var count : lastNameCounts.values())
            total += count;
        System.out.println("Total = "+total);

        System.out.println(lastNameCounts.getClass().getName());

        /*
         * Let's create a pipeline stream that has side effects, meaning its going to change the state of an instance, that's not part of the pipeline
         * Set up a TreeMap variable manually before executing the stream pipeline
         *
         * Create a scenario, where the map already exists, in other words, the stream won't create the map, but it will add or change data in the map
         *  - Generate a stream of 10000 persons
         *  - make it a parallel stream
         *  - In the forEach()
         *      - use the map merge(), to merge data into the existing map
         *      - if the last name isn't in the map, this should add the last name, with an initial value of 1 to start
         *  - Remember, this kind of change, where we are adding a key is a structural change to the map
         *  - If the key exists, increments the value with 1
         *  - Print out this map
         *
         * Will then copy the code that prints total counts using the map values above and paste it below lastCounts print statement
         * Update lastNameCounts to lastCounts in the for loop
         *
         * Running this:
         *  - The output is very unpredictable and might get an exception when the merge() is executed in the stream
         *  - We get ConcurrentModificationException from the stream pipeline code, while it's trying to use some kind of iterator
         *  - Run a couple of times, until it actually hopefully completes without an exception
         *
         *  - Prints the last name in order but the total count is less than 10000 which is not true
         *
         * The way we have coded below with a TreeMap, with a parallel stream making updates to it, potentially has many diff kinds of problems
         *  - Concurrent modification problem
         *  - memory consistency errors
         *
         * These are some of the problems that can occur, when you use a class that's not safe, in a multi-threaded operation
         *  - Using with a parallel streams, like we are doing here , really demonstrates the problems
         *
         * Changing TreeMap to a sorted ConcurrentSkipListMap
         *
         * Running this:
         *  - prints sorted by last name
         * `- did not throw an exception
         *  - prints the correct total of 10000 counts in my map
         *
         * Using ConcurrentSkipListMap is one thread safe option
         *
         */

        var lastCounts = new ConcurrentSkipListMap<String,Long>();
        Stream.generate(Person::new)
                .limit(10000)
                .parallel()
                .forEach(person -> lastCounts.merge(person.lastName(), 1L,Long::sum));
        System.out.println(lastCounts);

        total = 0;
        for (var count : lastCounts.values())
            total += count;
        System.out.println("Total = "+total);
        System.out.println(lastCounts.getClass().getName());

        System.out.println("_".repeat(50));

        /*
         * Another way to fix this is to wrap instantiation of the TreeMap in a call to a synchronized wrapper collection
         * Call synchronizedMap() on the Collections Class
         *
         * Running this:
         *  - prints last names in a sorted order
         *  - prints 10000 records
         *  - no exception thrown
         *  - consistent results after several runs
         *
         * The type printed in this output too, is a special type on Collections Class called synchronizedMap()
         *
         * TreeMap (sorted) & HashMap (not sorted)
         * .......................................
         *  - Neither of these classes is thread-safe and shd be avoided when you're working with parallel processes
         *      if multiple threads are accessing them asynchronously
         *
         * ConcurrentHashMap(not sorted) & ConcurrentSkipListMap(sorted)
         *  - These are 2 concurrent classes in the java.util.concurrent package
         *  - Both are thread safe
         *
         * Concurrent$SynchronizedMap
         * There's also a synchronized class we can get by using a static () on the Collections Class
         * Only Concurrent$SynchronizedMap is considered a blocking type, of the 3 thread-safe types
         * This means other threads will block, waiting for the access to the map
         *
         * That's one of the differences between synchronized classes and concurrent classes
         *
         * Concurrent Classes vs Synchronized Wrapper Classes
         * ..................................................
         * Both concurrent and synchronized collections are thread-safe, and can be used in parallel streams, or in a
         *  multi-threaded application
         *
         * Synchronized collections
         *  - Implemented using locks which protect the collection from concurrent access
         *      - a single lock is used to synchronize access to the entire map
         *
         *  Concurrent collections
         *  - Are more efficient than synchronized collections
         *      - They use fine-grained techniques or non-blocking algorithms to enable safe concurrent access
         *          without the need for heavy-handed locking meaning synchronized or single access locks
         *
         * Concurrent collections are recommended over Synchronized collections in most cases
         */

        var lastCounts2 = Collections.synchronizedMap(new TreeMap<String,Long>());
        Stream.generate(Person::new)
                .limit(10000)
                .parallel()
                .forEach(person -> lastCounts2.merge(person.lastName(), 1L,Long::sum));
        System.out.println(lastCounts2);

        total = 0;
        for (var count : lastCounts2.values())
            total += count;
        System.out.println("Total = "+total);
        System.out.println(lastCounts2.getClass().getName());

        System.out.println("_".repeat(50));

        /*
         * Examples with other Collections Types
         * For Arrays and ArrayList, you can use terminal operations, toArray() or toList() with parallel streams
         *
         * Create MainLists Class
         *  -> Go to this class to check comments
         */






    }
}



















