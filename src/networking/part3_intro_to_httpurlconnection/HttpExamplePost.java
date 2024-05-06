package networking.part3_intro_to_httpurlconnection;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import static java.net.HttpURLConnection.HTTP_OK;

public class HttpExamplePost {

    /*
     * HttpURLConnection Posts
     * .......................
     * Copy HttpExample to HttpExamplePost
     *  - Update setRequestMethod to "POST"
     *
     * Add info specific for posting data
     * First, to have data sent to the server, we need to execute the method : setDoOutput(boolean doOutput) and pass true
     *  - This indicates that the connection will be used for output
     * Next, set the content type toa specific value
     *  - This is "application/x-www-form-urlencoded", tells the server that the content is formatted in a very special way
     *  - Each key-value pair is separated by an ampersand (&) and the key and value are separated by an equal sign
     *
     * Next, set a String variable named parameters
     *  - Set 2 parameters in exactly the format we just specified above
     * It's also a good practice to include the length of the content in the header
     *  - call getBytes() on the parameters and chain length property
     * Then set the content length header field to the string value of the length variable
     * We still need to populate the request body with the parameter data
     * Next set the DataOutputStream
     *  - Call DataOutputStream() constructor and pass connection.getOutputStream()
     *      - call writeBytes() on the above instance
     *      - then flush the output stream
     *      - then close the output
     *
     *
     * Running this:
     *  - Fire SimpleHttpServer.java
     *  - Run HttpExamplePost.java
     *
     *  - Server Output
     *      - Prints "Server is listening on port 8080"
     *      - Prints Request method is POST
     *      - prints body data that we sent in "HttpExamplePost.java"
     *
     * Next, let's tweak the code in this class
     *  - comment out on "connection.setDoOutput(true);"
     *      - running while this is commented throws an error
     *      - revert back
     *
     *  - comment out on "connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");"
     *      - running while this is commented works
     *      - revert back
     *
     * So, it's not required to add the Content-Type, but it's always a good practice to include it there to be more specific
     *
     *
     *
     * Right now the server doesn't do anything with the data it gets on a request except print it
     *  - Let's change that
     *
     * Going to SimpleHttpServer.java
     *  - Add a private () to parse the parameter data and return it as a map
     *  - Some implementations of HTTP servers provide this functionality but this simple java version doesn't
     *  - parseParameters(String requestBody)
     *      - takes a request body and returns a Map of key value pairs
     *      - Initialize a Map to a HashMap
     *      - split the request body by the ampersand character, which will give us an array of key value pairs
     *      - Loop through this array and split the key-value pairs by the equals sign
     *          - If there are 2 values found, a key and a value,
     *              - add the data to the map
     *      - Return the map from the method
     *
     * Call parseParameters(String requestBody) from the main() and pass the requestBody
     *  - Print map out to the console
     *
     *
     * Restart SimpleHttpServer.java & then HttpExamplePost.java
     *  - Prints the body data and the map of printed data as well
     *
     *
     * Parameters aren't much good unless we use them for something
     *  - Let's include them in a form
     *
     * Extract data (firstName, and lastName) from the Map using the key names
     * Add labels to the form and pass these as the input values
     * Then add firstName and lastName to the formatted ()
     *  - check for nulls and print empty , else print values
     *
     *
     * Run the server
     *  - Test this from the browser
     *      - Enter firstName and lastName and submit
     *          - increments the no of visitors by 1
     *          - prints the first 2 "GET" s
     *          - In the post, we can see body data and there is the last and first name configured as configured in the HttpExamplePost class
     *
     *  - Next, execute this from HttpExamplePost.java
     *      - We get the html back with the values filled out with data that we passed to the server
     *      - At the server output, an additional post was made and there's body data it received from the client
     *      - Also prints the map data as passed for HttpExamplePost
     *
     *
     *
     *
     *
     */

    public static void main(String[] args) {
        try {

            URL url = new URL("http://localhost:8080");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("User-Agent","Chrome");
            connection.setRequestProperty("Accept","application/json , text/html");
            connection.setReadTimeout(30000);

            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            String parameters = "first=Joe&last=Smith";
            int length = parameters.getBytes().length;
            connection.setRequestProperty("Content-Length",String.valueOf(length));

            DataOutputStream output = new DataOutputStream(connection.getOutputStream());
            output.writeBytes(parameters);
            output.flush();
            output.close();

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
