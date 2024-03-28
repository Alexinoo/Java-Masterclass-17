package files_input_output.part17_manage_files_1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class RenameDirectory {

    public static void main(String[] args) {

        /* Rename Directory
         *.................
         *
         * Create a path to "copied_dir" folder & we can do that using a relative path
         *
         * Running this renames "copied_files" folder to "copied_dir"
         *
         */

        Path copyFiles = Path.of("copied_files");
        Path copiedDir = Path.of("copied_dir");
        try{
            Files.move(copyFiles,copiedDir);
            System.out.println("Directory renamed");

        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
