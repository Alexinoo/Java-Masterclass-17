package concurrency.part29_concurrency_extras;



    /*
     * java.util.concurrency.atomic
     * ............................
     * Java API documentation defines it as a small toolkit of classes that support lock-free, thread-safe programming on
     *  single variables
     *
     * Why is lock-free so important ?
     * ...............................
     * These classes can significantly improve the performance of concurrent applications, especially in high throughput
     *  systems
     *
     * Example:
     * .......
     *
     * Create a Student Record that implements Comparable (we want the list to be ordered)
     *  Fields
     *      name : String
     *      enrolledYear : int
     *      studentId : int
     *
     *  methods
     *      compareTo() - override
     *          - return the diff between the student ids
     *
     * Create a StudentId Class - keeps track of the most recent student id
     *  Fields
     *      id : int
     *  Getters
     *      getId() : int
     *          - returns id
     *  Methods
     *      getNextId() : int
     *          - increments id with a pre increment operator
     *          - and return it;
     *          - this operation is not atomic
     *
     */

import java.util.Collections;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

record Student(String name , int enrolledYear,int studentId) implements Comparable<Student>{
        @Override
        public int compareTo(Student o) {
            return this.studentId - o.studentId;
        }
    }

    class StudentId {
        private int id = 0;

        public int getId(){
            return id;
        }

        public synchronized int getNextId(){
            return ++id;
        }

    }

    class AtomicStudentId {
        private final AtomicInteger nextId = new AtomicInteger(0);

        public int getId(){
            return nextId.get();
        }

        public int getNextId(){
            return nextId.incrementAndGet();
        }
    }

public class Main {
        /*
         * Create a Random variable to generate random year for the enrollment
         * Set up the Student set (ConcurrentSkipListSet)
         *  - Need this to be a concurrent collection and sorted
         *
         * main()
         *  - Create an instance of the StudentId Class
         *  - Create a Callable that return a generated student
         *      - local variable - studentId - initialized to getNextId()
         *      - create a new instance of a Student Record
         *      - Add student to the studentSet
         *      - Return the student
         *  - Create an ExecutorService to manage the Callable variable
         *      - Use a CachedThreadPool
         *  - Create a try{}
         *      - create a variable and assign the result of calling invokeAll() on the executor
         *          - use Collections.nCopies() to quickly pass 10 copies of Callable variable
         *          - invokeAll() will return when all the tasks have completed
         *      - At that point, we will have all the students in the studentSet and will just print them out
         *  - Handle InterruptedException from invokeAll()
         *      - throw run time exception
         *  - SHut down executor
         *
         * Running this :
         *  - We kicked off 10 threads, but if we run this several times, looks like it's alternating between
         *      7 - 10 students
         *  - We seem to have a glitch
         *
         * Can you guess why this might be :-
         *  - The increment() is not atomic, so if 2 threads try to run it asynchronously, there's interference
         *     happening and maybe memory inconsistency
         *  - Because the collection we're using is a set, only unique student ids are added
         *  - The thread generated 10 students but 3 students might get the same id
         *
         * To see this a little easier, wrap the code that is invoking all these tasks, in a for loop
         *  - loop for 10 iterations
         *  - clear the student set each time
         *  - print the no of student in the student set
         *  - comment out the code that is printing each student
         *
         * Re-running this:
         *  - We get diff results each time we run this
         *      - we see a couple of iterations where the set has 7,8 or 9 students
         *
         * We can fix this by synchronizing the getNextId() on the StudentId Class
         *  - Add synchronized keyword on getNextId()
         *
         * Re-running this:
         *  - This time my set in all 10 cases, has 10 students
         *  - true even when we run it a couple of times
         *
         * This means synchronizing getNextId() fixed the threading problems
         *
         * Another option is using a class called AtomicInteger instead of just a primitive integer for student id
         *
         * Create AtomicStudentId Class after StudentId Class
         *  Fields
         *      nextId : AtomicInteger - instantiate with an initial value 0
         *      - we can make it final since it is an instance
         *  Methods
         *      getId() : int
         *      - instead of returning nextId , we want to return an int and we can use the get() on the AtomicInteger
         *
         *      getNextId() : int
         *      - call incrementAndGet() on nextId
         *      - incrementAndGet() is guaranteed to be atomic, meaning thread-safe
         *
         * Back to main()
         *  - Replace StudentId with AtomicStudentId class (comment out instead)
         *
         * Running with these changes:
         *  - We get the no of students is always 10
         *  - Running it a couple of times gives us the same results
         *
         * The AtomicInteger since it's lock free, will probably be better performant, in a high throughput environment
         *  than synchronizing on ()s that increment integers
         *
         * Atomic Classes
         * ...............
         * The java.util.concurrent.atomic package has several atomic classes , including atomic arrays as shown on the slide
         * In each of these classes, an instance of one of these classes can be updated atomically
         *      Single                  Array Of Elements
         *  - AtomicBoolean             n/a
         *  - AtomicInteger             AtomicIntegerArray
         *  - AtomicLong                AtomicLongArray
         *  - AtomicReference           AtomicReferenceArray
         *
         * Other classes that exist that you might run into that are related to concurrency
         * The Timer Class has been around since java 1.3 and preceded the java.util.concurrent package
         * This is a single threaded task scheduler
         *
         * Create TimerExample Class on this package
         */
    private static Random random = new Random();
    private static ConcurrentSkipListSet<Student> studentSet = new ConcurrentSkipListSet<>();
    public static void main(String[] args) {

        //StudentId idGenerator = new StudentId();
        AtomicStudentId idGenerator = new AtomicStudentId();

        Callable<Student> studentMaker = ()->{
          int studentId = idGenerator.getNextId();
          Student student = new Student("Tim "+studentId, random.nextInt(2018, 2025), studentId );
          studentSet.add(student);
          return student;
        };

        ExecutorService executor = Executors.newCachedThreadPool();

        for (int i = 0; i < 10; i++) {
            studentSet.clear();
            try {
                var futures = executor.invokeAll(Collections.nCopies(10,studentMaker));
                //studentSet.forEach(System.out::println);
                System.out.println("# of students = "+ studentSet.size());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }


        executor.shutdown();

    }
}
