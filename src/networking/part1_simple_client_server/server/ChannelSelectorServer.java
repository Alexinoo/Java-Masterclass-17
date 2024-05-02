package networking.part1_simple_client_server.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class ChannelSelectorServer {
    /*
     * Core Features of Event Driven Concepts
     * ......................................
     * When using event driven architectures , events that are expected to occur, are identified, and components can
     *  register an interest in an event
     * When 1 of these events is triggered, the system reacts , sharing the event's notification with interested parties
     * In an event driven architecture, components are loosely coupled and operate independently, one from another
     * Communication occurs through the firing of, and listening for events
     * Events usually trigger an asynchronous action , to components with an interest in that event
     * The asynchronous action can then in turn be registered - called a Promise in some languages because a result or a
     *  response is promised to occur at some later date
     * The system can then listen for the completed response event, and also react when it occurs
     * Events drive the action, rather than code looping indefinitely trying to identify changes to respond to, wasting
     *  precious resources in the process
     *
     * Selectable Channels
     * ...................
     * Selectable channels are like any channels and rep various I/O endpoints like sockets , pipes and file channels
     * Importantly, selectable channels are associated with a Selector, which is the key to event driven NIO programming
     *  in java
     * The SelectableChannel class is in java.nio.channels package
     * ServerSocketChannel and SocketChannel are 2 examples of selectable channels
     *
     * What does a Selector do ?
     * .........................
     * A selector provides a mechanism for monitoring one or more NIO channels and recognizing when one or more become
     *  available for data transfer.
     * The Selector monitors a set of channels for readiness, for specific events
     * Events are sometimes called interests, and the application can register that a channel has an interest in a
     *  specific event
     * This registration is done via the Selector
     * When an event occurs on a registered channel, the Selector wakes up the program, providing info abou the event
     *  , and the interested channel
     * The program the reacts to the event in some way, typically by performing the I/O operation, or taking other
     *  appropriate actions
     * This way, a single thread can be used for managing multiple channels, and thus multiple network connections.
     *
     *
     * Implementations
     * ...............
     * SelectableChannel
     * Create a ServerSocketChannel by calling a static open() on this class
     * Bind these serverChannel to a port through an InetSocketAddress instance and pass 5000
     * For Event-Driven code, you want to set the configureBlocking to false on your channels
     * Catch IO exception and print the message
     *
     * Selector
     * A selector may be created by invoking the static open method of the Selector class, which will use the system’s
     *  default selector provider to create a new selector:
     *
     * Registering Selectable Channels
     * Channels have a register() that lets us register which events we want to be notified about, linking it to the
     *  current channel
     * In order for a selector to monitor any channels, we must register these channels with the selector.
     * We do this by invoking the register method of the selectable channel.
     *  - The first parameter is the Selector object we created earlier,
     *  - The second parameter defines an interest set, meaning what events we are interested in listening for in the
     *     monitored channel, via the selector.
     *
     * There are four different events we can listen for, each is represented by a constant in the SelectionKey class:
     *  - Connect – when a client attempts to connect to the server. Represented by SelectionKey.OP_CONNECT
     *  - Accept – when the server accepts a connection from a client. Represented by SelectionKey.OP_ACCEPT
     *  - Read – when the server is ready to read from the channel. Represented by SelectionKey.OP_READ
     *  - Write – when the server is ready to write to the channel. Represented by SelectionKey.OP_WRITE
     * The returned object SelectionKey represents the selectable channel’s registration with the selector.
     *
     * The SelectionKey Object
     * When we register a channel with a selector, we get a SelectionKey object.
     *  - This object holds data representing the registration of the channel.
     *  - Java calls it a registration token
     *  - Serves as a bridge between the channel and the program
     * When a program becomes ready for any of it's registered interests, the selector wakes up the program and provides
     *  the corresponding selection key which has more info about the event and the channel that registered for that event
     * We still need this thread to stay actively
     *  - use a while loop to perform a continuous process of selecting the ready set
     *      - we do selection using selector’s select()
     *          - select() selects a set of keys whose channels are ready for IO operations
     *          - it's important to know that this is a blocking () by default - you could provide a timeout interval
     *            as an arg, if you've got other work that you want thi thread to do
     *      - If any events happen on the channels and we've registered for them, they will be returned
     *      - The integer returned represents the number of keys whose channels are ready for an operation.
     *      - Next,we usually retrieve the set of selected keys for processing:
     *
     * The set we have obtained is of SelectionKey objects, each key represents a registered channel which is ready for
     *  an operation.
     * After this, we usually iterate over this set and for each key, we obtain the channel and perform any of the
     *  operations that appear in our interest set on it.
     *  - get an iterator for the keys
     *      - iterate by checking hasNext() - means there is data
     *  - get the next key from iterator.next()
     *  - remove the key as we're processing it
     *      - that's why we used an iterator, it lets us remove an element while iterating
     * The first operation we're interested in is when a client want's to establish a connection
     *  - we can test for this by calling the isAcceptable() on the key
     *  - The accept action hasn't yet occurred, only the event that a client is waiting to be accepted was registered
     * If you don't actually execute the accept() on the interested channel, this key will continue to be returned
     *  in the selected keys set
     *  - Print that the client was connected and get the client remote address
     *
     * During the lifetime of a channel, it may be selected several times as its key appears in the ready set for
     *  different events.
     * This is why we must have a continuous loop to capture and process channel events as and when they occur.
     *
     * Running this:
     *  - Fire ChannelSelectorServer.java
     *      - We can see a client was connected
     *      - We can see another client was connected too
     *
     *  - Fire SimpleClient
     *
     * - Fire another SimpleClient
     *
     * Shut down the three processes
     *
     * Do something similar for handling a client request
     *  - Use a echoData() to process one client request
     *     - we only need key as the parameter as it contains the related channel as an attribute
     *     - throws an IOException instead of catching it
     * echoData()
     *      - setup a local variable for the client's Socket channel which is stored on the key & we can get it using the
     *         channel accessor ()
     *      - create a buffer and allocate it 1024 bytes
     *      - get the no of bytes to read by calling read() passing the buffer
     *          - read() is called read but the method is writing request data from the channel to the buffer instance
     *            we pass to it
     *          - returns the no of bytes that it added to the buffer as a result
     *      - next, process the request and echo back the data as a response
     *          - only echo back if there is data in the buffer as long as bytesRead > 0
     *          - flip the buffer since it was just written to and get ready to read
     *          - first read it into a byte array which will be same value as what's remaining
     *          - we can read info from the buffer into myByte array by calling get() and passing it initialized array
     *      - Create a response string, starting with string literal echo and then append a string constructed from the
     *         byte array using the system's default character set
     *      - To write the response to the client, we can use wrap() on ByteBuffer and passing it a byte array and call
     *         getBytes() to convert it to a string
     *
     *      - Handle the situation if -1 comes back from the read()
     *          - add an else statement and print the client disconnected and include his remote address
     *      - Because the channel is gone, clean up the selection key that's associated with it
     *          - call cancel() on the key
     *      - Next , close the clientChannel that the server opened
     *
     * Call echoData() when the event occurs
     *  - Event is when there is a request from the client
     *  - But we have to register for this event
     *      - Ensure each clientChannel is non blocking and call configureBlocking() passing it false on the clientChannel
     *  - Then call register() and pass the selector variable and the event we want to listen for
     *      - In this case , is read, so use OP_READ
     *  - In addition to registering, we have to react when the key gets returned as one of the selected keys
     *      - so add an else statement to check for this type of key
     *      - call isReadable() on the key and if true, call echoData() passing it the key
     *
     * - Run this server
     *
     *  - Then run SimpleClient
     *      - Enter "A. Hello" then Enter
     *          - And we got the response from the server
     *      - Enter "A. goodbye" then Enter
     *          - And we got the response from the server
     *
     *  - Again run SimpleClient
     *      - Enter "B. Hello" and press Enter
     *          - And we got the response from the server
     *      - Enter "B. goodbye" and press Enter
     *          - And we got the response from the server
     *
     * Switching to the server tab
     *  - prints the 2 clients have connected
     *
     * - Again run the 3rd SimpleClient
     *      - Enter "C. Hello" and press Enter
     *          - And we got the response from the server
     *      - Enter "C. goodbye" and press Enter
     *          - And we got the response from the server
     *
     * Switching to the server tab
     *  - prints the 3 client has connected
     *
     * - For the 3rd SimpleClient
     *      - Type "exit"
     *
     * Switching to the server tab
     *  - prints the 3 client has disconnected
     *
     * Type exit for the other clients also
     *  - Both exits gracefully
     *
     * There you have it, we have created our first java event driven server application
     * It's capable of responding to multiple clients, each with multiple requests and it
     *  handles this without using multiple threads
     *
     */

    private static void echoData(SelectionKey key) throws IOException{
        SocketChannel clientChannel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int bytesRead = clientChannel.read(buffer);
        if (bytesRead > 0){
            buffer.flip();
            byte[] data = new byte[buffer.remaining()];
            buffer.get(data);
            String message = "Echo: "+ new String(data);
            clientChannel.write(ByteBuffer.wrap(message.getBytes()));
        } else if (bytesRead == -1) {
            System.out.println("Client disconnected: "+clientChannel.getRemoteAddress());
            key.cancel();
            clientChannel.close();
        }
    }

    public static void main(String[] args) {

        try(ServerSocketChannel serverChannel = ServerSocketChannel.open()){
            serverChannel.bind(new InetSocketAddress(5000));
            serverChannel.configureBlocking(false);

            Selector selector = Selector.open();
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);

            while (true){
                int channels = selector.select();
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectedKeys.iterator();
                while (iterator.hasNext()){
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    if (key.isAcceptable()){
                        SocketChannel clientChannel = serverChannel.accept();
                        System.out.println("Client connected :"+ clientChannel.getRemoteAddress());
                        clientChannel.configureBlocking(false);
                        clientChannel.register(selector,SelectionKey.OP_READ);
                    } else if (key.isReadable()) {
                        echoData(key);
                    }

                }
            }

        }catch (IOException e){
            System.out.println(e.getMessage());
        }

    }
}
