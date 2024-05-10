package networking.part12_writing_websocket_chat_app;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.Scanner;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

public class WebSocketClient {

    /*
     * Create a Http Client instance
     *
     * Create a WebSocket variable using the client instance to get a new web socket builder
     *
     * After we get a builder, we could add headers, timeouts and sub-protocols, but we really
     * don't need all that for this example
     *
     * Lastly call buildAsync and pass it a URI, in this case it will be "ws://localhost:8080"
     *  - When connecting to websockets, "ws" is used instead of http
     *  - If it's a secure connection, we'd use "wss"
     *
     * The 2nd parameters to buildAsync() is a websocket listener
     *  - So we'll create a new WebSocket.Listener & we can do this with an anonymous class declaration
     *  - WebSocket.Listener is an interface, but right now we will leave the body of this class empty
     *
     * The Listener interface has default ()s on it  which we'll cover later
     *
     * Finally, the buildAsync() returns a CompletableFuture, which will complete normally with a websocket, so, we
     *  can call join() on that to get the websocket value when it completes
     *
     * This code doesn't compile because the URI throws a checked exception
     *  - Since the sole purpose of this class is to connect to the web socket server, we'll add exception to the ()
     *    signature
     *
     * If an exception occurs, the application will just stop running and that's fine here
     *
     * Running both the server and the client
     *  - The server output tells us that the connection was open and then it was closed
     *  - The client output just tells us that the app completed without errors
     *
     * We've got the scaffolding we need to do some more interesting stuff, in the next video, we'll tweak this code a
     *  little bit and turn it into a little chat server
     *
     *
     *
     *
     *
     * ////////////// PART 2 - Writing A WebSocket Chat App  //////
     *
     * Add a scanner with System.in to make this code interactive
     *  - Prompt a user to enter a name to join the chat
     *  - Read the name from terminal - scanner .nextLine() and assign that to the name variable
     *
     * We can still pass parameters to a web socket call, and so we'll pass the user's name in our created URI
     *  - add ? and follow that with name=%s
     *      - then call formatted () passing name variable to it
     *
     * After, the websocket is connected,meaning after it's returned from the join(), we'll set up a while loop that runs
     *  as long as true
     *  - We'll collect input from the user, we'll not prompt the user , since we want this to emulate a chat window
     *  - If the user type "bye" though, we'll call sendClose on the webSocket
     *      - This will close the connection and here, we can use a constant on WebSocket, called NORMAL_CLOSURE and
     *        pass any msg - say user left normally
     *      - Here again, a CompletionStage is returned, and we can use get(), to wait here until the connection is closed
     *          - If this happens, exit from the loop
     *  - Otherwise, we'll call sendText, on the webSocket which sends whatever the user entered to the websocket server
     *      - The 2nd parameter of this () is an indicator to the server that this is the last part of the message
     *
     *  - If we have large msgs, these may get split up, and in that instance, this parameter would be set to false
     *
     * This doesn't compile as is because of the checked exception from the get()
     *  - surround with try-catch and collapse the 2 catch-blocks
     *
     *
     * Running this:
     *  - Fire the server first and the client next
     *
     *  - Client
     *      - Typed my name
     *
     *  - Server
     *      - prints server is listening on port 8080 and connection is opened on the remote address
     *      - says message received
     *
     * Right now the server hasn't sent any kind of info back to the client, except a confirmation that the connection
     *  was made
     * Typing bye - the server recognizes that the client wants to close the connection, and then it closes it cleanly
     *
     * So we're communicating, but it's still not a very interesting application
     *
     * Let's get back to the server and a little bit of code that will let it act like a chat server
     *
     *
     *
     * /////////////////////
     *
     * Within the empty curly braces of WebSocket.Listener and then overload the methods
     *  - Here we've got a list of all the ()s we can override and customize & they mirror the server's ()s somewhat
     *  - Pick the onText() , which is the one we want to overload
     *      - print the message we get from the server, the message that was broadcasted in other words
     *
     *
     * To test this, we want to have more than 1 client running
     *  - modify the run configuration for this client, to run multiple instance
     *
     * Running the server and the client
     *  - start the server
     *
     *  - start 1 client
     *      - Enter my name at the prompt : "Alex"
     *      - In the server output, you can see my name printed
     *  - start a 2nd client
     *      - Enter "Mary"
     *      - Now both Tim and Mary are in the list in the server output
     *      - Looking at the Tim's window
     *          - A message is received that says "Mary Has Joined"
     *
     *  - From the Tim Window,
     *      - We'll type "Hello"
     *      - This msg gets printed in Mary's Terminal with Tim's name prefixed to the chat message, meaning it was from Tim
     *      - Have Mary respond with "Hi Tim"
     *          - and there it is in Tim's chat window
     *
     * So this is a very simple chat app
     * You can image these clients running from your phone, or a browser, and you've got instant communication via a
     *  web socket server
     *  - With minimal code on our part, which is very exciting
     *
     * Type "bye" in both Alex and Mary terminals which shuts the clients down cleanly and I'll manually stop the server
     *
     * The broadcast() on the web socket server, can be used to facilitate not only chat servers like this, but stock
     *  tickers, interactive games & so on
     *
     *
     * Let's revisit the Listener interface and it's ()s, on a couple of slides next
     *
     * WebSocket.Listener
     * ..................
     * Listener is an interface declared on the java.net.http.WebSocket type
     * It's described as the receiving interface of a WebSocket
     * Even if you don't override any of it's ()s, it's processes are still occurring
     * This type handles all the specifics of opening a connection , receiving data from a connection , as well as
     *  responding to pings from the server
     * If you want to add additional handling, you override the individual (), and make a call to the listener's super
     *  class ()
     *
     * WebSocket Frames
     * ................
     * The term frames is used to describe communication messages or payloads between the WebSocket clients and the server
     * The term "frame" refers to the basic unit of data that is sent over the WebSocket connection
     * It's a structured unit of info, which can consist of data or control signals
     * Control frames are passed, when opening or closing connections, or pinging connections
     * Data frames are used to pass msg data
     * When a msg is too large to fit into a single frame, it can be split into multiple smaller frames, each of which
     *  is transmitted separately
     * These smaller frames are then reassembled by the receiving WebSocket endpoint, to reconstruct the original complete
     *  msg
     * The size of a frame is determined by the WebSocket server, and client constraints
     *
     * WebSocket Control Methods
     * .........................
     * In all cases, there's a default implementation, so you don't have to implement any of these
     * In all likelihood, you'll probably want to override the data frame ()s, to process the text or data delivered from
     *  the server
     * In all cases, the first parameter is the WebSocket instance itself
     *  - In this case, the WebSocket parameter, is a java.net.http.WebSocket
     *
     * onOpen()
     *  - Is invoked when a connection is made
     *  - This occurs once and is triggered when the buildAsync() is executed, on the WebSocket builder
     *  - The server responds to a client's connection request with an open control frame
     *
     * onError()
     *  - Is invoked when an error unexpectedly happens and the error is delivered as part of the frame
     *
     * All other ()s return a CompletionStage,
     *  - It's value can be retrieved, with either a join() or get()
     *
     * onClose()
     *  - Is invoked with a status code, and a reason why the connection was closed
     *  - Some common codes are shown below
     *      - 1000 - Normal Closure
     *      - 1001 - Going Away
     *      - 1006 - Abnormal Closure
     *      - 1008 - Policy Violation
     *      - 1009 - Message Too Big
     *
     * Ping and Pong Control Methods
     * .............................
     * The ping and pong control frames are used to maintain the connection between the client and the server, and
     *  ensure liveliness
     * When 1 party (either the client or the server) wants to check if the other party is still responsive, it sends
     *  a "ping" frame
     * Upon receiving a "ping" frame, the recipient must respond with a "pong" frame, to acknowledge that it is still
     *  active
     * This exchange helps in detecting unresponsiveness connections , and allows for timely reconnection or error
     *  handling if necessary
     * These frames ensure the reliability and stability of the connection
     *
     * Data Methods
     * .............
     * There are 2 data frame ()s
     *  - onText()
     *      - makes it very easy to send and receive text messages
     *
     *  - onBinary()
     *      - used with messages with binary components, like images and audio
     */

    public static void main(String[] args) throws URISyntaxException {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your name to join the chat: ");
        String name = scanner.nextLine();

        HttpClient client = HttpClient.newHttpClient();
        WebSocket webSocket = client.newWebSocketBuilder()
                .buildAsync(new URI("ws://localhost:8080?name=%s".formatted(name)),
                        new WebSocket.Listener() {
                            @Override
                            public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
                                System.out.println(data);
                                return WebSocket.Listener.super.onText(webSocket, data, last);
                            }
                        }).join();

        while (true){
            String input = scanner.nextLine();
            if ("bye".equalsIgnoreCase(input)){
                try {
                    webSocket.sendClose(WebSocket.NORMAL_CLOSURE,"User left normally").get();
                } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e);
                }
                break;
            }else {
                webSocket.sendText(input,true);
            }
        }

    }
}
