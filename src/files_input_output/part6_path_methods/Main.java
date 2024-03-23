package files_input_output.part6_path_methods;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.Instant;

public class Main {
    /*
     * Review some of the method on Path Interface
     */

    public static void main(String[] args) {
        /* Create a Path variable and assign that path instance, using the factory method on Path
        * Then pass the path instance to printPathInfo(Path path)
        *
        */

        //Path path = Path.of("this/is/my/folder/files/testing.txt");
        Path path = Path.of("files/testing.txt");
       // printPathInfo(path);
        logStatement(path);
        extraInfo(path);
    }
    /*
     * Create a private static method that takes a Path instance and will use several methods on Path
     * Print path
     *      - prints exactly what was passed as the String literal - "files/testing.txt"
     *
     * Print file name - use getFileName()
     *      - prints filename "testing.txt"
     *
     * Print parent - use getParent()
     *      - prints parent directory "files"
     *
     * Print Absolute Path
     *      - Call toAbsolutePath() and assign result to a variable [absolutePath] of type Path
     *      - Prints "C:\JMC17\Java-Masterclass-17\files\testing.txt"
     *
     * Print root path - Use the absolutePath variable on getRoot()
     *      - Prints "C:\"
     *
     * Call getRoot() on the path itself to compare with above
     *      - prints "null" - directory files/testing.txt" is not on the root
     *      - A one indication that "files/testing.txt" is a relative path
     *
     * Call isAbsolute on path instance
     *      - Print "false" since we provided a relative path
     *
     * Call printPathInfo from the main()
     *
     * Now let's print the hierarchy of this absolute path
     *      - Print the root again
     *      - Initialize a counter to use for indentation
     *      - get the iterator of all the dir that make up the path instance - use toAbsolutePath() to start at the root
     *      - loop through this iterator with a while loop checking the hasNext value on the iterator
     *          - print indentation by calling repeat on "."
     *          - print subfolder for each iteration by calling next() on iterator
     *
     * Instead of using iterator, to loop through the directory tree, we can also use 2 methods available to us on path
     *      - Get the depth of the iterator tree using getNameCount() on absolutePath (start from the root)
     *      - Loop from 0 to the folder depth [absolutePath.getNameCount() ]
     *      - Print the indentation as before, but get the dir name at each level by calling getName(int index) on the absolutePath
     *          and passing the index
     *
     * Running this gives us the same output as we had with the iterator
     * Using the getName() though gives you a lot more flexibility in how you might iterate through the file tree
     */
    private static void printPathInfo(Path path){
        System.out.println("Path: "+path);
        System.out.println("fileName: "+path.getFileName());
        System.out.println("parent: "+path.getParent());
        Path absolutePath = path.toAbsolutePath();
        System.out.println("Absolute Path: = "+absolutePath);
        System.out.println("Absolute Path Root: = "+absolutePath.getRoot());
        System.out.println("Root = "+path.getRoot());
        System.out.println("isAbsolute = "+path.isAbsolute());

        System.out.println("-- Print Hierarchy of the Path using iterator() --");
        System.out.println(absolutePath.getRoot());
        int i = 1;
        var iterate = absolutePath.iterator();
        while(iterate.hasNext()){
            System.out.println(".".repeat(i++) +" "+ iterate.next());
        }


        System.out.println("-- Print Hierarchy of the Path using for loop --");
        System.out.println(absolutePath.getRoot());
        int pathParts = absolutePath.getNameCount();
        for (int j = 0; j < pathParts; j++) {
            System.out.println(".".repeat(j+1) + " "+absolutePath.getName(j));
        }




        System.out.println("_".repeat(50));
    }

    /*
     * However, in our hierarchy, we know that the last 2 paths [files/testing/ do not exist yet
     * We can create some code to fix this, first by creating another psvm and call it logStatement(Path path)
     * that takes a path instance as an arg
     * Start with a try{} since we are going to need it
     * First get the parent folder of the last element in the path which the getParent() does
     *  - If it doesn't exist in the file system, we want to create it
     *  - We can do this by static() createDirectory on Files, passing it the parent path
     *  - Then catching the checked exception in the catch(){}
     *      - print error printStackTrace - kind of ignores the error unless someone's watching the default output
     * Next, we need to create the file
     * There is a method that lets us both create and write to a file in a single statement
     * Use overloaded writeString()
     *  - Takes path as the 1st arg
     *  - String that will get printed as 2nd arg - since this is a log file include date and time
     *      - use Instant.now() to get a timestamp and print hello file world with an ending new line character
     *  - Takes the last arg - variable list of Options types
     *      - You can find these options on the StandardOpenOption enum in the java.nio.file package
     *          - First will specify the create option - gets created if it doesn't exist
     *          - Second, we want the append option, which means each sttmnt will get appended to the end of the file
     *
     * Call logStatement() from the main()
     *
     * Suppose , my file path contains several folders that don't exist yet ?
     * If we update the filename as "this/is/several/folders/files/testing.txt"
     * This throws a NoSuchFileException error from the createDirectory()
     * The solution is to use createDirectories() instead - works on one or many folders
     * Running this creates all the dir "this/is/several/folders/files/testing.txt" with the logStatement on testing.txt
     */
    private static void logStatement(Path path){
        try{
            Path parent = path.getParent();
            if(!Files.exists(parent)){
                Files.createDirectories(parent);
            }
            Files.writeString(path,
                    Instant.now()+": hello file world\n ",
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /*
     * Lets see what kind of information, we can get from this file
     * Create another method called extraInfo()
     *
     * Call readAttributes on Files passing the path instance that takes 2 args
     *  - 1st argument is the path instance
     *  - 2nd argument is a string which defines a list of attribute names you want info about
     *      - An asterisk which is a wildcard to retrieve all the file attributes
     * readAttributes() returns a map & therefore we can use forEach on the entrySet to print each
     * attribute key-value pair
     * readAttributes() throws a checked IOException that we need to handle
     * Call extraInfo() from the main()
     *
     * Running this outputs OS-specific attributes about this file such as
     *  - lastAccessTime = datetime
     *  - lastModifiedTime = dateTime
     *  - size=50 (few bytes - couple of log statements in this file)
     *  - creationTime=datetime
     *  - isSymbolicLink=false
     *  - isRegularFile=true
     *  - fileKey=null
     *  - isOther=false
     *  - isDirectory=false
     *
     * File attributes are going to be different, on diff OS
     *
     * In addition to getAttributes() ,there's also getAttribute() singular, so you can get 1 attribute at a time
     * by passing a specific String literal
     *
     * The Files.getAttribute() returns this data as an Obj, which means you may have to cast it to it's corresponding type
     * before processing e.g. FileTime, Long, Boolean
     * In addition to the getAttributes() and getAttribute(), you can get a couple of above fields by specifically named
     * methods on the Files class.
     *  - Files.size
     *  - Files.isRegularFile
     *  - Files.isDirectory
     *  - Files.isSymbolicLink
     *
     * Let's go through 1 method that might helps us understand the content of the file that you might be working with
     * This () is called probeContentType and will print what this file named [testing.txt]
     * Running this - indicates that this file has a content type of text/plain
     * In the context of web development,content type is used to indicate the type of data that is being sent/received
     * from the server
     */
    private static void extraInfo(Path path){
        try{
            var atts = Files.readAttributes(path,"*");
            atts.entrySet().forEach(System.out::println);
            System.out.println(Files.probeContentType(path));
        }catch (IOException e){
            System.out.println("Problem getting attributes");
        }
    }
}
