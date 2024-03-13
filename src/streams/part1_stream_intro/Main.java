package streams.part1_stream_intro;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        //
        //
        //
        //
        // Using Collection
        List<String> bingoPool = new ArrayList<>(75);

        int start = 1;
        for (char c: "BINGO".toCharArray()) {
            for (int i = start; i < (start + 15); i++) {
                bingoPool.add("" + c + i );
            }
            start += 15;
        }

        //shuffle and print the first 15
        Collections.shuffle(bingoPool);
        System.out.println(bingoPool);
        for (int i = 0; i < 15; i++) {
            System.out.print(bingoPool.get(i) +" ");
        }

        System.out.println("\n______________________________________");

        //Assign the first 15 to another variable
        //Then sort in natural order
        //List<String> firstFifteen = bingoPool.subList(0,15); // returns a view - use it only when you want to alter the original list
        List<String> firstFifteen = new ArrayList<>(bingoPool.subList(0,15)); // make a modifiable copy of the sublist
        firstFifteen.sort(Comparator.naturalOrder());
        System.out.println(firstFifteen);


        // replaceAll() and replace first character to G- or O- if it
        System.out.println("\n______________________________________");

        firstFifteen.replaceAll(s -> {
            if(s.indexOf('G') == 0 || s.indexOf('O') == 0){
                String updated = s.charAt(0) + "-" + s.substring(1);
                System.out.print(updated + " ");
                return updated;
            }
            return s;
        });

        System.out.println("\n______________________________________");


        //Print again first 15
        for (int i = 0; i < 15; i++) {
            System.out.print(bingoPool.get(i) +" ");
        }

        System.out.println("\n___________ Using Streams ___________________");

        /*
         * Using Streams

         * By chaining stream() to our bingoPoll Arraylist this means we have access to the following stream methods

            1. limit(long maxSize) - Returns a stream consisting of the elements of this stream,
                                  truncated to be no longer than maxSize in length.

            2. filter( Predicate<? super T> predicate ) - Returns a stream consisting of the elements of this
                                                          stream that match the given predicate.

            3. map(Function<? super T,? extends R> mapper) - Returns a stream consisting of the results of applying
                                                          the given function to the elements of this stream.

            4. sorted() - Returns a stream consisting of the elements of this stream, sorted according to natural order.

            5. forEach(Consumer<? super T> action) - Performs an action for each element of this stream.

         * */
        bingoPool.stream()
                .limit(15)
                .filter(s -> s.indexOf('G')== 0 || s.indexOf('O') == 0)
                .map(s-> s.charAt(0) + "-" + s.substring(1))
                .sorted()
                .forEach(s -> System.out.print(s + " "));

    }
}
