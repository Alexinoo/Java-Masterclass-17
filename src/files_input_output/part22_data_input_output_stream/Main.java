package files_input_output.part22_data_input_output_stream;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

class Player implements Serializable{
    private String name;
    private int topScore;
    private List<String> collectedWeapons = new ArrayList<>();

    public Player(String name, int topScore, List<String> collectedWeapons) {
        this.name = name;
        this.topScore = topScore;
        this.collectedWeapons = collectedWeapons;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", topScore=" + topScore +
                ", collectedWeapons=" + collectedWeapons +
                '}';
    }
}
public class Main {

    public static void main(String[] args) {

        /*
         * Create a Path variable and pass a file called "data.dat"
         * Then pass it to writeData()
         *
         * Running this :-
         *  - Prints "writeInt writes 4" - means write() wrote 4 bytes out to the file
         *
         * Call readData() and pass it the dataFile
         */
        Path dataFile = Path.of("data.dat");
        writeData(dataFile);
        System.out.println("_".repeat(50));
        readData(dataFile);

        /*
         * Define Player Class that implements Serializable
         * Create Player Tim
         * Print tim instance
         *
         * Next, write a code that will write this obj to an output stream - writeObject()
         */
        Player tim = new Player("Tim",100_000_010,
                List.of("knife","machete","pistol"));
        System.out.println(tim);

        /*
         * Set up a path instance
         * Write the player, Tim here
         * Then read the player out of the generated file and pass that back to a variable named "reconstitutedTim"
         * Print deserialized obj out
         *
         *
         * Running this :-
         *  - The 2 lines of code for printing Tim are exactly the same
         *  - Output is identical
         * This means we are able to read a serialized player obj back into memory, from a flat file with a single
         *  readObj() , so that seems like a pretty neat feature
         */
        Path timFile = Path.of("tim.data");
        writeObject(timFile , tim);

        Player reconstitutedTim = readObject(timFile);
        System.out.println(reconstitutedTim);

    }

