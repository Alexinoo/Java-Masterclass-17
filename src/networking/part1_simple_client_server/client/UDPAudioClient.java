package networking.part1_simple_client_server.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPAudioClient {

    /*
     * Setup port and packet size constants
     * Setup a DatagramPacket starting that declaration in a try-with-resources block
     *  - construct it with no args
     *  - the client doesn't need to include the port
     * Setup a byte array called audioFileName using the string literal "Audioclip.wav" and call getBytes()
     *  to turn that into a byte array
     * Will pass data to the server in form of a datagram packet
     *  - create a variable called packet1
     *  - this constructor takes 4 args
     *      - data that goes across - audioFileName
     *      - length of the audioFileName
     *      - info/address - destination - (which is the localhost)
     *      - port - destination port - server port
     * Call send() on the clientSocket and pass the packet to this ()
     * Catch IOException
     *
     * THe code that we'll be using which is part of the java's swing UI code only supports audio in wav, AIFF, and AU formats
     *
     * Running this :
     *  - Fire the server (UDPPacketServer.java)
     *      - prints it's waiting for a client to connect
     *
     *  - Fire UDPAudioClient
     *      - RUns but doesn't show any output
     *
     * Switching back to the server
     *      - prints it's waiting for a client to connect
     *      - prints Client requested us to listen to :AudioClip.wav
     *      - We can see the server got the request from the client to listen to the audio
     *
     * UDP Interactions are Connectionless
     * ...................................
     * The communication between client and server is connection-less
     * There's no explicit connection establishment phase, like there is with a TCP handshake
     * UDP sockets don't have to establish a persistent connection, in other words, before sending data
     *  - It's like addressing an envelope and posting it
     *  - The info about where it's going is part of the packet
     *
     * For TCP, the info about where it's going is established in the persistent connection
     */

    private static final int SERVER_PORT = 5000;
    private static final int PACKET_SIZE = 1024;
    public static void main(String[] args) {

        try(DatagramSocket clientSocket = new DatagramSocket()){

            byte[] audioFIleName = "AudioClip.wav".getBytes();
            DatagramPacket packet1 = new DatagramPacket(
                    audioFIleName, audioFIleName.length, InetAddress.getLocalHost(),SERVER_PORT);
            clientSocket.send(packet1);
        }catch (IOException e){
            System.out.println(e.getMessage());
        }

    }
}
