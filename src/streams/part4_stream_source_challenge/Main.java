package streams.part4_stream_source_challenge;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Stream;

public class Main {

    private static int counter = 0;

    public static void main(String[] args) {

        // Stream B
        int seed = 1;
        var streamB = Stream.iterate(seed, i -> i <= 15, i -> i +1)
                .map(i -> "B" + i);


        // Stream I
        seed += 15;

        var streamI = Stream.iterate(seed, i -> i +1)
                .limit(15)
                .map(i -> "I" + i);

        // Stream N
        seed += 15;
        String[] oLabels = new String[15];
        int nSeed = seed;
        Arrays.setAll(oLabels, i -> "N" + (nSeed + i));
        var streamN = Arrays.stream(oLabels);

        //Stream G
        seed += 15;
        var streamG = Stream.of("G46","G47","G48","G49","G50","G51",
                "G52","G53","G54","G55","G56","G57","G58","G59","G60");


        // Stream O
        seed += 15;
        int rSeed = seed;
        var streamO =  Stream.generate(Main::getCounter)
                .limit(15)
                .map( i -> "O" + (rSeed + i) );


        // Concatenate all the streams and print all the labels from the concatenated stream
        var streamBI = Stream.concat(streamB,streamI);
        var streamNG = Stream.concat(streamN,streamG);
        var streamBING = Stream.concat(streamBI,streamNG);

        Stream.concat(streamBING,streamO).forEach(System.out::println);


        System.out.println("___________________________________________");

        //Better and more efficient operation than generate above of using external method
        // use intermediate method called distinct
        // distinct() - Returns a stream consisting of the distinct elements (according to Object.equals(Object)) of this stream.
        // generate() - Returns an infinite sequential unordered stream where each element is generated by the provided Supplier.
        // use limit to 15 numbers in my resulting stream
        Stream.generate(()-> new Random().nextInt(rSeed , rSeed + 15))
                .distinct()
                .limit(15)
                .map(i -> "O" + i)
                .sorted()
                .forEach(System.out::println);

    }

    private static int getCounter() {
        return counter++;
    }
}
