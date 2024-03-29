package files_input_output.part20_random_access_file;

/*
 * This class will have a build() that takes 1 arg , the data file name which will
 *  just be the prefix of the file name
 * Create a path to the students.json file
 * Create a file name from the arg passed and concatenate the ".dat" extension
 * Create a Map same as the main class mapped by an id and the mapped value will be the pos in the file
 *  - used a Map to keep the order in which elements are inserted
 *
 * Delete if the file exists - deleteIfExists requires a try-catch {}
 * Read the entire content of "students.json"
 * Remove both opening and closing brackets using replaceFirst with a regex
 *  - 1st replaceFirst - removes the 1st opening bracket "[" at the start of the entire string
 *  - 2nd replaceFirst - removes the 1st closing bracket "]" it finds at the end of the entire string
 * Split the record by the line separator
 * Then print the no of records to the console
 *
 * Then write the code to derive the starting position - is the position where we will start outputting the records
 * This start position is going to be (16L * recordCount ) + 4 bytes
 * To create the index, we need to extract the student id from each record
 *  - Will do this with a regex and the Matcher class "studentId\":([0-9]+)"
 *
 * Use a try-block and create a new instance of the try block
 * RAC constructor takes 2 args
 *  - file name
 *  - mode that you want to open this file "rw" means read-write , "r" means read-only
 * Call seek() on the RAF, leaving enough space to print the indexed data later
 * Loop through the records from the json file
 *  - Match each record to the pattern
 *  - Look for the first match and if we find a match, we can get the student id from group 1
 *  - Add this id as the id to the index map & the current file pointer as the position of the record in the data file
 *  - Use writeUTF to print the record to the file
 *
 * The above code only rep step 1 from the slide
 *
 *
 */

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BuildStudentData {

    public static void build(String datFileName){

        Path studentJson = Path.of("students.json");
        String dataFile = datFileName + ".dat";
        Map<Long,Long> indexedIds = new LinkedHashMap<>();

        try {
            Files.deleteIfExists(Path.of(dataFile));
            String data = Files.readString(studentJson);
            data = data.replaceFirst("^(\\[)","")
                    .replaceFirst("(\\])$","");

            var records = data.split(System.lineSeparator());
            System.out.println("# of records = "+records.length);

            long startingPos = 4 + (16L * records.length);
            Pattern idPattern = Pattern.compile("studentId\":([0-9]+)");

            try(RandomAccessFile raf = new RandomAccessFile(dataFile,"rw")){
                raf.seek(startingPos);
                for (String record: records ) {
                    Matcher m = idPattern.matcher(record);
                    if (m.find()){
                        long id = Long.parseLong(m.group(1));
                        indexedIds.put(id,raf.getFilePointer());
                        raf.writeUTF(record);
                    }
                }
                writeIndex(raf , indexedIds);
            }catch (IOException e) {
                throw new RuntimeException(e);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /*
     * Step 2- Printing the record count and the index data
     * Use writeIndex() - returns void and takes an RAC to be written to and the index map as args
     * wrap with try-catch
     * Initialize the start position to start at 0
     * Write the size of the map which should be equal to the no of records
     * Then loop through the indexMap entries
     *  - write the key and the value as longs - each write taking up 8 bytes
     *
     * Then call writeIndex () after the for loop in the build () above
     */
    private static void writeIndex(RandomAccessFile ra, Map<Long,Long> indexMap){
        try {
            ra.seek(0);
            ra.writeInt(indexMap.size());
            for (var studentIdx :indexMap.entrySet() ) {
                ra.writeLong(studentIdx.getKey());
                ra.writeLong(studentIdx.getValue());
            }
        }catch (IOException e){
            throw new RuntimeException(e);
        }

    }
}
