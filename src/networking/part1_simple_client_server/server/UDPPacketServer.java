package networking.part1_simple_client_server.server;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.TimeUnit;

public class UDPPacketServer {
    /*
     * UDP Client Server with DatagramSocket
     * .....................................
     *
     * Overview
     * We'll explore network communication, over the User Datagram Protocol
     * UDP is a communication protocol that transmits independent packets over the network with no guarantee of arrival and no guarantee of the order
     *  of delivery.
     *
     * Why Use UDP ?
     * .............
     * UDP is quite different from the more common TCP.
     * When using, UDP, there is no handshaking at all, and the destination host, which may or may not be a server, doesn't send a response to the msg
     *  sender
     * You use it when you do not need a reliable connection, or a two-way connection, or when speed is essential
     * If you require a response to a message you send over the network, then you wouldn't use UDP
     *
     * Building UDP Applications
     * .........................
     * Building UDP applications is very similar to building a TCP system; the only difference is that we don’t establish a point to point connection
     *  between a client and a server.
     * To perform networking operations over UDP, we only need to import the classes from the java.net package: java.net.DatagramSocket and
     *  java.net.DatagramPacket.
     *
     * Datagrams
     * .........
     * UDP uses datagrams, sometimes called packets
     * A datagram is a self-contained message and there's no guarantee it will arrive at its destination
     * In UDP communication, a single message is encapsulated in a DatagramPacket which is sent through a DatagramSocket.
     * UDP is often used for time-sensitive communication, and when losing the odd message or packet here or there won't matter
     * e.g. Voice over IP applications like skype and video streaming might use UDP, because speed is more important than ensuring that absolutely
     *  every packet arrives
     * We won't even notice if the occasional package isn't received when we're watching a video for example
     * Also the data arriving ath the client is immediately replaced by more data, so it's not critical that every single msg reach the client
     * Like ServerSockets and ServeChannels, we also have DatagramSockets and DatagramChannels
     *
     * Implementations - SERVER DOWNLOADS audio file from the client
     * .............................................................
     * Instead of exchanging a bit of text between the client and the server, create a server that downloads packets from an audio file
     * The client will then play the audio as it receives it
     * Use a Swing component to facilitate this
     *
     * main()
     * Set up port and packet size as constants
     * Create DatagramSocket variable inside try-with-resources block
     *  - call DatagramSocket constructor and pass the Port no
     * Set up a buffer variable which we can use to get data from the client
     * Print we're waiting for a client connection
     * Create a Datagram packet "clientPacket"
     *  - constructed by byte array , and the length specified, so we'll use byte array variable
     *
     * A server receives a datagram from a client when using UDP
     *  - It doesn't accept a connection, which is in contrast to TCP
     *  - Any request data will be received in the buffer, and so we'll create a String variable for that
     *
     * The client is going to request the name of the
     *  - will print the name of the file we got from the client here
     *
     * This server is single threaded & it does only 1 thing, it receives a packet from a single client printing out that data, then shuts down
     *
     *
     * ///////////////////////////
     * Write The Client Code Next
     * ///////////////////////////
     *
     * Create a class in the client package
     *
     *
     *
     * // Print more info about the audio file ///////
     * - Info that you might need on the client side when you go to play it
     *  - Use try catch
     *      - Create a new file from the string passed by the client
     *      - Instantiate an audio input stream using the AudioSystem class which is part of swing's audio features
     *          - call getFormat() on the audioInputStream and print it
     *  - Catch UnsupportedAudioFileException if one gets thrown and print it
     *
     * Running this again:
     *  - Fire server
     *  - Fire Simple client
     *
     * Results
     *  - Fire Server
     *      - prints the formatting info for the requested file
     *      - this is info specific to audio files and becomes important when playing the audio file's contents back
     *
     * Let's look at how we can send packets of this audi file back to the client
     * Then look at how to have the client receive the data incrementally playing it as it's received
     *
     *
     *
     * ///// audio sharing //////
     * change this class so that it sends the selected audio data back to the client in datagram packets
     * Use a private static()
     * sendDataToClient(String file,DatagramSocket serverSocket)
     *  - Takes the file name, datagram socket, and the datagram packet that was received from the client
     *  - open the audio file requested and send it bit by bit in datagram packets to the client
     * Create ByteBuffer with 1024 as the packet size
     * Use FileChannel
     *  - use it to open the audio file by calling open() on FileChannel and pass it a path
     *      - we can get a path in multiple ways and one of them is calling get() on Paths, passing the filename
     *          - also need to pass the options, in this case, READ
     * We can send packets back to the client by getting the client address info & the port from the client packet
     *  we received earlier
     * Set up a while loop to read data from the file channel
     *  - clear the buffer before any write operations are done on it
     *  - fileChannel.read() will read data from the channel, writing it into the buffer we supply it until it reaches
     *    the end of the file
     *      - when it returns -1 and when that happens, will break out of the while loop
     *      - don't forget that fileChannel.read() actually writes data to the buffer which can be confusing and it's
     *        important to understand that the channel and buffer are diff entities and that when you call read on the
     *         channel, you're writing to the buffer you supplied it
     *  - flip the buffer so tht we can now read the data
     *  - while there is data in the buffer, create a byte array with the same size as data in the buffer
     *      - we can read data from the buffer, populating the byte array with the get()
     *  - Create a Datagram packet for the client, passing it the byte array, it's length and client address info
     *  - Finally use serverSocket to send it
     *
     *  - Before we call this ()
     *      - we'll add a bit of time between each packet being sent
     *          - sleep for 22ms , which is the no that worked for Tim's test which allows the client receiving the
     *            audio to play it seamlessly
     *          - might need to adjust this in your system , if you experience a lot of noise or audio overlap
     *  - Call this () from the main()
     *      - Call sendDataToClient() after decoding the audio file info passing
     *          - the audioFileName
     *          - server socket
     *          - client packet
     * Handle IOException and print out any err message
     *
     *
     * /////////// Update UDPAudioClient ///////
     *
     *
     */

