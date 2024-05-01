package networking.part1_simple_client_server.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

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
     * //// PART 2 /////////////////
     * // Polling Socket Channels with a custom Channel Manager /////
     * We left this code only being able to process 1 request maximum per client
     * To help us understand what's happening in this code, we'll add a couple of print statements
     *  - First, before invoking accept() - print waiting to connect to another client
     *  - Second,before processing client data
     *
     * Running this:
     *  - Fire ServerSocketChannelExample.java
     *      - We can see the server is waiting to connect to another client
     *      - (in other words , this thread is blocked here, waiting)
     *  - Fire up 1 simple client process
     *
     *  - The output from the Server now says connected to client and it's waiting on client request data
     *    - So now it's blocked on channel.read() statement
     *  - Type hello in the Simple Client
     *      - the request is handled properly and hello is echoed back
     *  - But the server isn't listening for data from this client anymore but rather is waiting for a new client to connect
     *      - means it's not interacting with this client any longer with the client it's already connected to
     *
     * That's a problem,
     * The server socket allowed a timeout period on the accept(), but the server socket channel doen't have that
     *  functionality
     * That's because channels have a non blocking mode
     *  - We can also create a new thread for each client connection and have the thread asynchronously listen in a while
     *    loop for more request data - That's the approach we took with the multithreaded server
     *  - If I wrapped all the reading code in a while loop, in this current thread, we'd be in a busy wait loop
     *  - Instead of blocking, waiting for a new client, i'd be stuck in the loop, but we'd at least be able to handle
     *    multiple requests from this client
     *  - But it's not useful for another client trying to connect and make a request
     *
     * Shut down both processes and rework this code so that it can rework multiple clients without using threads
     *
     * Start by maintaining a list of client socket channels that have connected
     *  - just a list that will contain socket channels instances and will call this clientList and initialize it to a new
     *    array list
     *  - when a client connects, add that client channel to this list after the accept() statement
     *  - Before we try to process any requests, we'll create a for loop that will loop through my connected client channels
     *  - Add this after the buffer declaration
     *      - use traditional for loop and loop from 0 to the size of the client channels list
     *      - if the client disconnects or the read times out, remove the client channel from the list of the clientList
     *  - We need to change "clientChannel" variable to be a channel from the client channels
     *
     * This code manages multiple clients but would still blocks on accept()
     * We can make my serverSocketChannel non-blocking
     *  - We have to do this before you execute any ()s that would normally block
     *      - so we'll need to include it before we accept the first client
     *      - we'll add it after the bind statement and call configureBlocking passing false as the arg which is how you
     *        make a channel non blocking
     *  - This means the accept(), will no longer block and it returns immediately, either with a new client connection
     *    or with a null value, meaning no new client connection was identified
     *  - Because client channel might be null, we need to handle that situation
     *      - We'll include it in an if statement and add it to the list as long as it's not null
     *      - close the if block before allocating buffer
     *  - This code allows this thread to continuously look for connecting clients without blocking
     *
     * But it still got a problem,
     *  - It will block when reading request from an existing client
     * But fortunately, the client's channel can also be put into non-blocking mode and we'll can do this next
     *  - call configureBlocking() and set it to false
     * Then remove the 2 println statements we added before
     *  - this is because they will print continuously as this loop continually checks for work to do
     *  - in my case we'll comment this out
     *
     * Running this :
     *  - Fire up the server
     *
     *  - Fire up 2 instances of Simple Client
     *      - First Client
     *          - Type A: Hello
     *          - we get an echoed back from the server
     *
     *          - Type A: Hello again
     *          - we get an echoed back from the server
     *
     *      - Second Client
     *          - Type B: Hello
     *          - we get an echoed back from the server
     *
     *          - Type B: Hello again
     *          - we get an echoed back from the server
     *
     * Split the run windows so that we can see all 3 outputs
     *  - prints we're connected to both clients
     *
     *      - Third Client
     *          - Type C: Hello
     *          - gets echoed back
     *
     *          - Type C: Hello again
     *          - gets echoed back
     *
     *          - Type "exit"
     *          - get Client Disconnected
     *              - from server "Connection to *** Lost"
     *
     * Finally, type exit on the other 2 clients which also get disconnected as well
     *
     * We've now got a server that is capable of processing multiple clients and multiple client requests with a single
     *  thread.
     *
     * This is pretty powerful and why non-blocking IO is being embraced
     * This solution may not scale well if your requests or responses contain large amounts of data or if you're fielding
     *  large no of requests
     * But this non-blocking behavior in combination with multithreading gives you a lot more options and scales much
     *  better than blocking IO solutions
     * In this server we've used polling to continually check for new connections and new requests
     *
     * We'll write a server using socket channels, but with event driven ()s which the Channel API supports
     *
     */

    public static void main(String[] args) {

        try(ServerSocketChannel serverChannel = ServerSocketChannel.open() ){

            serverChannel.socket().bind(new InetSocketAddress(5000));
            serverChannel.configureBlocking(false);
            System.out.println("Server is listening on port "+serverChannel.socket().getLocalPort());

            List<SocketChannel> clientList = new ArrayList<>();

            while (true) {
//                System.out.println("Waiting to connect to another client");
                SocketChannel clientChannel = serverChannel.accept();
                if (clientChannel != null) {
                    clientChannel.configureBlocking(false);
                    clientList.add(clientChannel);
                    System.out.printf("Client %s connected%n", clientChannel.socket().getRemoteSocketAddress());
                }

                ByteBuffer buffer = ByteBuffer.allocate(1024);

                for (int i = 0; i < clientList.size(); i++) {

                    SocketChannel channel = clientList.get(i);
//                    System.out.println("Waiting on client request data");
                    int storedBytes = channel.read(buffer);

                    if (storedBytes > 0) {
                        buffer.flip();
                        channel.write(ByteBuffer.wrap("Echo from server ".getBytes()));
                        while (buffer.hasRemaining()) {
                            channel.write(buffer);
                        }
                        buffer.clear();
                    } else if (storedBytes == -1) {
                        System.out.printf("Connection to %s lost%n", channel.socket().getRemoteSocketAddress());
                        channel.close();
                        clientList.remove(i);
                    }
                }
            }

        }catch (IOException e){
            System.out.println("Server exception "+e.getMessage());
        }
    }
}
