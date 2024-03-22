package files_input_output.part1_Introduction;

public class Main {

    public static void main(String[] args) {
        /*
         * Up until now our code has stayed internal to the JVM
         * At this point we are going to branch out and start communicating with the environment the JVM
         * is running in.
         * We'll do this by taking advantage of resources - can be files, network connections,database connections,
         * streams or sockets
         * We use these resources to interact with file systems, networks and databases to exchange information
         * However, it's impossible to access a resource without first a need to address exceptions whether we want
         * to deal with them or not
         *
         * Exception Handling
         * When you're dealing with external resources, exception handling becomes much more important
         * When you instantiate one of Java's file access classes, Java will delegate to the OS to open a file from
         * the OS File System
         * This then performs some or all of the following
         *  - First, checks if the file exists
         *  - If the file exists, it next checks if the user or process has the proper permissions for the type of
         *    access being requested
         *  - If the user has required permissions, then the file metadata is retrieved and a file descriptor is
         *    allocated. This descriptor is a handle to the opened file
         *  - An entry is made in the file table or file control block table of the OS, to track the opened file
         *  - The file may be locked
         *  - The file may be buffered by the OS, meaning, memory gets allocated, to cache all or part of th file contents
         *    , to optimize read and write operations.
         * Many of methods in Java make opening a file look just like instantiating another obj
         * You don't have to open on a file, so it's easy to forget that you've really opened a resource
         * Closing the file handle will free up the memory used to store any file related data. and allow other
         * processes to access the file
         * Fortunately, most of the Java classes you'll use to interact with files, also implement an AutoCloseable
         * interface, which makes closing resources seamless
         *
         * IO , NIO, NIO2
         * ---------------
         * Java has what seems like a very confusing and large series of classes in many packages, to support input/output
         * First, IO was a term for input/output and java.io is a package that contains the original set of types, which
         * support reading & writing data from external resources
         * NIO was introduced as Non-blocking IO with the java.nio package in Java 1.4 as well as few other related classes
         * The communication with resources is facilitated through specific types of Channels, and the data stored in containers
         * called Buffers, when exchanged.
         * NIO.2 stands for New IO and is a term that came into being with java 1.7, emphasizing significant improvements to
         * the java.nio package, most importantly the java.nio.file package and it's types
         * NIO.2 introduced the Path and FileSystem types and some helper classes such as Files, Path and FileSystems that
         * do make some common functionality for working with OS File Systems, much easier as well as delegating work to
         * native systems
         * Where functionality overlaps, this will be shown to us side by side
         *
         *
         *
         *
         *
         *
         *
         *
         */
    }
}
