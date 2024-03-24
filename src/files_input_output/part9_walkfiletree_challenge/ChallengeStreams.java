package files_input_output.part9_walkfiletree_challenge;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.stream.Collectors;

public class ChallengeStreams {

    /*
     * An example that doesn't propagate or roll up the data to the parent's level but simply aggregates it,
     *  at the level you chose
     */
    public static void main(String[] args) {

        /*
         * Add starting path - parent folder of the cwd
         * Get the starting path's index count
         * All the dir will be relative to this
         * Use try-with-resources statement & use Files.walk() passing startingPath & Integer.MAX_VALUE for depth
         * Start the streams pipeline starting with the source paths
         *  - Filter files and not dir(s) - we are only going to sum up the file size and get the file count with built
         *    in features of the stream pipelines
         *  - Use collect() terminal operation with groupingBy()
         *      - group with the first relative path name - so use subpath with index of the original path's no of
         *        parts and add 1 to that, so this folder will just have a single part of the path name
         * In other words, data will be grouped by the first level of sub-folders
         *  - Next, call summarizeLong for summary statistics on the field that we want
         *      - If we use file size, this data will give us both sum,count and other stats like avg etc
         *      - lambda exprssn will return the size using Files.size
         *          - use try-catch to handle IOException
         * Append forEach to the result, a map with paths  and LongSummaryStatistics
         *
         * Add catch clause for IOExceptions & print the stack trace out
         *
         * OK, That's it..Running that
         * Prints
         *      "[Java-Masterclass-17] - 3,998,053 bytes, 3575 files" - which is actually correct
         * Notice, these are not ordered because the resulting map is a HashMap
         *
         * We can quickly change that by piping the map entries to a stream
         * Will call entrySet on the first stream's resulting map and follow it to a stream()
         * Then sort using Comparator on the key and converting it to a String
         *
         * We have to change forEach now , by passing it the entry rather than key and value
         *
         * Lastly, remove the try-catch block and replace with p.toFile().length() which gives the same data and size
         * Running this gives us similar results
         *  "[Java-Masterclass-17] - 3,999,650 bytes, 3575 files"
         */
        Path startingPath = Path.of("..");
        int index = startingPath.getNameCount();
        try(var paths = Files.walk(startingPath,Integer.MAX_VALUE)){
            paths
                    .filter(Files::isRegularFile)
                    .collect(Collectors.groupingBy(p -> p.subpath(index , index +1),
                            Collectors.summarizingLong(p -> p.toFile().length()
//                                    {
//                                try{
//                                    return Files.size(p);
//                                }catch(IOException e){
//                                    throw new RuntimeException(e);
//                                }}
                            )
                            ))
                    .entrySet()
                    .stream()
                    .sorted(Comparator.comparing(e -> e.getKey().toString()))
                    .forEach(e->{
                        System.out.printf("[%s] - %,d bytes, %d files %n",
                                e.getKey(),e.getValue().getSum(),e.getValue().getCount());
                    });

        }catch(IOException e){
           e.printStackTrace();
        }
    }
}
