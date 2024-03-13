package streams.part2_stream_pipeline;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
    Stream Pipeline
    ...............

    - source - bingoPool.stream() - converted to strea,

    - immediate operation - filter() , map()

    - Terminal operation - forEach()
 */

public class Main {

    public static void main(String[] args) {

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

        System.out.println("______________________________________");

        //Use variable in streams
       var tempStream = bingoPool.stream()
                .limit(15)
                .filter(s -> s.indexOf('G')== 0 || s.indexOf('O') == 0)
                .map(s-> s.charAt(0) + "-" + s.substring(1))
                .sorted();
                //.forEach(s -> System.out.print(s + " "));

        tempStream.forEach(s -> System.out.print(s + " "));


        // Not allowed - pipeline is already closed
        tempStream.forEach(s -> System.out.print(s.toLowerCase() + " "));

    }
}