    private static final int PORT = 5000;
    private static final int PACKET_SIZE = 5000;

    private static void sendDataToClient(String file, DatagramSocket serverSocket,DatagramPacket clientPacket){
        ByteBuffer buffer = ByteBuffer.allocate(PACKET_SIZE);
        try(FileChannel fileChannel = FileChannel.open(Paths.get(file), StandardOpenOption.READ)){

            InetAddress clientIP = clientPacket.getAddress();
            int clientPort = clientPacket.getPort();

            while (true){
                if (fileChannel.read(buffer) == -1)
                    break;
                buffer.flip();
                while (buffer.hasRemaining()){
                    byte[] data = new byte[buffer.remaining()];
                    buffer.get(data);
                    DatagramPacket packet = new DatagramPacket( data, data.length,clientIP,clientPort);
                    serverSocket.send(packet);
                }

                try{
                    TimeUnit.MILLISECONDS.sleep(1);
                }catch (InterruptedException e){
                    throw new RuntimeException(e);
                }

            }

        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }


    public static void main(String[] args) {

        try(DatagramSocket serverSocket = new DatagramSocket(PORT)){

            byte[] buffer = new byte[PACKET_SIZE];
            System.out.println("Waiting for client to connect...");
            DatagramPacket clientPacket = new DatagramPacket(buffer , buffer.length);
            serverSocket.receive(clientPacket);
            String audioFileName = new String(buffer,0, clientPacket.getLength());
            System.out.println("Client requested us to listen to: "+audioFileName);

            try{
                File audioFile = new File(audioFileName);
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
                System.out.println(audioInputStream.getFormat());

            }catch (UnsupportedAudioFileException e){
                System.out.println(e.getMessage());
            }
            sendDataToClient(audioFileName,serverSocket,clientPacket);

        }catch (IOException e){
            System.out.println(e.getMessage());
        }

    }

}
