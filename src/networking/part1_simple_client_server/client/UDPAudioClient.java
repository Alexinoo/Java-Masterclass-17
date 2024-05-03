package networking.part1_simple_client_server.client;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

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
     *
     *
     * ///// audio data //////////////////
     * Add a private playStreamAudio()
     *  - Takes 1 param, client Datagram Socket
     *  - set a time out of 2 sec on the client socket so that if it doesn't receive any data for that length of time,
     *    it will time out and shut down cleanly
     *  - add exception to () signature
     *  - Create a local variable of a new type - Swing Type called AudioFormat
     *      - AudioFiles contain info about how to play them which we went through while printing the format info in
     *          the server code
     *      - this includes
     *          - the frequency , or the sample rate which for my wav file is 22050
     *          - the sample size in bits so 16 ,
     *          - no of channels , 1
     *          - whether the file is signed or not , true for my file
     *          - whether it's bigEndian or not - which describes 1 or 2 possible ways the bytes are ordered , so false
     *
     * JavaFX also has support for audio in its media library
     *  - To play audio, you create a DataLine.Info variable
     *  - The key to playing the audio is the source data line which we can get that using AudioSystem.getLine()
     *      - trows exception, add it to the () signature
     *  - Next line need to be open
     *  - then start the line
     *
     * Next, get the data from the server to play it
     *  - create a byte array
     *  - set up a loop to continue to read data packets incoming from the server
     *      - create a datagram packet
     *      - execute receive on the client socket passing the packet variable
     *  - The byte[] we called buffer, that's part of the packet, will be populated with the audio from the server
     *  - We can write that buffer's data to the SourceDataLine which will play it
     *  - Catch any exception and print any if we get 1
     *      - break out of the loop if there's any error
     *  - Lastly, close the line, the source data line, when we break out of this loop
     *
     * Calling this () from the main()
     *  - place it as the last statement in the try block
     *      - pass the client socket
     *      - catch LineUnavailableException to the catch
     *
     * Running this:
     *  - Fire the UDPPacketServer.java
     *      - starts up and is waiting for a client
     *
     *  - Run the UDPAudioClient
     *      - Adjust the TimeUnit.Milliseconds to a 1 or 2 ms
     *
     * Each datagram packet is sent containing the audio data incrementally, as well as the client's destination
     *  contained within the packet
     *
     *
     */

    private static final int SERVER_PORT = 5000;
    private static final int PACKET_SIZE = 1024;

    private static void playStreamedAudio(DatagramSocket clientSocket)
            throws SocketException, LineUnavailableException {
        clientSocket.setSoTimeout(10);
        AudioFormat format = new AudioFormat(22050,16,1,true,false);
        DataLine.Info info =  new DataLine.Info(SourceDataLine.class,format);
        SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
        line.open();
        line.start();
        byte[] buffer = new byte[PACKET_SIZE];
        while (true){
            try{
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                clientSocket.receive(packet);
                line.write(buffer,0, packet.getLength());
            }catch (IOException e){
                System.out.println(e.getMessage());
                break;
            }
        }
        line.close();
    }
    public static void main(String[] args) {

        try(DatagramSocket clientSocket = new DatagramSocket()){

            byte[] audioFIleName = "AudioClip.wav".getBytes();
            DatagramPacket packet1 = new DatagramPacket(
                    audioFIleName, audioFIleName.length, InetAddress.getLocalHost(),SERVER_PORT);
            clientSocket.send(packet1);
            playStreamedAudio(clientSocket);
        }catch (IOException | LineUnavailableException e){
            System.out.println(e.getMessage());
        }

    }
}
