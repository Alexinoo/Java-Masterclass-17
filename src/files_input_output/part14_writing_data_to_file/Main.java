package files_input_output.part14_writing_data_to_file;

import files_input_output.part14_writing_data_to_file.student.Course;
import files_input_output.part14_writing_data_to_file.student.Student;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Main {

    /* Writing data to a File */

    public static void main(String[] args) {

        /*
         * Set up a header that describes which order each data element is printed in - use a textBlock
         * Added a backslash so that a new line won't be included - ensure there are no extra spaces after \ - won't compile
         *
         * Then set up 2 courses
         * Generate a list of 5 random students
         * Next, loop through the random students and get their engagement records
         * Each student will have 2 engagement records, 1 for each course
         *
         * Print the header to the console
         * Loop through each student's engagement records
         * Records are returned as a List<String> and will just print each record
         *
         * Running this prints 10 records for the randomly generated 5 students
         *
         * Now instead of printing to the console, we want to print this to a file
         * Comment on the code that write to the console
         *
         * Writing to a File
         * Start by specifying the path with a filename "students.csv"
         * Call writeString(Path path, CharSequence char ) on Files - pass it path and header text-block
         * Loop through the students list and call write() on Files - which lets you pass an iterable collection
         * Surround with try-catch for checked IOException - print error to the console
         *
         * Running this generates a csv file with only 2 last records of the last student
         * Notice, we are not using try-with-resources {}
         * This is because each of these calls Files.write() and Files.writeString(), opens the file, write to it & close it
         * The file resource doesn't stay open btwn these calls
         * Since we didn't specify to the code what we wanted to happen, Java selected  its default options of writing to a file
         *
         * Default Open Options
         * All available options are found on an enum in the java.nio.file package called StandardOpenOption
         * We have 2 options if we want this data to be printed
         *  - We can pass all the data into a single write()
         *  - Specify a diff set of open options
         * This is because we don't want to truncate existing records each time
         *
         */


        /*
         * 1st Option
         * Specify Open Options to the write() as the 3rd arg
         * Use APPEND constant on StandardOpenOption enum - any text that's written to the file will get appended to eof
         * Running this , and examining the csv file, all data is now printed plus the headers but still would be more
         *  efficient to create a single iterable obj and pass that
         * This would make 1 call to the write(), so that we're not opening and closing a file resource for every student
         */
        String header = """
                Student id, Country code,Enrolled Year,Age,Gender,\
                Experienced, Course Code, Engagement Month,Engagement Year,\
                Engagement Type""";

        Course jmc = new Course("JMC","Java Masterclass");
        Course pyc = new Course("PYC","Python Masterclass");

        List<Student> students =  Stream
                .generate(()->Student.getRandomStudent(jmc,pyc))
                .limit(5)
                .toList();

        //System.out.println(header);
        //students.forEach(s -> s.getEngagementRecords().forEach(System.out::println));

        Path path = Path.of("students.csv");

//        try {
//            Files.writeString(path,header);
//            for (Student student: students ) {
//                Files.write(path, student.getEngagementRecords(), StandardOpenOption.APPEND);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        /*
         * 2nd Option
         * Commenting out on the try-catch {} above
         * Passing all data in a single write
         * Create a single iterable obj and pass that
         * This would make 1 call to the write(), so that we're not opening and closing a file resource for every student
         *
         * Setup a new arrayList called data typed String
         * Add header list to that String
         * Loop through the students and call addAll() - all engagement records will get added to the list
         * Since the data is in one iterable collection, pass it the write() on Files without any open options defined
         * Catch IOException and print error
         *
         * Running this, we get the same results, all the 5 random students, and formatted nicely
         *
         */

        try {
            List<String> data = new ArrayList<>();
            data.add(header);
            for (Student student: students ) {
                data.addAll(student.getEngagementRecords());
            }
                Files.write(path, data);
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
         * When using write() on Files, it's important to understand that doing incremental writes as we did in the 1st
         *  try-catch is very inefficient
         * There are other ()s for incremental writes
         * One of these is a () on Files Class called newBufferedWriter() that returns a BufferedWriter
         *
         * newBufferedWriter()
         * Setup a try-with-resources block and set up a BufferedWriter variable named writer
         * Get a new BufferedWriter instance from a () on the Files Class called newBufferedWriter()
         * newBufferedWriter() takes a path instance & we can pass the filename
         *
         * Start by writing the header
         * Then loop through the students
         * Unlike Files.write() , this class write() does not let us pass an iterable, so will have to loop through
         *  each record individually and write each record
         * Catch IOException and print error info
         *
         * Running this, prints all the data on the same line as the header
         *
         * Fixing this:
         *  - Call newLine() on writer instance after the header
         *  - Copy the statement and paste it below the write() that prints each record
         *
         * Running this now fixes the above and issue & we now get the same results as we had with students.csv
         */
        try(BufferedWriter writer = Files.newBufferedWriter(Path.of("take2.csv"))){
            writer.write(header);
            writer.newLine();
            for (Student student: students) {
                for (var record: student.getEngagementRecords()) {
                    writer.write(record);
                    writer.newLine();
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }


    }
}
