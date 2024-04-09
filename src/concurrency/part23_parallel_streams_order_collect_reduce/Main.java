package concurrency.part23_parallel_streams_order_collect_reduce;

import java.util.Comparator;
import java.util.stream.Stream;

public class Main {

    /*
     * Parallel Streams , Ordering , Reducing and Collecting
     * .....................................................
     *
     * Its not always a straightforward decision , whether to use parallel operations or not
     * Nothing is free in life generally and that's true of software too
     * Even though it seems easy and like a good idea to always use parallel streams when you can, there are some very
     *  good reasons not to do so
     *
     * Create a Record type - Person
     *  firstName : String
     *  lastName : String
     *  age : int
     * Randomly assign each person's age and their age
     * For the First and Last names, create a short [] of six values to pick from
     *  - firsts : final static String[] {"","","","","",""}
     *  - lasts : final static String[] {"","","","","",""}
     *  - random : new Random()
     * Constructor
     *  - no args - all the fields will be generated
     *  - get the first name randomly from the firsts[]
     *  - get the last name randomly from the firsts[]
     *  - age a random in between 18 and 99
     * toString()
     *  - override
     *  - return a formatted string with lastName, first name and then age
     *
     *
     * main()
     *  Generate a list of 10 persons
     *   - Use Stream.generate() - pass the () reference for a new Person
     *   - limit to 10
     *   - sort by the last name, pass the Comparator derived using Person's last name as a method ref
     *   - print using forEach
     *
     * Running this:
     *  - Prints 10 persons with randomly selected first and last names
     *  - duplicates though names printed in order by last name
     *
     * Let's make this stream parallel
     *  - add a parallel() after the limit()
     *      - notice that intelliJ has grayed out the sorted()
     *          - intelliJ hint - Redundant sorted()
     *
     * Running this :
     *  - Regardless of whether this was faster or not, in parallel, we've got a problem
     *  - list not sorted
     *
     * If you want things to be ordered by parallel processes, ypu might have to do things a little differently at the expense
     *  of some benefits you might get from parallelism
     * Comment out on the sorted operation
     */

    public static void main(String[] args) {

        Stream.generate(Person::new)
                .limit(10)
                .parallel()
                .sorted(Comparator.comparing(Person::lastName))
                .forEach(System.out::println);

    }
}
