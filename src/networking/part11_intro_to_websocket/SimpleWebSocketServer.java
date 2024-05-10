package networking.part11_intro_to_websocket;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;

/* HTTP Communication is one-directional
 * .....................................
 * So far in all the Http server examples we've covered, you might have realised that they all have 1 thing in common
 * The client made a request to the server, and the server optionally responded.
 *  - The server never initiated any communication or independently communicated with the client
 * For 2 clients to communicate with each other through a server's application, each client would have to regularly
 *  submit requests to see if there's new data to be communicated
 * Or, if you're creating some kind of info feed, your clients would need to periodically request the server for new
 *  info
 * This puts the onus of staying up to date on the client, to poll the server regularly, checking for status update
 *
 * WebSocket Communication is bi-directional
 * .........................................
 * A WebSocket creates a persistent connection between the server and client, which can be used to send messages back
 *  and forth
 *  - In other words, the server knows who it's clients are and can reach out or broadcast info to them as that info is
 *    received
 * This makes it ideal for real-time applications like chat, stock tickers, and collaborative tools, where data needs
 *  to be constantly updated and distributed
 * The WebSocket also allows data to be pushed from the server to the client,and vice versa, without the overhead of
 *  repeatedly establishing new connections
 *
 * WebSocket Communicate over TCP, using an "Upgrade"
 * ..................................................
 * WebSockets leverage the reliability of TCP connections
 *  - However, they don't solely depend on standard HTTP
 * Instead, WebSocket establish a connection using something called a HTTP handshake
 * This starts with an upgrade header on the request
 *  - If the server supports WebSockets, it responds with a special status code, signalling the successful upgrade
 * This handshake paves way for the upgrade to the WebSocket protocol itself, with real-time data exchange, and low
 *  overhead and latency
 * This upgrade process ensures compatibility with existing web infrastructure
 * At the same time, it enables more efficient and responsive communication for interactive web applications
 *
 * WebSocket Communication can get Complicated
 * ...........................................
 * Communicating with WebSockets, involves moe than a standard HTTP connection
 *  - WebSockets require a special handshake mechanism when a connection is established
 *  - They use a specific message format
 *  - They typically use a binary framing format, and text needs to be encoded before sending, and decoded on the
 *    receiving end
 *      - check out the documentation on the MDN site, the Mozilla Developers Network
 *       - This sites gives you information on how to create a Web Socket server in multiple software languages, Java
 *          being one of them
 *  - There's a link to RFC 6455, which is the official document that describes the web socket protocol in detail
 *
 * Java's WebSocket implementation
 * ...............................
 * The java.net.http package provides a class called WebSocket
 * It's very important to understand that this is a WebSocket client
 *  - It's provided to help you connect to and communicate with, existing websocket servers, using the websocket
 *    protocol, without the need to worry about the handshake, and message op codes and so on
 * We'll be using a 3rd party option to set up a simple web socket server which will help us communicate with a server
 *  using Java's client side WebSocket class to communicate with it
 *
 * Implementations
 * ...............
 * First, we'll pull some code from the Maven central repository - "org.java_websocket" - chose the latest version "1.5.6"
 *  - This is an open source lib which includes functionality to simplify writing WebSocket servers as well as their
 *    clients in java
 * We'll use it to quickly create a very simple WebSocket server implementation
 * We'll then use Java's WebSocket type
 * - This lib has a WebSocket type as well, which we'll use in the server code shortly
 *
 * Next,
 *  - Create a SimpleWebSocketServer class that extends WebSocketServer
 *      - If we've included the lib correctly, we shd get the proper import statement
 * Next,
 *  - Implement abstract ()s in WebSocketServer class
 *  - Notice, that each () has a WebSocket parameter
 *      - This isn't the java.net.http WebSocket, rather, it's this library's own type instead, which provides server-side
 *         functionality for us
 *  - There are 5 ()s here, 4 of which matches the life cycle of a web socket
 *      - these are onOpen, onClose, onMessage and onError
 *  - The onStart is different because it's used to start up the WebSocket server
 *
 * Setup a port as a constant in this class and set it to 8080
 *  - WebSockets can use any port, but they commonly use port 80 for unencrypted connections and port 443 for encrypted
 *    connections
 *  - 8080 is a good port to use for development testing
 *  - The host will default to local host if we don't specify, and so we won't define it
 *
 * We also need to generate a no args constructor for this class
 *  - make a call to parent constructor by calling super() and pass an InetSocketAddress with our port
 *
 * Add a main()
 *  - Create a new instance of this server and call start() on it
 *  - This code will run but we won't have any info about what's happening
 *      - output some texts in each of the ()s
 *
 *  - onOpen()
 *      - Print the connection opened and include the remote socket address as an identifier
 *  - onClose()
 *      - Print the connection closed and include the remote socket address as an identifier
 *  - onMessage()
 *      - Print the message received and include the remote socket address as an identifier
 *  - onError()
 *      - Print the error received and include the remote socket address as an identifier
 *
 *  - onStart()
 *      - Print the server is listening on port , get the port that this class inherits
 *
 * Running this server:
 *  - We get a couple of statements about SLF 4J, which are just warnings because this library expects you to have
 *    some kind of logging implementations set up like log4j or something similar
 *  - We'll ignore these warnings for now because this simple exercise, logging isn't necessary
 *  - More importantly, we can see that the server is listening on port 8080 printed
 *
 * Next,
 *  We'll create our client application and call it WebSocketClient
 *
 */
public class SimpleWebSocketServer extends WebSocketServer {
    private static final int SERVER_PORT = 8080;

    public SimpleWebSocketServer() {
        super(new InetSocketAddress(SERVER_PORT));
    }

    public static void main(String[] args) {
        var server = new SimpleWebSocketServer();
        server.start();
    }

    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
        System.out.println("Connection Opened " + webSocket.getRemoteSocketAddress());
    }

    @Override
    public void onClose(WebSocket webSocket, int i, String s, boolean b) {
        System.out.println("Connection Closed " + webSocket.getRemoteSocketAddress());
    }

    @Override
    public void onMessage(WebSocket webSocket, String s) {
        System.out.println("Message Received " + webSocket.getRemoteSocketAddress());
    }

    @Override
    public void onError(WebSocket webSocket, Exception e) {
        System.out.println("Error For " + webSocket.getRemoteSocketAddress());

    }

    @Override
    public void onStart() {
        System.out.println("Server is listening on Port: "+getPort());
    }
}
