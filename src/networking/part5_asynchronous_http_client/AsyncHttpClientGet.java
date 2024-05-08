package networking.part5_asynchronous_http_client;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Stream;

import static java.net.HttpURLConnection.HTTP_OK;

public class AsyncHttpClientGet {

    /* HttpClient Asynchronous Request, CompletableFuture
     * ..................................................
     *
     * New HttpClient provides two possibilities for sending a request to a server:
     *  - send() – synchronously (blocks until the response comes)
     *  - sendAsync() - asynchronously (doesn't wait for the response, non-blocking)
     *
     * Move the code that processes the response into a separate private ()
     * handleResponse(HttpResponse<Stream<String>> response)
     *  - Takes a response
     *  - Returns void
     *  - Check
     *    - if the status code is 200
     *      - filter element with h1 tag
     *      - strip the tags and print the plain text
     *    - otherwise, prints there was on the response form the response url
     *
     *  - Print a new line after retrieving response
     *  - Call handleResponse() & pass the response body
     *
     * Running the server and the client
     *  - SimpleHttpServer.java
     *      - listens on port 8080
     *      - print request method , body data and other headers data
     *  - AsyncHttpClient.java
     *      - Prints the response from the server, not until the 5 seconds sleep is over
     *
     * Let's see if we can add code that will at least let the user know it's just in a waiting pattern
     *  - Add this code after the response variable is declared, but before calling handleResponse
     *      - Loop as long as result is -1 ,
     *          - Print a dot with a space and wait a second before printing the other one
     *      - break out of the loop if the result changes
     *
     * Running the server and the client
     *  - Server runs normally
     *  - client doesn't print the dots at all and after 5 sec, we can see the response data
     *
     * This is because send() on HttpClient is synchronous & it blocks there
     *  - the code isn't even getting to the start of the while loop until a response is returned from the send() because of the delay we added in
     *     the server
     *
     * Solution
     * ........
     * Either use threads
     * use the HttpClient's asynchronous version of the send() called asyncSend()
     *
     * send() vs asyncSend()
     * .....................
     * Synchronous send()
     *  - Returns HttpResponse<T> response = client.send(request,Http.BodyHandlers.ofString())
     *
     * Asynchronous send()
     *  - Returns CompletableFuture<HttpResponse<T>> responseFuture = client.sendAsync(request,Http.BodyHandlers.ofString())
     *
     * Notice the difference between the return type and the method name
     *  - send() blocks when it's invoked until a response is returned
     *  - sendAsync returns immediately with a special type called a CompletableFuture
     * The () parameters are the same for either call
     *
     * As seen earlier, the return type is different for these 2 methods
     *  - send() we get back an HttpResponse<T>
     *  - sendAsync() we get back a CompletableFuture
     *      - HttpResponse<T> becomes a type argument itself for a completable future type (CompletableFuture is wrapping the response or is a
     *        container for that type)
     * However, the type in this case is determined by the body handler we use, this could be String, Path, Stream<String> etc
     *
     * We covered the Future type in the concurrent programming section & we said it represents a result of an asynchronous computation
     * 
     * Future vs CompletableFuture
     * ...........................
     * A Future is an interface which the concrete CompletableFuture class implements
     * The CompletableFuture specifically extends Future's capabilities to support asynchronous processing
     * Officially, the Java API states:
     *  - A CompletableFuture is a Future that may be explicitly completed (by setting its value and status), and may be used as a CompletionStage
     *    , supporting dependent functions and actions that trigger upon its completion
     * The keywords here are CompletionStage and actions that get triggered
     *
     * If you're familiar with Promises and callbacks in Javascript or some other language, you'll have some idea about what a completable is and
     *  how it works
     *  - It's a promise of a future result, so in the case of sendAsync on Http client, it would be a future Http request response
     *
     * You can use specific ()s on completable future, to execute code at certain lifecycle stages
     *  - In other words, there are ()s on completable future that support callbacks
     *
     * What's a Callback ?
     * ...................
     * A callback is a general programming concept, which describes passing a function as an argument to another function
     *  - This gets called later, in the future, when a specific event occurs
     *  - Significantly, the calling function doesn't wait around for the callback to be executed
     *
     * Lambda expressions don't get executed, if they're used as method arguments, until the method is invoked
     * Similarly, callbacks don't get executed until a certain stage, or triggering event occurs
     *  - It's yet another level of deferred execution of a stored function, if that makes sense
     * These special types of ()s (which takes callbacks as arguments) wait until a certain event is triggered
     *
     *
     *
     * Back to the code
     *  - Replace send() with sendAsync
     *  - Update HttpResponse<Stream<String>> with CompletableFuture<HttpResponse<Stream<String>>>
     *    - In my case , will comment out on both lines
     *  - Comment on the while loop
     *  - Change the while loop condition to simply check the response future's done status by calling isDone() on responseFuture variable
     *  - Next, we need to get the data and set the response
     *  - When the while loop completes, we know that the response has been returned, but how do we use it or get it
     *
     * There are several ways we can do this:
     *  - The Future interface has a get() and we can use it here to get the response
     *      - Surround with try-catch since get() throws a checked exception - ExecutionException
     *
     * Running the server and the client
     *  - The dots are now printed out, 1 for each second that elapses until 5 seconds elapse and then the response is printed
     *    - This means , the main thread is free to do other work in this while loop and isn't blocked on the send()
     * We're still looping to periodically check the status of this response future's state, which is a big improvement over a blocked thread
     *
     * Before we look at callbacks, let's look at other ways to get the response, once its complete
     *
     * The thread had a join()
     *  - join() was a blocking (), if used without a timeout, to have the current thread wait for the spawned thread to finish its job, to die in
     *     other words
     * The CompletableFuture has a join() too
     *  - also blocks as it does for a thread, until the future completes, but in this case, the join() also returns the response and doesn't throw a
     *    checked exception
     *
     * Next,
     *  - Comment on the try-catch where we used get()
     *  - call join() on the responseFuture
     *
     * Running the server and the client again:
     *  - The behavior is exactly the same as if I'd used get, so join returns the same response without throwing a checked exception
     *
     * Next,
     *  - We could also use the getNow() in the while loop itself
     *      - Instead of using the status code, followed by using either get() or join(), to retrieve the response, we can combine these operations
     *          - comment on the join statement
     *      - Change the condition statement in the while loop
     *          - comment on while and add another afresh
     *          - start by assigning the response variable to the value we get back from calling getNow() on the responseFuture
     *              - getNow() takes 1 arg, which is the value that gets returned if the response is absent, meaning the response future isn't done
     *                  - set the arg passed to null and immediately check if what is returned is null and if so, continue to loop
     *              - The argument of the getNow() has to be the same type as the type that would get returned
     *                  - In this case, that's a HttpResponse<Stream<String>>
                    - Though it doesn't make a lot of sense right now to return anything but null in this case
     *
     *
     * Running the server and the client again:
     *  - The result is exactly the same
     *
     * Finally, there is a get() with a timeout
     *  - Re-write the while loop (in my case comment it out)
     *  - Run while loop as long as it is true
     *      - call get() and pass 1 second on responseFuture variable and assign this result to response variable
     *          - means the code waits here 1 sec for a response, then throws an exception if a response isn't returned before that
     *      - If the response isn't null, we can break out of the loop
     *      - There are 2 checked exceptions that might get thrown, ExecutionException and TimeoutException
     *      - For ExecutionException
     *          - throw a run time exception
     *      - For TimeoutException
     *          - print the dot, stay in the loop and try again
     *
     * Running the server and the client again:
     *  - The behavior is the same in this case, but we have more fine-tuned control over what might happen
     *
     * In all of these cases, we're looping to check the status of the future, either by checking isDone(), or by getting a response back
     *  - This keeps the thread available for other work, while the completable future is not yet done
     *
     * Next video, we'll do the same thing but use special ()s on completable future that take callback functions
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

           // HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());
           //  HttpResponse<Stream<String>> response = client.send(request,HttpResponse.BodyHandlers.ofLines());
            HttpResponse<Stream<String>> response;
             CompletableFuture<HttpResponse<Stream<String>>> responseFuture =
                     client.sendAsync(request,HttpResponse.BodyHandlers.ofLines());
            /*
            int result = -1;
            while ( (result = response.statusCode()) == -1){
                System.out.print(". ");
                TimeUnit.SECONDS.sleep(1);
            } */

            /* while (!responseFuture.isDone()){
                System.out.print(". ");
                TimeUnit.SECONDS.sleep(1);
            } */


           /* while ( (response = responseFuture.getNow(null)) == null){
                System.out.print(". ");
                TimeUnit.SECONDS.sleep(1);
            } */
            while (true){
                try {
                    response = responseFuture.get(1, TimeUnit.SECONDS);
                    if (response != null)
                        break;
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                } catch (TimeoutException e) {
                    System.out.print(". ");
                }

            }


            System.out.println();
            /* try {
                response = responseFuture.get();
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }*/

           /* response = responseFuture.join(); */

            handleResponse(response);

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


}
