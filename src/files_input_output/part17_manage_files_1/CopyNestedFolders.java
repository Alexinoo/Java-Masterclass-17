package files_input_output.part17_manage_files_1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class CopyNestedFolders {

    public static void main(String[] args) {

        /* Copy With Nested Directories
         *.............................
         *
         * Use copy(Path source, Path target , CopyOption...options) on Files class
         *
         * Running this :
         *  - Creates a directory named "copied_dir" but there's nothing in it
         *
         * The copy() performs a shallow copy of your folder and in this case, doesn't include students files
         * Delete copied_dir manually using intelliJ delete menu option
         *
         * If you want a deep copy , you will have to write a little bit of recursive code to handle it
         *
         * You'll see online examples using walkFileTree() but let's see if we can do something similar ,
         *  and with less code, using streams
         *
         */

        Path copyNestedFiles = Path.of("copied_files");
        Path copyHere = Path.of("copied_dir");
        try{
            Files.copy(copyNestedFiles,copyHere);
            System.out.println("Directory copied to "+copyHere);

        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
