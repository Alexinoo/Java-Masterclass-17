package files_input_output.part11_readinfiles_scanner;

import java.io.*;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.MatchResult;

public class Main {

    /* Reading Files with Scanner*/

    public static void main(String[] args) {

        /* Create a Scanner variable
         * The Scanner class has overloaded versions that lets you pass diff types of sources to this constructor
         * e.g. File source, Path source, A String source, A Readable source, InputStream source , ReadableByteChannel
         * It also lets you pass a char set or a char set name
         */

        /* Setting a Scanner Variable using a File instance
         *..................................................
         * Handle checked exception - surround with try-with-resources block
         * A scanner isn't automatically closed, so if you don't put that in try-with-resources {}, this code will keep the file open
         * We didn't have to worry about closing the scanner when the source was a String, because a String doesn't open & hold onto
         *  an open resource
         * We've also used Scanner with System.in (is a special InputStream called the standard input stream) - it's a special case because
         *  the JVM opens 1 instance of it for console, or keyboard and you don't really want to close it
         * Scanner is nice because once you get familiar with all it's functionality, you can process data from diff inputs in a standardized way
         *  which the scanner lets you do
         *
         * Use a while loop and check hasNextLine() to determine if there's more data to process
         * Then use nextLine() to get next line from the "file.txt"
         *
         * Running this prints text file line for line
         *
         *  */

        try (Scanner scanner = new Scanner(new File("file.txt"))) {
            while (scanner.hasNextLine()){
                System.out.println(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        /*
         * Using tokens() to get stream of Strings
         * Lets use tokens() to read the file but first, review what delimiter the tokens() will use
         * We can do this by printing the result of invoking the delimiter () on scanner
         *  Prints "\p{javaWhitespace}+" - a regex - the text will be split by 1 or more white space, any white space including new line chars
         * This means if we use tokens() with the default delimiter, we'll just get a list of words
         * Since that is not what we want, we'll set the scanner's delimiter
         * We'll call useDelimiter() on scanner instance and pass a regex , and put a dollar sign "$" which is a meta char for end of line
         * Now call tokens() which returns a stream of Strings and print each string
         * Running this prints the same result as the while loop example
         * Each line of text in the file, is returned on the stream & we can work with lines of text, rather than words
         *
         */
        System.out.println("------------------ using tokens() on scanner -----------------");

        try (Scanner scanner = new Scanner(new File("file.txt"))) {
            // System.out.println("Default delimiter : "+scanner.delimiter());
            scanner.useDelimiter("$");
            scanner.tokens().forEach(System.out::println);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


        /*
         * Using findAll()
         * Call findAll() on scanner and pass a string that contains a regex that prints word with >= 10 characters in each line
         * This returns a stream of MatchResult items
         * This is very similar to the findLine() reviewed in the regex section
         * Because it returns a MatchResult, we are only interested with what came back as a whole match
         * MatchResult has a group() on it which returns all the char(s) that matched the regex
         * Map the MatchResult to a String , using the group() with a method reference
         * Then filter distinct values with distinct intermediate operation
         * sort and print each stream element
         *
         * Running this prints a list of words with 10 char(s) or more
         * And they are ordered naturally, in alphabetical order in other words
         */
        System.out.println("------------------ using findAll() on scanner -----------------");

        try (Scanner scanner = new Scanner(new File("file.txt"))) {
            scanner.findAll("[A-Za-z]{10,}")
                    .map(MatchResult::group)
                    .distinct()
                    .sorted()
                    .forEach(System.out::println);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        /*
         * Attached is fixedWidth file that we are going to use in this example
         * Contains data with no delimiters
         * First-line is the header
         *  Name - 15 char(s)
         *  Age  - 3 char(s)
         *  Dept - 12 char(s)
         *  Salary - 8 char(s)
         *  State - 2 char(s)
         *
         * Create a local variable and initialize it scanner.findAll()
         * In this case, the regex is going to group each column
         * Use "(.{15})(.{3})(.{12})(.{8})(.{2}).*" to specify each group
         * Then in the parentheses we have a dot (.) meaning any char, then specify width in {} to match fixed width length of char(s)
         * End with .* so that the line won't fail if there are extra char(s)
         * Use map and get group 5 which rep states (the last column)
         * sort and get unique values
         * Collect into a String [] with toArray() terminal operation - specify the array that we want
         *
         * Finally, print the results in 1 statement
         * Running this prints
         *  "[CA, IL, NY, ST, TX, WA]"
         * Notice that the header ST also gets printed in the list of distinct states and that's because of the header row
         *  - use skip(1) to skip the header
         * Running this prints distinct list of states
         *
         * Let's try with group(3) which rep dept
         * Running this prints
         *      "[Finance     , HR          , IT          , Marketing   ]" - with spaces btwn
         *    - use trim() on group to remove the extra spaces
         */
        System.out.println("------------------ using findAll() with a fixedWidth.txt text file -----------------");

        try (Scanner scanner = new Scanner(new File("fixedWidth.txt"))) {

            var results = scanner.findAll("(.{15})(.{3})(.{12})(.{8})(.{2}).*")
                    .skip(1)
                     //.map(m -> m.group(5))
                     .map(m -> m.group(3).trim())
                    .distinct()
                    .sorted()
                    .toArray(String[]::new);

            System.out.println(Arrays.toString(results));

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


        /* Setting a Scanner Variable with a Path.of()
         *............................................
         * Handle IOException instead of FileNotFoundException
         *
         * Running this prints the same thing as results above
         *
         */

        System.out.println("------------------ using Path.of(fixedWidth.txt) on the Scanner constructor -----------------");

        try (Scanner scanner = new Scanner(Path.of("fixedWidth.txt"))) {
            var results = scanner.findAll("(.{15})(.{3})(.{12})(.{8})(.{2}).*")
                    .skip(1)
                    //.map(m -> m.group(5))
                    .map(m -> m.group(3).trim())
                    .distinct()
                    .sorted()
                    .toArray(String[]::new);

            System.out.println(Arrays.toString(results));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }



        /* Setting a Scanner Variable with an instance of FileReader
         *............................................
         * This code compiles with that 1 change
         *
         * Running this prints the same thing as results above
         *
         */

        System.out.println("------------------ using new FileReader(fixedWidth.txt) on the Scanner constructor -----------------");

        try (Scanner scanner = new Scanner(new FileReader("fixedWidth.txt"))) {
            var results = scanner.findAll("(.{15})(.{3})(.{12})(.{8})(.{2}).*")
                    .skip(1)
                    //.map(m -> m.group(5))
                    .map(m -> m.group(3).trim())
                    .distinct()
                    .sorted()
                    .toArray(String[]::new);

            System.out.println(Arrays.toString(results));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        /* Setting a Scanner Variable with an instance of BufferedReader
         *............................................
         * This code compiles with that 1 change
         *
         * Running this prints the same thing as results above
         *
         */

        System.out.println("------------------ using new BufferedReader(new FileReader(fixedWidth.txt)) on the Scanner constructor -----------------");

        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader("fixedWidth.txt")))) {
            var results = scanner.findAll("(.{15})(.{3})(.{12})(.{8})(.{2}).*")
                    .skip(1)
                    //.map(m -> m.group(5))
                    .map(m -> m.group(3).trim())
                    .distinct()
                    .sorted()
                    .toArray(String[]::new);

            System.out.println(Arrays.toString(results));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
