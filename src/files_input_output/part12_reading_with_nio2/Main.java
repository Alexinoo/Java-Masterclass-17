package files_input_output.part12_reading_with_nio2;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {
    /* Reading with NIO 2*/

    public static void main(String[] args) {
        /*
         * Java has the most common encodings specified on an enum, called StandardCharsets
         * Check the default character encoding
         * Call getProperty() which takes a String on System class and pass "file.encoding"
         * Alternatively, we can call defaultCharset() on the Charset class
         *
         * Running this - both gives us the same result and the system default encoding is UTF-8
         * This can also be overridden by passing the char set, in most class constructors that read text files
         */
        var defaultEncoding = System.getProperty("file.encoding");
        var defaultEncoding2 = Charset.defaultCharset();
        System.out.println("Default Encoding: "+defaultEncoding);
        System.out.println("Default Encoding: "+defaultEncoding2);

        /*
         * use readAllBytes()
         * Start by reading the smallest unit, in this case bytes.
         * Ultimately,all data is really bytes & it gets encoded to char(s) , if it contains text, if we use a text
         *  based reader
         * Create a path variable to the file fixedWidth.txt which is in the cwd
         * wrap in a try-catch{} - readAllBytes will throw a checked IOException
         * use readAllBytes from the Files Class passing path instance
         * Pass the result to a new String instance and print it
         */
        Path path = Path.of("fixedWidth.txt");

        try {
            String textBlock = new String(Files.readAllBytes(path));
            System.out.println(textBlock);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        /*
         * use Files.readString(Path path)
         * says new String is redundant and therefore, we'll not use it here
         * Returns a String
         * wrap in a try-catch{} - readString() will throw a checked IOException
         * Prints the same result as above
         *
         * Ultimately calling readAllBytes()
         * Shd be used if you're reading text file - handles security checks & access issues
         */
        System.out.println("_".repeat(50));

        try {
            var textBlock = Files.readString(path);
            System.out.println(textBlock);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        /*
         * use Files.readAllLines(Path path)
         * Let's see if we can do something similar to what we did with Scanner
         * Will parse the distinct values out of certain col(s) using the readAllLines()
         * readAllLines() return a String[], so let's see it without using a stream pipeline
         *
         * Create the same regex
         * This pattern groups a specified no of char(s) rep a col of data in a fixed width file
         * Create a TreeSet to store the values - doesn't allow duplicate(s) and we want this sorted
         * Execute readAllLines on Files passing it path and chaining that to forEach
         *  - Set up a multi-line lambda
         *      - ignore the headerRow
         *  - Get a matcher using the compile pattern passing each string that was read form the file
         *  - If there is a match, use it for the 3rd group trimming the extra spaces and add it to the set
         * Then print the set
         *
         * Running this prints distinct values of the 3rd column of the fixedWidth text file - dept of the employee
         */
        System.out.println("_".repeat(50));
        Pattern pattern = Pattern.compile("(.{15})(.{3})(.{12})(.{8})(.{2}).*");
        Set<String> values = new TreeSet<>();
        try {
            Files.readAllLines(path).forEach(s -> {
                if (!s.startsWith("Name")){
                    Matcher m = pattern.matcher(s);
                    if(m.matches()){
                        values.add(m.group(3).trim());
                    }
                }
            });
            System.out.println(values);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        /*
         * Using Files.lines(Path path) : Stream<String>
         * Files class has it's own ()s that returns Stream<String> , one string for each line
         * When using the stream ()s on Files, you need to wrap the assignment in a try-with-resources {}
         * Streams are lazily executed, so resources are opened & never closed until a terminal operation is applied
         *
         * Create a Stream<String> from lines() on Files class passing path instance
         * Create a local variable that returns an
         *  - skip the header row
         *  - get a matcher from the patter variable
         *  - filter by actual matches
         *  - Map to a String using group-3 and trimming it
         *  - sort and filter unique values and return that in a string array
         * Then print the results
         *
         * Running this code - we get the same list of dept printed
         */
        try (var stringStream = Files.lines(path)) {
            var results = stringStream.
                    skip(1)
                    .map(pattern::matcher)
                    .filter(Matcher::matches)
                    .map(s -> s.group(3).trim())
                    .distinct()
                    .sorted()
                    .toArray(String[]::new);
            System.out.println(Arrays.toString(results));
        }catch (IOException e){
            throw new RuntimeException(e);
        }

        /*
         * Lets up the game a little bit and get the counts of employee in each dept
         * Copy the same code above
         * Use collect terminal operation passing it a Collectors.groupingBy()
         *  - group with the col 3 which we can get from the MatcherResult and trim as well
         *  - follow that up with Collector.counting() - counts all records within a group
         * We get back a Map keyed by a String (dept) and the value is the no of records in the dept
         * Print the data out using the entrySet
         */
        System.out.println("_".repeat(50));
        try (var stringStream = Files.lines(path)) {
            var results = stringStream.
                    skip(1)
                    .map(pattern::matcher)
                    .filter(Matcher::matches)
                    .collect(Collectors.groupingBy( m -> m.group(3).trim(),
                            Collectors.counting()));
            results.entrySet().forEach(System.out::println);
        }catch (IOException e){
            throw new RuntimeException(e);
        }

        /*
         * Summary
         * ........
         *
         * All of these methods read the entire contents of a file into memory
         * They support files up to about 2GB
         * For large files, you wanna use a BufferedReader or a Channel
         *
         *
         * byte[] Files.readAllBytes(Path path) throws IOException
         *  - reads entire contents of any file into a byte[]
         *  - closes the resource for you
         *  - means it doesn't need to be used with try-with-resources {}
         *
         * String Files.readString(Path path) throws IOException
         *  - reads entire contents of a text file into a string
         *  - preferred over readAllBytes for text files
         *  - closes the resource for you
         *  - means it doesn't need to be used with try-with-resources {}
         *
         * String Files.readAllLines(Path path) throws IOException
         *  - reads entire contents of a text file but returns a list of string
         *  - each element rep a line of text from the file
         *  - closes the resource for you
         *  - means it doesn't need to be used with try-with-resources {}
         *
         * String Files.lines(Path path) throws IOException
         *  - reads entire contents of a text file but returns a stream source of String
         *  - each element rep a line of text from the file
         *  - Should be included in a try-with-resources {}
         *  - resource is closed when terminal operation is applied
         */
    }
}
