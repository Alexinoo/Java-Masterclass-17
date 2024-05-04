package networking.part2_http_url_uri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class WebContent {

    /*
     * Start with a url to a test website
     * Use URL constructor to create this and catch MalformedURLException
     * There are versions of URL constructor that accepts protocol, a host, port, file and other info but when reading
     *  webpages, an API or a service on the internet, you usually h=just have to provide an http web address and include
     *  any required parameters that form part of the URL
     *
     * Add a private printContent(InputStream is)
     *  - Takes an input stream and writes the content to the console
     *  - Create a new Buffered Reader pass an new input stream reader
     *  - Create a Sting line variable
     *      - don't need to initialize it here as it will get set on the next line
     *  - Set up a while loop with a condition I can both assign a value to the line variable by calling inputStream.read()
     *     within the parenthesis and then check if that's not null in the same condition
     *  - Print IOException if any
     *
     * main()
     *  - Call printContents() and pass the result of calling openStream() on url
     *      - replace MalformedException with a generic IOException
     *
     * Running this :
     *  - Prints the entire webpage, the html in its entirety is returned
     *
     * So what does the url.openStream() do ? Ctrl+click
     *  - An openConnection() is executed with getInputStream() chained to that
     *  - The comments tell us that this () opens a connection to this URL and returns an InputStream for reading
     *     from the connection
     *  - It's really just a shortcut for us to use
     *  - If you don't need to adjust any settings on your connection, this is all you need
     *
     * Let's try a different URL,
     *  - this time one that returns a bit of JSON
     *
     * Rerun this code:
     *  - We get back a little bit of JSON here that has a user ID, id, title and completed
     *  - This test site contains a to do list , each item' title is in Latin
     *
     * Comment out on printContent() line
     *
     * This time will connect a different way
     *  - Manually get a connection, which lets us get info about the connection
     *  - Setup a variable URLConnection by calling openConnection on our url
     *  - Once you have the connection, you can have quite a bit of info about the resource through it e.g.
     *      - content tye of the page
     *
     * Running this:
     *  - The content type of the 2nd url is application/json and the charset is utf-8
     *
     * A web page comes with a lot of data in its header fields
     *  - get header fields which returns a Map of key-value pairs
     *      - means we can chain an entrySet and call forEach passing a () reference of println
     *  - Running this again
     *      - there are a lot of header fields, some of which might be important
     *
     * If tou want to get a single header field, you can also get it by its key
     * Say, we want cache-control value
     *  - We can call getHeaderField() on the urlConnection instance passing it cache-control
     *
     * Running this:
     *  - Prints cache-control (max-age = 43200)
     *      - The cache-control header field specifies the max time in seconds , a cached response can be considered
     *         fresh without revalidation
     *
     * So we've created a URLConnection obj, which lets us query info about the site
     * Please note that calling openConnection, doesn't actually establish the actual network connection which can
     *  be quite confusing
     *
     * You'll establish the network connection by calling URL connection.connect() which we'll add shortly
     *
     * Because of the 2-step process, you can use the instance to configure the connection before you actually connect
     *  to it
     * We've already seen you can get preliminary info but you can also make changes
     *  - say whether you want to read or write to the connection if that's available for the protocol you're using
     * This gives you a chance to send any configurations settings that will ultimately influence how the cionnection
     *  is made
     *
     * We'll call connect() on urlConnection instance
     *  - then call printContents() and pass it the result of calling getInputStream on the urlConnection
     *
     *
     * Running this:
     *  - prints json data as before
     *
     * We'll explore this connection and some of the things we can do with it
     */

    private static void printContent(InputStream is){

       try(BufferedReader inputStream = new BufferedReader(new InputStreamReader(is));) {
           String line;
           while ((line = inputStream.readLine()) != null) {
               System.out.println(line);
           }
       }catch (IOException e){
           System.out.println(e.getMessage());
       }

    }

    public static void main(String[] args) {

        try {
            //URL url = new URL("http://example.com");
            URL url = new URL("https://jsonplaceholder.typicode.com/todos?id=5");
            //printContent(url.openStream());
            URLConnection urlConnection = url.openConnection();
            System.out.println(urlConnection.getContentType());
            urlConnection.getHeaderFields()
                    .entrySet()
                    .forEach(System.out::println);
            System.out.println(urlConnection.getHeaderField("Cache-Control"));

            urlConnection.connect();
            printContent(urlConnection.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
