package networking.part1_simple_client_server.server;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

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
     */

    private static final int PORT = 5000;
    private static final int PACKET_SIZE = 5000;

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

        }catch (IOException e){
            System.out.println(e.getMessage());
        }

    }

}
