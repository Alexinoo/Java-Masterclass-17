package files_input_output.part10_reading_files;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    /* Reading Files*/

    public static void main(String[] args) {
        /*
         * FileReader
         * Used to read text files & it reads data by default - one integer at a time
         * An integer is 4 bytes in Java
         * To read all data in the file set up a for loop populating the data variable by assigning it the result of
         *  reader.read()
         * read() is a static () on the FileReader class and this will run as long as data isn't -1;
         *  - takes no args, but there is an overloaded version of this that takes char[]
         * -1 means it's the end of the file
         * Print the data for each read
         *
         * Catch IOException and print Stack trace
         *
         * Running this prints integers below between the values 48 and 57
         *      "49 50 51 52 53 54 55 56 57 49 48"
         * Remember that a character in Java is represented by unsigned integer.
         * IF we want to see the character value, we can use the character wrapper to pass the int, or cast data to char
         * Running this prints our actual encoded data using a default character set
         *      "1 2 3 4 5 6 7 8 9 1 0"
         * This is a tedious way to read data from a file,& can be expensive if each call to read() was a disk read
         *
         * Disk read means something is physically,or mechanically,occurring on your hard disk to read that character
         *  from the file
         * This is expensive and Java provides ways to reduce the no of disk reads being done
         * It would be a lot more efficient to read many characters at a time which reduces the no of disk reads & make
         *  processing the info we get back a lot easier
         * In truth, FIleReader actually does some of this.
         * It has a default buffer size meaning it reads a certain no of characters into a memory space called buffer
         * A file buffer is a computer memory temporarily used to hold data, while it's being read from a file
         * It's primary purpose is to improve the efficiency of data transfer and processing
         * Reduces the no of direct interactions, or disk reads, against the actual storage device
         * We can't override this buffer size, with the FileReader class, will be discussing another class that can set
         *  the buffer size to something larger than the default
         * You can read more than 1 character at a time, and avoid the cast by passing a char[] to read()
         *
         * Add a char[] block with a size of 1000 characters
         * Then pass this array to read()
         * Create a String by passing it the block, and the starting character and ending index
         * Even though we specified 2000 chars, less might be read in, if we read the eof
         * Replace sout with souf
         * Running this gets all the numbers printed once
         *      "---> [11 chars] 12345678910"
         *
         * Change file.txt content with some large dummy data
         * Running this now explains that this text was retrieved with only 2 reads
         *  - the 1st returned 1000 chars
         *  - the 2nd returned the remaining 648 chars
         *
         *
         * Let's talk about how a FileReader actually works..
         * Readers read chars as seen from the ()s on the abstract parent class [ Reader Class ]
         * A InputStreamReader is a bridge, from byte streams to chars streams
         * If you want to read a char stream, it's recommended you use a FileReader vs an InputStreamReader
         * A FileReader is doing buffered reading , so it's doing a hard disk read, for a certain amount of chars &
         *  storing them in memory
         *
         * A BufferedReader will also do buffered reading, using a much larger buffer size than the FIleReader
         * You can modify the size of the buffer on BufferedReader by passing it to the constructor
         * Java states that the default buffer size is large enough for most purposes
         * The BufferedReader also provides convenient ()s for reading lines of text
         *
         * Reviewed these classes so that we get more familiar with terms such as binary data vs character data,
         * input streams and readers, as well as buffers, and disk reads.
         *
         *
         * InputStreams
         * ............
         * An InputStream is an abstract class rep an input stream of bytes
         * It rep a source of data and a common interface for reading that data
         * Input Streams can return a byte stream or a char stream
         * One Input stream we are familiar with is System.in
         *
         * For files, the implementation is FileInputStream
         * This class is used for files containing binary data
         * Using read() on a FileInputStream iks very inefficient, so if you're going to use FileInputStream,
         *  you'll want to wrap it in a BufferedInputStream
         * Notice that almost all the read() return byte[] or accept a byte[] as parameter
         *
         * */
        try(FileReader reader = new FileReader("file.txt")){
            char[] block = new char[1000];
            int data;
            while( (data = reader.read(block)) != -1){
                String content = new String(block,0,data);
                System.out.printf("---> [%d chars] %s%n",data,content);
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        /*
         * Look at an example of a BufferedReader and then move into simpler ways to read data from a file
         * To use a BufferedReader, you usually wrap a FileReader with it
         * i.e You pass a FileReader to the constructor of the BufferedReader
         * Wrap in a try-with-resources statement
         * Create BufferedReader variable and pass a new instance of FileReader with a String literal "file.text"
         * Catch the IOException and print the stack trace if we get an error
         * With a FileReader, you read data either by int or char[]
         * What makes BufferedReader nice, besides being more efficient is that it gives us ()s to read the data by
         *  lines
         * Set up line variable a String
         * Use a while loop as before but quit the while loop if null comes back from that
         * Print the line of the data we get
         *
         * Running this : We get each line printed as is from the file.txt
         *

         */
        System.out.println("_".repeat(50));
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader("file.txt"))){
            String line;
            while ((line = bufferedReader.readLine()) != null){
                System.out.println(line);
            }
        }catch (IOException e){
            e.printStackTrace();
        }

        /*
         * It gets even better than that as of JDK-8, another () was added which gives us a source of lines, for a
         *  stream pipeline
         * Copied the code above
         * Remove local variable as well as the while loop
         * Replace that with a single statement
         * Will call the lines() from bufferedReader instance and immediately call the terminal operation forEach
         * and print each line
         *
         * Running that we get the same results as before
         *
         * What's nice though is that we now have all the stream operations at your disposal to query,filter,transform
         *  and slide and dice the file data, line by line
         */
        System.out.println("_".repeat(50));
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader("file.txt"))){
            bufferedReader.lines().forEach(System.out::println);
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
