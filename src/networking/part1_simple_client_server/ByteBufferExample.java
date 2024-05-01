package networking.part1_simple_client_server;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

public class ByteBufferExample {
    /*
     * ByteBuffer
     * ..........
     * The Buffer classes are the foundation upon which Java NIO is built.
     * However, in these classes, the ByteBuffer class is most preferred.
     * That’s because the byte type is the most versatile one. For example, we can use bytes to compose other
     *  non-boolean primitive types in JVM.
     * Also, we can use bytes to transfer data between JVM and external I/O devices.
     *
     * ByteBuffer Creation
     * ...................
     *
     * The ByteBuffer is an abstract class, so we can’t construct a new instance directly.
     * However, it provides static factory methods to facilitate instance creation.
     * Briefly, there are two ways to create a ByteBuffer instance, either by allocation or wrapping:
     *  - ByteBuffer.allocate(int capacity)
     *      - create an instance and allocate private space with a specific capacity
     *  - ByteBuffer.wrap(bytes)
     *      - allows an instance to re-use an existing byte array
     *
     * Four Basic Indices
     * ..................
     * There are four indices defined in the Buffer class.
     * These indices record the state of the underlying data elements:
     *  - Capacity: the maximum number of data elements the buffer can hold
     *  - Limit: an index to stop read or write
     *  - Position: the current index to read or write
     *  - Mark: a remembered position
     *
     * Implementations
     * ...............
     * To understand what the state of the buffer is, and how different operations effect it,
     * We'll print specific data about the buffer after every single operation we execute
     * To do this, we'll wrap the execution of any () we want to call on byte buffer in our own custom ()
     * This () will have both a buffer and a consumer parameter
     * The consumer parameter will be the operation or the () that'll get called on the buffer parameter
     * My custom() will execute the function, then print the attribute data we're interested in after each operation
     *
     * doOperation(String , ByteBuffer, ()->{})
     *  Parameters
     *      - Takes a string that will describe the operation
     *      - byte buffer
     *      - operation itself - a lambda expression or a method reference
     *  Print the operation's description
     *  Next execute the operation
     *      - Remember for a consumer's functional interface, the single abstract () is accept with one parameter
     *      - pass buffer as its arg
     *  Finally after each operation gets executed
     *      - print the state of the buffer which includes the capacity,the limit, the position and remaining result
     *      - these are all accessor ()s on the Buffer class
     *
     * This () will help us monitor the changes of the buffer as we apply different operations on the buffer
     *
     * main()
     * printBuffer(buffer) -  take a buffer and print the buffer's contents
     *  - Setup a consumer variable and assign it a lambda expression - takes 1 parameter a ByteBuffer
     *  - The Consumer Type arg is a ByteBuffer and will call the variable printBuffer
     *  - Set up a byte array, creating a new byte array that's the size of the buffer's limit
     *  - call buffer.get() and passing data to populate - the byte array with data from buffer
     *      - print the value after transforming it to a string
     *      - we can do this with a String constructor that takes a byte array and the encoding - UTF-8 (standard charsets)
     * Next,
     *  - Create a ByteBuffer variable named buffer with a capacity of 1024
     *
     * Next,
     *  - call doOperation() and see what the buffer's toString() prints
     *      - pass the description of the operation
     *      - pass the buffer instance
     *      - lambda that we want to be executed
     *          - in this case pass a buffer with an extra space at the end coz it will be followed by additional
     *            output when executed from doOperation()
     *
     * Running this:
     *  - Prints "Print: ***" and then the result of calling toString() on this kind of buffer
     *  - Prints a HeapByteBuffer and the position, limit and the capacity are also printed as part of
     *    the buffer's standard toString()
     *  - Then our own print statement follows that, which is a little easier to read and also contains the
     *    remaining value
     * Monitoring the changes to these values shd help us to understand the read and write operations better
     * So, when you first create a buffer and do nothing with it, the position is 0, the Limit is equal to the
     *  capacity and the remaining value is also equal to limit
     *
     * Writing some text to the buffer - Invoke doOperation()
     *  - Pass "Write: " as the operation desc
     *  - Pass the buffer instance
     *  - Pass a lambda by invoking put() to write data to the buffer
     *      - put() takes a byte array - so we'll pass a text literal and call getBytes() on that
     *
     * Running this:
     *  - We get "Write:" printed out followed by the state of the buffer's fields
     *
     * We can now compare the state of the buffer after the put or write operation to it's initial state
     *  - Capacity hasn't change since it is immutable
     *  - Value of limit hasn't changed either
     *  - The pos of the buffer was 0 before the put() and is now 14, which means 14 bytes were written to this buffer
     *  - The value of remaining is how much space is still available for writing more data (capacity - position)
     *
     * Next,
     * We'll flip the buffer - means changing the state
     *  - In this case going from a writeable data state to a readable buffer state
     *  - Invoke doOperation
     *      - Pass "Flip: (from Write to Read) " as the operation desc
     *      - Pass buffer as the 2nd arg
     *      - pass a method reference of the flip() on byte buffer
     *
     * Running this:
     *  - Limit has changed from 1024 to 14
     *  - Position has changed from 14 to 0
     *  - Remaining value is 14
     * What happened here..?
     *  - If you think about how you would read the contents of the buffer, you'd start at 0 and you'll read or
     *    consume all elements in the container that has data
     *  - That's what flip has figured out for you, it has set the position to 0 and limit contains the last index
     *    or length of your byte elements
     *  - The value in remaining indicates the no of bytes that will be read which is also 14
     *
     * Next,
     *  - Execute printBuffer consumer variable
     *  - calls get() on the buffer which is a read operation
     *  - Invoke doOperation
     *      - Pass "Read and Print" the values as the operation description
     *      - Pass a buffer instance
     *      - pass Consumer variable as the last arg
     *  - Running this:
     *      - Prints the description of the operation
     *      - The text that was read from the buffer is printed and that's "This is a test" whic is all the text
     *         that was in the buffer
     *      - Limit did not change , it's 14 - which is the length of the data in the buffer
     *      - Position of the buffer changed from 0 to 14
     *      - Remaining value is 0 when before the read operation it was 14
     *  - This means if we try the read operation again , we get no data
     *  - This is because the next read operation would start at the position of the buffer, so 14 which is also
     *     equal to the limit or the end of the existing data in this buffer
     *
     * Now what would have happened, if we hadn't called flip before we did the "Read and Print Value" operation ?
     *  - comment the flip operation and rerun this again
     *  - We get an exception on the "Read and Print Value" operation
     *      - A BufferUnderflowException
     *          - starts reading at the index in the position field 14 and will try to read the remaining no of bytes
     *             that are after this position - so 1010 bytes
     *          - and in this case when it goes to read the bytes, at index 1024, it's gone to far and that's why we got
     *            that exception
     *  - Just remember that if you see either a BufferUnderflow or a BufferOverflow Exception, you've forgot to flip
     *    the buffer
     *
     * One thing that's kinda neat about this specific container is you can read, write and dissect it, moving its part
     *  around a little bit with such ()s such as slice and compact
     *
     * Next,
     * Let's append some additional text to the buffer
     * We need to flip the buffer from read to write
     *  - Print we're flip from read to write and pass method reference for the flip()
     *
     * Running this:
     *  - Limit is set to the position value of the previous state, 14
     *  - Current position is set to 0
     *  - This is actually the same behavior as the previous flip operation
     * Whether we're flipping from read to write OR write to read, the position of the buffer get's set to 0
     *  after the limit is set to the previous position
     *
     * If we attempt to append data , we'll get an exception for this
     *  - Invoke doOperation
     *      - Pass "Append :" the values as the operation description
     *      - Pass a buffer instance
     *      - pass put(" This is a new test") on the byte buffer and pass a string literal and call getBytes on that
     *
     *  - Running this:
     *      - We get a BufferOverflowException
     *      - Remember that Limit is the max amount of space we're allowed to expand to when writing
     *      - Since Limit is 14, we can't append or add data to the buffer
     *  - Solution (2 options)
     *      - We can set the position to the end of the text, the current limit in other words
     *          - means we'd start writing at index 14 but because the limit is 14, we'd still get stuck
     *      - We'd also have to set the limit to the capacity or at least some no > 14
     *
     * Let's do this next,
     *  - It takes 2 operations
     *  - Invoke doOperation() before appending data
     *      - First, change the position of the buffer and set it to the current buffer's limit
     *      - Next, change the limit and set it to the capacity
     *  - Running this:
     *      - Append is now successful & we don't get any errors
     *          - the position went from 14 to 33, so 19 bytes were added
     *      - We were also successful in changing position to 14 and also changed limit to capacity 1024
     *      - notice the value remaining was also updated to 1010
     *          - means we can write upto 1010 bytes after that operation
     *
     * Another simpler way to do the same thing in 1 operation is to use compact()
     *  - It does what the above 2 operations do
     *  - Comment out on 1 and 2 operations
     *  - The insert the compact operation before the append
     *  - Invoke doOperation and pass compact() as a () reference
     *  - Rerunning this code
     *      - We get the same results as if we'd executed the no 1 and 2 operations
     *  - But did this really append our new string?
     *
     * To find out we, need to read the buffer and use print buffer code again to print text out
     *  - But since we're in write mode, to change to read mode , we have to flip it
     *  - Copy the flip line as well as read and print value and paste below append()
     *
     *  - Rerunning the code
     *      - we can see that the text was appended successfully to the contents of the buffer
     *
     * What if we want to print data in my buffer without always having to do this flipping between read and write?
     * Well, we don't have to read data from the buffer,
     * We can instead get a subsection of the buffer
     *  - The slice() will return a shared subsequence in a new buffer reference
     *      - Comment on the flip operation
     *      - On the "Read and Print Value" operation, instead of just passing buffer as the 2nd arg, we'll pass a
     *        subsequence of the buffer
     *      - We only want the part of the buffer that actually contains the text that's contained in the buffer
     *          - We can use slice, and get the data from position 0 to the buffer's limit using position
     *
     *  - Running this:
     *      - We get the right data printed out without having to flip my current buffer
     *
     * And if we now append data
     *  - This time append just a few asterisks
     *  - Copy the "Read and Print Value" statement and paste a copy below it
     *  - Running this code
     *      - prints the buffer contents which include the asterisks
     *
     * We could actually put the slice operation in print buffer consumer code, if we really wanted to be efficient
     * By using slice to get another buffer, which is backed by the same byte array, we can read from this second buffer
     *  without effecting the limit or position of the original buffer
     *
     *
     */

