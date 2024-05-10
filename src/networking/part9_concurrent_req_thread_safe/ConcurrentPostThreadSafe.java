package networking.part9_concurrent_req_thread_safe;

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
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConcurrentPostThreadSafe {
    /*
     * Previously, we looked at how to execute 5 asynchronous post requests and write responses to a file
     *
     * In the sendPosts()
     *  - we used the body handlers ofString and compiled the response results into an array list of strings, posting
     *    that list to a file
     *
     * In the sendPostsWithFileResponse()
     *  - we used the body handlers ofFile with the append option
     *  - This worked but the body handler isn't thread safe and not recommended to use it as we did in the challenge
     *     for concurrent requests
     *
     * We'll look at how to write custom body handlers
     *
     *
     *
     * But first, let's look at how we might use a callback function to write the response to a file in a thread-safe manner
     *  - Set up a Reentrant lock
     *      - Any thread writing to the file will first acquire the lock
     *  - create writeToFile() that will acquire this lock before it writes to the JSON file
     *      - acquire the lock , and that's just lock.lock()
     *      - wrap the Files.writeString() in a catch block
     *          - pass the Path instance and pass the content with a new line appended to it
     *          - pass the Standard Open option "Append" as the 3rd param
     *      - catch any IOException and throw it
     *      - use a finally clause to call unlock() on lock so that the lock gets released in all cases
     *
     * Next,
     * Update sendPostsWithFileResponse() to sendPostsSafeFileWrite()
     *  - In the last map operation, update from using ofFile and go back to ofString with no args
     *  - then chain .thenAccept() and pass a lambda expression that takes the response.body()
     *      - notice that we don't use join() in the thenAccept() callback code
     *  - This function gets called after the response is complete and the processed response is passed as the
     *     parameter
     *      - so here we simply pass that string to the writeToFile()
     *
     * Call sendPostsSafeFileWrite() from the main()
     *  - comment sendGets()
     *  - call sendPostsSafeFileWrite() instead of sendPostsWithFileResponse()
     *  - delete orderTracking.json file
     *
     * Running the server and the client
     *  - server prints the request and the client prints the server response
     *  - generates orderTracking.json file with the 5 orders each printed on each line
     *
     * This is a way to leverage the callback ()s to continue additional processing on your responses
     *  - We used the callback to write to the file in a thread safe way
     *  - we can make the callback asynchronous as well
     *      - update thenAccept() to thenAcceptAsync() (comment it out)
     *      - delete the orderTracking.json file again
     *      - re-running again
     *          - and we get the same results
     *
     * If you're processing a large no of requests, using asynchronous callback may provide some performance gains
     *  for you
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

        sendPostsSafeFileWrite(client, baseUrl , urlParameters, orderMap);
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

    private static void writeToFile(String content){
        lock.lock();
        try {
            Files.writeString(orderTracking, content + "\r", StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            lock.unlock();
        }
    }
    private static void sendPostsSafeFileWrite(HttpClient client , String baseURI,
                                               String paramString , Map<String,Integer> orders){

        var futures  = orders.entrySet().stream()
                .map(e -> paramString.formatted(e.getKey() , e.getValue()))
                .map(s -> HttpRequest.newBuilder(URI.create(baseURI))
                        .POST(HttpRequest.BodyPublishers.ofString(s))
                )
                .map(HttpRequest.Builder::build)
                .map(request -> client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                       // .thenAccept(response -> writeToFile(response.body())))
                        .thenAcceptAsync(response -> writeToFile(response.body())))
                .toList();
        var allFutureRequests = CompletableFuture.allOf(
                futures.toArray( new CompletableFuture<?>[0])
        );

        allFutureRequests.join();

    }

}
