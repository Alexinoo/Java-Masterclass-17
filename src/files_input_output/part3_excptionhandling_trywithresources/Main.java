package files_input_output.part3_excptionhandling_trywithresources;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        String filename = "testing.csv";

        testFile2(null);

        System.out.println("_".repeat(50));

        File file = new File(filename);
        if(!file.exists()){
            System.out.println("I can't run unless this file exists");
            System.out.println("Quitting the application, go figure this out");
            return;
        }
        System.out.println("I am good to go.");
    }

    /*
     * Comment List<String> lines = Files.readAllLines(path);
     * Use FileReader class instead passing the filename to its constructor
     * This class has methods to read data in from a character file but will just instantiate it for now
     * Notice the highlight from intelliJ - says FileReader is used without a try-with-resources statement
     * Try-with-resources was introduced in JDK-7
     * If you use try-catch with FileReader class, that implicitly open a resource, it's very important to
     * close the resource in the finally block
     * First, we need to declare the reader variable outside the try clause and initialize it to null
     * Then close the reader manually before print statement
     *
     * Close the reader if it's not null
     * However, reader.close() throws a checked exception - we have to catch or specify
     * This makes the code a bit ugly in the finally block but before JDK-7 , this is how you'd have
     * closed a file resource
     * We are going to leave this method as is and create testFile2
     *
     */
    private static void testFile(String filename)  {
        Path path = Paths.get(filename);

        FileReader reader = null;

        try {
            //List<String> lines = Files.readAllLines(path);
             reader = new FileReader(filename);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            if (reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println("Maybe I'd log something either way...");
        }
        System.out.println("File exists abd able to use as a resource");
    }

    /*
     * Create testFile2() with the same signature as testFile()
     * Use FileReader class instead passing the filename to its constructor
     * This throws a checked exception with intelliJ suggesting we need to handle it
     * Use surround the FileReader with try-with-resource block from more-actions
     * However, try block is diff because it now has a set of parentheses associated with
     * In the parentheses, we have the FileReader instantiation without the semi-colon
     * The try-with-resources takes a colon-delimited list of resource variables
     * The resources in this list must implement the AutoCloseable or the Closeable interface
     * The Closeable interface extends AutoCloseable as of JDK-7
     * The try-with-resources can be used without a finally block, because all resources are automatically closed
     * when this type of try block completes, of if it's gets an exception.
     *
     * However, our code still doesn't compile either, and intelliJ suggests we add catch clauses.
     * intelliJ shows 2 catch clauses i.e. FileNotFoundException amd IOException
     * This adds 2 catch clauses with both exceptions above
     * Multiple catch clauses aren't unique to the try-with-resources clause
     * You can have multiple catch clauses with either try statement -
     * The reason they are 2 is the first one - FileNotFoundException - may occur when opening the resource
     * The IOException may occur when working or closing the resource
     * Ironically, IntelliJ doesn't like this and highlights the 2nd catch clause and provides a hint to collapse
     * catch-blocks
     * Add a print statement - File not found inside the first catch block - (solves the intelliJ highlight on 2nd catch)
     * This makes the code a bit easier to read because there is no finally block -its optional though meaning , you can
     * still add it to log something
     * Adding it anyway for fun
     * Go to the main() and call testFile2() - throws err
     *
     * There are some rules around having multiple catch block
     *   - Switching the order to have IOException as the 1st catch and FileNotFoundException as the 2nd
     * This introduces a compiler error on the 2nd catch clause suggesting that FileNotFoundException has already
     * been caught - How is that possible..?
     * As it turns out, Java evaluates the catch clauses in the order we declare them
     * When IOException is declared first, it catches all instances of that Exception, as well as all instances of
     * it's subclasses
     * Because FileNotFoundException is a subclass of IOException, it get's caught by the first clause
     * This means the 2nd catch clause is redundant or unreachable when specified after it's super class
     *
     * If you do have code that's different for specific exceptions, you need to declare your clauses in herachical
     * order from most specific, downward to the most general
     *
     * Reverting the switching order again - to follow hierarchical order rule
     *
     * Then, will add a truly catch all clause, catching just Exception as the last clause
     *  print something in this clause
     * The class Exception extends Throwable, so we could include Throwable in this hierarchy as well
     * Throwable is the parent class of all of Java's exceptions and error classes
     *
     * Another variation of the catch clause which lets you catch multiple targeted Exceptions with a single clause
     * Instead of listing 1 type of Exception and a local variable for that exception, you list multiple exception
     * separated by a pipe character i.e OR statement
     * It lets you remember that the catch expression means one exception or the other
     * Add another catch for NullPointerException | IllegalArgumentException badData'
     *  Print that user has added data in this catch block
     *
     *
     * Go to the main() and pass null and test this out
     * We don't get an exception thrown or a stack trace
     * Runs and print 3 statement
     *      - One from the 2nd catch block
     *      - Second from the finally block
     *      - Last print statement from the testFile2() was reached and executed
     * Apparently, passing null to FileReader does not throw a FileNotFoundException but a NullPointerException
     * In this case, we are not propagating any error to the stack
     *
     * You can't list exceptions in the multi exception clause that are derivatives of the same class
     *  i.e. Passing RuntimeException in the 2nd catch block won't work - is a more generic exception
     *
     * Key takeaways are that many of the types to read and write to files are instantiated using the new keyword
     *
     * Underneath the covers, the constructor opens the file resource and its important to close the resource
     * when you're done with it
     *
     * Using try-with-resources is the recommended approach both to make your code more concise and to avail yourself
     * of Java's built-in support for automatically closing resources with that try-block
     */
    private static void testFile2(String filename){
        try (FileReader reader = new FileReader(filename)) {
        } catch (FileNotFoundException e) {
            System.out.println("File '" + filename + "' does not exist");
            throw new RuntimeException(e);
        }catch (NullPointerException | IllegalArgumentException badData){
            System.out.println("User has added bad data "+ badData.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch(Exception e){
            System.out.println("Something unrelated and unexpected happened");
        }finally {
            System.out.println("From finally clause : Maybe I'd log something either way...");
        }
        System.out.println("File exists and able to use as a resource");
    }
}
