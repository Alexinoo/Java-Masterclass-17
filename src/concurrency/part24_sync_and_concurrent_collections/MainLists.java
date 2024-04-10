package concurrency.part24_sync_and_concurrent_collections;

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Stream;

public class MainLists {

    /* Synchronized and Concurrent Collections with Arrays
     *......................................................
     * Generate random persons
     * limit to 10000
     * run it as a parallel stream
     * call toArray() - pass a () reference (a constructor for a new Person[]
     *  - If we don't pass Person[]::new to toArray() we'll get Object[] rather than a typed Person[]
     *  - Passing Person[]::new gives us Person[]
     * Print the total no of the elements in the array out
     *
     * Running this:-
     *  - prints 10000 records in the array and the inferred type is a Person[]
     *
     */
    public static void main(String[] args) {

        var persons = Stream.generate(Person::new)
                .limit(10000)
                .parallel()
                .toArray(Person[]::new);
        System.out.println(persons.length);

        /*
         * There are instances where you may include the parallel operation, but the Stream pipeline manager may not actually
         *  execute it in parallel
         * Let's see if this is the case with this instance
         *
         * Set up a concurrent map variable that collects thread names, and count total processes each thread executes
         *  - Use ConcurrentSkipListMap - sorted by the natural order of the keys - alphabetically by thread name
         *      - Keyed by thread name
         *      - values of type Long
         *  - Use peek() intermediate operation, inserting after parallel() but before toArray()
         *      - We can pass a lambda expression since each element in the stream is a person record
         *          - get the current thread name
         *          - strip out part of the name if it contains info about the common pool worker, and replace it
         *              with a shorter thread name
         *          - then merge the threadName into the map, with 1 as initial value, or summing the values for each processed
         *              thread
         *  - Might not recommend us to use the peek() this way
         *      - peek() is used for some sort of debugging code, and here the debugging code is creating side effects which
         *         is intentional just to demo which threads are doing the work
         *  - Next print the data out
         *      - print the entire map
         *
         *  - Add up the values in each entry
         *      - total : long
         *      - loop through and add cumulative totals
         *  - Print the totals
         *
         * Running this:
         *  - We can see how the work was distributed across diff threads, and we can confirm that this is really running
         *     in parallel and that it accurately collected 10000 values on the stream , into a Person[]
         */
        System.out.println("_".repeat(50));
        var threadMap = new ConcurrentSkipListMap<String,Long>();
        var persons2 = Stream.generate(Person::new)
                .limit(10000)
                .parallel()
                .peek( p -> {
                    var threadName = Thread.currentThread().getName()
                            .replace("ForkJoinPool.commonPool-worker-","thread_");
                        threadMap.merge(threadName , 1L, Long::sum);
                })
                .toArray(Person[]::new);
        System.out.println(persons2.length);
        System.out.println(threadMap);
        long total = 0;
        for (var value: threadMap.values()) {
            total += value;
        }
        System.out.println("ThreadCounts = "+ total);
    }
}
