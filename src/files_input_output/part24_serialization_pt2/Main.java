package files_input_output.part24_serialization_pt2;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

/*
 * Serialization and Change - Part 2
 *
 * There are 2 ()s called readObject and writeObject, that are hooks into customizations
 * These aren't ()s we override though.
 * If we implement them, on our serializable class much like the serializable UID, the serialization will use our
 *  explicit versions
 * We have to stick to the signatures of these ()s as they are identified in the serialization documentation
 *
 * readObject()
 * Will start with readObj() creating it on the serializable Player class
 * It's a special () whose signature doesn't change
 * Throws an IOException , and also include ClassCastException
 * When you do create this (), you'll often want to call the default serialization process first
 *  - done by invoking defaultReadObject()
 *  - we can also make changes to the obj that got deserialized
 *      - we can set bigScore for players losing to a default score of 1 billion
 * Follow the intelliJ warning of annotating readObject() as serial - @Serial
 * This annotation informs all interested parties that this () will be used by the serialization process
 *
 * Running this :-
 *  - Now the reconstituted player has a score of 1 billion
 *  - Might be 1 way of dealing with any players caught up in this upgrade problem
 *
 *
 * Transient Modifier
 * ..................
 * The transient modifier is used to indicate that a field should not be serialized
 * This can be useful for variables that contain sensitive info, or just variables that don't need to be persisted,
 *  for other reasons
 *
 * Suppose we have an accountId for the players to manage billing and so on
 * Will include it as a private final field on the Player class and add transient modifier as well
 *  - Include it in the constructor as 1st param and initialize it
 *  - Add it to toString()
 * Fix the error in the main() where we are calling the constructor - Pass 555 in the 1st arg
 *
 * Running this code:
 *  - Notice that we did not get any errors
 *  - Means adding a transient field does not cause any incompatibility btwn versions
 * This wasn't a great test though of the transient modifier, because "tim.dat" file never had the account id
 *  in it at all
 * We'll serialize a 2nd player with this new class structure and then immediately de-serialize that player
 *
 * Serialize a player Joe
 *  - With account id = 556
 *  - top score = 75 - he has only played once
 *  - weapons [crossbow,rifle,pistol]
 *  - Then serialize his data to a "joe.dat" file
 *  - Then write the joe obj to that file
 *  - Then immediately de-serialize joe instance assigning that to the reconstitutedJoe variable
 *  - Print both original obj and deserialized obj
 *
 * Running this :-
 *  - The id for joe in the deserialized obj is 0 , because the accountId was not written out for this player
 *    and that's because we declared it transient
 *
 * Let's see if we can leverage Java serialization, but wrap some of our version control around parts of it
 *  - Create a constant/final version variable and initialize it to 1
 *
 * writeObject()
 * Like the readObject() we can get a hook into how Java writes data with the writeObject()
 * Add writeObject() right after readObject()
 * Throws an IOException
 * Will print something to the console so that we know when this code is executed
 *  - Write out the static version no - normally a static field wouldn't get serialized but since we are customizing
 *     this code, we can do whatever we want
 *  - We can write the fields in any order that we want:
 *      - write the collectedWeapons first
 *      - write the name
 *      - write the topScore as an int
 * Again, Follow the intelliJ warning of annotating writeObject() as serial - @Serial
 *
 * Now, we need to redo the readObject()
 * When you write a customized writeObject() as we have done below, you can't just simply delegate to the
 *  defaultReadObject() which is what we are doing currently
 * If we don't change this (), the writes will be out of sync with the reads and the code will fail
 *
 * Update readObject() in Player class
 *  - Comment the 2 statements
 *  - Want to read everything exactly the same way we wrote it out
 *      - use readInt to get the version
 *      - read list of weapons by casting it to List<String>
 *      - read player name with readUTF
 *      - read topScore with readLong
 *  - IntelliJ gives a warning about the cast on List<String> and since this is a list, and this is what we want
 *    we can add another annotation to suppress this warning -@SuppressWarnings and we can pass a String literal
 *    "unchecked"
 *  - Update topScore to an int in the parameter and print it out in the toString()
 *
 * Now to the main() - uncomment the line that prints tim's obj
 *
 *
 * Running this :-
 *  - We can see that "--> Customizing Writing" was called during the 2 deserialization processes
 *  - They also have their topScore reconstituted correctly
 *
 * Now notice what happens if we make topScore transient
 *  - Add keyword transient after the private access modifier
 *  - Running this again :-
 *      - Even though topScore is transient, it didn't matter because my methods and the rules now overrides Java rules
 *  - Will remove the transient modifier
 *
 * Now ,
 *  - update topScore to an int
 *  - set version = 2
 *  - update writeLong to writeInt in the writeObject()
 *  - Before running this code, comment out on writeObject() that writes tim
 *      - We want to keep the version that was written when the topScore was declared as a long
 *
 * Running this :-
 *  - ran's successfully even though we changed the field from a long to an int
 *  - the serialVersionUID did not change and it's always been 1
 * Because we are reading and writing using the custom code, we didn't get an exception reading the data in
 *
 * But there is a problem, joe Score is 0 and the reason it's 0 because we are reading his score in as an int
 *  which worked fine for tim whose code version was 1
 *
 * But it's not great for version 2, because the score's data is stored in 8 bytes
 *
 * This is where the version no comes in handy. we can change the readObject() to do something diff, based on the version
 *  we read in from the serialized obj
 * We can fix this by ternary operator by testing the value of the local variable serializedVer, if it's 1 read as an
 *  int , otherwise read as Long
 *
 * However, changing the serialVersionUID to 2 creates an error because the serialVersionUID now are out of sync
 *
 * Note :
 *  - Writing your own custom readObject() and writeObject() will not always prevent all invalid Class Exceptions
 */

