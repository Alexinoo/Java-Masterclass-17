package files_input_output.part16_writing_file_challenge;

import files_input_output.part16_writing_file_challenge.student.Course;
import files_input_output.part16_writing_file_challenge.student.Student;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    /* Writing to a file and output to a JSON format
     *
     * To output each student as a JSON record
     *  - You can use write() or writeString() on Files
     *  - Try FileWriter, PrintWriter or BufferedWriter or a combination of these
     * The text printed shd be a list of students
     * The entire set of students shd be contained in [] which is JSON rep for an array of elements
     * Each student shd be enclosed in {}, containing student data in key-value pairs & separated by commas
     * At a minimum, print the student id and some demographic data
     *  - keys are normally enclosed in quotes as are values if they're text based and the colon (:) is used to
     *    separate the key and value
     *
     * Hints
     * .....
     * You might want to explore IntelliJ's template functionality, to create your own JSON string template
     * Don't forget about the StringJoiner class, tht lets you define a delimiter, as well as prefix and suffix
     * Start out by testing 2 or 3 students in your data set
     *
     * */

    public static void main(String[] args) {

        /* Create courses and generate 2 students
         *
         * Change List<Student> to List<String>
         * Limit the size to 2 students first
         * Ue a map operation that will take a student, mapping it to a string , using toJSON()
         * Print each element to the console to start
         *
         * Running this,
         *
         * Use with JSON Linter to test if it's a valid JSON format
         * A Linter is a software dev tool that will analyze source code for potential err and styling issues
         *
         * Copy ad paste the 2nd student and paste in JSON linter
         *  - Validates it and confirms it's a valid JSON
         *
         *
         * Writing to File
         * Use Files.writeString() passing it 1 very large string
         * How do we do this:-
         *  - Update List<String> to String
         *  - Remove toList() and use collect terminalOP
         *  - use Collect.joining() which uses a StringJoiner under the hood
         *      - takes the 1st arg as a delimiter - use a comma
         *      - takes the 2nd arg as a prefix and for this arr of students need to be a "["
         *      - takes the 3rd arg as a suffix - use "]"
         * Print to the console
         *
         * One more change to help with readability of the output
         * Make the delimiter between students to include a line separator
         * Use a local variable named "delimiter" & set that to a "," plus a line separator from the System Class
         * Replace "," and pass the delimiter variable instead
         *
         * Now , Let's write to a file
         * Use writeString() on Files Class passing it a path instance with a file named "students.json" & pass
         *  the students string
         * Surround with try-catch - Catch IOException and throw a RuntimeException
         *
         * Running this with 2 records for testing
         *  - 2 students printed in a console
         *  - Enclosed within [{},{},{}]
         *  - At the end of the 1st student there is a comma
         * This is what the Collectors.joining() did
         * We also have students.json file in the project panel containing the data that was printed in the console
         * Copy the entire contents and paste into JSON linter
         *  - text is formatted & it's a valid JSON for this array of 2 students
         *
         * Change from 2 students to 1000 students
         * Running this prints 1000 students to the console and write to the file "students.json"
         *
         * Generate toJSON with all fields with the shortcut (alt + insert ) - use StringJoiner template
         *  - Course
         *  - CourseEngagement
         *  - Student demographics
         *  - Student - Customize a little bit for courses and engagement map since they are also both collections
         *
         * Running this and selecting the last (1000rd)student from "students.json" and testing with JSON linter
         *  - Prints a valid JSON with both courses enrolled and engagement map nicely formatted
         */

        String delimiter = "," + System.lineSeparator();

        Course jmc = new Course("JMC","Java Masterclass");
        Course pyc = new Course("PYC","Python Masterclass");

        String students =  Stream
                .generate(()-> Student.getRandomStudent(jmc,pyc))
                .limit(1000)
                .map(Student::toJSON)
                .collect(Collectors.joining(delimiter ,"[","]"));
        System.out.println(students);

        try{
            Files.writeString(Path.of("students.json"),students);

        }catch(IOException e){
            throw new RuntimeException(e);
        }


    }
}
