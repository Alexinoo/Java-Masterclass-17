package networking.part1_simple_client_server.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class SimpleClient {
    /*
     * Client
     *
     * Use try-with-resources
     *  Create a socket to initiate communication with the server
     *      - However we need to pass serverAddress and the port no
     *          - This is the same port used in the server that the server is listening on
     *
     *  Set up a way to pass data and get data back from the other end point
     *      - Use BufferedReader to read info from the server socket using a Buffered reader wrapping inputStreamReader around the clientSocket's
     *      getInputStream()
     *
     *      - use PrintWriter to send a response to the client
     *        - wrap it around the clientSocket getOutputStream()
     *        - pass true to automatically flush the output when a printf,println, or a format() is executed
     *
     *      - use a do while - need to send the request at least once
     *
     *          - Use a Scanner to get input from the user
     *              - print it
     *
     *          - create 2 variables
     *              - requestString - sent to the server
     *              - responseString - response from the server
     *
     *          - Print user request
     *              - check if the user entered "exit" and if so, exit from the loop
     *
     *          - Keep prompting the user for input and sending it to the server, until the user enters the word "exit"
     *      - loop until the user enters the word exit
     *
     *  Catch IOException
     *      - Print exception msg
     *
     * Add finally clause
     *      - print client disconnected
     *
     * Running this:
     *  - works as expected
     *
     * Edit configurations
     *  - Allow multiple instances
     *
     * Then,
     *  - Run SimpleServer.java
     *  - Then run SimpleClient
     *      - Enter "First Request" then Enter
     *          - And we got the response from the server
     *  - Again run SimpleClient
     *      - Enter "Second Request" and press Enter
     *          - This time nothing happens & we don't get a response back from the server
     *
     * Switching to the server tab
     *  - The server doesn't have any info about another request
     *
     *
     * Problem
     * .......
     * A server connects to a client when it calls the accept() on the server socket instance
     *  - accept() is executed only once in the server code, so only the first client that makes a request is going to be successful
     *
     * What we really need to do is to handle multiple connections simultaneously
     *  - In other words , make a multithreaded server
     *
     * Need to adjust the code to accept multiple connections
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     */

    public static void main(String[] args) {

        String serverAddress = "localhost";
        int serverPort = 5000;

        try (Socket socket = new Socket(serverAddress, serverPort);){
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

            Scanner scanner = new Scanner(System.in);
            String clientRequest;
            String serverResponse;

            do{
                System.out.println("Enter string to be echoed (sent to server): ");
                clientRequest = scanner.nextLine();

                output.println(clientRequest);
                if (!clientRequest.equals("exit")) {
                    serverResponse = input.readLine();
                    System.out.println(serverResponse);
                }

            }while (!clientRequest.equals("exit"));

        } catch (IOException e) {
            System.out.println("Client Error : "+e.getMessage());
        }finally {
            System.out.println("Client Disconnected");
        }

    }
}
