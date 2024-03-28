package files_input_output.part18_manage_files_2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class CopyNestedFolders {

    public static void main(String[] args) {

        /* Copy With Nested Directories - DeepCopy
         *.........................................
         *
         *
         */

        Path copyNestedFiles = Path.of("copied_files");
        Path copyHere = Path.of("copied_dir");
        try{
//            if (Files.exists(copyHere)){
//                Files.delete(copyHere);
//            }
//            Files.deleteIfExists(copyHere);
            recursiveDelete(copyHere);
            recursiveCopy(copyNestedFiles,copyHere);
            System.out.println("Directory copied to "+copyHere);

        }catch (IOException e){
            e.printStackTrace();
        }

    }

    /*
     * Create a Recursive (Path source , Path target) that takes 2 args
     * Declare a throws clause to the () signature since this could fail in multiple ways
     * Make a shallow copy of the source path
     *
     * Check if the source path is a dir & if so get the contents via Files.list that returns a stream
     * Remember, for any () on Files that returns a stream, needs to be wrapped in try-with-resources
     * Use the stream's toList operation to create a list of Path
     * Chain forEach and pass a lambda expression.
     * For each path - call recursiveCopy() recursively by passing the current path and destination
     * For the destination though, we need to adjust the name of the destination child path with resolve()
     * Why resolve()
     *  - For relative paths, the source and target paths are joined or concatenated when you use it
     *  - Invoking this method with the path string "gus" will result in the Path " foo/bar/gus
     * Surround with try-catch since recursiveCopy() throws an IOException
     *
     * Then replace Files.copy(source,destination) from the main () to recursiveCopy(source,destination);
     *
     * Running this :-
     *  - copies copied_files files and dir() into copied_dir folder
     *
     * When we re-run this code, we'll get a FileAlreadyExistException thrown by the recursiveCopy()
     * The Files.copy() contains an overloaded () which lets you specify an option to copy a path even if it exists
     * We will add this arg which is an enum value from StandardCopyOption called REPLACE_EXISTING
     *
     * Running this throws another error - DirectoryNotEmptyException
     * Similarly to the issue of being unable to do a deep copy, we can't replace an existing dir that already has content
     *
     * One solution will be to delete the dir target if it exists
     * Go to the main() and check if the dir exists first and if so, delete it with a delete(Path path) on Files
     *
     *
     * Running this :- we still get DirectoryNotEmptyException error
     * This means we can't just delete a dir if it has contents either
     * If we want to delete the dir, we'll have to recursively delete all contents of its sub-folders similar to what we did
     *  with copying (recursiveCopy)
     *
     * There is another () on Files called deleteIfExists(Path path) which replaces the if statement and is much cleaner
     * Running this :- we still get DirectoryNotEmptyException error
     *
     * Solution is to add recursiveDelete() and call delete on each sub-folders
     */

    public static void recursiveCopy(Path source , Path destination) throws IOException{

        Files.copy(source,destination, StandardCopyOption.REPLACE_EXISTING);

        if(Files.isDirectory(source)){
            try( var children = Files.list(source)){
                children.toList().forEach(
                        p -> {
                            try {
                                CopyNestedFolders.recursiveCopy(p , destination.resolve(p.getFileName()));
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                );
            }
        }

    }

    /*
     * Copy recursiveCopy() and paste below
     * Update to recursiveDelete(Path path)
     * Takes only 1 arg - target deletion path
     * Remove Files.copy statement
     * Update source/destination to target
     * Change Main.recursiveCopy() to Main.recursiveDelete()
     * Pass only the path to this ()
     * The right place for delete, is after the if statement, that is when the recursive operations is completed
     * If we added it before the if statement, we'd simply get an exception, saying the dir is not empty
     * Return to the main() and replace deleteIfExists(copyHere) with recursiveDelete(copyHere)
     *
     * Running this works & we get the following output
     *  "Directory copied to copied_dir"
     * We also get copied_dir with the same contents as copied_files
     *
     *
     * For good measure, let's add another sub-folder under data and call it newdata
     *
     * Running this again: - we get the same output
     *  "Directory copied to copied_dir"
     * We can see that the newdata also gets copied to copied_dir : means copied_files has the same files with copied_dir
     *
     */

    public static void recursiveDelete(Path target) throws IOException{

        if(Files.isDirectory(target)){
            try( var children = Files.list(target)){
                children.toList().forEach(
                        p -> {
                            try {
                                CopyNestedFolders.recursiveDelete(p);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                );
            }
        }
        Files.delete(target);

    }

}
