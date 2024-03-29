package files_input_output.part19_file_and_dir_manipulation_challenge;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    /* Directory and File Manipulation Challenge
     *
     * Create a directory at the root of your IntelliJ Project named "public" in the cwd
     *  - Inside "public", create a sub dir named "assets"
     *  - Inside "assets", create a sub dir named "icons"
     *
     * Create a process that will generate an index.txt file for each dir and sub dir
     *  - In each of the dir(s) ("public","assets" and "icons"), create an index.txt file
     *  - In each index.txt file,
     *      - list all the contents in the curr dir, with full paths and the date each item was created - recursive
     *      - the index.txt file of the parent should contain all items that are listed in the index.txt of the child
     *
     * Make a copy of the index.txt in each sub folder
     *
     * After you have created these copies, run your code to generate each index.txt file, and verify your backup copies
     *  are listed there
     *
     * Hint:
     * Files Class offers you many diff ways to create dir(s) , file(s) and for iterating through a file tree.
     * Stick to using Paths and NIO2 functionality
     *
     *  */

    public static void main(String[] args) {

        /*
         * Set up a path using a factory () that takes a variable list of paths
         * Pass each dir in a list i.e. , public,assets,icons
         * To create this dir(s)
         *  - Call createDirectories() on Files class and pass it his path
         *  - Surround with try-catch for possible IOException
         *
         * Running this :
         *  - Creates public folder and assets and icons as nested folders
         */

        Path deepestFolder = Path.of("public","assets","icons");
        try {
            Files.createDirectories(deepestFolder);
            generateIndexFile(deepestFolder.getName(0));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        /*
         * Loop through the name parts starting with 1 to get each subpath of the deepest Folder path
         * Get the path for the existing file index.txt , resolving to the subpath
         * Create a backup path in the same way, but with the name indexCopy.txt
         *
         * Execute Files.copy and copy the indexedPath to the backupPath
         *  - Add a try-catch to Files.copy
         *
         * Running this :-
         *  - Creates copies in the project panel within every sub-folder
         */
        for (int i = 1; i <= deepestFolder.getNameCount(); i++) {
            Path indexedPath = deepestFolder.subpath(0,i).resolve("index.txt");
            Path backupPath = deepestFolder.subpath(0,i).resolve("indexCopy.txt");
            try {
                Files.copy(indexedPath,backupPath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

        /*
         * The final step is to generate the index.txt again
         * call generateIndexFile() passing it the name part with an index of 0
         *
         * Running this :-
         *  - We get FileAlreadyExistsException
         *  - is because copy is not going to overwrite na existing file, unless we specify by passing a copy option
         * So scroll up to the Files.copy() and pass StandardCopyOption.REPLACE_EXISTING enum value as the 3rd arg
         *
         * Running this :- the code ran without any issues
         *    - Examining the contents of the index.txt in the public folder
         *      - the file has grown and the content includes the listing for the copies
         */
        generateIndexFile(deepestFolder.getName(0));

    }

    /*
     * Next
     * Write a code that generate index.txt at each sub-folder level
     * Create generateIndexFile(Path path) with path arg
     * We want to create one of these in every sub-folder and therefore , we need a path index that reflects the
     *  changing path, based on what's passed as the arg
     * Start by calling resolve on the starting path and passing it the filename which will always be "index.txt"
     * Use a stream - so use try-with-resources and set a variable to be a Stream<Path> named contents
     *  - Use find() on Files and pass
     *      - startingPath
     *      - depth - INTEGER.MAX_VALUE for the depth
     *      - predicate and return true
     *  - We have used Files.find() instead of list or walk?
     *      - find gives us access to file attr in the predicate shd we need it
     *
     * Next
     * Collect all the stream's paths into a String, that'll will be the content of the file
     * Create a local var, a String, named fileContents
     *      - map it to absolutePath's string
     *      - collect that with Collectors.joining()
     *      - Then join each item with a system line separator
     *      - A header "Directory Contents" in a file
     *      - A trailer "Generated: " with a local date time
     *
     * Finally, write this string to a file using Files.writeString and pass the following
     *  - indexFilePath
     *  - fileContents
     *  - options specifying how the file is opened
     *      - 1st to Create
     *      - 2nd truncate existing - overwriting
     *
     * Call generateIndexFile(Path startingPath) and pass "public" as the starting path
     *
     * Running this :-
     *  - Create index.txt file with
     *      - a header
     *      - absolute paths to child dir(s) - nested sub-folders
     *      - and trailer
     *
     * We don't see index.txt but if run again - we'll see it as also part of the absolute path
     *
     * The challenge was to create an index.txt file for each subfolder & we've created only 1 for the top level
     *
     * Therefore , we need to change generateIndexFile() to call itself recursively, for any children that are dir(s)
     * Use Files.list() which returns a Stream of Paths named contents
     *  - Filter by paths that are dir(s)
     *  - Collect the paths toList - we don't want to nest a recursive call within a stream operation
     *  - Chain forEach to the list that we get back
     *      - Execute generateIndexFile on child dir
     *
     * Running this :-
     *  - Looking at the Project panel, the 3 index.txt files are generated
     *  - The index file for each folder contains
     *      - header,
     *      - path to the current dir
     *          - any path to the sub-folders
     *      - trailer
     * Running this again, we get also each of the index.txt files listed in the output
     *
     *
     * Next part of the challenge was to create copies of the index.txt files on each sub-folder
     * Do this on the main() using a for loop
     *  -> to the main()
     */
    private static void generateIndexFile(Path startingPath){
        Path indexFilePath = startingPath.resolve("index.txt");
        try(Stream<Path> contents = Files.find(startingPath , Integer.MAX_VALUE ,(p,attr) -> true)){
            String fileContents = contents
                    .map(path -> path.toAbsolutePath().toString())
                    .collect(Collectors.joining(
                            System.lineSeparator(),
                            "Directory Contents: "+System.lineSeparator(),
                            System.lineSeparator() + "Generated: "+ LocalDateTime.now()
                    ));
           Files.writeString(indexFilePath,fileContents, StandardOpenOption.CREATE,StandardOpenOption.TRUNCATE_EXISTING);
        }catch (IOException e){
            throw new RuntimeException(e);
        }

        try(Stream<Path> contents = Files.list(startingPath)){
            contents
                    .filter(Files::isDirectory)
                    .toList()
                    .forEach(dir ->{
                        generateIndexFile(dir);
                    });
        }catch (IOException e){
            throw new RuntimeException(e);
        }

    }
}
