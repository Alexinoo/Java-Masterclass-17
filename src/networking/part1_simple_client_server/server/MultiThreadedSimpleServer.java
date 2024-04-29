package networking.part1_simple_client_server.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThreadedSimpleServer {

    /*
     * Multithreaded Simple Server
     * ...........................
     *  The SimpleServer app was flawed
     *      - We connected to a single client at the point where we call serverSocket.accept()
     *      - Since this occurs only once, there's no way for a second client to get in and connect to this server
     *      - In addition, in the while loop,input.readLine() is a blocking ()
     *      - This application will block here indefinitely, until the client sends another request
     *      - If the client never sends another request, it will just block here until we terminate the application
     *
     *  Solution - Without MultiThreading
     *  - Wrap a while loop that calls the accept() on the server socket
     *  - Include a catch clause for the nested try clause, where the code is accepting a connection from a client
     *      - catch any IOException
     *          - print "client socket resources will be closed here"
     *  - However, if we ran the code now, we'd still have the same problem we had previously
     *
     *  - The 2nd client will never get the chance to be accepted by or connected to the server until the first client enters the "exit" string and then
     *    terminates
     *  - At this point the server will loop back and accepts the next client
     *  - If the 1st client never sends an exit request though, another client will never get accepted
     *  - The code blocks at the input.readLine()
     *  - Solution
     *      - Specify a socket time out which will help
     *          - call setSOTimeout() on socket, need to do this before we call the buffered reader ()
     *              - pass 20000ms  - client socket will time out after 20 sec
     *      - This means the input.readLine() will only block for 20sec
     *      - A socket timeout exception will get thrown which is a type of IO exception and that will close the connection
     *
     * Running this:
     *  - Fire the MultiThreadedSimpleServer.java first
     *  - Then fire Simple Client twice
     *      - 1st client
     *          - type 'hello'
     *          - we get a response from the server
     *      - 2nd client
     *          - type 'goodbye'
     *          - no response
     *  - Note that, if we initiate a communication with the 1st client, we're not successful and the client error's out because it's single socket
     *    connection is no longer valid after it timed out
     *  - Then we get client disconnected
     *
     * One solution to the above is to use multi thread
     *  - Restructure the multi thread simple server class
     *  - Create handleClientRequest(Socket socket)
     *      - cut everything from the BufferedReader up to the end of the while loop
     *      - start with a try-with-resources block and pass clientSocket (is valid)
     *          - means that if an exception happens in this block, you want to close this socket and its resources cleanly
     *          - also pass BufferedReader and PrintWriter into try() block after socket
     *      - catch Exception and print the client socket shut down
     *
     * main()
     *  - Create a thread executor service as the first line in the main()
     *  - remove try-with-resources around the client socket being created
     *      - closing the socket and it's resources will be handled by the thread code now
     *  - submit a runnable lambda that calls handleClientRequest()
     *  - update session timeout to 900_0000 - (15 min)
     *      - means will drop clients if there's no communication after 15 min
     *
     * Running this:
     *  - Fire this server
     *      - then 1st client
     *          - Type 'First Hello'
     *               - get echoed by the server
     *          - Type again 'First client, Hello again'
     *               - get echoed again
     *          - Type 'exit'
     *               - client is disconnected (no effect on the server)
     *
     *
     *      - then 2nd client
     *          - Type 'Second Hello;
     *              - get echoed by the server also in
     *          - Type again 'Second Client, Second Hello'
     *              - get echoed again
     *          - Type again 'Second Client, Still working'
     *              - get echoed/response again
     *
     *  - The server opens connections to 2 clients
     *  - Both clients are successfully communicating with the server
     *  - Continued communicating with the server even after the 1 client exits
     *
     * Shut down all the running processes
     *
     * We've now built a multi-threaded client server application that can field as many requests as it gets from clients
     * The Server socket class is built on Java's traditional IO mechanisms and we've looked at how this causes threads to block
     *
     * Java introduced NIO that is non-blocking IO
     * Rather than using server socket and socket, we can also use NIO types, the server socket channel and socket channel
     *
     * This is the preferred approach for servers that have to handle high volumes of concurrent connections
     *
     * Because of the channel's non-blocking nature, your server will be able to achieve superior scalability and efficiency
     *
     * On the flip side, NIO introduce a lot more complexity which is the trade off
     */

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();

        try(ServerSocket serverSocket = new ServerSocket(5000)){

            while (true){

                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Server accepts client connection");
                    clientSocket.setSoTimeout(900_0000);
                    executorService.submit(()-> handleClientRequest(clientSocket));
//
//                    BufferedReader clientInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//                    PrintWriter serverResponse= new PrintWriter(clientSocket.getOutputStream(), true);
//
//                    while (true){
//                        String clientRequest = clientInput.readLine();
//                        System.out.println("Server got requested data : "+clientRequest);
//                        if (clientRequest.equals("exit"))
//                            break;
//                        serverResponse.println("Server says : "+clientRequest);
//                    }

            }

        }catch (IOException e){
            System.out.println("Server exception "+e.getMessage());
        }

    }
    private static void handleClientRequest(Socket clientSocket) {
        try (clientSocket;
             BufferedReader clientInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter serverResponse = new PrintWriter(clientSocket.getOutputStream(), true);) {

            while (true) {
                String clientRequest = clientInput.readLine();
                System.out.println("Server got requested data : " + clientRequest);
                if (clientRequest.equals("exit"))
                    break;
                serverResponse.println("Server says : " + clientRequest);
            }
        }catch(Exception e){
            System.out.println("Client socket shut down here");
        }
    }
}
