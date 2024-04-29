package networking.part1_simple_client_server.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleServer {

    /*
     * Purpose - Echo back to the client whatever text the client sends it
     *
     * Start with a try-with resources block
     *  - Create a Server Socket variable by calling 1 arg constructor that specifies the port - pass 5000
     *      - port no can be an in between 0 - 65535
     *      - port no is the port on which the server will listen for client connections
     *
     *  - Accept incoming client connections
     *      - call accept() on the serverSocket instance - is blocking (waits until a client tries to connect)
     *      - once a client connection is accepted, you can use the returned clientSocket to communicate with the client
     *          - we can read from the socket or pass info to the socket
     *      - print the server has accepted client connection
     *
     *  - Use BufferedReader to retrieve info from the clientSocket using a Buffered reader wrapping inputStreamReader around the clientSocket's
     *      getInputStream()
     *
     *  - use PrintWriter to send a response to the client
     *      - wrap it around the clientSocket getOutputStream()
     *      - pass true to automatically flush the output when a printf,println, or a format() is executed
     *
     *
     * Catch IOException
     *  - print error msg
     *
     * However, closing a serverSocket doesn't close socket connections
     *  - CLosing a Server Socket just means it won't accept new connections
     *      - It doesn't automatically close existing connections or their streams
     *  - Enclose the clientSocket variable in a nested try-with resources block
     *
     *  - The input and output streams get closed when the socket connection is closed, and don't need to be specifically or individually closed
     *  - Any exception will get caught by the outer try catch
     *
     * Next,
     * Let's respond to input from the client and use it to send a response back
     * Use a while loop so that we can continue to listen for input from the client and not just for a single request
     *  - Call buffered readers readLine() and print the client request
     *      - if the client pass "exit", break out of the loop
     *
     * Next,
     * Echo back the request we got from the client, prefixed with the text "Echo from the server"
     *
     * Running this application now will look like it's stuck in an infinite loop but it's actually waiting for a client to connect on port 5000
     *
     * Next, set up a client connection
     *
     * Running this:
     *  - Nothing really happens and there's no output
     *  - Looks like the program is hanging but it's because it's blocked, waiting for a client to establish a connection
     *
     * Run the Simple client
     *
     * Typical TCP/IP Client-Server, and bind it to a port
     * ...................................................
     *
     * Server
     * ......
     * The Server needs to first create a ServerSocket  and bind it to a port - in this case 5000
     * The Server calls accept() on the ServerSocket, which returns a Socket when a client connects
     *  - At this point, the server is ready to accept a request from a single client
     * A request from the client comes in on an InputStream , which we can get from the socket instance on this server
     * The server can respond by pushing data to the output stream
     *
     * Client
     * ......
     * The client creates a Socket using a constructor that takes a host and a port
     * The client sends a request by pushing data to the socket's output stream
     * Because it's wrapped in a PrintWriter, this means you can use ()s you're familiar with like printf,println or format to make a request that's
     *  text based
     * Similarly, the response from the server is retrieved, from the client's socket's input stream
     *
     *
     * The server and client continue to use their respective sockets to exchange data
     *
     *
     *
     */

    public static void main(String[] args) {

        try(ServerSocket serverSocket = new ServerSocket(5000)){

           try( Socket clientSocket = serverSocket.accept();) {

               System.out.println("Server accepts client connection");
               BufferedReader clientInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
               PrintWriter serverResponse= new PrintWriter(clientSocket.getOutputStream(), true);

               while (true){
                   String clientRequest = clientInput.readLine();
                   System.out.println("Server got requested data : "+clientRequest);
                   if (clientRequest.equals("exit"))
                       break;
                   serverResponse.println("Server says : "+clientRequest);
               }
           }

        }catch (IOException e){
            System.out.println("Server exception "+e.getMessage());
        }

    }
}
