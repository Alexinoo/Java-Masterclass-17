package networking.part6_completable_future_methods;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static java.net.HttpURLConnection.HTTP_OK;

public class AsyncHandlerClient {

    /*
     * CompletableFuture Methods
     * .........................
     *
     * We looked at event driven concepts with java's selectable channels that uses selectors allowing channels to register for events
     * Another feature in java that supports the event driven model, is the completable future's series of then methods
     *
     * Selector vs CompletableFuture
     * .............................
     * The Selector is more low-level, and is specifically designed for non-blocking I/O operations
     * The CompletableFuture with its support for callback functions, is higher-level and not really specific to a single use case, though we're
     *  examining it here, as part of the HTTP Client
     * It's a general purpose class designed for managing asynchronous computations
     *
     * Previously,
     * - We left off with a while loop which periodically checked the status of this response future's state, or it's value but between checking,
     *   the thread was available for other work
     * - Let's change the main focus of the main thread to something else while still handling the response we get back from the request we made
     *
     * Copy AsyncClientGet.java from previous package
     *  - Remove the code from the while loop up to the catch clause
     *
     * We're not going to loop or check for the status code, instead we'll loop though to do something unrelated to the request we made, so let's say
     *  we've got 10 other things we need to do
     *  - Print that there are 10 jobs to do
     *  - Set a counter variable that prints 10 times but sleep for 1 second (emulating the length of time to do one fo these jobs)
     *     before printing the next iteration
     *      - print job index each time to see how we're progressing with the work
     *  - Non eof this has to do with the request we sent , or the response that we'll get back
     *  - It's a valid use case to make a request and maybe ignore any response back
     *  - Imagine you're posting a time card entry at the start of this, it doesn't really matter if there's a response
     *  - But now let's say we do care about the response in some way but as a background function
     *      - Maybe we're building a data set and know it will take a while, and we'll process other stuff while it's getting built
     *      - Between the call to sendAsync and the while loop, we can specify what we want to happen with the response future completes
     *
     * There are several ()s we can use that take lambda expression of different types
     *  - thenAccept()
     *      - call thenAccept() on responseFuture
     *          - pass lambda expression variable , letter r stands for the response that we get back (This would be the type that got wrapped in
     *             that completable future) , so here it would be an HttpResponse<Stream<String>>
                - we'll call handleResponse() pass ing it the response instance
     *              - replaced with lambda expression (IntelliJ inlay hint)
     *          - the handleResponse() has 1 parameter and doesn't return any data - void return type
     *      - This is what functional accept() does
     *
     * Running the server and the client
     *  - It's working its way through the jobs but after job 5, we get the response printed out and then the jobs continue
     *
     * So while we're processing these jobs, the code to handle the response occurred, that process was really unrelated to this in terms of events
     *  and data
     * The main thread didn't have to manage the response handling
     *  - It was done in another process, once we pass a callback function to the thenAccept()
     *
     * There's is some risk in this example, that if the main thread finishes, the callback function may never complete
     * Would you want to have the main () oblivious to the results of the callback? ( Maybe or maybe not)
     *  - depends entirely on your own requirements
     *      - maybe you are writing the results to a file,
     *      - or building a large data set of some sort, and you might as well multi-task
     *  - There are use cases for both types of processing
     *  - The diff between these 2 methodologies is a bit subtle and the instructor would not like us to miss the difference
     *
     * Without a callback, the response would never get processed if we used this kind of loop
     *  - But because we're using a callback which processes the completion apart from the main code, there's no requirement for the calling thread
     *    to wait for that event
     *
     * Different Approaches to the Asynchronous Send
     *
     * Using Callback (Event Driven)
     * ..............................
     * The sample code specifies what should happen when the response completes - Nothing more needs to happen for that to occur
     * The only requirement is the application has to remain running long enough, for the event to occur and the function to be executed
     * The result of the callback isn't relevant to the calling thread's processing
     *      responseFuture.thenAccept(AsyncHandlerClient::handleResponse);
     *      while(true) {
     *           // do other stuff
     *      }
     *
     * Polling for Completion Status (Linear or procedural flow)
     * .........................................................
     * In this sample, the main thread is polling the status of the response future but can still do work while it's waiting
     * You then define what to do with the response once it's available
     * This () might seem more straightforward and intuitive, but the callback approach provides a lot more options
     *
     *      while(!responseFuture.isDone()){
     *          // do Stuff while waiting
     *      }
     *      response = responseFuture.join();
     *      handleResponse(response);
     *
     * Pull up CompletableFuture class methods in the java API
     * In java, how the callback is handled is determined by the JVM when you're using thenAccept()
     * If you've got a callback function with blocking IO, or you want to use a different thread pool, you may want to use the async
     *  versions of these methods
     *
     * If the callback function is lightweight and doesn't involve blocking or long running operations, you can safely stick to the non async versions
     * There are other versions of thenAccept() which let's you pass a completion stage to them
     * It is important to understand that the completable future is an implementation of a completable stage
     *  - Where we sees it as a parameter type, this means we can pass a completable future there and process the completion stages of both jointly
     *
     * We also have ()s that takes functional interfaces
     *  - These are thenApply() which takes a function lambda as a parameter
     *
     * We also have thenRun()
     *  - These takes a runnable
     *
     * There are a lot of ()s on this class which lets you take advantage of asynchronous processing, using multiple completable futures
     *  - We'll look at multiple concurrent completable futures in the next video
     *
     * Back to the main()
     *  - Split up the 1 handleResponse() into 3 different stages/methods to demo a couple of the other ()s
     *  - Copy handleResponse() and paste a copy below
     *      - handleResponse(HttpResponse<Stream<String>>) : void
     *      - filterResponse(HttpResponse<Stream<String>>) : Stream<String> - will only filter the stream
     *          - print "filtering response"
     *          - if status code is OK
     *              - return filter
     *          - otherwise, return empty stream
     *      - transformResponse(Stream<String>) : Stream<String> - call the second part of the stream operation - map
     *          - print what it's doing
     *          - will remove all html tags and extra leading or trailing white spaces
     *      - printResponse(Stream<String>): void
     *          - print what it's doing
     *          - call forEach terminal operation on this stream and print each element
     *
     * Back to main()
     *  - Pass filterResponse() instead of handleResponse
     *      - in my case , will comment it out
     *
     * Running server and the client:
     *  - see the jobs printed and we see "filtering response" message
     *  - that's all we see and that's what it does
     *
     * Now let us try chaining a method to that
     *  - remove a semi-colon at the end of the statement
     *  - chain another thenApply() and pass the second method reference - transformResponse
     *      - results to incompatible types ( void to Stream<String>> )
     *      - This means that void is returned from the first () - thenAccept(AsyncHandlerClient::filterResponse)
     *      - The second chained (), also a thenAccept, is expecting to use the results of the first () as it's own input but void isn't valid input
     *      - So you can't chain a thenAccept or a thenApply () to a thenAccept() because both of them take arguments
     *
     *  - So we need to change the first () to thenApply() which takes an argument and returns a value
     *      - This compiles and we could run it but we're still not doing anything with the Stream<String> or the response
     *  - We need to call the last () which has the stream's terminal operation against it
     *      - Here we can only use thenAccept() because the () we're passing to it returns void and requires a single parameter
     *      - this time we'll be using printResponse as the callback function as state
     *
     * Running the server and the client again:
     *  - Prints the jobs and the all 3 ()s got executed with "Hello World..." printed at the end of it
     *
     * We could keep chaining ()s but since there's no results coming back from thenAccept(), we are only able to chain run() at this point
     * Let's say after the response is processed, we want to run some final process
     *  - print out numbers 0 through 10 on a single line
     *      - call thenRun() and pass it a lambda, with a for loop in it
     *  - Runnable takes no arg and returns no data and we'll just print each number out
     *
     *  - Will then chain another runnable that prints a new line
     *
     * Running the server and the client again:
     *  - We can see after the response is processed, the code is doing other stuff at that point,
     *      - so it's printing 0 through 9 and following that with a new line print statement
     *
     * In some ways, this feels like working with stream operations or builder methods
     *
     * We have seen how we might build some kind of response handling chain of events which could be challenging to do in a more procedural way
     *
     * We'll look at how we can take this step further to do multiple concurrent requests
     *
     */

