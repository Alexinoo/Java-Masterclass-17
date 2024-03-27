package files_input_output.part15_more_writing_methods.student;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public class Main {

    /*
     * BufferedWriter, FileWriter and PrintWriter
     *
     * We have looked at Files.write() and Files.writeString()
     * Each execution of these ()s is an isolated call,which both opens & closes the file resource
     * These ()s can be used to write data to a log file but aren't ideal for writing a lot of records to an output file in an iterable fashion
     *
     */

    public static void main(String[] args) {

        String header = """
                Student id, Country code,Enrolled Year,Age,Gender,\
                Experienced, Course Code, Engagement Month,Engagement Year,\
                Engagement Type""";

        Course jmc = new Course("JMC","Java Masterclass");
        Course pyc = new Course("PYC","Python Masterclass");

        List<Student> students =  Stream
                .generate(()-> Student.getRandomStudent(jmc,pyc))
                .limit(25)
                .toList();

        /*
         *
         * Let's look at the BufferedWriter
         *
         *
         * Add a counter variable that gets incremented after each write
         * Use % operator - so whenever the count is divisible by 5, I will pause the application for 2 sec - sleep for 2 sec
         *  - Call sleep(2000) on Thread class - is a way to make your app pause a bit
         *  - takes milliseconds 2000 here means 2 sec after every 5 records
         *  - print the dot, so we can watch the status
         *  - Thread.sleep(2000) - throws an InterruptedException, a checked expression - add a try-catch block
         *
         * Execute the code, while the take2.csv is open
         * There's a key combination that will reload all files from disk to help us monitor this file from intelliJ
         *  - Windows - Ctrl Alt Y
         * After 10 dots, the application ends and now we can see all the records in the file
         * The buffer was big enough to contain engagement records
         * Flush the buffer manually - using the modulo operator - flush the buffer after every 10s
         *
         * Flush is just a () on any writer class
         *
         * Running this,
         *  - The text that was there gets truncated
         *  - After 2 dots, we get 10 records for the 1st 5 students
         *  - After 4 dots , we'll get 20 records displayed and so on
         * This demonstrates that the buffer is flushed after every 10 records versus just allowing the BufferedWriter Class to manage ot for us
         *
         */

        try(BufferedWriter writer = Files.newBufferedWriter(Path.of("take2.csv"))){
            writer.write(header);
            writer.newLine();
            int count = 0;
            for (Student student: students) {
                for (var record: student.getEngagementRecords()) {
                    writer.write(record);
                    writer.newLine();
                    count++;
                    if (count % 5 == 0){
                        Thread.sleep(2000);
                        System.out.print(".");
                    }
                    if (count % 10 == 0){
                        writer.flush();
                    }
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        /*
         * Let's look at the FileWriter
         * Copy try-catch block above and paste below
         * Update BufferedWriter to FileWriter
         *  - Create a new instance of FileWriter and pass the file name
         *  - Remove newLine() since FileWriter doesn't have newLine()
         *
         * Running this prints all data in one line. So how do we add a new line using FileWriter.?
         *  - We can call lineSeparator() on the System class and pass to write()
         *  - Do the same thing in the loop
         *
         * Running this, prints the same results as with BufferedWriter above
         *
         */

        try(FileWriter writer = new FileWriter("take3.csv")){
            writer.write(header);
            writer.write(System.lineSeparator());
            for (Student student: students) {
                for (var record: student.getEngagementRecords()) {
                    writer.write(record);
                    writer.write(System.lineSeparator());
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }

        /*
         * Let's look at the PrintWriter
         * Copy try-catch block above and paste below
         * Update FileWriter to PrintWriter both in the variable declaration and instantiation
         * Update filename to take4.csv
         * This compiles but instead of using the write(), we'll use a () on PrintWriter Class named println
         * We have kept write() on header - means you can use either of these and mix and match if you want
         *
         * Running this, doesn't have a line separator after the header because we are using write & not println
         * That is 1 advantage to using println - we don't have to include another statement, or include the new line in our string that is output
         * Updating writer.write(header); writer.println(header);
         *
         * Running this fixes, the issue above
         *
         */

        try(PrintWriter writer = new PrintWriter("take4.csv")){
            writer.println(header);
            for (Student student: students) {
                for (var record: student.getEngagementRecords()) {
                    writer.println(record);
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }

        /*
         * Use PrintWriter to write data to a fixed length file
         * Copy code for PrintWriter (above) and change the filename to take4.txt
         * Remove  writer.println(record); and split each record by commas - call split on each record
         * Call printf() on writer and start with a formatted string specifying diff widths for the 1st 6-columns
         *  - specify the width of each field
         *  - if left justified, include a - before the width - Many fixed width files left justify text and right justify numbers
         * Instead of using the string tokens, use obj by calling get()s on student
         *  - get student id
         *  - get country code
         *  - get enrollment year
         *  - get enrollment month
         *  - get age
         *  - get gender
         * We can use 1 printf statement or a series of them
         * Start another and print with just one specifier for the experienced flag
         *  - Should print Y or N depending with the hasExperience()
         * Use format() on writer as an alternative to printf
         *  - format the delimited data, for the engagement record info
         * You can use printf() or format() interchangeably
         *  - use recordData array with index
         * Finally, call println() without arg to print line separator - make suse of the System.lineseparator()
         *
         * Running this, all the data is lined up in fixed width columns
         *
         * Means you can use PrintWriter, if you need more flexibility as you print
         *
         * Ctrl+c click on PrintWrite class for more info
         * It has an overloaded constructor that's a boolean and is called autoFlush which is set to false
         *
         * Flush
         * ........
         *
         * For reading files, a buffer is a temporary storage to reduce the no of disk reads
         * Data is read in larger chunks and stored in the buffer
         *
         * For writing files, something similar occurs, so there's temporary storage, that gets filled us as writes are executed, on a Writer class
         * Physical writes to disk happen when the buffer is flushed
         * In other words, not every write  or print statement you execute is going to be physically written, until the buffer is flushed
         *
         * It it the process of taking the text stored in the buffer, and writing it to the output file, and clearing the buffer's cache
         * The buffer is always flushed when a file is closed
         * You can manually flush a buffer by calling the flush()
         * You might want to do this meaning frequently, when working with time-sensitive data
         * Any other thread or process that is reading the file, won't be able to see the buffered text, until the flush occurs
         *
         * Update limit size from 5 - 25
         *
         * Let's go to the BufferedWriter block
         */

        try(PrintWriter writer = new PrintWriter("take4.txt")){
            writer.println(header);
            for (Student student: students) {
                for (var record: student.getEngagementRecords()) {
                   String[] recordData = record.split(",");
                   writer.print("%-12d%-5s%2d%4d%3d%-1s".formatted(
                           student.getStudentId(),
                           student.getCountry(),
                           student.getEnrollmentYear(),
                           student.getEnrollmentMonth(),
                           student.getEnrollmentAge(),
                           student.getGender()
                   ));
                   writer.printf("%-1s",(student.hasExperience() ?  'Y':'N'));
                   writer.format("%-3s%10.2f%-10s%-4s%-30s",
                           recordData[7],
                           student.getPercentComplete(recordData[7]),
                           recordData[8],
                           recordData[9],
                           recordData[10]);
                   writer.println();
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }

    }
}
