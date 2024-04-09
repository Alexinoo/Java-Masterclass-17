package concurrency.part23_parallel_streams_order_collect_reduce;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
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
     *  - change forEach to forEachOrdered
     *
     * Running this:-
     *  - still isn't sorted
     *
     * So what does forEachOrdered really do ?
     *  - is easier to see if we save off some persons in a sorted array then use sorted array in a stream
     *
     * Will do this before Stream.generate()
     *  - use toArray() instead
     *  - loop via enhanced loop and print each person
     *  - include a separation line
     *
     * Change the second array to use persons as the source
     *
     * Running this :
     *  - prints an array that is sorted by last name
     *  - And the stream that is sourced by this array, is also printed in the same order
     *
     * Change forEachOrdered to forEach
     *
     * Running again :-
     *  - Now the second set of names is not ordered
     *
     * So forEachOrdered operation guarantees the order to be the same as the stream source's order
     * It doesn't guarantee that the stream will be sorted
     *
     * Using forEachOrdered () on a parallel stream is going to increase the overhead, of the parallel processing
     * To figure out how much overhead is incurred, you'd need to do metrics on your pipeline, using diff data set sizes
     *
     * Simple reduction on a Parallel stream
     * .....................................
     *
     * Get a stream of integers from the IntStream Class
     *  - use the range() to get values between 1 and 100 - upper bound exclusive
     *  - make it a parallel stream
     *  - use the reduce terminal operator that takes 2 args
     *      - identity - which both creates and initializes the value
     *      - accumulator which adds valued
     *  - print the sum
     *
     * Running this:
     *  - prints the correct sum of the nos, using parallel processing, with a reduction
     *
     * Let's look at another reduction though , using strings
     *  - Set up a text block with a familiar nursery rhyme
     *  - use scanner with text block as input and get tokens stream, to get a list of words.
     *  - print the words out
     *
     * Running this :
     *  - see each other , or token from the scanner in this case, printed on each line
     *
     * Then use a stream to put humpty dumpty back together again
     *  - use reduce() that takes 3 args
     *      - identity - use StringJoiner() with a space as a delimiter
     *      - accumulator - use String::add as the acc
     *      - combined - use String::merge
     *  - print the combined results again
     *
     * Running this:
     *  - works using a serial stream
     *
     * We can change this to a parallelStream instead of a stream
     * This ios the second way to get a stream
     * Like the stream() on most collection classes, most also have a parallelStream()
     *
     * Running this :-
     *  - throws a nullPointerException
     *
     * It turns out that StringJoiner isn't thread safe and these errors are caused by interleaving threads
     *
     * Let's replace this code - reduce () with a special type of Collector operation instead
     * In my case , will copy and paste and comment out on the reduce()
     *
     * Use .collect() and pass Collectors.joining passing an empty space
     *  - This () uses a StringJoiner but in a thread safe way
     *
     * Running this:
     *  - prints out the nursery rhyme correctly
     *
     * Instead of Collectors.joining() that uses StringJoiner, will try a different reduction operation using Strings and String concatenation
     *  - use the 2 arg reduce()
     *      - identity (pass a new empty string)
     *      - accumulator (pass a lambda that concatenates one string to another and concatenates a space after that)
     *
     * Running this:
     *  - prints words with different numbers of spaces almost randomly throughout
     *
     * When you run a reduction in parallel, each thread will use the identity to create a new instance
     * It will then start processing some part of the data, using the accumulator to combine it's queue of data
     * It will then use the accumulator to join its reduction to other threads results
     *
     * Let's look at another example with a collection, this time getting a mpa back
     *  - set up a Map
     *      - String as key
     *      - Long as the value
     *          - returns a count of persons with the same last name
     *      - Generate a new set of 10,000 persons
     *      - process them in a parallel fashion
     *      - use collect()
     *          - pass Collectors.groupingBy - group by last name
     *          - pass Collectors.counting() - get the counts
     *
     *  - print this map, each key value pair
     *
     * Running this:
     *  - Each last name is printed with the counts of each that were on this stream
     *
     *
     * Add another code to add these counts up
     *  - Declare and initialize "total" to 0
     *  - loop through the values in the mapped results
     *  - add each value to the total
     *  - print the total
     *
     * Running this:
     *  - confirms that the counts equals 10000
     *
     * Although, you can use groupingBy with parallel streams, Java states in its API for this (), that the merge(), of combining multiple threads
     *  maps, is very costly and that using groupingByConcurrent is much more efficient
     * The Collectors.groupingByConcurrent() will return a concurrent implementation of a map
     */

    public static void main(String[] args) {

        var persons = Stream.generate(Person::new)
                        .limit(10)
                        .sorted(Comparator.comparing(Person::lastName))
                        .toArray();
        for(var person : persons){
            System.out.println(person);
        }

        System.out.println("_".repeat(50));

        Arrays.stream(persons)
                .limit(10)
                .parallel()
               // .sorted(Comparator.comparing(Person::lastName))
                .forEach(System.out::println);

        System.out.println("_".repeat(50));

        int sum = IntStream.range(1,101)
                .parallel()
                .reduce(0, Integer::sum);

        System.out.println("The sum of the numbers is : "+sum);

        System.out.println("_".repeat(50));

        String humptyDumpty = """
                Humpty Dumpty sat on a wall,
                Humpty Dumpty had a great fall,
                All the king’s horses and all the king’s men,
                Couldn’t put Humpty together again.
                """;

        var words = new Scanner(humptyDumpty).tokens().toList();
        words.forEach(System.out::println);

//      var backTogetherAgain = words
//                .parallelStream()
//                .reduce(new StringJoiner(" "),
//                        StringJoiner::add,
//                        StringJoiner::merge);

//      var backTogetherAgain = words
//                .parallelStream()
//                .collect(Collectors.joining(" "));

        var backTogetherAgain = words
                .parallelStream()
                .reduce("",(s1,s2)->s1.concat(s2).concat(" "));

        System.out.println(backTogetherAgain);

        Map<String,Long> lastNameCounts =
              Stream.generate(Person::new)
                      .limit(10000)
                      .collect(Collectors.groupingBy(Person::lastName, Collectors.counting() ));

        lastNameCounts.entrySet().forEach(System.out::println);

        long total = 0;
        for(var count : lastNameCounts.values())
            total += count;
        System.out.println("Total = "+total);

    }
}