    public static void main(String[] args) {
        try {

            URL url = new URL("http://localhost:8080");

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(url.toURI())
                    .header("User-Agent","Chrome")
                    .headers("Accept","application/json","Accept","text/html")
                    .timeout(Duration.ofSeconds(30))
                    .build();

            CompletableFuture<HttpResponse<Stream<String>>> responseFuture =
                     client.sendAsync(request,HttpResponse.BodyHandlers.ofLines());

           // responseFuture.thenAccept(response -> handleResponse(response)); -- same as below
           // responseFuture.thenAccept(AsyncHandlerClient::handleResponse);

           /* responseFuture.thenAccept(AsyncHandlerClient::filterResponse)
                    .thenApply(AsyncHandlerClient::transformResponse); */

              responseFuture.thenApply(AsyncHandlerClient::filterResponse)
                    .thenApply(AsyncHandlerClient::transformResponse)
                      .thenAccept(AsyncHandlerClient::printResponse)
                      .thenRun(() -> {
                          for (int i = 0; i < 10; i++) {
                              System.out.print(i +" ");
                          }
                      })
                      .thenRun(System.out::println);

            System.out.println("Ten Jobs to do besides handling the response");
            int jobs = 0;
            while (jobs++ < 10){
                System.out.printf("Job %d%n", jobs);
                TimeUnit.SECONDS.sleep(1);
            }
        } catch (IOException | URISyntaxException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    private static void handleResponse( HttpResponse<Stream<String>> response ){
        if (response.statusCode() == HTTP_OK){
            response.body()
                    .filter(s -> s.contains("<h1>"))
                    .map(s -> s.replaceAll("<[^>]*>","").strip())
                    .forEach(System.out::println);

        }else{
            System.out.println("Error reading response from "+response.uri());
        }
    }

    private static Stream<String> filterResponse( HttpResponse<Stream<String>> response ){
        System.out.println("Filtering response...");
        if (response.statusCode() == HTTP_OK){
            return response.body()
                    .filter(s -> s.contains("<h1>"));
        }else{
           return Stream.empty();
        }
    }

    private static Stream<String> transformResponse(Stream<String> response) {
        System.out.println("transforming Response...");
        return response.map(s -> s.replaceAll("<[^>]*>","").strip());
    }

    private static void printResponse(Stream<String> response){
        System.out.println("printing Response...");
        response.forEach(System.out::println);
    }

}
