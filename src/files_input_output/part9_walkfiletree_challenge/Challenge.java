package files_input_output.part9_walkfiletree_challenge;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class Challenge {
    /*
     * In addition to summing up directory sizes, summarize the number of files in a dir
     * For a bonus, include the summary of the number of sub-folders
     * These numbers should include nested files or folders
     *
     * Hint
     * Instead of a map value being a Long, use a nested Map
     */

    public static void main(String[] args) {
        Path startingPath = Path.of(".");

        /* Update from 1 level deep to Integer.MAX_VALUE */
        FileVisitor<Path> statsVisitor = new StatsVisitor(Integer.MAX_VALUE);
        try {
            Files.walkFileTree(startingPath,statsVisitor);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*
     * Change the type of the field from Long to a nested Map keyed by String and Long
     * The nested Map is going to hold
     *  - file size summary in bytes,
     *  - count of all files
     *  - count of all folders
     * These changes results to compile errors everywhere this code is referencing that map
     *
     * Set up a couple of constants i.e. Keys into this map
     *  DIR_CNT - key for the count of dir or sub-folders
     *  FILE_SIZE - accumulated file size - sum of all files in the folders
     *  FILE_CNT - number of files in the folders
     *
     *
     * preVisitDirectory()
     * Start here since this is the first point of contact, into the walk
     * folderSizes.put(dir,0L) - created a map entry for every single dir
     * Update folderSizes.put(dir,0L); to  folderSizes.put(dir,new HashMap<>());
     *
     * visitFile()
     * Remove "folderSizes.merge(file.getParent(),0L,(o,n)-> o += attrs.size());"
     * Replace that with code to populate the nested map instead
     * Start by getting the parent folder's map for the curr file's parent
     * It's possible that the curr file's parent might be the original path and that's not part
     *  of my map - so check for null
     * Remember, we are purposely clearing the map at the start of each subfolder of the original path
     *  to give feedback to the user
     * File attributes are available as a () arg & we can get the size from there
     * Merge the file size, if it's there, just put the FILE_SIZE there, if the record isn't new, add the file
     *  size to the curr data and since that will be the new value, we can just use n in the expression
     * Do something similar for the FILE_CNT, setting the value to 1L, if the record didn't exist, or adding this
     *  value in the map - use method reference with a () on Math class addExact
     *
     *
     * For good measure, will override visitFileFailed() and insert this after the visitFile() declaration
     * Ctr+click visitFileFailed and copy the body and replace it with return statement
     * Print exception name since they are named like AccessDeniedException,FileSystemLoopException to the user
     *  and often offer enough info without a full stack trace
     * Finally, we don't wanna stop the walk and will return CONTINUE
     *
     * Now our StatsVisitor Class does not need to be a subclass of SimpleFileVisitor anymore, since we have implemented
     *  custom implementations for every ()
     * Change it to implement FileVisitor Interface with Path as the Type
     *
     * Next, need to change one () from postVisitDirectory()
     * This () is going to roll the data up, to the parent levels
     * Start with the else{} and remove the 2 statements
     *      long folderSize = folderSizes.get(dir);
            folderSizes.merge(dir.getParent(),0L,(o,n)-> o += folderSize);
     * First, get the parentMap data again from the folderSizes map using dir.getParent()
     * Next, get the childMap from folderSize map using dir
     * Set up some local variables for the 3 fields that we will add to the parent's summary fields
     *  - folderCount - use getOrDefault() on childMap and pass DIR_CNT as 1st arg and 0L as 2nd arg, in the case of a dir
     *                  didn't have a sub-folder in it, so return 0L
     *  - fileSize - Do something similar for fileSize
     *  - fileCount - Do something similar for fileCount too
     * Next, merge this data into the parent's mapped values
     *  - For folderCount , include an extra increment by 1 so that the curr dir is added to the parent's summary
     *  - For the other fields, just insert them if these entries don't exist or add it to the existing value
     *
     * Next, Let's change how this dtat gets printed out and move up to the if clause
     * Remove souf but do something similar but setup a local variable for the size
     * We can get size from the entry being iterated on and if file size isn't in the nested map, return 0L
     * The print statement will start like the previous one with an
     *      - indent level,
     *      - path name - use key.getFileName()
     *      - no of bytes
     *      - no of files and folders
     *      - Tabs will be based on the level ".".repeat(level)
     *      - last part of the path name
     *      - size
     *      - file count
     *      - dir count
     * Ok, That's it and the code compiles and we should be able to run this for the cwd
     * Prints info about this cwd, size, folders and files count
     * One thing that makes walkFileTree kind of nice, is that you can catch the exception and ignore it and continue
     *  working which is harder to do when you're working with streams
     *
     *
     */

    private static class StatsVisitor implements FileVisitor<Path>{
        private static final String DIR_CNT = "DirCount";
        private static final String FILE_SIZE = "fileSize";
        private static final String FILE_CNT = "fileCount";

        private Path initialPath = null;
        private final Map<Path,Map<String,Long>> folderSizes = new LinkedHashMap<>();
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
            var parentMap = folderSizes.get(file.getParent());
            if(parentMap != null){
                long fileSize = attrs.size();
                parentMap.merge(FILE_SIZE,fileSize,(o,n)-> o += n);
                parentMap.merge(FILE_CNT,1L,Math::addExact);

            }
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc)
                throws IOException {
            Objects.requireNonNull(file);
            if(exc != null){
                System.out.println(exc.getClass().getSimpleName() + " "+ file);
            }
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
                throws IOException {
            Objects.requireNonNull(dir);
            Objects.requireNonNull(attrs);
            if (initialPath == null){
                initialPath = dir;
                initialCount = dir.getNameCount();
            }else {
                int relativeLevel = dir.getNameCount() - initialCount;
                if (relativeLevel == 1){
                    folderSizes.clear();
                }
                folderSizes.put(dir,new HashMap<>());
            }
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc)
                throws IOException {
            Objects.requireNonNull(dir);
            if(dir.equals(initialPath)){
                return FileVisitResult.TERMINATE;
            }
            int relativeLevel = dir.getNameCount() - initialCount;
            if (relativeLevel == 1){
                folderSizes.forEach((key,value)->{
                    int level = key.getNameCount() - initialCount - 1;
                    if (level < printLevel) {
                        long size = value.getOrDefault(FILE_SIZE,0L);
                        System.out.printf("%s[%s] - %,d bytes, %d files, %d folders %n",
                                "\t".repeat(level), key.getFileName(), size,
                                value.getOrDefault(FILE_CNT,0L),
                                value.getOrDefault(DIR_CNT,0L) );
                    }
                });
            }else{
                var parentMap = folderSizes.get(dir.getParent());
                var childMap = folderSizes.get(dir);
                long folderCount = childMap.getOrDefault(DIR_CNT,0L);
                long fileSize = childMap.getOrDefault(FILE_SIZE,0L);
                long fileCount= childMap.getOrDefault(FILE_CNT,0L);

                parentMap.merge(DIR_CNT,folderCount + 1,(o,n)-> o += n);
                parentMap.merge(FILE_SIZE,fileCount,Math::addExact);
                parentMap.merge(FILE_CNT,fileCount,Math::addExact);

            }
            return FileVisitResult.CONTINUE;
        }
    }
}
