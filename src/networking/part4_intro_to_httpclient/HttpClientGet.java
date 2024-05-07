package networking.part4_intro_to_httpclient;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.stream.Stream;

import static java.net.HttpURLConnection.HTTP_OK;

public class HttpClientGet {

    /*
     * HttpURLConnection vs HttpClient
     * ...............................
     * Historically, however, many java developers felt the http client classes in java.net package, weren't robust
     *  enough
     * They preferred other alternatives, like Apache's Http, OKHttp, or even Jetty's client API
     * An incubator version of an enhanced Java Http client was introduced in JDK9
     * This incubator version wasn't well received, and the feedback was incorporated in the next version
     *
     * In JDK 11, Java released java.net.http package, with its own HttpClient class
     * HttpUrlConnection is still functional and hasn't been slated for deprecation
     *
     * There are significant differences between HttpURLConnection with HttpClient
     *
     *
     * Implementations HttpClient
     * ...........................
     * - Create an instance of HttpClient
     *      - call static () newHttpClient on the HttpClient class
     *      - creates a new instance with builder ()s which we'll cover later
     * - More importantly, it comes with default settings
     *      - This includes a preference of Http/2 protocol over Http/1
     *
     * HTTP/1.1 and HTTP/2
     * ...................
     * The newer HTTP/2 protocol was introduced in 2015, and has many advantages over the previous HTTP/1.1 version
     * HTTP/2 is faster
     * It uses several techniques like multiplexing and header compression to transfer data more efficiently, resulting
     *  in faster web page loading times
     * HTTP/2 is also more efficient
     *  - allows prioritization of critical resources so that the most important content can be loaded first, leading
     *    to a smoother user experience
     * HttpUrlConnection works only with HTTP/1.1
     * Java's Http Client supports both
     * You might also remember that with HttpURLConnection, it wasn't always obvious when a request was really being sent
     * With HttpClient, we first create a request obj and then invoke a send() on the client instance, passing the request
     *  obj
     * This makes the workflow more transparent and easier to understand
     * Like the HttpClient the HttpRequest can also be constructed with a builder pattern
     * But unlike HttpClient, there's not a simple () to do it in a single step
     * The builder pattern provides a way to construct obj(s) that have a large no of optional parameters or configurations
     * The JPA criteria builder and criteria query classes also uses a builder pattern to build up instances
     *
     * For HttpRequest,
     *  - We start by calling newBuilder() on HttpRequest class and chain specific ()s to it
     *      - First, we'll call GET  - sets the request () to GET
     *      - Next, we'll chain the uri() - takes a URI instance - transform url with toURI()
     *          - toURI() throws a checked exception (URISyntaxException)
     *      - Next, call build() - returns the request instance
     *  - We can still set header values and timeout as part of the builder
     *      - chain header() and pass a single key-value pair as 2 strings
     *      - chain headers() to pass multiple key-value pairs
     *          - specify the key in both instances
     *      - chain timeout()
     *          - takes a duration , and so we can use Duration class with one of it's static ()s to specify the time
     *          - in this case, use ofSeconds() and pass 30
     *
     * The HttpClient builder code is a lot easier to read, because it's less verbose
     * The chaining mechanism of the builder pattern also quickly provides the info that all of these ()s are specific
     *  to the request
     * Hopefully, you remember that calling getResponseCode() on HttpURLConnection instance, under the hood, makes a
     *  connection, sends the request and receives a response
     * With HttpClient, we instead specifically use the client instance, invoking a send() that takes a request obj
     *  - When we call the send(), we get a HTTPResponse back
     *      - The type of the data we get back is dependent on the type of body handler we use
     *      - In this case it will be a String
     *  - send()
     *      - Takes a request obj and the 2nd specifies how we want to handle the response body
     *      - The body handlers class on HTTP response has several options but in this case if we call ofString(), the
     *         body gets returned as a string
     *      - The response code is called status code on the response instance
     *          - check if it's not OK, using a constant on HttpURLConnection
     *              - if so , print there was an error and print the url, and exit
     *      - send() throws an InterruptedException - add it to catch clause
     *
     *  - Finally, with HttpClient, there's no input stream to deal with, unless we actually chose that option with a
     *    different kind of body handler
     *  - We can print out the result of the response body, as we used the String body handler
     *
     *
     * Before running this:
     *  - Make a few change on SimpleHttpServer.java
     *      - Print the request headers
     *      - We can get the request headers from the exchange handler instance by calling getRequestHeaders
     *          - Then chain entrySet() and call forEach to print each header entry using a println method reference
     *
     * Let's start SimpleHttpServer.java and then run HttpClientGet.java
     *  SimpleHttpServer.java
     *      - outputs Request method
     *      - body data - in this case empty
     *      - outputs header properties - Accept that we set
     *      - the next 4 headers are created under the covers
     *          - Connection
     *          - Http2-settings
     *          - Host
     *          - Upgrade - negotiate protocol upgrades, which enables more advanced communication protocols
     *
     * HttpClientGet.java
     *      - outputs the response body
     *
     * Body Handlers
     * .............
     * The body handler's class is on the HTTPResponse and provides implementations of a body handler class
     * They provide various useful out of the box handlers , each of which handles the response data in diff ways
     * Pull API for the body handlers class on HttpResponse
     *  - Check all the ()s that are prefixed with of
     *      - ofByteArray()
     *      - ofFile()
     *      - ofInputStream()
     *      - ofLines()
     *      - ofPublisher()
     *      - ofString() - the one we used in our code
     *  - All of these are commonly used and Java provides these helpers to us,& don't have to write the boiler plate code
     *  - These are all the diff off the shelf ways java provides that lets us process the response data
     * In other words, we don't have to write the printContents as we did from scratch every time we use the HttpUrlConnection
     *
     *
     * Next,
     *  Let's change the handler to be a stream of string
     *
     * First, we need to change the type that my HttpResponse container will contain from String to Stream<String>>
     *  - Then update ofString() to OfLines() which returns each line of text as a string on a stream pipeline
     *
     * Comment out on the print statement that prints the response body
     *  - The response body is now a Stream<String>, a source for a stream pipeline and so we can use all the stream operations
     *    on the response
     * Get the response body, the source of the stream pipeline
     *  - Filter header tags on the html page , specifically the h1 tag
     *  - Use Map operation to strip out html tags from the string and remove any extra white space
     *      - use replaceAll() with a common regex to match on HTML tags and replace with an empty string
     *          - chain strip() to remove leading or trailing white spaces
     *  - use forEach and pass println as a method reference
     *
     * Rerunning both the server and the client
     *  - The client code only prints the text that was contained within the h1 tag, printing its contents only
     *
     * You can now imagine how you'd use HttpClient to query info specific to your needs with all of this built in
     *  functionality
     *
     * This code is easier to read and much simpler to use especially with the ability to use diff body handlers
     * It also used HTTP/2 protocol to do the work
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

           // HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());
             HttpResponse<Stream<String>> response = client.send(request,HttpResponse.BodyHandlers.ofLines());
            if (response.statusCode() != HTTP_OK){
                System.out.println("Error reading web page "+url);
                return;
            }
           // System.out.println(response.body());
            response.body()
                    .filter(s -> s.contains("<h1>"))
                    .map(s -> s.replaceAll("<[^>]*>","").strip())
                    .forEach(System.out::println);
        } catch (IOException | URISyntaxException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

}
