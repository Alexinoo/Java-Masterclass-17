package files_input_output.part18_manage_files_2;

import java.io.*;

public class TransferTo {

    /* transferTo()
     *
     * Now let's explore a () on both the Reader and the InputStream interfaces called transferTo()
     * This () was added to InputStream in Jdk 9 and to the Reader interface in JDK 10
     *
     * Set it up with a BufferedReader to the students-activity.json which is in the copied_files and pass a
     *  new FileReader to the BufferedReader
     * Also create a new writer, a PrintWriter which lets us pass a string to it, and will create a BufferedWriter under the hood
     * Pass our desired file as the output file "students-backup.json" to PrintWriter() constructor
     *
     * Then inside the try block, call transferTo() and pass writer instance
     *
     * Running this, and examining the project panel, we see "students-backup.json" outputted and upon opening, we see we have successfully
     *  made a copy of the json file
     *
     * In essence, we have used reader.transferTo() to do what the Files.copy() did
     *
     * So which is better ? - Well, while working with Files, you will probably want to stick with Files.copy() which takes advantage of
     *  the File System provider to do the work as efficiently as possible
     *
     * However, reader.transferTo() might be more efficient esp if a file is being copied across diff network drives
     *
     * Where the transferTo() really shines though, is when our of your input streams, differs from the output stream type
     */

    public static void main(String[] args) {

        try(BufferedReader reader = new BufferedReader(
                new FileReader("copied_files//students-activity.json"));
            PrintWriter writer = new PrintWriter("students-backup.json")
        ) {

            reader.transferTo(writer);
            System.out.println("Contents transferred successfully");

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}

