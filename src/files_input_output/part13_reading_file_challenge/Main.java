package files_input_output.part13_reading_file_challenge;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {

    /*
     * Challenge - Reading from a Text file
     *
     * Pick some text of your choice (from a doc, online article, some wiki page)
     * Read the text doc with one of the ()s we have covered
     * Pick whichever () you want, but your program shd do the following
     *
     *  - Tokenize the text into words, remove any punctuation
     *  - Ignore words with 5 char(s) or less
     *  - Count the occurrences of each word
     *  - Display the top 10 most used words
     *
     * The point of this exercise is to see if you can pick out what the articles
     *  might be about by simply getting the most used words
     *
     *
     * Bonus
     * After you use 1 (), try a 2nd ()
     * If you used a () that used a stream, try some code without a streaming () or
     *  vice versa
     */

    public static void main(String[] args) {

        /*
         * Use BufferedReader with try-with-resources
         *  - Pass FileReader instance and provide a String literal of the text file as an arg to its constructor
         * Catch IOException adn print stack trace
         *
         * Will use the Files.lines() that returns Stream<String>
         * Count of lines in the file - call lines().count() on BufferedReader instance
         * Running this prints :-
         *   "533 lines in file"
         * Commenting out souf
         * Next - Count the number of words -
         *  we have 2 options below
         *    1. Use a pattern with a string that the scanner class uses to split strings into words
         *      - Print the number of words
         *      - start with the source buffered reader br.lines()
         *      - call flatMap() and call a () on the pattern splitAsStream() - pass it as () reference
         *          - splitAsStream() - splits the String and return a Stream<String>
                    - flatMap() is needed here to flatten the hierarchy b4 using the terminalOp, count
                    - Finally, Call count() terminalOP
         *      - Running this prints:
         *          "13,801 words in file"
         *
         */

        System.out.println("_".repeat(50));
        try (BufferedReader br = new BufferedReader(new FileReader("article.txt"))) {
            //System.out.printf("%,d lines in file%n",br.lines().count()); -  "533 lines in file"
            //System.out.printf("%,d lines in file%n",br.lines().count()); -  "0 lines in file" - file pointer is still pointing to eof after term op
            Pattern pattern = Pattern.compile("\\p{javaWhitespace}");
            System.out.printf("%,d words in file%n",
                    br.lines()
                            .flatMap(pattern::splitAsStream)
                            .count());
        }  catch (IOException e) {
            e.printStackTrace();
        }

        /*
         *    2. Split the string
         *      - create a stream here because flatMap() works on a resulting stream
         *          - call stream() on Arrays passing it the String[] we get back by using pattern on each line
         *      - Running this prints :-
         *          "13,801 words in file"
         */

        System.out.println("_".repeat(50));
        try (BufferedReader br = new BufferedReader(new FileReader("article.txt"))) {
            //System.out.printf("%,d lines in file%n",br.lines().count()); -  "533 lines in file"
            //System.out.printf("%,d lines in file%n",br.lines().count()); -  "0 lines in file" - file pointer is still pointing to eof after term op
            Pattern pattern = Pattern.compile("\\p{javaWhitespace}");
            System.out.printf("%,d words in file%n",
                    br.lines()
                            .flatMap(l -> Arrays.stream(l.split(pattern.toString())))
                            .count());
        }  catch (IOException e) {
            e.printStackTrace();
        }

        /*
         *    3. Use mapToLong()
         *        - Pass a lambda expression that will still split the string using the pattern, but this time now
         *           return the length of that resulting []
         *        - Instead of count, use sum terminalOP
         *        - Running this prints :-
         *          "13,801 words in file"
         *        - More efficient, because there is no overhead with the streams & it's processing a smaller no of long values
         *           vs strings
         *
         */

        System.out.println("_".repeat(50));
        try (BufferedReader br = new BufferedReader(new FileReader("article.txt"))) {
            //System.out.printf("%,d lines in file%n",br.lines().count()); -  "533 lines in file"
            //System.out.printf("%,d lines in file%n",br.lines().count()); -  "0 lines in file" - file pointer is still pointing to eof after term op
            Pattern pattern = Pattern.compile("\\p{javaWhitespace}");
            System.out.printf("%,d words in file%n",
                    br.lines()
                            .mapToLong(l -> l.split(pattern.toString()).length)
                            .sum());
        }  catch (IOException e) {
            e.printStackTrace();
        }

        /* Challenge
        .............
         *  - Tokenize the text into words, remove any punctuation
         *  - Ignore words with 5 char(s) or less
         *  - Count the occurrences of each word
         *  - Display the top 10 most used words
         *
         * Set up a local variable for results and start out with the source
         *  - Use a flatMap() to evaluate every word
         *  - call map and act on each word and removing punctuation via regex using replaceAll and passing with an empty string literal
         *  - evaluate words more than 4 char(s)
         *  - make all the words lowercase, so we don't have duplicates just based on case
         *  - terminalOp will group by the whole word and count all instances of the same words
         *  - print the results with only the top 10 words with the highest frequency
         *  - Stream again, by getting the entrySet of the result
         *      - sort by entry value which is the count of occurrence but in reverse so that we can pass the 2nd comparator i.e. reverseOrder
         *      - limit results to 10
         *      - print word (key) and the value - no of occurrences
         *
         * Create a list of words that you want to exclude from the text file
                - Add a filter to the result stream using filter intermediateOp
         *
         *
         */

        System.out.println("_".repeat(50));
        try (BufferedReader br = new BufferedReader(new FileReader("article.txt"))) {
            Pattern pattern = Pattern.compile("\\p{javaWhitespace}");

            List<String> excluded = List.of("grand","canyon","retrieved","archived","service","original");
            var result = br.lines()
                    .flatMap(pattern::splitAsStream)
                    .map(w -> w.replaceAll("\\p{Punct}",""))
                    .filter(w -> w.length() > 4)
                    .map(String::toLowerCase)
                    .filter(w -> !excluded.contains(w))
                    .collect(Collectors.groupingBy(w -> w, Collectors.counting()));
            result.entrySet().stream()
                    .sorted(Comparator.comparing(Map.Entry::getValue, Comparator.reverseOrder()))
                    .limit(10)
                    .forEach(e -> System.out.println(e.getKey() + " - "+ e.getValue()));
        }  catch (IOException e) {
            e.printStackTrace();
        }

        /*
         * Use bigben.txt
         * Update source file to bigben.txt
         */

        System.out.println("_".repeat(50));
        try (BufferedReader br = new BufferedReader(new FileReader("bigben.txt"))) {
            Pattern pattern = Pattern.compile("\\p{javaWhitespace}");

            var result = br.lines()
                    .flatMap(pattern::splitAsStream)
                    .map(w -> w.replaceAll("\\p{Punct}",""))
                    .filter(w -> w.length() > 4)
                    .map(String::toLowerCase)
                    .collect(Collectors.groupingBy(w -> w, Collectors.counting()));
            result.entrySet().stream()
                    .sorted(Comparator.comparing(Map.Entry::getValue, Comparator.reverseOrder()))
                    .limit(10)
                    .forEach(e -> System.out.println(e.getKey() + " - "+ e.getValue()));
        }  catch (IOException e) {
            e.printStackTrace();
        }

        /*
         * Bonus Part
         * Use with streaming ()s and vice versa
         *
         * Do this without a stream
         * Use Files.readString(Path path)
         * Replace all punctuation in the entire String with one call
         * Catch IOException
         *
         * Set up the pattern - going to be diff, since we are not splitting or tokenizing
         * Will use Matcher to find big words into this big string
         * The pattern should look for 1 or more char(s) - then get a Marcher passing the input string
         *
         * THen set up a new Map keyed by String, containing a long value
         * use while loop with matchers find() to loop through each match, or each word it found
         * we can use matcher.group to get the next word and convert it to lower case
         * Check if the word is has more than 4 char(s) and if so add it to the result Map with 1 as initial value and
         *  incrementing by 1 if it's not a new keyed entry
         * That will populate the map with distinct values and counts.
         *
         * Next , we will sort this and print
         *  - Create an ArrayList of the entries
         *  - Then sort the entries, by the value in reverseOrder
         *  - Loop from 0 to 9 or less if the text doesn't have 10 entries
         *      - get the entry from the list
         *      - print as we did before
         * Updated regex - at least 5 char(s)
         */
        System.out.println("_".repeat(50));
        String input = null;
        try {
            input = Files.readString(Path.of("bigben.txt"));
            input = input.replaceAll("\\p{Punct}","");

            Pattern pattern = Pattern.compile("\\w{5,}");
            Matcher matcher = pattern.matcher(input);

            Map<String,Long> result = new HashMap<>();
            while (matcher.find()){
                String word = matcher.group().toLowerCase();
                //if (word.length() > 4){
                    result.merge(word , 1L,(oldVal,newVal) -> oldVal += newVal);
               // }
            }
            var sortedEntries = new ArrayList<>(result.entrySet());
            sortedEntries.sort(Comparator.comparing(Map.Entry::getValue , Comparator.reverseOrder()));
            for (int i = 0; i < Math.min(10, sortedEntries.size()); i++) {
                var entry = sortedEntries.get(i);
                System.out.println(entry.getKey() + " - "+ entry.getValue());
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
