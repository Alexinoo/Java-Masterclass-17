package networking.part11_intro_to_websocket;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.WebSocket;

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
     */

    public static void main(String[] args) throws URISyntaxException {

        HttpClient client = HttpClient.newHttpClient();
        WebSocket webSocket = client.newWebSocketBuilder()
                .buildAsync(new URI("ws://localhost:8080"),
                        new WebSocket.Listener() {
                        }).join();

    }
}