    public static void main(String[] args) {

        Consumer<ByteBuffer> printBuffer = buffer ->{
            byte[] data = new byte[buffer.limit()];
            buffer.get(data);
            System.out.printf("\"%s\"",new String(data, StandardCharsets.UTF_8));
        };

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        doOperation("Print :",buffer, b -> System.out.println(b + " "));
        doOperation("Write :",buffer, b -> b.put("This is a test".getBytes()));
        doOperation("Flip (from Write to Read): ",buffer,ByteBuffer::flip);
        doOperation("Read and Print Value: ",buffer,printBuffer);
        doOperation("Flip (from Read to Write): ",buffer,ByteBuffer::flip);
        //doOperation("1. Move position to end of text",buffer,b -> b.position(b.limit()));
        //doOperation("2. Change limit to capacity",buffer,b -> b.limit(b.capacity()));
        doOperation("Compact: ",buffer,ByteBuffer::compact);
        doOperation("Append :",buffer, b -> b.put(" This is a new test".getBytes()));
        //doOperation("Flip (from Write to Read): ",buffer,ByteBuffer::flip);
        //doOperation("Read and Print Value: ",buffer,printBuffer);
        doOperation("Read and Print Value: ",buffer.slice(0,buffer.position()),printBuffer);
        doOperation("Append :",buffer, b -> b.put(" ****".getBytes()));
        doOperation("Read and Print Value: ",buffer.slice(0,buffer.position()),printBuffer);
    }

    private static void doOperation(String op, ByteBuffer buffer, Consumer<ByteBuffer> consFunc){
        System.out.printf("%-30s", op);
        consFunc.accept(buffer);
        System.out.printf("Capacity = %d, Limit = %d, Position = %d, Remaining = %d%n",
                buffer.capacity(),
                buffer.limit(),
                buffer.position(),
                buffer.remaining()
                );
    }
}