class Player implements Serializable {
    private final static long serialVersionUID = 1L;
    private final static int version = 2;
    private String name;
    private long topScore;
    private long bigScore;

    private final transient long accountId;
    private List<String> collectedWeapons = new LinkedList<>();

    public Player(long accountId,String name, int topScore, List<String> collectedWeapons) {
        this.accountId = accountId;
        this.name = name;
        this.topScore = topScore;
        this.collectedWeapons = collectedWeapons;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + accountId + ", " +
                "name='" + name + '\'' +
                ", topScore=" + topScore +
                ", collectedWeapons=" + collectedWeapons +
                '}';
    }

    @Serial
    @SuppressWarnings("unchecked")
    private void readObject(ObjectInputStream stream) throws IOException , ClassNotFoundException{
       // stream.defaultReadObject();
       // bigScore = (bigScore == 0) ? 1_000_000_000L : bigScore;
        var serializedVersion = stream.readInt();
        collectedWeapons = (List<String>) stream.readObject();
        name = stream.readUTF();
       // topScore = stream.readInt();
        topScore = (serializedVersion == 1) ? stream.readInt(): stream.readLong();

    }

    @Serial
    private void writeObject(ObjectOutputStream stream) throws IOException{
        System.out.println("---> Customized Writing");
        stream.writeInt(version);
        stream.writeObject(collectedWeapons);
        stream.writeUTF(name);
        stream.writeLong(topScore);
    }
}

public class Main {
    public static void main(String[] args) {
        Player tim = new Player(555,"Tim",100_000_010,
                List.of("knife","machete","pistol"));
        System.out.println(tim);

        Path timFile = Path.of("tim.dat");
        //writeObject(timFile , tim);

        Player reconstitutedTim = readObject(timFile);
        System.out.println(reconstitutedTim);

        System.out.println("_".repeat(50));
        Player joe = new Player(556,"Joe",75,List.of("crossbow","rifle","pistol"));
        Path joeFile = Path.of("joe.dat");
        writeObject(joeFile , joe);

        Player reconstitutedJoe = readObject(joeFile);
        System.out.println(joe);
        System.out.println(reconstitutedJoe);

    }

    private static void writeObject(Path dataFile , Player player){
        try(ObjectOutputStream objStream = new ObjectOutputStream(
                Files.newOutputStream(dataFile)
        )){
            objStream.writeObject(player);

        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }


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
