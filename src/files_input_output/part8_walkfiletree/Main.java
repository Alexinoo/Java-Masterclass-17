package files_input_output.part8_walkfiletree;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class Main {
    /*
     * Walking the file tree
     * The Files class had a () called walkFileTree that has been around since JDK-7
     * This () walks the file tree Depth First
     * Depth First means , the code will recursively visit all the child elements before visiting
     * ano of a folder's siblings
     * The alternative is Breadth first which means any dependent nodes are walked after the siblings
     * nodes
     * But remember, walk() and walkFileTree() are Depth first
     * Because it is depth first, the Files.walkFileTree() provides a mechanism to accumulate info
     * about all the children, up to the parent
     * Java provides entry points in the walk to execute operations, through a FIleVisitor interface
     * This provides ()s you can implement by overriding them, at certain events of your walk
     * These events are :-
     *  - Before visiting a directory
     *  - After visiting a directory
     *  - When visiting a file
     *  - A failure to visit a file
     */

    public static void main(String[] args) {

        /* Set cwd as the starting point */
        Path startingPath = Path.of(".");

        /* Set a variable for an instance of StatsVisitors class
        * Because we extended SimpleFileVisitor, we can use it's default no-args constructor
        * Call the walkFileTree() - pass it the starting path and FileVisitor obj
        * As usual, need to wrap this in a try-catch
        * We don't need to wrap this () in a try-with-resources because this () will get executed
        * at this point & resources will be closed as part of it's execution
        * If we run this now, it's not going to look like it's doing anything, and that's because,
        * the SimpleFileVisitor ()s just returns CONTINUE, the enum value
        * If we ran it on my drive, it will take a pretty long time & we will still get no output
        * To make it useful, we have to override some of the methods on this class
        */
        FileVisitor<Path> statsVisitor = new StatsVisitor(1);
        try {
            Files.walkFileTree(startingPath,statsVisitor);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /*
     * Add a static class as a member that extends SimpleFileVisitor and since it's generic, we need
     * to specify a type which will be Path
     *
     * Start by overriding visitFile() - Ctrl+O and select visitFile
     * Returns a call to the super class ()
     * Ctrl+click on visitFile() - copy the 3 statements and replace the return sttmnt
     * You might remember that Object.requireNonNull simply throws a NullPointerException,
     * if the arg is null.
     * This also returns the enum constant, CONTINUE
     * The value that we return from this () will determine how the walk is processed after this ()
     * I can continue or skip all my siblings, skip a subtree or terminate altogether
     * Lets print the file name for now
     *
     * Running this prints all the files in any of the sub-folders or in the current directory listed out
     * And remember, this is depth first, so they are printed in that order
     * Right away,visitFile() only visits types that are files but it's hard to tell
     *
     * How would I print the directory names ?
     * We can add code in another spot either preVisit or postVisit of the dir
     * Let's override the preVisit() on SimpleFileVisitor
     * Ctrl+click on preVisitDirectory() - copy the 3 statements and replace the return to super.preVisitDirectory() sttmnt
     * Looks exactly like visitFile()
     * Let's print Filename on dir - available to us from the () parameter
     * However, it's a little hard to see without proper indentation
     * One advantage of using a FileVisitor implementation is that you can keep track of some state, during the walk
     * Lets do this with tracking the depth-level
     * There are other ways to do this, but let's do this just so that I can have an idea of how you might work with
     *  state , during the walk
     *      - Setup a private variable level of type int - implicitly initialized to 0 cause it's a class field
     *      - Will get incremented everytime we visit a dir and decrement when we leave a dir
     * That means in the preVisitDirectory(), we can increment this level
     *
     *
     * We also need to override the postVisitDirectory - Ctrl+O -select postVisitDirectory()
     * Ctrl+click on postVisitDirectory() - copy the  statements and replace the return to super.postVisitDirectory() sttmnt
     * Is a bit diff because we have an IOException as a 2nd parameter - so we might need to comment the throw exc
     *  on the if statement - so that it doesn't prevent us for some other reasons
     * Decrement level before returning from this ()
     *
     *
     * Finally, include indentation based on the level count on each print for the 3 methods
     *
     * Running this code, prints with some indentation which helps us see the tree structure
     *    - Started printing from the cwd (.)
     *    - Files are printed on the same level as their parent folder
     *          - We are keeping track of the level of the folder not the files
     *          - Adding 1 to the level in our visitFile() will fix this
     *
     *
     * A better use case for this will be suppose you want to get a total number of bytes of a folder/ or the sum of
     * its file sizes and you want each parent's size, to include all of it's children sizes
     * First
     *  - comment out level and every place it has been used in print statements and decrements
     *
     *
     * Then , set up some new fields
     *   1. Initial path and initialize to null
     *   2. A Map keyed by Path with a value of type Long rep the cumulative size of the folder
     *          - used a HashMap since we want to maintain the insertion order & the dir will be printed inorder
     *   3. Use the Path's name count to figure out the level - count is for the initial path's
     *      & all paths will be relative to it
     *
     * Start with preVisitDirectory() since this is the entry point for all accumulations
     *  First
     *    - set initialPath to dir (first directory) if null
     *    - Figure out what the level is from the root or relative path this is by using getNameCount() on dir param
     *    - If it's not null
     *       - calculate the relative indentation level with getNameCount() on the curr dir, minus initialCount field
     *       - If relativeLevel is 1 - clear the map
     *          - allows us to print info to the user, after each sub-folder is visited
     *       - Then initialize the keyed entry to 0 , the key being the path or the dir here
     *          - This ensures that data will go in insertion order
     *
     * visitFile()
     * Add a file size to the parent entry
     * Use merge on the Map where key is the parent of this file because we are accumulating sizes to the parent
     * This () will put a new entry in the map, with the default value specified, 0 in this case, if the key doesn't exist
     * If it exists, then it will execute the function here, adding the size of the current file to the parent's running total
     * The merge() takes a Bi-function
     *  - The 1st arg is the existing value in the map - o for old
     *  - The 2nd arg is the new val - in this case 0, so take the old val and add file size to it
     *
     * This code is tallying up file sizes on the parent folder but these totals are not propagated up, to their parent's yet
     * Let's do this int postVisitDirectory folder
     *
     *
     * postVisitDirectory()
     * First
     *  - Check if the curr dir is equal to the initialPath
     *      - Terminate and will be done with the walk (another value on that FileVisitResult enum)
     *  - CONTINUE would have worked perfectly well, TERMINATE used for demonstration
     * Next
     *  - Set up a local variable for the level of the curr path or dir as done earlier
     *  - If the relativeLevel is 1 - means we are back at a level 1 subfolder of the initialPath
     *    - Print the data collected with a forEach on the map
     *    - Get the level of each subfolder subtracting 1,so we don't indent the 1st level
     *    - Print an indent based on the level, then simple folder name in [] & the cumulative size of the folder or dir
     *      , then repeat the tabs using level and print the file name of the key, the path and the value is the acc size
     *      of that folder or dir
     *
     *  - If the level is not 1,use an else
     *     - Get the folder size for the curr dir
     *     - Do the same thing as we did with the files
     *          - merge the data for this dir parent , adding the folder size here
     *
     * All right, That's It.
     * Let's have fun with this !!!!
     *
     * Running this prints nested folder with their sizes
     *
     * Add a print only of for the first level of summaries
     * Add another field on StatsVisitor class printLevel of type int
     * Generate a custom constructor and select printLevel
     *  - This field will drive the output & since the o/p is in the postVisitDirectory() - will do it there
     *
     *
     * postVisitDirectory()
     * Check if the level is less than the printLevel, and only print if that's true
     * To test this lets go to the main method and pass 1 to the StatsVisitor constructor
     * Running this, we get the directory sizes for the 1st level of sub-folders only
        [.git] - 1,034,367 bytes
        [.idea] - 38,084 bytes
        [files] - 150 bytes
        [out] - 1,632,860 bytes
        [resources] - 316 bytes
        [src] - 1,259,074 bytes
     *
     *
     * If we change cwd from "." to ".." we get the size of the parent folder of the cwd
     *  [Java-Masterclass-17] - 3,965,689 bytes

     */

    private static class StatsVisitor extends SimpleFileVisitor<Path>{

        //private int level;
        private Path initialPath = null;
        private final Map<Path,Long> folderSizes = new LinkedHashMap<>();
        private int initialCount;

        private int printLevel;

        public StatsVisitor(int printLevel) {
            this.printLevel = printLevel;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                throws IOException {
            Objects.requireNonNull(file);
            Objects.requireNonNull(attrs);
            //System.out.println("\t".repeat(level+1) + file.getFileName());
            folderSizes.merge(file.getParent(),0L,(o,n)-> o += attrs.size());
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
                throws IOException {
            Objects.requireNonNull(dir);
            Objects.requireNonNull(attrs);
           // level++;
           // System.out.println("\t".repeat(level) + dir.getFileName());
            if (initialPath == null){
                initialPath = dir;
                initialCount = dir.getNameCount();
            }else {
                int relativeLevel = dir.getNameCount() - initialCount;
                if (relativeLevel == 1){
                    folderSizes.clear();
                }
                folderSizes.put(dir,0L);
            }
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc)
                throws IOException {
            Objects.requireNonNull(dir);
            //  if (exc != null)
            //    throw exc;
            //level--;
            if(dir.equals(initialPath)){
                return FileVisitResult.TERMINATE;
            }
            int relativeLevel = dir.getNameCount() - initialCount;
            if (relativeLevel == 1){
                folderSizes.forEach((key,value)->{
                    int level = key.getNameCount() - initialCount - 1;
                    if (level < printLevel) {
                        System.out.printf("%s[%s] - %,d bytes %n",
                                "\t".repeat(level), key.getFileName(), value);
                    }
                });
            }else{
                long folderSize = folderSizes.get(dir);
                folderSizes.merge(dir.getParent(),0L,(o,n)-> o += folderSize);
            }
            return FileVisitResult.CONTINUE;
        }
    }
}
