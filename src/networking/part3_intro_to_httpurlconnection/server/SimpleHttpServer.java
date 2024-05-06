package networking.part3_intro_to_httpurlconnection.server;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

import static java.net.HttpURLConnection.HTTP_OK;

public class SimpleHttpServer {

    /* Setting up Simple HttpServer
     * ............................
     *
     * Java has a really simple multi-threaded Http server we can use which will let us test different methods using the client and then move into a
     *  discussion of newer functionality that was introduced in JDK 11 with java.net.http package
     *
     * HTTPServer class
     * ................
     * Java SDK provides an in-built server called HttpServer.
     *  - This class is an internal package which we can tell by the package name "com.sun.net.httpserver.HttpServer" used in the import statement
     *  - This class has been around since JDK 6
     *      - It's a lightweight HTTP server implementation
     *      - It's not going to perform well as some third party HTTP server libraries or frameworks, but for testing or simpler use cases, its good
     *         enough
     * We can instantiate the server like this:
     *  - HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 8080), 0);
     * The above line creates an HTTPServer instance on localhost with port number 8080. But, there is one more argument with value 0.
     *  - This value is used for back logging.
     * create() throws an IO exception that we need to handle
     *
     * BackLogging
     * ...........
     * When a server accepts a client request, this request first will be queued by the operating system
     * Later, it will be given to the server to process the request. All of these simultaneous requests will be queued by the operating system.
     * However, the operating system will decide how many of these requests can be queued at any given point in time.
     * This value represents back logging. In our example, this value is 0, which means that we do not queue any requests.
     *
     * Create Http Contexts and set the handler
     * ........................................
     * A HttpContext represents a mapping from a URI path to a code that will handle the request
     * This code is referred to as an Exchange Handler
     * Once created, all requests received by the server for the path, will be handled by calling the given handler obj, the exchange
     * The context is identified by the path
     * Our initial context is the root and the second parameter is a handler instance, which will handle the HTTP requests.
     *
     * Inside the handler instance :
     *  - we will get the request method via getRequestMethod() on the handler parameter
     *      - then print it out
     *
     * Start the server
     * Print that the server is listening on port 8080
     *
     * GoTo HttpExample.java
     *  - Change the URL to point to the newly locally hosted website
     *      - update url to point to "http://localhost:8080"
     *      - in my case , comment it out
     *
     * Running this:
     *  - Fire SimpleHttpServer.java
     *      - Prints "Server is listening on port 8080..."
     *      - Prints "Request Method: GET"
     *
     *  - Fire HttpExample.java
     *      - Doesn't do anything
     *
     * On a normal web page, we'd return the home page, from the root context
     * Right now, the server is not responding or sending anything back to the client which is why it's hanging there
     * We have the client set to timeout after 30 sec, so we'll do that
     *  - The client crashed, but the server is still running
     *      - shut it down
     *
     * Next,
     * On SimpleHttpServer.java , generate a mini home page
     *  - This will get parsed back to any client that surfs to the root web site
     *  - Build a response string using a text block which will make it easier to see
     *      - return html
     * To send this back to the client , we need to pass a byte array
     *  - call getBytes() on the response string
     *      - we can pass the encoding we want to this (), but if we don't , it will use the system default ,"UTF-8"
     *  - send the response headers , that includes at a minimum the response code, "HTTP_OK" and the length of the content which we can get from
     *    the byte[]
     *  - get the response body from the exchange handler, which we can get from the bytes[]
     *      - then get the response body from the exchange handler and write my byte[] to it
     *  - Finally, close the handler using exchange.close()
     *
     *
     * Running this:
     *  - Fire SimpleHttpServer.java
     *      - Prints "Server is listening on port 8080..."
     *      - Prints "Request Method: GET"
     *
     *  - Fire HttpExample.java
     *      - We get a response code "200" back
     *      - prints the HTML that the server sent back to the client
     *
     * Will the server running , for a minute and we can surf to this website using the browser
     *  - prints the home page served up by the running java process
     *
     * Next,
     * We can change this server to handle diff types of requests
     * This was a test of the GET method against the root context
     *
     * Let's set up a POST next
     * Update SimpleHttpServer.java
     *  - set up a static counter variable - visitorCounter and initialize it to 0
     *  - Check if the request method equals "POST"
     *      - increment visitor counter
     *  - Add a paragraph in the text html block, to print the no of visitors using %d as the specifier
     *  - Add a form to this page with a method set to post
     *      - Include an input field with type submit
     *  - chain with formatted() since we passed a format specifier, and pass the visitor counter
     *
     * Running this:
     *  - Fire SimpleHttpServer.java
     *      - GoTo web browser and type "localhost:8080"
     *      - Click submit several times and the counter gets incremented
     *
     * Remember, the button is executing a POST to the server, which in turn is incrementing the visitor data
     *
     *
     * Back to HttpExample.java
     *  - Update the request method from GET to POST
     *
     * Shut down the server and restart it, so the visitor count is zero.
     *  - Fires SimpleHttpServer.java
     *
     *  - Fire HttpExample.java
     *      - Prints response code of 200
     *      - Prints the html with the no of visitors that we get back as 1
     *          - Rerunning this again increments the no of visitors
     *
     * Each time we're executing HttpExample.java, we're executing a POST and the static variable gets incremented on the server
     * Even though we're doing POST from HttpExample.java, we're not really sending any data in the request body
     *  - We'll be able to see that, if we print out the request body from the server
     *
     * Back to SimpleHttpServer.java
     *  - add 2 lines of code in the handler
     *      - Create a String variable and pass exchange.getRequestBody() and chain readAllBytes() on that
     *  - print body data that we get back
     *
     * Restart SimpleHttpServer.java again:
     * Start HttpExample.java
     *  - prints the no of visitors as 1
     *  - Prints an empty body data
     *      - If you want to send data from a client to an HttpServer, there a re a couple of more steps that have to take place
     *
     *
     * Running this:
     *  -
     *
     *
     */

