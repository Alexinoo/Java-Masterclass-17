package networking.part8_challenge_concurrent_req_post;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class ConcurrentPostRequests {
    /*
     * HttpClient Concurrent Posts Challenge
     * .....................................
     *
     * We looked at how to send a series of concurrent requests, using Http client
     *  - Those requests by default used the GET method meaning the parameters were passed as part of the URI
     *
     * Challenge
     * .........
     * We'll create a sendPosts() to this class, that will be similar to the sendGets()
     * This method will use a POST method
     * It will write the order receipt it receives back to a file, rather than print them to the console
     * Will have 4 parameters
     *  - The HttpClient
     *  - The base URI
     *  - The formatter parameter String
     *  - The Map of Orders that should get submitted
     *
     * Additionally, we'll add a class variable for the path of a local file that this () can access
     *
     * Unlike a GET request, the parameters for a POST are passed to the POST build()
     *  - So instead of building a List<URI>, we'll simply pass the base URI
     *
     *
     * sendPosts Functionality
     * .......................
     * Is similar to sendGets() but with some differences
     *  - Format the parameter string with each order's values
     *  - Use the POST builder method
     *  - Needs to send each post request asynchronously, creating a list of CompletableFuture , similar to sendGets()
     *  - Use CompletableFuture.allOf() to create an aggregated completable future , similar to sendGets()
     *      - Use the join() on this future, to await completion of all requests
     *  - Print each response, the order's receipt, to a file , each order's response data should be appended to the file
     *  - The main(), should execute sendPosts() , instead of sendGets()
     *
     *
     * Solution
     * ........
     * Create a variable for the file that we want to write this data to
     *
     * main()
     *  - Check if the file exist  - Files.exists()
     *      - if not create it with Files.createFile() passing the Path variable
     *      - catch any checked exc and throw a runtime error
     *
     * Next,
     *  - Create sendPosts() - copy sendGets()
     *      - Remove List<URI> uris params
     *          - for a GET request, we were able to create the URI's with the parameters but this doesn't work for a POST
     *          - this means building the URI will be part of the builder code itself
     *      - Add baseURI as the 2nd param
     *      - Add paramString which is the string that contains the formatting for a parameter
     *      - Pass the Order Map
     *
     *  - Change uris.stream to orders.entrySet and chain a call to stream()
     *      - This makes this stream a Stream<Entry<?>> and not a Stream<URI>
              - however, we can still convert it into a Stream<URI> using the map operation
     *  - First, use the key-value pair to format the parameter spring
     *      - insert this as the first map operation and simply return the param string formatted using getKey and getValue on the entry to
     *          populate those values
     *      - this returns a Stream<String> and we still need to get to a Stream<URI>

     *  - To do this, on the next line, instead of URI being passed to new builder, we'll use URI,create() passing it the baseURI
     *      - The code compiles but if we run iot, we'll be executing 5 requests, all GET's and no parameters would get passed
     *
     *  - Because, we want to use the formatted string from the previous map to populate the body of the POST, we're going to chain the POST() to
     *    URI.create() in this map operation
     *      - This will send 5 posts with the order info in the body of the POST request
     *
     *  - Where we're processing the request body responses, in the for loop, we'll change this code to get a list of string which we'll then write to
     *    the file
     *      - Initialize an Array list
     *      - Then instead of writing to the console, we want to add body's response data to the array list
     *          - replace println with lines.add there
     *
     *  - Next,
     *      - Write the list to the file
     *      -  call Files.write() and pass
     *          - the file to write
     *          - content, lines in this case
     *          - write options which is an enum value from StandardOpenOption
     *      - catch checked exception
     *
     *  - Next,
     *      - call sendPosts from the main() and pass the
     *          - HttpClient
     *          - base url
     *          - url params
     *          - order map
     *
     * Running the server and the client
     *  - in the server output, all 5 GET's are sent until the GET's are processed
     *      - this is because of the aggregated join, so the statement allFutureRequests.join() in the get()
     *      - the thread blocks there , waiting for all the GET requests to complete before proceeding
     *  - After the GET requests, we can see the 5 POST which have successfully been written to the orderTracking.json file
     *  - Confirmed orderTracking.json file is generated with the order details
     *
     *
     *
     * Approach 2 - use ofFile BodyHandler
     * ...................................
     * - Delete the generated orderTracking.json file
     * - copy sendPosts()
     *      - update to sendPostsWithFile()
     *  - update HtpRequestBodyHandlers.ofString() to HtpRequestBodyHandlers.ofFile()
     *      - ofFile() takes Path variable, and write options from StandardOpenOption enum
     *  - This writes the order response JSON to the orderTracking file when the response is received
     *      - therefore , for loop that creates a list of lines or the code that writes the file using that list is not needed
     *      - also remove the try-catch block
     *  - The body handler will do all that
     *
     *  - Call sendPostsWithFileResponse() from the main()
     *
     *  - Running the server and the client again
     *      - produces the exact same result both in the console output but also in the generated file with 1 exception, there is no line feed
     *        between the records
     *
     * Although this code feels a bit cleaner, it's actually a bit dangerous to use
     *  - The ofFile body handler isn't thread safe, it may cause unexpected results because of it not being thread-safe
     *  - This means concurrent requests could interleave or deadlock when writing to this file
     *      - You can make it thread safe by calling a thread-safe write or you can write your own custom body handler that uses a thread safe write
     *
     */


    private static void sendPostsWithFileResponse(HttpClient client , String baseURI, String paramString , Map<String,Integer> orders){

        var futures  = orders.entrySet().stream()
                .map(e -> paramString.formatted(e.getKey() , e.getValue()))
                .map(s -> HttpRequest.newBuilder(URI.create(baseURI))
                        .POST(HttpRequest.BodyPublishers.ofString(s))
                )
                .map(HttpRequest.Builder::build)
                .map(request -> client.sendAsync(
                        request, HttpResponse.BodyHandlers.ofFile(orderTracking,
                                StandardOpenOption.APPEND )
                ))
                .toList();
        var allFutureRequests = CompletableFuture.allOf(
                futures.toArray( new CompletableFuture<?>[0])
        );

        allFutureRequests.join();

    }


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

       // sendPosts(client, baseUrl , urlParameters, orderMap);
        sendPostsWithFileResponse(client, baseUrl , urlParameters, orderMap);
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

    private static void sendPosts(HttpClient client , String baseURI, String paramString , Map<String,Integer> orders){

        var futures  = orders.entrySet().stream()
                .map(e -> paramString.formatted(e.getKey() , e.getValue()))
                .map(s -> HttpRequest.newBuilder(URI.create(baseURI))
                        .POST(HttpRequest.BodyPublishers.ofString(s))
                )
                .map(HttpRequest.Builder::build)
                .map(request -> client.sendAsync(
                        request, HttpResponse.BodyHandlers.ofString()
                ))
                .toList();
        var allFutureRequests = CompletableFuture.allOf(
                futures.toArray( new CompletableFuture<?>[0])
        );

        allFutureRequests.join();
        List<String> lines = new ArrayList<>();

        futures.forEach(f -> {
            lines.add(f.join().body());
        });

        try {
            Files.write(orderTracking,lines, StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
