package networking.part7_http_concurrent_requests;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class ConcurrentRequests {
    /*
     * Set up a Map with the product and the amount or quantity we will be ordering keyed by String and Integer as the value
     *  - Set this up using Map.of() which takes a list of key-value pairs
     *
     * Set up a formatted string to rep the request parameters
     *
     * Set up a string to rep base URL - localhost:8080
     *
     * One way to execute a set of requests is to use a List<URI> which we'll build next
     *  - Initialize an array list of these
     *  - loop through the order map and use a lambda with a parameters for the key and value
     *      - for each mapped value, use the URI.create() because it doesn't throw a checked exception
     *          - pass the base url variable, concatenate it with "?" followed by the urlParams variable formatted using each key and value
     *
     * Next,
     * Create Http client instance
     *
     * Next, create a () to process these requests
     *  sendGets(HttpClient client , List<URI> uris) : void
     *   - use type inference which makes this a lot easier to read
     *   - Build the list using a stream
     *      - map each URI instance on the stream to an HttpRequest builder instance using HttpRequest.newBuilder()
     *       - after creating a builder, use the builder's build () to get a Http request instance
     *          - use the builder's build() to get an HttpRequest instance
     *              - use map again to execute any method that returns a value, so use client.sendAsync()
     *                   (sends the request asynchronously, for each request on the stream)
     *                  - pass the request on the stream, followed by bodyHandlers type that return a string
     *                    - this returns an instance of a completable future
     *                      - Finally, collect all of these completable future instances into a list
     *
     *  - Now that we have a list of completable futures, we can create a single aggregated completable future from them
     *      - we can use a special () on the CompletableFuture called allOf()
     *          - pass this an array of completable futures, using the toArray() on the futures list
     *              - passing a new 0 element to that
     *  - Because the array as an argument isn't big enough to house the elements, a new array of the same runtime type is allocated for this purpose
     *      - Remember, this is a way to type the returned array, otherwise it's typed to an obj or a raw array & we don't want that
     *
     *  - Once we get an aggregated completable future, we can call join on that
     *      - This blocks until all the requests, that were aggregated on this completable future, complete in some way
     *
     *  - When you use join() on an aggregated completable future, you don't get a response back
     *
     *  - To get the responses we have to individually get the response from each of the futures
     *
     *  - Will loop through each of the futures using forEach and print the response for each
     *      - so join() in this instance returns the response & we can get the response body from that
     *
     * Finally,
     *  call sendGets() from the main()
     *
     *
     * Running the OrderFulfillmentServer.java and the ConcurrentRequests.java
     *  - OrderFulfillmentServer.java
     *      - processed the parameters from the GET request and delivered a JSON response back
     *
     *  - ConcurrentRequests.java
     *      - prints a record with a
     *          - generated order id, 10 digits long with a unique order id
     *          - product and amount
     *          - date timestamp of when the order was received and then the order delivery date is 3 days later
     *
     */



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
}
