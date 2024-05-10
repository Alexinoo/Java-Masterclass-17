package networking.part10_custom_body_handlers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import networking.part10_custom_body_handlers.handlers.JSONBodyHandler;
import networking.part10_custom_body_handlers.handlers.ThreadSafeFileHandler;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CustomBodyHandler {
    /*
     * Update sendPostsSafeFileWrite to sendPostsFileHandler
     *  - Create a new instance of ThreadSafeFileHandler
     *  - Next instead of passing HttpResponse.BodyHandle.ofFile() and it's parameters, pass handler instance
     *  - Next, call sendPostsFileHandler from the main()
     *  - Delete orderTracking.json
     *
     * Copy OrderFulfillment.java and add it here
     * Rerunning the server and client
     *  - We get the same outcome with the orders written to the file
     *
     * Let's create 1 more body handler, that might be useful at some point in the future
     *
     * Since the response is a JSON string, let's create a body handler that will return a JSON obj
     *  - We'll use a third party library for this and that's the "jackson-databind" module
     *      - Click on the Project > open module settings  > libraries > Plus Sign > select From Maven
     *          - search "jackson-databind" and search
     *              - pick the latest version and Apply changes
     *
     * Create JSONBodyHandler.java in the handlers package
     *
     *
     *
     *
     *
     *
     *
     * Using External Library - "jackson-databind"
     *  - Create JSONBodyHandler.java
     *
     * copy sendPosts from previous classes
     *  - Update name to sendPostsGetJSON
     *
     * sendPostsGetJSON()
     *  - Initialize a new Object Mapper
     *  - Also initialize a new Json Body Handler, passing it the new objMapper instance
     *
     *  - Replace HttpResponse.BodyHandlers.ofString with the handler instance
     *  - Remove the list initialization
     *  - remove the content in the forEach loop
     *      - set up a JsonNode variable which we can get from the future.join() with body chained to that
     *          - This returns a Json node, but we also want to additionally get the order node which has all the dat
     *              - we can do this with the get() and pass the order as a string literal
     *                  - will print both the order id and the expected delivery date using printf
     *              - we can then use node.get() & pass it orderId ,will execute the toString() on the Json node returned
     *              - we can then get the order delivery date & we can chain the asText() to that which gives us a
     *                string value for this node data
     *  - remove the try-catch block that writes to a file
     *      - since this () will just print data to the console
     *
     * Call sendPostsGetJSON() from the main()
     *
     * Running the server and the client:
     *  - This time we get the output in the client output, with th order id and the expected delivery date printed
     *    for each of the 5 orders
     *
     * So in this case, we can use ()s on the Json node obj to access and manipulate any response data, which can
     *  helpful if you get back large and complicated JSON responses
     *
     * So we've seen there is a lot of functionality in the java.net.Http package, with the Http client and
     *  related classes
     *
     * Let's look at the WebSocket class which is another class that was introduced with this package
     *
     *
     */

    private static final Lock lock = new ReentrantLock();
    private static final Path orderTracking = Path.of("orderTracking.json");
    public static void main(String[] args) {
        Map<String , Integer> orderMap = Map.of(
                "apples",500,
                "oranges",1000,
                "banana",750,
                "carrots",2000,
                "cataloupes",100);

        String urlParameters = "product=%s&amount=%d";

        String baseUrl = "http://localhost:8080";

        List<URI> sites = new ArrayList<>();

        orderMap.forEach( (k,v) -> {
            sites.add(URI.create(baseUrl + "?" + urlParameters.formatted(k,v)));
        });

        HttpClient client = HttpClient.newHttpClient();
        sendGets(client,sites);

        if (!Files.exists(orderTracking)){
            try {
                Files.createFile(orderTracking);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

       // sendPostsFileHandler(client, baseUrl , urlParameters, orderMap);
        sendPostsGetJSON(client, baseUrl , urlParameters, orderMap);
    }
    private static void sendGets(HttpClient client , List<URI> uris){

        var futures  = uris.stream()
                .map( uri -> HttpRequest.newBuilder(uri))
                .map(HttpRequest.Builder::build)
                .map(request -> client.sendAsync(
                        request, HttpResponse.BodyHandlers.ofString()
                ))
                .toList();
        var allFutureRequests = CompletableFuture.allOf(
                futures.toArray( new CompletableFuture<?>[0])
        );

        allFutureRequests.join();

        futures.forEach(f -> {
            System.out.println(f.join().body());
        });

    }

     private static void sendPostsFileHandler(HttpClient client , String baseURI,
                                               String paramString , Map<String,Integer> orders){

        var handler = new ThreadSafeFileHandler(orderTracking);

        var futures  = orders.entrySet().stream()
                .map(e -> paramString.formatted(e.getKey() , e.getValue()))
                .map(s -> HttpRequest.newBuilder(URI.create(baseURI))
                        .POST(HttpRequest.BodyPublishers.ofString(s))
                )
                .map(HttpRequest.Builder::build)
                .map(request -> client.sendAsync(request, handler))
                .toList();
        var allFutureRequests = CompletableFuture.allOf(
                futures.toArray( new CompletableFuture<?>[0])
        );

        allFutureRequests.join();

    }

    private static void sendPostsGetJSON(HttpClient client , String baseURI, String paramString , Map<String,Integer> orders){

        ObjectMapper objectMapper = new ObjectMapper();
        var handler = JSONBodyHandler.create(objectMapper);

        var futures  = orders.entrySet().stream()
                .map(e -> paramString.formatted(e.getKey() , e.getValue()))
                .map(s -> HttpRequest.newBuilder(URI.create(baseURI))
                        .POST(HttpRequest.BodyPublishers.ofString(s))
                )
                .map(HttpRequest.Builder::build)
                .map(request -> client.sendAsync(
                        request, handler))
                .toList();
        var allFutureRequests = CompletableFuture.allOf(
                futures.toArray( new CompletableFuture<?>[0])
        );

        allFutureRequests.join();

        futures.forEach(f -> {
           JsonNode node = f.join().body().get("order");
            System.out.printf("Order Id: %s Expected Delivery: %s %n",
                    node.get("orderId"),
                    node.get("orderDeliveryDate").asText()
                    );
        });
    }

}
