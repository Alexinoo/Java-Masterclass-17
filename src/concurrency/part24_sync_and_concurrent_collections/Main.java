package concurrency.part24_sync_and_concurrent_collections;

import java.util.Map;
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
         * Changing TreeMap to ConcurrentSkipListMap
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







    }
}



















