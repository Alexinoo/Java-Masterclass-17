package concurrency.part1_introduction;

/*
 * Introduction to Concurrency and Threads
 *
 * Process
 * .......
 * A Process is a unit of execution, that has its own memory space
 *  - Most instances of a JVM, run as a process
 *  - When we run a java console application, we are running off a process
 *  - The term process and application are often used interchangeably
 *
 * Process Memory Space - Heap
 * ...........................
 *  - If one java application is running & we start up another one,each application has its own memory space ,
 *     also known as the heap
 *  - The first application can't access the heap that belongs to the 2nd java application
 *  - i.e. The heap isn't shared btwn 2 applications , or 2 processes - they each have their own
 *
 * Thread
 * ......
 *  - A thread is a single unit of execution , within a process
 *  - Each process can have multiple threads
 *  - In Java, every application/process has at least 1 thread and that's the main thread for most programs
 *  - Infact, every java process has multiple system threads , that handle every day tasks like memory management
 *     & input-output
 *  - We don't have to explicitly create, and code these kinds of threads, the JVM does this work
 *  - Our code will run on the main thread, which is created automatically by your java program
 *  - We can also have our code run in other threads, which we can explicitly create and start
 *
 * We are going to learn different ways to create threads, execute them and manage them
 *
 * Threads Share Process Memory
 * - Creating a thread doesn't require as many resources as creating a process does
 * - Every thread created by a process , shares that process's memory space, - the heap
 * - This can cause big problems with your applications
 * - In addition to the process's memory, heap, each thread also has a thread stack
 * - This is memory that only a single thread, will have access to
 * - Every java application runs as a single process, and each process can then have multiple threads within it
 * - Every process has a heap and every thread has a thread stack
 *
 *
 * Why use multiple threads ?
 * What are some of the advantages of creating a multithreaded application?
 *  - To offload long-running tasks
 *  - Process large amounts of data , which can improve performance, of data intensive operations
 * A web server is another use case for many threads, allows multiple connections & requests to be handled, simultaneously
 *
 *
 * Concurrency
 * ...........
 * Concurrency, refers to an application doing more than one thing at a time
 * Concurrency, allows different parts of a program to make progress independently, often leading to better resource
 *  utilization and improved performance
 * Concurrency means that 1 task doesn't have to complete, before another one can start, and multiple threads can
 *  make incremental progress
 */















