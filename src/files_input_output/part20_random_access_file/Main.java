package files_input_output.part20_random_access_file;

import java.util.LinkedHashMap;
import java.util.Map;

public class Main {

    /*
     * RandomAccessFile
     * This class provides the ability to directly access and modify data at any specific location within the file
     * A random access file behave like a large array of bytes, stored in a file system
     * There's a kind of cursor, or index into the implied array , called the file pointer
     * A RAC both reads and writes binary data, using special ()s, which keep track of how many byes will be
     *  read or written
     * This class can be used for both read and write operations
     *
     * RAC File Pointer
     * When you open a RAC, a file pointer is at 0, or at the start of the file
     * To move the file pointer, you execute a () on the file called seek(Long val), passing iy a long value, rep a
     *  position in the file you want to go
     * To get the file pointer, you execute getFilePointer()
     * Depending on the type of read or write ()s you're using, the file pointer will move a certain no of bytes
     *  when these operations complete
     *
     * Still Confused about why you would use this ?
     * Suppose you have a file with millions of records and at any one time,you really need to access about 50 of those
     * Instead of loading a million of records into memory,you can load a simple array or small map which will tell you
     *  how to locate records of interest in the big file
     * You wouldn't want to start reading from the beginning of the file and read 10m records, checking each one to see
     *  if it's a match
     * The RAC lets you fast-forward or backward, to a position in the file, using seek()
     * From this position, you can read in only the data that matters, for your app
     * To do this though, you need to understand how many records are in your file, what its record length is
     *  and how you want to identify each record, to retrieve it
     *
     * Understanding the RAC's index
     * A RAC needs an index, which houses a file pointer, to each record of interest
     * This index could be implied for a file with fixed width records, if you only need to get data by a row number
     * This means it's very easy to do a little math to get the 10 thousandth record when all the records are 250 char(s)
     *  in length
     * 10000 * 250 will point you to the 10000th record in your file
     *
     *
     * We'll use students.json as the source of the data file we create using RAC
     *
     * Start with a couple of private static variables
     *  - A Map keyed by a Long rep the record Id and a Long value rep the starting file pointer position of the stored
     *     record in the file
     *  - An int recordsInFile - keeps track of how many records are in the file
     *
     *
     * Next create a separate class to build the data file and index named BuildStudentData
     *
     * Test this by calling the build() and we want our file name to be fixed with studentData
     */

    private static final Map<Long,Long> indexedIds = new LinkedHashMap<>();
    private static int recordsInFile = 0;
    public static void main(String[] args) {
        BuildStudentData.build("studentData");
    }
}
