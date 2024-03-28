package files_input_output.part17_manage_files_1;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class RenameFile {

    /* Files and Directory Management
     *...............................
     * Renaming, copying, deleting files and directories
     * So much of what you might want to do with files and dir(s) is renaming, copying , moving & deleting them
     * We might also want to make a global search & replacement, on the contents of an existing file
     *
     * The Files Class has a wealth of helpful ()s - will use students.json file to demonstrate this ()s
     * We'll rename, move,copy and delete files using ()s on this class
     * Under the covers, these ()s delegate to the OS FileSystem provider for increased efficiency.
     *
     */

    public static void main(String[] args) {

        /*
         * Renaming A File using the IO way with 2 file instances
         *
         * Create a File instance to the existing "students.json"
         * Create a File instance using the new name of the file "student-activity.json"
         * Notice, above is perfectly valid to do, you can create a File instance, using a file name, even though it doesn't exist
         *
         * Confirm the file "students.json" exist via instance exists() on File class
         *  - Execute a renameTo(File destination) an instance () on File Class
         *      - takes new path name for the named file
         *      - returns true if and only if the renaming succeeded; false otherwise
         * Print if successful or not
         *
         * Running this successfully renames the file to "student-activity.json"
         *
         * There's a couple of problems with this code though as
         *  - intelliJ is highlighting a warning that we are not using the output from the renameTo()
         *  - a lot of things could go wrong while trying to perform the rename operation
         *  - ignoring the result is a bad idea as anything could possibly fail with rename operation
         *      - could be due to user permissions, network connectivity e.t.c.
         * One of the problem with Java.io classes is they don't throw an IOException
         * They instead return a boolean & you normally don't have an idea of what went wrong- if you get a false back
         * We can leave it this way for now and
         *
         */
        File oldFile = new File("students.json");
        File newFile = new File("students-activity.json");

        if(oldFile.exists()){
            oldFile.renameTo(newFile);
            System.out.println("File renamed successfully");
        }else{
            System.out.println("File does not exist!");
        }

        /*
         * let's use Path and Files Class to rename a filename
         * First, we'll execute the toPath() on File that lets us take an existing File instance & turn it into an NIO2 Path instance
         * The Path interface has a similar () called toFile(), so you can work with both IO or NIO2 classes
         *
         * Create a Path instance named oldPath by calling toPath() - an instance () on File Class that returns an abstract path
         * Create a Path instance named newPath by calling toPath() - an instance () on File Class that returns an abstract path
         *
         * Start by try-catch {} because most ()s on Files throw an IOException
         * This is an improvement, because it results in targeted & informative exceptions about any problems that occur during the operation
         * However, there isn't a rename () on Files Class & instead we need to use the move(Path source, Path destination , copyOption...options)
         *      - source (the path to the file to move)
         *      - target (the path to the target file)
         *      - options (options specifying how the move should be done)
         * Our file was renamed from "students.json" to "students-activity.json" and now we can rename it back to "students.json"
         * Print out if successful or catch IOException and print it
         *
         * Running this prints :-
                File does not exist! (attempted to look for "students.json" renamed in the previous run)
                Path renamed successfully
         * "students-activity.json" was renamed successfully back to "students.json"
         *
         *
         */

        Path oldPath = oldFile.toPath();
        Path newPath = newFile.toPath();
        try{
            Files.move(newPath,oldPath);
            System.out.println("Path renamed successfully");

        }catch (IOException e){
           e.printStackTrace();
        }



    }
}
