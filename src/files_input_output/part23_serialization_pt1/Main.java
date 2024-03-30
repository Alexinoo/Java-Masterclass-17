package files_input_output.part23_serialization_pt1;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

/*
 * Serialization and Change
 *
 * We have a Player class with 3 fields, a name, topScore and a List of weapons
 * When we serialized an instance of this class, all 3 fields were serialized including the ArrayList of weapons
 * That's because the ArrayList itself implements serializable
 *
 * Suppose, a developer wants to improve the game, and we have provided more ways to score points
 * This means we need to change the topScore to be a long to accommodate much higher scores
 *  - so let's update the topScore variable from int to long or vice versa
 *
 * Comment out on writeObject() - so that we don't write again as the file exists but only read this time
 *
 * Running this :-
 *  - We get a runtime exception -InvalidClassException, local class incompatible
 *  - The msg also prints serialVersionUID - a great long number there but the local class has a different value
 *  - What's a serialVersionUID and why are they totally different here
 *
 * serialVersionUID
 * is a runtime field, that the compiler will implicitly create, if it's not explicitly declared for classes that are
 *   serializable
 * Its based on class details such as the no of fields, their types and declarations
 * Changing a field type like I did in this example, will generate a diff id which is what happened here
 * When we read an obj from the stream, the runtime checks the stored serialVersionUID
 * This is stored with the object written ".dat" file and compared to the one contained within the compiled class file
 * If they don't match, then there is a compatibility problem and the runtime will throw this invalid class exception
 *
 * Versions may be incompatible between JVM's
 * It's also possible that diff compilers will generate this implied field differently
 * i.e. if you got a newer version of java, between writing the file and reading it, its possible you might not be able
 *  to deserialize your data because of a mismatch in this implicitly generated id
 *
 * It is strongly recommended that you include this as a private static field "serialVersionUID"
 *
 * Adding it to the player class to see if it fixes the problem
 *
 * Running this :-
 *  - We get the same error only that this time round the local class has a serial version UID of 1
 *  - The IDS are still diff though, so we can't deserialize this data
 *
 * Reverting back the changes
 *  - change from int to long for topScore and re-run again
 *  - comment out on the writeObject to start afresh
 *  - Running this :-
 *      - works & we are able to deserialize
 *
 * Reverting back the changes
 *  - change from long to int for topScore
 *  - comment on the writeObject to start
 *  - Running this:
 *      - Unfortunately, it doesn't work either
 *      - we do get a diff exception msg though, which tells us more specifically what the problem really is
 *          - we've got incompatible types for the field, topScore
 *
 * It's very important to understand the rules that you need to play by, if you're going to rely on
 *  Java's serialization of obj(s)
 *
 * What Constitutes an Incompatible change ?
 *  - Changing the declared type of a primitive field e.g. int to long (primitives are written specifically taking
 *     up certain width and if that changes, reading the data in will either read too many or too few bytes)
 *  - Deleting fields
 *  - Changing a non-static field to static, or a non-transient field to transient
 * There are other more complicated changes such as moving a class within its hierarchy, changing the writeObject and
 *  readObject() ()s after you've used to serialize previously and a few others
 *
 * Not all changes you make to your class are going to invalidate the serialization process
 * Some changes are compatible changes and won't cause an InvalidClassException on de-serialization, of an
 *  earlier version of the class
 *
 *
 * What Constitutes a Compatible change ?
 *  - Adding fields - when the class being reconstituted has a field that isn't on the stream, that field in the obj
 *                   gets initialized to the default value for its type
 *  - Adding readObject() and writeObject() ()s
 *  - Changing the access to a field i.e. access modifiers , public, private, protected
 *  - Changing a field from static to non-static , or transient to non-transient
 *
 * Reverting back to original changes
 *  - change from long to int
 *  - add another field bigScore
 *  - Running this :-
 *      - Good news is that this code ran without any exceptions but look at the output
 *      - Reconstituted Tim has a bigScore of 0
 *          - Adding the field bigScore doesn't break the de-serialization code
 *      - The de-serialization doesn't call the constructor at all, any constructor, on the class
 *          that's been serialized
 *      - This means if you got code in the constructor, to pass your topScore to the new field bigScore, this isn't
 *          going to happen, when the obj is reconstituted
 *      - Your new field won't break the deserialization process but it won't get populated by anything but the default value
 *          in this case 0.
 *
 * Let's make another change
 *  - Change from an ArrayList to a LinkedList
 *  - Running this -
 *      - Runs without any exceptions & collectedWeapons updated with all the values that were originally written out
 *
 * The serialization process for an obj field, includes info about the object's type and object's super type
 * For the ArrayList and LinkedList, they share a super type, so the deserialization went on smoothly here
 *
 * The default out of the box serialization, takes control of the process, out of your hands
 * This can lead to problems if your class structure is going to be changing
 *
 * Solution
 * --------
 * Implement certain customizations, which are hooks into the serialization that can let you control some or all of the
 *  process to manage some of these problems we've seen
 *
 */

class Player implements Serializable {
    private final static long serialVersionUID = 1L;
    private String name;
    private long topScore;
    private long bigScore;
    private List<String> collectedWeapons = new LinkedList<>();

    public Player(String name, long topScore, List<String> collectedWeapons) {
        this.name = name;
        this.bigScore = topScore;
        this.collectedWeapons = collectedWeapons;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", bigScore=" + bigScore +
                ", collectedWeapons=" + collectedWeapons +
                '}';
    }
}

public class Main {
    public static void main(String[] args) {
        Player tim = new Player("Tim",100_000_010,
                List.of("knife","machete","pistol"));
        System.out.println(tim);

        Path timFile = Path.of("tim.dat");
        //writeObject(timFile , tim);

        Player reconstitutedTim = readObject(timFile);
        System.out.println(reconstitutedTim);
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
