package regular_expressions.part3_regex_methods;

import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        /*
         * Scanner class also makes significant use of regular expressions
         * Let's check some of it's methods as well
         * Add a text block , the first 10 lines of MacBeth's Song of the WItches by Shakespeare
         */

        String paragraph = """
                Double, double toil and trouble;
                Fire burn and caldron bubble.
                Fillet of a fenny snake,
                In the caldron boil and bake,
                Eye of newt and toe of frog,
                Wool of bat and tongue of dog,
                Adder's fork and blind-worm's sting,
                Lizard's leg and howlet's wing,
                For a charm of powerful trouble,
                Like a hell-broth boil and bubble.
                """;
        /*
         * Use split() to count the number of lines in this text
         * Pass a string literal , backslash n literal without any metacharacters in it
         */
        String[] lines = paragraph.split("\n");
        System.out.println("This paragraph has "+lines.length +" lines");

        /*
         * Use \\R - one of the predefined character classes
         * Called line break matcher and matches any Unicode line break sequence
         * We get the same result as above
         */
        lines = paragraph.split("\\R");
        System.out.println("This paragraph has "+lines.length +" lines");

        /*
         * Use the character class \s to identify white space - one of the predefined character classes
         * Matches any and all white space including new line character combinations
         * count of words in this text block - including white spaces
         */
        String[] words = paragraph.split("\\s");
        System.out.println("This paragraph has "+ words.length + " words");

        /*
         * Use replaceAll
         * Replace any word that ends in ble with GRUB in capital letters
         * Use "[a-zA-Z]+ble" - use alphabetical ranges from a-z for both upper/lower case
         * Follow that with + quantifier which means it has 1 or more characters & shd be followed by ble
         * Replace all matches with [GRUB]
         */
        System.out.println("_".repeat(50));
        System.out.println(paragraph.replaceAll("[a-zA-Z]+ble","[GRUB]"));

        /*
         * Let's consider which methods can be used on the Scanner class with a regex
         * Create a scanner using the text block by passing the text-block to the scanner constructor
         *
         * Prints what comes from the delimiter() - static method on the scanner class
         * Prints \p{javaWhitespace}+
         * delimiter() returns an instance of the Pattern class
         * Scanner is going to use java whitespaces as the delimiter for it's next method -
         * we can override this in several ways
         * Let's see what we get with this default delimiter and use the scanner to print each delimited element
         */
        System.out.println("_".repeat(50));
        Scanner scanner = new Scanner(paragraph);
        System.out.println(scanner.delimiter());

        /*
         * Set up a while loop that checks the hasNext() to decide if there's another delimited element
         * Set up a local variable, element, and assign the result of calling scanner.next()
         * Print each element
         *
         * Every word on this paragraph gets printed on a new line
         */
        System.out.println("_".repeat(50));
        while (scanner.hasNext()){
            String element = scanner.next();
            System.out.println(element);
        }

        /*
         * If we wanted to print each line we can use hasNextLine() and scanner.nextLine()
         *
         * Works but need to comment above while to see results
         *
        */

        System.out.println("_".repeat(50));
        while (scanner.hasNextLine()){
            String element = scanner.nextLine();
            System.out.println(element);
        }

        /*
         * Scanner class has useDelimiter() that we can use to specify the regex
         * Use \\R - character class for unicode newline
         */
        scanner.useDelimiter("\\R");

        System.out.println("_".repeat(50));
        while (scanner.hasNext()){
            String element = scanner.next();
            System.out.println(element);
        }

        /*
         * JDK introduced the tokens method on scanner which returns a Stream of strings
         * Need to comment while loop above to see below output
         * We get the same output as above
         */
        System.out.println("tokens()");
        scanner.tokens()
                .forEach(System.out::println);

        /*
         *
         * Use a similar stream to count the words in each sentence
         * Use map to create a stream from the array we get from the method split, using the regex
         * that splits by any number of space characters
         * The +character is a quantifier - meaning match at least one space or many
         * Finally, have the stream return a count
         * !!!! Need to comment while loop above to see below output !!!!
         */
        System.out.println("count words in each line");
        scanner.tokens()
                .map(s -> Arrays.stream(s.split("\\s+")).count())
                .forEach(System.out::println);

        /*
         *
         * !!!! Need to comment while loop above to see below output !!!!
         * We only get 1 word printed out - double
         * This is because , the paragraph is punctuated and the words includes commas and periods
         *
         */
        scanner.tokens()
                .flatMap(s -> Arrays.stream(s.split("\\s+")))
                .filter(s -> s.matches("[a-zA-Z]+ble"))
                .forEach(System.out::println);

        /*
         * Strip out punctuation before using flatMap()
         * We are using \p and abbbrev for punctuation,Punct
         * This is one way to strip punctuation
         * !!!! Need to comment while loop above to see below output !!!!
         *
         */
        scanner.tokens()
                .map(s -> s.replaceAll("\\p{Punct}",""))
                .flatMap(s -> Arrays.stream(s.split("\\s+")))
                .filter(s -> s.matches("[a-zA-Z]+ble"))
                .forEach(System.out::println);

        /*
         * findInLine() returns the first line of a paragraph or a text that is being scanned
         * For example, reading from a file - some files have header row with info about the rest of the text
         * Running this prints Double
         * Finds the first matching element and returns it
         *
         * Running it twice prints double
         * Running it thrice prints trouble
         *
         * Returns null if it reaches end of the line
         *
         * Need to Comment above !!!!!!1
         *
         */
        System.out.println(scanner.findInLine("[a-zA-Z]+ble"));
        System.out.println(scanner.findInLine("[a-zA-Z]+ble"));
        System.out.println(scanner.findInLine("[a-zA-Z]+ble"));
        System.out.println(scanner.findInLine("[a-zA-Z]+ble"));


        /*
         * Also call close on this scanner
         */
        scanner.close();

    }
}
