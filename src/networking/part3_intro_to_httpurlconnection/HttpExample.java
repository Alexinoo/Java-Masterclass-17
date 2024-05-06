package networking.part3_intro_to_httpurlconnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static java.net.HttpURLConnection.HTTP_OK;

public class HttpExample {

    /*
     * HTTP (Hyper Text Transfer Protocol)
     * ...................................
     * HTTP stands for Hyper Text Transfer Protocol
     * It's the foundation of data communication, for the World Wide Web on the internet operating over TCP/IP
     * It follows a client-server model, but importantly HTTP is stateless
     *  - This means that every HTTP request the server receives is independent and does not relate to requests that came prior to it.
     * HTTP messages consists of a request or a response, each containing a header, and an optional body
     * HTTPS is a secure version of HTTP
     *  - Encrypts data using special security protocols, providing a secure channel between the client and the server
     *
     *
     * HTTP Request Methods
     * ....................
     * The HTTP protocol supports various request methods
     * You might hear these called HTTP verbs in some circles
     * The most common methods are
     *  - GET - retrieves data from a site or server
     *  - POST - submits data to the server
     *  - PUT - updates data
     *  - DELETE - removes data
     * Additional ()s which are generally less commonly used are :- PATCH, HEAD, OPTIONS, CONNECT, and TRACE
     *
     * HTTP Response Codes
     * ...................
     * When your browser asks for a webpage, it will most often do what's called a GET request
     * The server that hosts the page will then respond and the response will include a code
     *  e.g. 200 means OK, the server found the web page and was able to return it with no problems
     *       404 means the server can't find the web page being requested
     *       500 means something went critically wrong, on the server side
     *
     * Implementations
     * ...............
     * Open a connection , but this time use HttpURLConnection
     *  - cast  url.openConnection() with
     * By casting it to a HttpURLConnection, we'll have access to more specific ()s related to HTTP requests
     *
     * Setting Request Headers
     * ........................
     * This entails setting the header fields which contain info about a request or a response
     *  - the header fields contain info about the web-page
     * Adding headers to a request can be achieved by using the setRequestProperty() method
     *
     *  - connection.setRequestMethod(String method)
     *      - takes a String literal rep the HTTP verbs
     *      - needs to be in Upper cases
     *      - GET is the default () for an HttpURLConnection
     *      - so it's not mandatory that we specify
     *
     *  - connection.setRequestProperty(String key , String value)
     *      - set user-agent property as an example
     *          - Takes a Key and a value of both strings
     *      - tells the server what browser or script you're using
     *      - in most cases, this is set by the client app making the request which would be typically be a browser of some sort
     *          - since we're using a java app to make this request, there is no browser
     *          - we can simulate by setting this to chrome
     *
     *      - If the property you are setting can accept multiple values, you can pass in a comma delimited string of values
     *          - "Accept" property on the header specifies what content types this request is willing to accept back
     *          - If the server can't provide either of these requested content types , it may return 406 response code , with a not acceptable message
     *
     *  - connection.setReadTimeout(int timeout)
     *      - And just as with sockets, you can also set a timeout value
     *          - If a website is down or the server is slow for some reason, your application won't hang waiting for a response
     *      - Pass 30000 ms
     *
     * Next,
     *  - connection.getResponseCode()
     *      - Check if the response code is good
     *          - store this in a responseCode variable
     *          - print out the response code
     *      - Otherwise, if it's not
     *          - print there was an error reading web page
     *
     * Next,
     *  - print the contents fo the web page
     *
     * Running this:
     *  - Prints the response code as 200
     *  - Prints the content of the web page afterwards
     *
     * Update the url to "http://example.com/extra"
     *
     * Running this:
     *  - Prints the response code as 500
     *  - Also prints there was an error reading the web page - "Internal Server Error"
     *
     * connection.getResponseMessage()
     *  - prints the response message from the connection
     *
     *
     *
     */

    public static void main(String[] args) {
        try {
            // URL url = new URL("http://example.com");
            URL url = new URL("http://example.com/extra");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent","Chrome");
            connection.setRequestProperty("Accept","application/json , text/html");
            connection.setReadTimeout(30000);

            int responseCode = connection.getResponseCode();
            System.out.printf("Response code: %d%n",responseCode);

            if (responseCode != HTTP_OK) {
                System.out.println("Error reading web page " + url);
                System.out.printf("Error: %s%n",connection.getResponseMessage());
                return;
            }
            printContents(connection.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static void printContents(InputStream is){

        try(BufferedReader inputStream = new BufferedReader(new InputStreamReader(is));) {
            String line;
            while ((line = inputStream.readLine()) != null) {
                System.out.println(line);
            }
        }catch (IOException e){
            System.out.println(e.getMessage());
        }

    }
}