    /*
     * Define writeData(Path path) that takes a Path instance
     * Create an instance of a java.io class named DataOutPutStream
     *
     * DataOutputStream lets an app write primitive java data types to an output stream, in a portable way
     * An application can then use a DataInputStream to read the data back in
     * We'll instantiate the new DataOutputStream in a try-with-resources block
     *  - this will wrap a BufferedOutputStream
     *      - which in turn will wrap a FileOutputStream because we will be writing to a file
     * It's not a must you wrap this in a BufferedOutputStream because the data will be just a few bytes but this is
     *  the standard way of doing this
     * Most files will benefit from being wrapped in a BufferedOutputStream
     * Used a helper () from the Path interface, toFile() to get a file instance from the path which we can pass to
     *  IO class
     * Catch possible exception from FileOutputStream -
     *  - Generates 2 exceptions
     *          - FileNotFoundException
     *          - IOException
     *  - We can remove FileNotFoundException since it's a child of IOException
     *
     * Next create a series of local variables, one for each primitive data types as well as a String
     *
     * Next create a long variable "position" which keeps track of how many bytes each operation is writing
     * Then call writeInt() from dataStream instance passing myInt variable
     * Will print out that the () writes, and then calculate how many bytes were written
     *  - we can do this by taking the current size of the dataStream and subtract the value of position
     *  - then update position to the stream current size
     *
     * Then call this () from the main()
     *
     * copy last 3 statements and do the same for long,boolean,char,float,double and String
     *
     * Running this :-
     *  - Notice that writeUTF writes 13 while "Hello World" contains 11 char(s)
     *  - The writeUTF uses the 1st 2 bytes to record the no of char(s) written - so that's how we get 13
     *
     * Now we have got data in a binary file, we can read it by using DataInputStream
     * Create a () for this
     *
     */
    private static void writeData(Path dataFile){
        try (DataOutputStream dataStream = new DataOutputStream(new BufferedOutputStream(
                        new FileOutputStream(dataFile.toFile())))){
            int myInt = 17;
            long myLong = 100_000_000_000_000L;
            boolean myBoolean = true;
            char myChar = 'Z';
            float myFloat = 77.7f;
            double myDouble = 98.6;
            String myString = "Hello World";

            long position = 0;

            dataStream.writeInt(myInt);
            System.out.println("writeInt writes " +(dataStream.size() - position));
            position = dataStream.size();

            dataStream.writeLong(myLong);
            System.out.println("writeLong writes " +(dataStream.size() - position));
            position = dataStream.size();

            dataStream.writeBoolean(myBoolean);
            System.out.println("writeBoolean writes " +(dataStream.size() - position));
            position = dataStream.size();

            dataStream.writeChar(myChar);
            System.out.println("writeChar writes " +(dataStream.size() - position));
            position = dataStream.size();

            dataStream.writeFloat(myFloat);
            System.out.println("writeFloat writes " +(dataStream.size() - position));
            position = dataStream.size();

            dataStream.writeDouble(myDouble);
            System.out.println("writeDouble writes " +(dataStream.size() - position));
            position = dataStream.size();

            dataStream.writeUTF(myString);
            System.out.println("writeUTF writes " +(dataStream.size() - position));
            position = dataStream.size();



        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*
     * DataInputStream
     * Define readData(Path dataFile) that takes a path to the data file
     * Set it up within a try-with-resources block
     *  Create dataStream instance of DataInputStream
     *      - Instead of passing in a new instance of a BufferedOutputStream, we'll pass what we get back from the ()
     *         Files.newInputStream (is a special NIO2 input stream)
     * Catch IOException
     *
     * Read the data in from the try block
     * For a DataInputStream,this means we need to know the data types that we will be reading in and the same order
     *  the types were output in the file
     *
     *
     * Call readData() from the main()
     *
     * Running this :-
     *  - All the data was read accurately and the values match the values we output in this file
     */
    private static void readData(Path dataFile){
        try(DataInputStream dataStream = new DataInputStream(
                Files.newInputStream(dataFile) )) {
            System.out.println("myInt = "+dataStream.readInt());
            System.out.println("myLong = "+dataStream.readLong());
            System.out.println("myBoolean = "+dataStream.readBoolean());
            System.out.println("myChar = "+dataStream.readChar());
            System.out.println("myFloat = "+dataStream.readFloat());
            System.out.println("myDouble = "+dataStream.readDouble());
            System.out.println("myString = "+dataStream.readUTF());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*
     * We could use this type of stream, to write an obj to a file
     * An obj after all comes down to primitive types or strings , so we could write ()s like these to write out the fields
     * This wouldn't be pretty esp if you had 20 fields, and complex types or collections
     *
     * Fortunately, we don't have to do that
     * Java provides us with another pair of classes to make this a bit easier
     *  - ObjectOutputStream - writes obj out
     *  - ObjectInputStream - reads data directly back into an obj
     *
     * Serialization
     * is the process of translating a data structure or obj, into a format that can be stored on a file
     * Only instances of Serializable classes can be serialized, meaning the class must implement the Serializable interface
     * This interface doesn't have many ()s, it's just used to mark the class as serializable
     * All subtypes of a serializable class are themselves also serializable
     *
     *
     * Deserialization or Reconstituting an obj
     * The default serialization mechanism , writes the class of the obj, the class signature and the values of
     *  non-static fields
     * These elements are used to restore the obj, and it's state, during the read operation
     * A process called reconstituting the data , or deserialization
     *
     * To demonstrate a simple example, create a second class in this Main class java file
     * Call the class Player
     * Defined above the main class
     *
     */

    /* ObjectOutputStream
     *
     * Next, write a code that will write this obj to an output stream - writeObject(Path path , Player player)
     * This () takes a Path instance and a Player obj
     * Will set up in a try-with-resources block and create an ObjectOutputStream instance and pass what we get from
     *  calling Files.newOutputStream with the dataFile path
     * Writing an obj is easy , we just call writeObject() from the objStream instance and pass the obj
     * Handle possible IOException
     * And that's it for the write
     * This is a lot easier than calling a bunch of ()s by each type
     */

    private static void writeObject(Path dataFile , Player player){
        try(ObjectOutputStream objStream = new ObjectOutputStream(
                Files.newOutputStream(dataFile)
        )){
            objStream.writeObject(player);

        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    /* ObjectInputStream
     *
     * Next, write a code that will read the dataFile and returns a player - readObject(Path path)
     * This () takes a Path instance and returns a Player obj
     * The setup is very similar to the ObjectOutputStream except it's an ObjectInputStream
     * We can get the player stored in the dataFile by calling readObj() but we have to cast that to a player
     * Handle possible exception from readObject()
     *
     * Next call the () from the main()
     */

    private static Player readObject(Path dataFile ){
        try(ObjectInputStream objStream = new ObjectInputStream(
                Files.newInputStream(dataFile)
        )){
           return (Player) objStream.readObject();

        }catch (IOException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }
    }
}
