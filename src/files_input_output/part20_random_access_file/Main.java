package files_input_output.part20_random_access_file;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

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
     *
     *
     * ///// PART 2 /////////////
     * Comment out on building - need to build this once & now we can write the client code that mak use of it
     * Start by writing code to load the indexed data first
     *
     *
     * Open a RAC passing it the name "studentDat" with a "r" read-only access mode
     * Call loadIndex passing the rac instance and 0 as the starting index
     *
     * To make this more interesting, we'll use a scanner so that the user can request a record by inputting a
     *  student id
     * Set a scanner and prompt to enter student id
     * use a while loop that uses hasNext() that returns a boolean and keep getting the input until the user enters 0
     * use nextLine() and pass it to Long.parseLong() to get the student id they entered
     * If id < 1 quit out of the loop
     * Otherwise, we'll get the file position from the map and use seek() to go directly to that point in file
     * We can use readUTF() to read the record and print it to the console
     * Then prompt for the next id
     *
     * Running this :-
     *  - Prints 1000 which is the no of records found in the index - so the code was able to read that accurately
     *    from the .dat file
     *  - Also prompted to enter student & we are able to get the details for the student id entered
     * Therefore, we've been able to load the index data, caching only that index in memory
     * We can retrieve records in from the file on as needed basis using a file pointer to do it
     *
     * Storing the index as the first part of a data file, is 1 way to provide this info to your users
     *
     * The second way is to store the index as a separate file altogether
     *
     *
     * After updating BuildStudentData.java..
     * Go to the main() and uncomment the line that is going to build the file
     *  - update from studentData to student so that the file names now will be "student.dat" and "student.idx"
     *  - pass true as the second parameter
     *
     * Running this :-
     *  - outputs both "student.dat" and "student.idx" files
     *
     * Comment out the line to build this data
     * Will change the way we load data by creating a static initializer to load the index
     * This is a block of code at the class level that starts with the keyword static
     * Create a new RAC instance which will read student.idx with a read-only mode here
     * Then call loadIndex() passing it the RAC instance and 0 as the starting position
     * Catch IOException
     *
     *
     * THen go to the main() and change "studentData.dat" to "student.dat"
     * Then remove the loadIndex()
     * It's much better to load the indices once, in a static initializer, rather than any time this () is executed
     *
     * Running this:-
     *  - Again we can see that the loadIndex found 1000 records
     *  - the app found the record with the id that we typed - 777 , 555 etc
     * This time, the index was built in it's own file and the record data is in a 2nd file
     *
     * This is a mini database now.
     *
     * Whether you include, the index at the start or at the end of the records data file, or you maintain it at a
     *  separate file is really upto you
     *
     * In this example, we've used the RAC to create a binary data file that contains an indexed mechanism to locate
     *  data records
     * We've also used the RAC to use this indexed binary data to read data
     *
     * These are only a couple of use cases, for this type of class
     *
     * We didn't have to cache all the records, just the index
     *
     * Another reason to use a RAC, would be for targeted data modifications, of a large file
     *
     * e.g. we could have found the record for student 777 and modified it
     *
     * RAC are also commonly used for binary data storage as you've seen
     */

    private static final Map<Long,Long> indexedIds = new LinkedHashMap<>();
    private static int recordsInFile = 0;

    static {
        try(RandomAccessFile rac = new RandomAccessFile("student.idx","r")){
            loadIndex(rac , 0);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) {

        //BuildStudentData.build("student",true);
        try(RandomAccessFile rac = new RandomAccessFile("student.dat","r")){

            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter a Student Id or 0 to quit");
            while (scanner.hasNext()){
                long studentId = Long.parseLong(scanner.nextLine());
                if (studentId < 1)
                    break;
                rac.seek(indexedIds.get(studentId));
                String targetedRecord = rac.readUTF();
                System.out.println(targetedRecord);
                System.out.println("Enter another Student Id or 0 to quit");
            }

        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    /*
     * Set loadIndex(RAC rac) that takes RAC instance and an index position to start reading data
     * Seek to the starting position - which is going to be 0
     * Call readInt() to get the 1st data element, the count of records in the file and assign it to recordsInFile
     * Print to the console
     * use recordsInFile in a loop to determine how many times the id should be read and the file position
     * Populate the map using readLong() to get the key & a 2nd readLong() to get the stored file position
     * Get back to the main() and set this () up.
     */
    private static void loadIndex(RandomAccessFile rac, int indexPosition){

        try {
            rac.seek(indexPosition);
            recordsInFile = rac.readInt();
            System.out.println(recordsInFile);
            for (int i = 0; i < recordsInFile; i++) {
                indexedIds.put(rac.readLong() , rac.readLong());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}


