    private static long visitorCounter = 0;
    public static void main(String[] args) {

        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(8080),0);

            server.createContext("/", exchange -> {
                String requestMethod = exchange.getRequestMethod();
                System.out.println("Request Method: "+requestMethod);

                String data = new String(exchange.getRequestBody().readAllBytes());
                System.out.println("Body data: "+data);

                Map<String,String> parameters = parseParameters(data);
                System.out.println(parameters);

                if (requestMethod.equals("POST"))
                    visitorCounter++;

                String firstName = parameters.get("first");
                String lastName = parameters.get("last");

                String response = """
                        <html>
                          <body>
                             <h1>Hello World from My Http Server</h1>
                             <p>Number of visitors who signed up = %d</p>
                             <form method="post">
                              <label for="first"> First Name:  </label>
                              <input type="text" id="first" name="first" value="%s" />
                              <br/>
                              
                               <label for="last"> Last Name:  </label>
                              <input type="text" id="last" name="last" value="%s" />
                              <br/>
                              
                              <input type="submit" value="Submit" />
                             </form>
                          </body>
                        </html>
                        """.formatted(visitorCounter,
                        firstName == null ? "": firstName,
                        lastName == null ? "": lastName);
                var bytes = response.getBytes();
                exchange.sendResponseHeaders(HTTP_OK, bytes.length);
                exchange.getResponseBody().write(bytes);
                exchange.close();
            });

            server.start();
            System.out.println("Server is listening on port 8080...");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static Map<String,String> parseParameters(String requestBody){
        Map<String,String> parameters = new HashMap<>();
        String[] pairs = requestBody.split("&");
        for (String pair : pairs){
            String[] keyValue = pair.split("=");
            if (keyValue.length == 2)
                parameters.put(keyValue[0], keyValue[1] );
        }
        return parameters;
    }
}
