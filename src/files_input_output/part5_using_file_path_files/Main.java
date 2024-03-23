package files_input_output.part5_using_file_path_files;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        useFile("testFile.txt");

        System.out.println("_".repeat(50));
        usePath("pathFile.txt");
    }

    /* Working with File Class
     * .......................
     * Create a private static () that takes a string and does not return anything
     * Create a new variable named file and get a new instance via the single arg constructor on the File class
     * that takes a string, so passing it filename
     * Create a boolean, fileExists and assign it the result of calling exists() on the file instance
     * Print the file name and whether it exists or not
     *
     * If the file exists, delete and recreate afresh
     * Check fileExists is true and if that's the case, delete it
     * Set fileExists to the opposite of file.delete() since it returns a boolean
     *  i.e. If the file can't be deleted, we get a false back from this delete() and set fileExists to true
     * Will use this flag later to determine whether to continue
     *
     * IF the file doesn't exist, create a new file via createNewFile()
     * createNewFile() returns a boolean but to keep it simple we are going to ignore it
     * Also check if the file can be written and if so print something
     *
     * The file() itself doesn't have any ()s to write to a file resource
     * However, intelliJ suggests that file.createNewFile() might throw an exception and suggests we add
     *  try-catch block
     * Print something in the catch-block instead of throwing RuntimeException
     *
     * Call useFile from the main() passing it a String literal rep a filename - "testFile.txt"
     *
     * Running this works.Create a file if it doesn't exist and delete if it exists by recreating it again
     * We used an instance of File to test some conditions in the file system as well as create & delete a file
     */
    private static void useFile(String fileName) {
        File file = new File(fileName);
        boolean fileExists = file.exists();
        System.out.printf("File '%s' %s%n", fileName, fileExists ? " exists." : " does not exist.");

        if (fileExists) {
            System.out.println("Deleting File: " + fileName);
            fileExists = !file.delete();
        }

        if (!fileExists) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                //throw new RuntimeException(e);
                System.out.println("Something went wrong");
            }
            System.out.println("Created File : " + fileName);
            if (file.canWrite()) {
                System.out.println("Would write to file here");
            }
        }
    }

    /*
     * Working with Path type - using static() on Files class
     * ......................................................
     * Create a Path variable named path
     * JDK-11 introduced a static factory () on the Path interface itself called of() that takes a String literal/path/url
     * Set fileExists by calling exists() on Files class and passing path instance
     * Do the same for delete a file - update Files.delete(path)
     *    - throws a checked exception IOException
     *    - Chose to add a try-catch block
     *    - We get another error that !Operator cannot be applied to void which is another diff btwn delete()
     *    - This is because, the one on this class throws an exception when there's a problem & doesn't return a boolean value
     *          - in short it returns void
     *    - So remove fileExists() assignment and set it manually to false
     *    - Also instead of throwing RuntimeException, call e.printStackTrace()
     *
     * Do the same for create a file - update Files.createNewFile(path)
     *    - We get an error here because createNewFile(path) does not exist on Files class
     *    - Replace with createFile() instead
     *    - Another diff btwn createFile() & the one on File class createNewFile() is that the createNewFile() returns a boolean
     *    - Similar to delete() , it will throw an error
     * Do the same for can write - update Files.canWrite(path)
     *    - We get an error here because Files.canWrite(path) does not exist on Files class
     *    - Replace with isWritable() instead
     *
     * Everything now compiles..
     * Call usePath() from the main() passing it a String literal rep a filename - "pathFile.txt"
     *
     * Making changes to usePath()
     * After testing if the file is writeable, instead of printing to the console, go ahead and write to the file
     * Call writeString() helper class on Files Class passing path variable and a text-block as the 2nd arg
     *    - throws a checked exception IOException
     *    - Rather than wrapping it with another catch-block, include it in the try {} above instead
     *
     * We can also read from this file after writing to it
     * Print "And I can read too"
     * Then call readAllLines() on Files class and pass Path instance
     * chain forEach and pass sout as method reference

     */
    private static void usePath(String fileName) {
        Path path = Path.of(fileName);

        boolean fileExists = Files.exists(path);
        System.out.printf("File '%s' %s%n", fileName, fileExists ? " exists." : " does not exist.");

        if (fileExists) {
            System.out.println("Deleting File: " + fileName);
            try {
                Files.delete(path);
                fileExists = false;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (!fileExists) {
            try {
                Files.createFile(path);
                System.out.println("Created File : " + fileName);
                if (Files.isWritable(path)) {
                    Files.writeString(path, """
                            Here is some data,
                            For my file,
                            just to prove,
                            Using the Files class and path is better!
                            """);
                }
                System.out.println("And I can read too");
                System.out.println("_".repeat(50));
                Files.readAllLines(path).forEach(System.out::println);
            } catch (IOException e) {
                System.out.println("Something went wrong");
            }
        }
    }

}
