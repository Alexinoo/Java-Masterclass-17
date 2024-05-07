package networking.part4_intro_to_httpclient;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.time.Duration;

import static java.net.HttpURLConnection.HTTP_OK;

public class HttpClientPost {

    /*
     * HttpClient - Post a Request
     * ...........................
     * Start with other techniques that we can use to create HttpClient instance using a few builder techniques
     * This will start similarly to that of HttpRequest, with a call to newBuilder, but on the HttpClient class
     *  - Then chain the methods to it
     *      - First, chain connectTimeout() and set Duration of 1 min
     *      - Second, chain version() and use HttpClient.Version enum - select HTTP_1_1
     *          - so for some reason, if you know your server isn't using HTTP/2, you could do this , or specifically chose HTTP/2 though it's the
     *            preferred protocol
     *      - Finish with the build () that returns the completed instance of the client obj
     *
     * Next,
     *  - Update the request () to POST
     *  - Unlike the GET (), the POST () needs an arg of the type HttpRequest.BodyPublishers.ofString
     *  - This is somewhat similar to the http request body handler
     *      - This will specify how to write the data to the request body
     *      - Then pass the data that we want to be delivered in the request body
     *  - Instead of setting up a URL variable to pass it to this uri(), we'll look at how to do it in-line
     *      - pass URI.create() and then pass the actual url
     *      - The create() catches the URLSyntaxException and will throw a none checked exception which makes it kind of nice to use here in these
     *         chained build ()s
     *          - remove URLSyntaxException from the catch clause
     *  - Remove the declaration of the URL
     *      - in my case, comment it out
     *      - replace url with request.uri() in the println statement
     *  - Remove the header() and header()s
     *      - add content type header
     *
     * Running this:
     * HttpClientPost.java
     *  - The output is similar to the one for the GET, i.e. "Hello World from my Http Server"
     *
     * SimpleHttpServer.java
     *  - Prints the request method which is POST
     *  - Body data is printed as well as the mapped parameters derived from that
     *  - The user agent has changed as we're not specifying it in this case
     *      - prints the correct user agent, a java http client and the version of java
     *  - The content type is the one we specified in the POST request
     *  - ALso prints the content length which we didn't specify - done for us by publisher
     *
     *
     * Next,
     * Change the HttpClientPost class and use a different body handler
     *  - Replace the return type from HttpResponse<Stream<String>> with HttpResponse<Path>
     *  - Replace ofLines() with ofFile() and pass Path.of() specifying the filename
     *      - In my case, I will comment out
     *  - This sends the output we get back to a file in the project's root directory named "test.html"
     *
     * Comment out on the code that prints out the response - previously working with Stream<String>

     * Running the server and the client
     *  - Client does not output anything because the output is directed to the file named "test.html"
     *      - generated "test.html" in root folder of this project with the values displayed in the input fields
     *  - Server prints the same details
     *
     * HttpClient and HttpURLConnection are distinct in 2 broader categories
     *  - Feature level
     *  - Application design and ease of use
     *
     *
     *
     * Feature Level Support
     * ......................
     *
     *
     * Feature                                          HttpClient                                  HttpURLConnection
     * .......                                          ...............                             ......................
     *
     * Package                                          java.net.http (java 11 and later)            java.net (Java 1.1 and later)
     *
     * HTTP Protocol Support                            HTTP/1.1 and HTTP/2                          HTTP/1.1
     *
     * Execution Model                                  Synchronous and asynchronous (non-blocking)  Synchronous (blocking)
     *
     * Features                                         Built-in handling for Cookies, redirects     Requires manual handling for cookies, redirects
     *                                                   & body processing                            ,bodies etc
     *
     * Advanced                                         Compression , Authentication schemes         Limited
     *
     * //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
     * //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
     *
     * Application Design and Ease of Use
     * ...................................
     *
     * Feature                                          HttpClient                                  HttpURLConnection
     * .......                                          ...............                             ......................
     *
     * Package                                          java.net.http (java 11 and later)            java.net (Java 1.1 and later)
     *
     * API                                              Fluent , builder-based                       Traditional, method-based
     *
     * Response Handling                                Reactive streams, Helper functions for       Require traditional stream or byte array read
     *                                                   basic response type handling
     *
     * Ease of Use                                      Simplified API, often less code for          Verbose for common tasks
     *                                                  common tasks
     *
     *
     * Key Takeaways
     * .............
     * HttpClient provides a more modern alternative to HttpUrlConnection
     * It's an efficient and feature-rich API for HTTP interactions
     * Oracle has been prioritizing development efforts on HttpClient, indicating a clear preference for its usage in new projects
     * Leading frameworks and libraries in the Java ecosystem, such as Spring, are increasingly adopting the JDK's HttpClient
     *
     *
     * Summary
     * .......
     * HttpUrlConnection is still usable, but it's future is uncertain
     * Use HttpClient for new projects in preference over HttpUrlConnection
     * It's the preferred choice , due to its modern design, performance, and ease of use
     * Consider migrating existing code to HttpClient, for better maintainability, and alignment with future java development trends
     *
     *
     *
     */

    public static void main(String[] args) {
        try {

            //URL url = new URL("http://localhost:8080");

            HttpClient client = java.net.http.HttpClient.newBuilder()
                    .connectTimeout(Duration.ofMinutes(1))
                    .version(java.net.http.HttpClient.Version.HTTP_1_1)
                    .build();


            HttpRequest request = HttpRequest.newBuilder()
                    .POST(HttpRequest.BodyPublishers.ofString(
                            "first=Joe&last=Smith"
                    ))
                    .uri(URI.create("http://localhost:8080"))
                    .header("Content-Type","application/x-www-form-urlencoded")
                    .build();

           // HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());
           // HttpResponse<Stream<String>> response = client.send(request,HttpResponse.BodyHandlers.ofLines());
             HttpResponse<Path> response = client.send(request,HttpResponse.BodyHandlers.ofFile(Path.of("test.html")));
            if (response.statusCode() != HTTP_OK){
                System.out.println("Error reading web page "+ request.uri());
                return;
            }

           // System.out.println(response.body());
            //  response.body()
            //       .filter(s -> s.contains("<h1>"))
            //       .map(s -> s.replaceAll("<[^>]*>","").strip())
            //       .forEach(System.out::println);

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

}
