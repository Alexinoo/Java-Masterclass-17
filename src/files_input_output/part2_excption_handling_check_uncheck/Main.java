package files_input_output.part2_excption_handling_check_uncheck;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Main {

    public static void main(String[] args)  {
        /*
         * Create a variable for a filename, and assign a String literal "testing.csv"
         *
         */
        String filename = "testing.csv";

        /*
         * Then get a path instance with the help of a static () on Paths called get and pass
         * the filename variable
         * After, we have a path instance, we can call a static readAllLines() on the Files class
         * passing it the path instance and returns the lines as a list
         *
         * However,this statement does not compile and intelliJ suggests we add exception to method
         * signature
         * Hovering this error says unhandled exception: java.io.IOException
         * The IOException is a special kind of exception called a Checked Exception
         * It's the parent class of many common exceptions you'll encounter when working with external resources
         * A checked exception rep an anticipated or common problem that might occur - e.g. A typo in the file name
         * and the system is not able to locate the file
         * It's very common and Java has a named exception for that situation, the FileNotFoundException, which is a
         * subclass of IOException
         * How do you handle a checked exception ? [ 2 options ]
         *  - Wrap the stmnt throwing a checked exception in a try-catch block & handle the situation in the catch block
         *  - Alternatively, change the method signature, declaring throws clause and specifying this exception type
         *
         * Going with option 1  - using a try-catch block
         * Running this gives a RuntimeException wrapped around another exception, NoSuchFileException because this file
         * doesn't exist yet
         * This way of handling the exception was not to handle the problem at all and not to force the calling code to handle
         * it either
         * Commenting out for now.
         *
         * Used testFile()
         */

//        Path path = Paths.get(filename);
//        try {
//            List<String> lines = Files.readAllLines(path);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

        /*
         * Added above statements to testFile(filename);
         * Calling testFile()
         * Running this code gives us the same exception as we got before, with 1 diff
         * Notice the stack trace here
         *  - The exception occurred in the testFile() and since it was not handled there, it propagated up to main()
         *    which called it
         *  - At the main(), the exception is also not handled , so the program exits ungracefully
         * The statement at the end of the testFile() was never printed
         * Let's add the finally clause in the try-catch block in the testFile()
         *
         * Running this code.. throws exception but the statement under the finally clause is printed though
         * Adding throws exception clause to method signature solves this problem
         *
         * Running this throws unchecked exception : NoSuchFileException
         *
         * Reverting back - removing throws Exception clause from method signature
         */

        testFile(filename);
        /*
         * Another approach might be to check if the file exists in the first place
         * Use the File class
         * Create a new file instance using the constructor in this class, that takes a filename
         * It has a method called exists() that will test if the file exists
         *  - if true - print something to the user and end the application via return keyword
         *  - if false - alert the user that things are ok
         */
        File file = new File(filename);
        if(!file.exists()){
            System.out.println("I can't run unless this file exists");
            System.out.println("Quitting the application, go figure this out");
            return;
        }
        System.out.println("I am good to go.");

        /*
         * In the above example, we are checking first, whether there will be an error situation
         * In the previous example, the code simply assumed that the file would exist & threw an exception if not
         * These 2 diff approaches have the acronyms , LYBL (Look Before You Leap) and EAFP (Easier to Ask Forgiveness
         * than Permission)
         *
         * LYBL
         * - style of coding that checks for errors first before you perform an operation
         *
         * EAFP
         *  - assumes an operation will usually succeed and then handles any errors that occur, if they do occur
         */

        /*
         * How do you recognize a Checked Exception
         * It's easy to recognize one because your code won't compile, if one is thrown from code you're calling.
         * An unchecked exception is an instance of a Runtime Exception, or one of it's subclasses
         * We have quite a long list of runtime, or unchecked exceptions -
         *  https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/RuntimeException.html
         * Some of them , we are pretty familiar with such as :-
         *      IndexOutOfBoundsException
         *      NullPointerException
         *      ArithmeticException and so on
         * If your code throws, any of these exception types, it's really upto you on whether to anticipate these
         * using either the LYBL or EAFP approaches
         * If you don't do anything, the exception will propagate up the call stack, looking for a code to handle it
         * This means that the exception is passed from the method where it was thrown, exiting that method at the point
         * it occured.
         * It's pushed back to the method that called it and so on until it reaches the top of the call stack which is
         * usually the main()
         * If the exception is not handle by any methods on the call stack, it terminates your program with an exception
         * stack trace
         * In general, any code in your code block that comes after the statment that threw an exception, isn't going to
         * be executed.
         *
         * But there's one exception to this
         * The try-clause has an additional clause called finally clause
         * Any code wrapped in the finally clause will get executed, whether an exception occurs or not
         */
    }

    /*
     * Finally clause
     * ...............
     * Does not have any parameters like catch clause and is used in conjunction with a try statement
     * A traditional try-statement requires either a catch-block or a finally clause or both
     * It's always declared after the catch block if one is declared
     * It's always executed regardless of what happens in the try or catch block
     * This block does not have access to either the try/catch block's local variables
     *
     * Was intended to perform cleanup operations such as closing open connections, releasing locks, or freeing-up resources
     * This block is executed during normal completion as well as in the event of an exception
     * This block can be used to execute other important tasks such as logging or updating UI
     *
     * The try-with-resources introduced in JDK-7 is a better approach than using finally clause for closing resources
     *
     * Disadvantages
     * .............
     * Difficult to read and understand code that uses this clause
     * Can be used to hide errors, making debugging more difficult
     * If you execute code that's not related to cleanup tasks, the code will be hard to maintain
     *
     *
     * Back to the testFile()
     * Comment out the catch block - this will throw a checked exception -
     * Add throw exception to the method signature for this code to compile
     * But we have pushed the problem to the main() - on the call to this method
     * Since we did not handle it in the testFIle(), any code that calls this method must now have to either
     * catch this exception or specify it in the throws clause
     *
     * Comment out catch-block and add throws exception clause to the method signature
     * Go to the main()
     *
     * Reverting back
     *      - removing throws Exception clause from method signature
     *      - Uncomment the catch block
     * Now in that catch-clause - let's force another error
     * Replace throw new RuntimeException(e) and purposely code a divide by 0 exception there
     * This means an exception is definitely going to occur, and it's going to happen in the catch clause
     * itself, while it's trying to handle a previous exception
     *
     * Running this now throws an ArithmeticException / by zero which is the last exception that occured
     * But we still get the statement from the finally clause still printed
     * This means an exception in your try/catch block, does not stop the code from finally block to execute
     *
     * Reverting last change
     *  - Remove division by 0 and uncomment out throws RuntimeException
     *
     * If the finally block, throws an exception however, execution ends at that point
     */

    private static void testFile(String filename)  {
        Path path = Paths.get(filename);

        try {
            List<String> lines = Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
           // int i = 1/0;
        }finally {
            System.out.println("Maybe I'd log something either way...");
        }
        System.out.println("File exists abd able to use as a resource");
    }
}
