package lambda_expressions.part5_minichallenges.mini_challenge1;

import java.util.Arrays;
import java.util.function.Consumer;

public class Main {

    public static void main(String[] args) {
        Consumer<String> printWords = new Consumer<String>() {
            @Override
            public void accept(String sentence) {
                String[] parts = sentence.split(" ");
                for (String part: parts) {
                    System.out.println(part);
                }
            }
        };

        ////Rewrite printWords() -above as Lambda expression - changed the name
        Consumer<String> printWordsLambda = sentence -> {
            String[] parts = sentence.split(" ");
            for (String part: parts) {
                System.out.println(part);
            }
        };

        ////Rewrite (printWordsLambda)-above using forEach
        Consumer<String> printWordsForEach = sentence -> {
            String[] parts = sentence.split(" ");
            Arrays.asList(parts).forEach(element -> System.out.println(element));
        };


        ////Rewrite (printWordsForEach)-above in a more concise manner
        Consumer<String> printWordsConcise = sentence -> {
            Arrays.asList(sentence.split(" ")).forEach(element -> System.out.println(element));
        };

        //Calling the methods ( Variations )
        printWords.accept("Let's split this up into an array");
        System.out.println("/".repeat(30));

        printWordsLambda.accept("Let's split this up into an array");
        System.out.println("/".repeat(30));

        printWordsForEach.accept("Let's split this up into an array");
        System.out.println("/".repeat(30));

        printWordsConcise.accept("Let's split this up into an array");

    }
}
