package networking.part10_custom_body_handlers.handlers;

/*
 * JSONBodyHandler
 *  - Will also implement Http.BodyHandler just like the ThreadSafeFileHandler class
 *     - in this case , it will be typed to a JsonNode which is a type in the library we just imported
 *      - JsonNode is a hierarchical type that rep JSON in java
 *  - Generate the apply()
 *      - will fill this after
 *
 *  - The jackson library contains an obj mapper which will take a String and create a JSON node from the string if
 *    it's well-formed JSON
 *      - we'll setup a private variable for that
 *
 *  - This time, instead of having a public constructor like we did in the other handler, we'll use a factory method
 *     pattern
 *      - Java uses this pattern for it's newer modules, common in this pattern to make the constructor private
 *      - this constructor has its object mapper as its 1 arg and it gets assigned to the variable object mapper
 *         on this class
 *
 *  - Continuing with the factory () pattern, we'll supply a static create(), taht takes an obj mapper and this ()
 *     will do the construction
 *      - This returns a new Json body handler
 *
 *  - There are advantages and disadvantages to this factory method pattern
 *      adv - gives more control and options to the developers, and it can return an instance of any sub-type which is
 *         pretty neat , making this pattern more flexible
 *      disadv - a private constructor can limit extending this class
 *
 *  - Finally, implement apply() like we did with ThreadSafeFileHandler.java
 *      - Will return the value we get back by using the mapping() on the HttpResponse.BodySubscribers class
 *          - But in this case, instead of ofString, we'll use ofByteArray
 *            - the byteArray becomes the parameter for the following lambda expression
 *          - As usual we'll have a try block and will return a JsonNode which we get by calling readTree() on the
 *            objectMapper variable, passing the byteArray lambda parameter
 *          - This will take the Json response string and rep it in a structure that would look like an obj, if it were
 *             in its native javascript obj hierarchy and with similar ()s
 *          - Throw an IO exception if we get 1
 *
 */

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.http.HttpResponse;

public class JSONBodyHandler implements HttpResponse.BodyHandler<JsonNode> {

    private final ObjectMapper objectMapper;

    private JSONBodyHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public static JSONBodyHandler create(ObjectMapper objectMapper){
        return new JSONBodyHandler(objectMapper);
    }

    @Override
    public HttpResponse.BodySubscriber<JsonNode> apply(HttpResponse.ResponseInfo responseInfo) {
       // return null;
        return HttpResponse.BodySubscribers.mapping( HttpResponse.BodySubscribers.ofByteArray(),
                byteArray -> {
                    try{
                        return objectMapper.readTree(byteArray);
                    }catch (IOException e){
                        throw new RuntimeException("Failed to parse JSON",e);
                    }
                });
    }
}
