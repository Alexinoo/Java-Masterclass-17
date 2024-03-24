package files_input_output.part7_files_listing;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.stream.Stream;

public class Main {

    /* Useful methods on the Files class
     * list() , walk() , find() - can be used to get a source, for a stream pipeline of path elements
     */

    public static void main(String[] args) {

        /*
         * Create a Path variable "path" and pass an empty string
         * A path with an empty string references cwd
         * Print the full path of cwd using the toAbsolutePath() on the Path interface
         *  */
        Path path = Path.of("");
        System.out.println("cwd = "+path.toAbsolutePath());

        /*
         * A very common need is to understand what the contents of directories are:
         * The Files class has a list() which takes a path instance & returns a stream ,of path instances,each path rep
         *  either a file,or a sub-folder in the dir specified
         * Create a local variable a Stream typed with Path and name it paths and assign it to the result of calling a
         *  static() named list() on Files class passing it the path instance & that's going to return a Stream of type Path
         * However, you can't use list() without catching IOException -
         * A Stream is lazily executed which means a reference to an open dir is maintained after this call and remains open
         *  until the stream's terminal operation is executed
         * It's easy to forget the ()s that return streams are opening a resource
         * Other ()s like readAllLines() are both opening & closing the resource for you as a convenience
         * You shd always use try-with-resources for the list(),walk() and find() that return these streams of paths
         * Follow intelliJ suggestion to use try-with-resources on list() below
         *
         * Call forEach on the paths and print each path
         * Running this prints all the folders and files in the cwd
         * This () isn't recursive, so won't see the contents of any sub-folders
         *
         * Include a map() intermediate operation and call listDir() passing
         */
        try(Stream<Path> paths = Files.list(path)) {
            paths.map(Main::listDir).forEach(System.out::println);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        /*
         * There is another () on Files, that's similar to list and is called walk()
         * Copy the try-catch block above and paste it below - update list() to walk()
         * walk() takes a 2nd arg - depth - set it to 1
         *
         * Running this code, prints the same output as above using walk() or list()
         * Significant diff between list() and walk() is that walk is recursive If we specify depth to be > 1
         * If we update the 2nd arg to 2, the code will now walk through the sub-folders of the original path (cwd)
         * This output shows us the contents of src folder but not going any deeper than that 2nd level
         * Reverting back to 1 to show fewer files
         */
        System.out.println("_".repeat(50));
        try(Stream<Path> paths = Files.walk(path, 1)) {
            paths.map(Main::listDir).forEach(System.out::println);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        /*
         * We can use stream pipeline techniques to filter out dir and just list files
         * Filter with () reference Files::isRegularFile
         */
        System.out.println("_".repeat(50));
        try(Stream<Path> paths = Files.walk(path, 1)) {
            paths
                .filter(Files::isRegularFile)
                .map(Main::listDir).forEach(System.out::println);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        /*
         * Files.find()
         * This works as the same as walk() but we can specify our condition as an arg
         * Copy the try-catch block above and paste it below - update walk() to find()
         * Takes 3 arg
         *  - 1st arg is the path instance
         *  - 2nd arg is the max depth
         *  - 3rd arg is a Bi Predicate type - takes 2 args and returns a boolean
         *      - 1st arg is the path itself - rep each stream element
         *      - 2nd arg is a type named BasicFileAttributes (A convenient access to all the attr on your file or dir)
         * Now we can remove the filter operation from the pipeline
         * Running this prints similar results with walk()
         *
         * That looks nice & all, but why not use walk with filter which seems a little easier to understand
         * First, we don't have to use "Files.isRegularFile(p)" at all
         *  - We can use the attr of the path which are available to us in an easy to use way, to filter instead
         *  - need to change to attr.isRegular() and don't have to pass an arg because the attr is already associated
         *    wth the current path in the stream
         * Running this prints similar results as we had
         *
         */
        System.out.println("_".repeat(50));
        try(Stream<Path> paths = Files.find(path, 1,
                (p,attr)-> attr.isRegularFile() )) {
            paths.map(Main::listDir).forEach(System.out::println);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        /*
         * Let's have more fun by printing more than just 2 levels
         * A common approach is to have Integer.MAX_VALUE to search all nested levels
         * Next , find files that are >= 10000 bytes in size
         * We can do this by adding attr.size() >= 10000 on our BiPredicate function
         * You can imagine using this () to search for any files, modified in the past week, or any files
         *  that exceed a certain size etc
         */
        System.out.println("_".repeat(50));
        try(Stream<Path> paths = Files.find(path, Integer.MAX_VALUE,
                (p,attr)-> attr.isRegularFile() && attr.size() >= 10000)) {
            paths.map(Main::listDir).forEach(System.out::println);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        /*
         * There is one more () on Files which can be very efficient
         * DirectoryStream is another Java NIO2 class
         * It provides an iterable for dir(s)
         * Like the others, we will wrap it with try-with-resources
         * Call newDirectoryStream() from Files class and pass the path instance
         * Because the result is an iterable type, we can use forEach to print each item using Main.listDir(d)
         *
         * Running this prints a directory listing of the cwd
         */

        System.out.println("_".repeat(50));
        System.out.println("======== Directory Stream ======================");
        try( var dirs = Files.newDirectoryStream(path)) {
            dirs.forEach(d -> System.out.println(Main.listDir(d)));
        }catch(IOException e){
            throw new RuntimeException(e);
        }

        /*
         * newDirectoryStream() has an overloaded version, that lets us filter which paths we want back,
         * first with a use of a string called glob
         * A glob is a limited pattern language, that resembles regex but with a simpler syntax
         * To show this with the globbing pattern, first let's change the dir we are starting at
         * We can use one of the Path creation factory (), but there is a () on path that allows one to navigate
         * from the path you are to another
         * This is called resolve, and we can pass it the name of a subfolder
         * Let's work with "idea" - intelliJ's project configuration folder and include the glob star "*.xml" as
         *  the 2nd arg to the newDirectoryStream()
         *
         * Running this code now gets us files that end in .xml which is cool
         */

        System.out.println("======== newDirectoryStream() overloaded version ======================");
        path = path.resolve(".idea");
        try( var dirs = Files.newDirectoryStream(path,"*.xml")) {
            dirs.forEach(d -> System.out.println(Main.listDir(d)));
        }catch(IOException e){
            throw new RuntimeException(e);
        }

        /*
         * newDirectoryStream() also has an overloaded version, that takes a type that's on the DirectoryStream class
         * and that's a filter, a functional interface
         * This means it's a target for lambda
         * It takes 1 arg and returns a boolean
         * Copy code above and delete "*.xml"
         * Add the 2nd arg as a lambda which will work like glob
         *  - Lambda takes a path- so will get the last part of the path, file name and convert it to a string
         *    & call endsWith() passing .xml
         *  - We can also make this lambda be anything we want - i.e add we only want regular files > kilobyte
         * Elements returned by the iterator are in no specific order
         * Running this code prints files > 1000 kb
         */

        System.out.println("======== newDirectoryStream() overloaded version 2 ======================");
        try( var dirs = Files.newDirectoryStream(path,
                p -> p.getFileName().toString().endsWith(".xml")
                && Files.isRegularFile(p) && Files.size(p) > 1000
                )) {
            dirs.forEach(d -> System.out.println(Main.listDir(d)));
        }catch(IOException e){
            throw new RuntimeException(e);
        }

    }

    /*
     * Create a () that use attributes from each path to print a friendlier directory listing
     * Call it listDir(Path path) - takes a path instance and returns a String
     * Use try{} since some of the ()s we will use throw IOException
     * Create isDir variable that stores a boolean value on whether a file is a dir using Files.isDirectory()
     * Get the file last modified and store it as a date field - use Files.getLastModifiedTime(path)
     *  - return a formatted string that includes
     *    - datefield as is - last modified time
     *    - if a path is a directory - print dir otherwise " "
     *    - print string rep of the path
     * Print "something goes wrong" and return path as a string
     * Call listDir from the main() by passing it to map() intermediate operation
     * Refine the dateField printing by
     *    - create a LocalDateTime variable out of an instant and a zone id using Instant() - modDt
     *    - Get the Local zone from ZoneId.systemDefault
     * Change the formatted string for the output
     *    - Add 2 dates time specifier - replace %s with %tD for Date and add %tT for time
     *    - Replace dateField with modDt and add it twice
     * Include file size and add %12s right justified for size
     *    - Check if it's a dir, return "", otherwise call Files.size(path) to return the size of the file
     *
     * Running this code, prints the size of the files if it's not a dir
     *
     */
    private static String listDir(Path path){
        try{
            boolean isDir = Files.isDirectory(path);
            FileTime dateField = Files.getLastModifiedTime(path);
            LocalDateTime modDt = LocalDateTime.ofInstant(dateField.toInstant(), ZoneId.systemDefault());
            return "%tD %tT %-5s %12s %s".formatted(modDt, modDt ,
                    (isDir ? "<DIR>":""),
                    (isDir ? "":Files.size(path)),
                    path);

        }catch (IOException e){
            System.out.println("Whoops!, Something went wrong with "+path);
            return path.toString();
        }
    }
}











