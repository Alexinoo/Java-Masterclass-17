package concurrency.part25_concurrent_collections_lists_queue;

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
     *
     */

    public static void main(String[] args) {

    }
}
