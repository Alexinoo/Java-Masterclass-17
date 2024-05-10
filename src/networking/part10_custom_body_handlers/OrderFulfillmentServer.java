package networking.part10_custom_body_handlers;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_OK;

public class OrderFulfillmentServer {
    /*
     * Http Concurrent Requests - POST
     * ................................
     *
     * We'll look at how we can send more than 1 request concurrently using Http client's asynchronous features
     * Create another server application, using HTTP Server
     *  - This server will emulate an order fulfilling system
     *  - It will process order requests, returning an order confirmation back in a JSON formatted response
     *  - This system will accept orders as either a GET or POST request and return a JSON string, with an order id, an order date time received, and a
     *    date of expected delivery
     *
     *  Copy SimpleHttpServer.java and modify
     *  - Remove some code from   exchange.getRequestHeaders() up to the end of the try catch block
     *
     * Next, get any parameters passed on a request's URI
     *  - Set up a String variable "reqParameters" - call getRequestURI() on exchange variable and chain .toString()
     *  - Print data from the client request
     *      - remote address
     *      - request method
     *      - reqParameters variable
     * Next,
     *  - Check request method and process the parameter data depending on the request method
     *  - Initialize data & response variables to an empty string to start
     *  - Initialize response code to Ok
     *      - if GET
     *          - get parameter data from the request string using substring and get data after the ?
     *      - if POST
     *          - print raw data
     *
     * Next,
     *  - Do minimal validation - (ensure there are at least 2 parameters
     *      - The data we'll be returning include the date time this server received this request
     *      - Estimate the delivery date as 3 days from now
     *  - Set up a response string as a text block
     *      - print it out
     *  - If the parameter data doesn't meet the validation,
     *      - set the response to a JSON string , with response "bad data sent"
     *      - set the response code to Http Bad Request
     *  - Pass the responseCode to the sendResponseHeaders()
     *
     * Next,
     *  - Generate order id for the incoming order
     *      - remove visitorCounter field
     *      - use AtomicLong , since we need this to be thread-safe
     *          - Initialize it with a constructor and pass initial value
     *      - create an accessor () for this field
     *          - returns lastId increment by 1
     *      - Java will take care of making sure there's no thread interference, we'll be executing processes concurrently, so it's important for this
     *        field to be thread safe
     *
     * Next,
     * Create JSON response that we will be returning from this server
     *  - Create a key value pair
     *  - Each value will be a format specifier , since we'll be formatting this string
     *      - print orderId - prefix with 0 and then specify how many digits - in this case 10
     *      - print product - use a string specifier
     *      - print amount - use a string specifier  - will be a number - don't enclose with quotes
     *  - call formatted() and pass the expected values respectively
     *      - call getNextId() to return the lastId
     *      - call get() on parameters Map and pass "product" which will return the value for this key
     *      - call get() on parameters Map and pass "amount" which will return the value for this key
     *      - call format() on now and pass the date time stamp formatter - use ISO_LOCALIZED_DATE_TIME
     *      - call format() on delivery and pass the date time  formatter - use ISO_LOCALIZED_DATE
     *  - strip out all extraneous white spaces
     *
     * SO that's all we need from the OrderFulfillmentServer.java
     * Next, let's code the client application
     *
     */

    private static AtomicLong lastID = new AtomicLong(1);

    private static long getNextId(){
        return lastID.getAndIncrement();
    }
    public static void main(String[] args) {

        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(8080),0);

            server.createContext("/", exchange -> {
                String reqParameters = exchange.getRequestURI().toString();
                System.out.printf("%S %s %s%n",
                        exchange.getRemoteAddress(),
                        exchange.getRequestMethod(),
                        reqParameters);


                String requestMethod = exchange.getRequestMethod();
                String data = "";
                String response = "";
                int responseCode = HTTP_OK;

                if (requestMethod.equals("GET")){
                    data = reqParameters.substring(reqParameters.indexOf("?") + 1);
                }else if (requestMethod.equals("POST")){
                    data = new String(exchange.getRequestBody().readAllBytes());
                }

                System.out.println("Body data: "+data);

                Map<String,String> parameters = parseParameters(data);

                if (parameters.size() == 2){
                    LocalDateTime now = LocalDateTime.now();
                    LocalDateTime delivery = now.plusDays(3);
                    response = """
                            { "order" :
                              { 
                                "orderId" : "%010d",
                                "product" : "%s",
                                "amount" : %s,
                                "orderReceived" : "%s",
                                "orderDeliveryDate" : "%s"
                              }
                                
                            }""".formatted(
                                    getNextId(),
                                    parameters.get("product"),
                                    parameters.get("amount"),
                                    now.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                                    delivery.format(DateTimeFormatter.ISO_LOCAL_DATE)).replaceAll("\\s","");

                    System.out.println(response);
                } else {
                    response = "{\"result\":\"Bad Data sent \"}";
                    responseCode = HTTP_BAD_REQUEST;
                }


                var bytes = response.getBytes();
                exchange.sendResponseHeaders(responseCode, bytes.length);
                exchange.getResponseBody().write(bytes);
                exchange.close();
            });

            server.start();
            System.out.println("Server is listening on port 8080...");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static Map<String,String> parseParameters(String requestBody){
        Map<String,String> parameters = new HashMap<>();
        String[] pairs = requestBody.split("&");
        for (String pair : pairs){
            String[] keyValue = pair.split("=");
            if (keyValue.length == 2)
                parameters.put(keyValue[0], keyValue[1] );
        }
        return parameters;
    }
}
