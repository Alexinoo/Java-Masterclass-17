package files_input_output.part17_manage_files_1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class CopyToFolder {

    /* Moving Files
     *.............
     * Let's examine this further and change the path we are moving to , adding a sub-folder as part of that path
     *
     * Copy contents of students.json and move to a folder named "copied_files"
     *
     * Running this throws NoSuchFileException since copied_files folder does not exist
     *  - Files.move() does not create subdirectories and copied_files dir doesn't exist
     *  - Fix this by creating using createDirectories() before calling the move() but not with the entire path though
     *  - We need to strip the last part of the path or the filename to get "copied_files"
     *      - use subpath() an instance () on Path Class to get the directories path
     * Print more informative on console
     *
     * Running this renames "students.json" to "students-activity.json" and moves it to copied_files folder
     */

    public static void main(String[] args) {


        Path oldPath = Path.of("students.json");
        Path copyToSubFolder = Path.of("copied_files/students-activity.json");

        System.out.println(copyToSubFolder.subpath(0, copyToSubFolder.getNameCount() - 1));
        try{
            Files.createDirectories(copyToSubFolder.subpath(0, copyToSubFolder.getNameCount() - 1));
            Files.move(oldPath,copyToSubFolder);
            System.out.println(oldPath + " moved (renamed to) -->" +copyToSubFolder);

        }catch(IOException e) {
            e.printStackTrace();
        }

    }
}
