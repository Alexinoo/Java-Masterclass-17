package streams.part5_intermediate_operations;

import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {

        /* Print characters between A - Z using corresponding integer values by incrementing + 1

        * Filter unwanted symbols - Character wrapper class provides a method that takes an integer
         and returns a boolean - good opportunity to use that method reference

        * Filter out A through E - can be done with an additional filter that takes an integer and compare that to the character,
          a literal E, if value > E , it will be part of the output

        * No limit to the no. of intermediate operations you specify

        * skip() - Can also use skip - which skips the first 5 generated elements

        * dropWhile(Predicate<? super T> predicate)
            - takes a predicate and filters out any element while they match the condition
            - Unlike filter - this predicate is only evaluated for elements, until it first becomes false
                dropWhile(i -> i <= 'E') - drop characters that are <= to 'E'
                - continues to exclude elements until this predicate becomes false
               dropWhile(i -> Character.toUppercase(i) <= 'E') - works as above

                - That's where the word while name becomes important, this happens until the predicate becomes false
                  the first time and then that condition is no longer checked

                - Think of it as a mini-while loop , drops data until the condition becomes false and then it moves
                  past the loop altogether

                - Meaning , it's job in the pipeline is complete and won't be revisited



        * takeWhile(Predicate<? super T> predicate) - takes a predicate and filters out any element that matches the condition

        * Both dropWhile and takeWhile works with ordered streams - Otherwise, if the streams are not ordered, the
          result will be non-deterministic(may differ upon subsequent executions)

        * distinct - Returns a stream consisting of the distinct elements (according to Object.equals(Object)) of this stream.

        *
          */


        IntStream.iterate( (int)'A',i -> i <= (int)'z',i -> i + 1)
                .filter(Character::isAlphabetic)
                .map(Character::toUpperCase)
                .distinct()
               // .dropWhile(i -> i <= 'E')
               // .takeWhile(i -> i < 'a')
               // .skip(5)
               // .filter(i -> Character.toUpperCase(i) > 'E')
                .forEach(d -> System.out.printf("%c ",d));

        System.out.println("____________________________________________");

        // Create a Stream source -
        // Generate random numbers between char A to Z
        // Limit the characters
        // Filter distinct
        Random random = new Random();
        Stream.generate(()->random.nextInt((int)'A', (int)'Z' + 1))
                .limit(50)
                .distinct()
                .sorted()
                .forEach(d -> System.out.printf("%c ",d));
    }
}
