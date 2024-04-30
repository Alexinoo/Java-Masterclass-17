package networking.part1_simple_client_server.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class ServerSocketChannelExample {

    /* Java NIO - ServerSocket Channel
     * ...............................
     *
     * A Java NIO ServerSocketChannel is a channel that can listen for incoming TCP connections, just like a ServerSocket in standard Java Networking.
     * The ServerSocketChannel class is located in the java.nio.channels package.
     *
     * Implement with try-with-resources
     * Server Socket channel can be created by invoking its static open() method,providing any pre-existing socket is not already present
     *  - acts as a gateway to listen to incoming connections
     *  - the channel establishes underlying or internal Socket obj
     *
     * Ensure the channel is bounded before performing any IO operation
     *  - this binds internal socket to the host and port
     *      - if you're using localhost, you don't need to specify the host
     *  - done by referencing socket() on the ServerSocketChannel variable and calling bind on that
     *      - then pass an inet socket address and specify the port as an arg to bind()
     *  - print the server is listening to the port
     *      - get the local port in a similar fashion by referencing the socket through the accessor method and calling getLocalPort on it
     *  - Throws NotYetBoundException if channel is not bound and any IO operation is tried
     *
     * Catch any IO exception and print it
     *
     * /// NOTE ///
     * We can access the internal socket associated with a channel using the socket accessor () to bind to a port
     *
     * Listening for Incoming Connections
     *  - is done by calling the ServerSocketChannel.accept()
     *  - the accept() returns a SocketChannel with an incoming connection.
     *      - Thus, the accept() method blocks until an incoming connection arrives.
     *  - Since you are typically not interested in listening just for a single connection, you call the accept() inside a while-loop.
     *      - use the channel internal socket to get the unique address using getRemoteSocketAddress() for the client
     *
     *
     *  Reads request data from the client
     *
     * Socket channels use buffers to pass data around
     *  - These are NIO buffers which aren't the same kind of buffers we talked about with input and output streams (some concepts are same)
     *  - Rather than reading data 1 byte or char at a time, data in an NIO buffer is exchanged in chunks and we can define how big the chunks of data
     *    are by defining a capacity on this buffer
     *  - Will use byte buffer which is the most commonly used buffer when working with network data
     *
     *  - To get a new instance of buffer, we execute a static() allocate and pass it a capacity
     *      - pass it 1024 - which is 2 to the power of 10
     *      - is a standard buffer size for historical reasons - based on the size of the hard disk sectors, communication protocols etc
     *  - Reassign current clientChannel to another SocketChannel variable
     *      - is because we'll be managing separate channels and this will vary
     *
     *  - To get the client request data, call read() on the appropriate channel passing a buffer instance and store this in readBytes variable
     *      - data will get transferred to the buffer from the channel
     *      -  The integer value of the read() method returns how many bytes were written into the buffer
     *
     *  - The no of storedBytes will never be greater than the capacity but it could be less
     *
     *  - Unlike, an input stream's buffer which is managed by the stream, we have direct access and control over the buffer used by channels
     *  - means we have to manually manipulate this buffer
     *
     *  - Once data is in the buffer, once the channel has written to it in other words, we now want to read from it
     *      - To do this we call the flip() on the buffer
     *          - This flips the buffer state from writeable to readable
     *      - Before, we write the data back to the channel, we want to first write "Echo From Server"
     *      - When writing data to a channel, you have to use a buffer
     *          - we can use a static() named wrap on a specific buffer type passing it a primitive array to get a buffer
     *      - Because we want to ultimately pass the data as bytes, will call getBytes on my string
     *          - iterate through the buffer we created for the client request data
     *              - using buffer.hasRemaining() to continue to write data while we still have some data
     *                  - then call channel.write() and pass the buffer data
     *      - After echoing the request back, clear this buffer
     *
     *  - There is 1 other scenario that we need to look for and that's if we get -1 back from the read() on the channel
     *  - -1 signifies that the read() operation timed out without receiving any data
     *      - will get this when a client drops connection
     *          - if so, print connection was lost to the client channel's remote socket address which uniquely identifies each client
     *  - Manually close the channel since we're not using try-with-resources block
     *
     *
     * Running this:
     *  - Fire ServerSocketChannelExample.java
     *
     *  - Fire 2 instance of Simple Client
     *      - First Client
     *          - Type 'First Client, First Request'
     *          - We get the response back from the server
     *      - Second Client
     *          - Type 'Second Client, First Request'
     *          - We get the response back from the server
     *
     * This might lead you to believe this code works and supports multiple clients simultaneously
     * If we try communicating with the server, the 2nd time with the 2 client instances , we don't get the response back on both instances
     *
     * However, if we run a 3rd Client instance, we get a response echoed back
     *  - But if we try to communicate again, we also don't get response back
     *
     * This server handles multiple clients but only for a single request from each client
     *  - Each time it makes a new connection, it waits to process the request but after processing the first request, it then tries to connect to
     *    an additional client
     *
     * Solution
     *  - We can handle this request using threads as we did with server sockets, so that each thread is listening on it's own channel and not blocking
     *    the current thread
     *
     * - We'll look at other solutions, neither of which use threads
     *
     *
     *
     *
     *
     *
     */

    public static void main(String[] args) {

        try(ServerSocketChannel serverChannel = ServerSocketChannel.open() ){

            serverChannel.socket().bind(new InetSocketAddress(5000));
            System.out.println("Server is listening on port "+serverChannel.socket().getLocalPort());

            while (true){
                SocketChannel clientChannel = serverChannel.accept();
                System.out.printf("Client %s connected%n", clientChannel.socket().getRemoteSocketAddress());

                ByteBuffer buffer = ByteBuffer.allocate(1024);
                SocketChannel channel = clientChannel;
                int storedBytes = channel.read(buffer);

                if (storedBytes > 0){
                    buffer.flip();
                    channel.write(ByteBuffer.wrap("Echo from server ".getBytes()));
                    while (buffer.hasRemaining()){
                     channel.write(buffer);
                    }
                    buffer.clear();
                } else if (storedBytes == -1) {
                    System.out.printf("Connection to %s lost%n",channel.socket().getRemoteSocketAddress());
                    channel.close();
                }
            }

        }catch (IOException e){
            System.out.println("Server exception "+e.getMessage());
        }
    }
}
