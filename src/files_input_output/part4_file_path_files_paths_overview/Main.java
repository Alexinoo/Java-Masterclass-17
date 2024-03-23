package files_input_output.part4_file_path_files_paths_overview;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    /*
     * java.io - Legacy(io)
     * ....................
     * The File class and the FileReader class have been part of Java, since v1
     * The FileReader class implements the AutoCloseable interface through it's parent class Reader
     * This class opens a file resources implicitly.
     * When you create an instance of a File, you aren't actually opening that file
     * Instead, you're working with a file handler, that lets you perform OS-like operations
     * Getting an instance of a file handle isn't the same as getting an instance of a file resource
     *
     * File Handler vs File Resource
     * .............................
     * A file handler is a reference to a file that is used by the OS to keep track of the file
     * It is an abstract rep of the file, and it does not contain any of the actual data from the file
     *
     * A file resource is the actual data from the file
     * It is stored on the disk and can be accessed by the OS and by applications
     */

    public static void main(String[] args) {
        /*
         * We are able to create a new instance of it using new and pass a String literal to this constructor
         * The String literal rep a path name
         * A path can include a filename as this one does e.g.  String filename = "testing.csv";
         * We can also add a directory there starting with files then / forward slash
         *      e.g. String filename = "files/testing.csv";
         * Creating files directory and testing.csv itself...with intelliJ
         *
         * Running this. we get "I am good to go" printed - meaning the file was found in the place we specified
         *
         * How did it know to start looking in the project directory of this project?
         * This is because we used what is called a relative path.
         * "files/testing.csv" did not include characters that said start at the root folder
         *
         * Let's print the current working directory -
         * We can instantiate a new file and pass it an empty string or string literal and call getAbsolutePath()
         * on that instance
         * Running this prints "C:\JMC17\Java-Masterclass-17" which is the cwd
         * A relative path means the path is relative to the current working directory
         * An absolute path means the path is starting at the root directory
         */
        System.out.println("Current Working Directory (cwd) = "+ new File("").getAbsolutePath());
        String filename = "files/testing.csv";

        File file = new File(filename);
        if(!file.exists()){
            System.out.println("I can't run unless this file exists");
            return;
        }
        System.out.println("I am good to go.");

        /*
         * How do we know what my root directories are ?
         * There is a static () on File called getRoots()
         * Call this in a for loop and use a static () on File class called listRoots which returns a File[]
         * Print out f returned
         *
         * Running this returns : C:\ D:\ E:\
         *
         */
        System.out.println("_".repeat(50));
        for (File f: File.listRoots() ) {
            System.out.print(f + " ");
        }

        /*
         * Copy files directory to the root folder [ C:\ folder]
         * The goal is to understand how do we access files and folders from the root
         * Testing whether this file exists and not the one existing from the project directory
         * To do this add a / forward slash as the first character
         *  e.g. "/files/testing.csv";
         * This indicates that this is an absolute path
         * We can use the getAbsolutePath() to check whether this is correct
         * Running this prints
         *  "Absolute Path: C:\files\testing.csv"
         *
         */
        System.out.println();
        System.out.println("_".repeat(50));
        filename = "/files/testing.csv";
        System.out.println("Absolute Path: "+new File(filename).getAbsolutePath());

        /*
         * There are overloaded constructors for file, that takes a parent path which is a string
         * Changing back to relative path
         * Then pass a single / forward-slash as a String literal
         * Running below prints the same as above
         *  "Absolute Path: C:\files\testing.csv"
         */
        System.out.println("_".repeat(50));
        filename = "files/testing.csv";
        file = new File("/",filename);
        System.out.println("Absolute Path: "+file.getAbsolutePath());

        /*
         * We can change the 1st arg to current working directory by passing a . dot as a String literal
         * is very common in many programming languages to refer to the current directory
         * Running below prints the same as above
         *  "Relative Path: C:\JMC17\Java-Masterclass-17\.\files\testing.csv"
         * Notice that the dot is printed in the absolute path
         * This is called a redundant name element
         * Nio2 types has methods to normalize paths and removes redundant name elements
         */
        System.out.println("_".repeat(50));
        filename = "files/testing.csv";
        file = new File(".",filename);
        System.out.println("Relative Path: "+file.getAbsolutePath());

        /*
         * There is another constructor that takes a File instance as the parent as the 1st arg
         * We can pass it directly as the parent instead of a String literal
         * The getAbsolutePath() actually returns a file instance and not a string
         * Running below prints the same as above
         *  "Relative Path: C:\JMC17\Java-Masterclass-17\.\files\testing.csv"
         */
        System.out.println("_".repeat(50));
        file = new File(new File("").getAbsolutePath(),filename);
        System.out.println("Relative Path: "+file.getAbsolutePath());

        /*
         * Summary - File System Concepts
         * ..............................
         * A dir ( or folder) is a file system container for other dir or files
         * A path is either a directory or a filename, and may include info about the parent dir or folders
         * A root dir is the top-level dir in a file system
         * In windows, this is often the C:\
         * The current working directory is the dir that the current process is working in or running from
         * An absolute path includes the root (by either starting with / or optionally C:\ in windows where C
         * is the root identifier
         * A relative path defines a path relative to the cwd & therefore would not start with / but may optionally
         * start with a dot . then a file separator character
         */






        /*
         * Updated (jdk nio2)
         *
         * A Path is an interface and not a class, like the File class was.
         * The Paths class consists of exclusively of static() that returns a Path instance
         * The Files class has many static() that performs operations on files and dir(s)
         * This class operates on a Path instance which you pass to these ()s.
         * It has a lot of functionality in common with the older java.io.File Class but provides much more as we
         * saw on the API doc
         */

        /*
         * IO -Using File
         *
         * When you use a File class ,you get an instance, with a File constructor, and then you execute a method
         * on that instance
         */

        /*
         * NIO2 -Using Files and Path (Instead of File)
         *
         * For NIO2 types, we first have to get an instance of Path
         * We don't do this directly but instead use factory () on the Path class, or the Paths Class or other types
         * We then call static() on the Files Class to do something , on the path instance passed as an arg
         * That's what file.exists() does but uses the NIO2 types
         * First, we need to get an instance of a type that implements the Path interface
         * We just have to use a factory () to get an instance
         * We start by calling a factory() on the Paths Class which is a class in existence to provide these factory()
         * It has overloaded get() on it that takes String literal as a relative path
         *
         * Running this works the same as the code above with File instance
         */
        System.out.println("_".repeat(50));
        Path path = Paths.get("files/testing.csv");
        if(!Files.exists(path)){
            System.out.println("2. I can't run unless this file exists");
            return;
        }
        System.out.println("2. I am good to go.");

    }
}
