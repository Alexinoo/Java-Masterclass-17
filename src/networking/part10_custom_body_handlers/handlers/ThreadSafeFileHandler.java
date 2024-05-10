package networking.part10_custom_body_handlers.handlers;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
public class ThreadSafeFileHandler implements HttpResponse.BodyHandler<Path> {

    /*
     * Custom Body Handlers
     * .....................
     * This class implements an HttpResponse.BodyHandler interface which is a generic type
     *  - so we'll specify Path in the angle brackets as the type
     * This interface has 1 method that we need to implement
     *  - implement apply() which returns null
     *
     * Next set up some class variables
     *  - Path path
     *  - Reentrant lock
     *
     * Next, set up a constructor that takes a Path as an argument
     *  - assign that to the path variable
     *
     * Next, we need to return a value from the overridden ()
     *  - This needs to be HttpResponse.BodySubscriber with a path type
     *  - We can use a static () on HttpResponse.BodySubscribers class called mapping
     *
     * mapping()
     *  - we can use other ()s on this same class, e.g. ofString on body subscribers to get a string from the response body
     *      - pass the default charset
     *  - the next arg is a lambda expression that will get applied to a mapped value, in this case, the response string
     *     which we will call value
     *      - acquire lock on the file
     *      - use try catch to write to the file - use Files.writeString()
     *          - pass the Path instance, with a new line appended to it
     *          - pass writing options
     *      - return the Path
     *      - handle IOExceptions
     *      - use finally clause to release the lock in all the cases
     *
     *  - This looks similar to the writeToFile that we wrote earlier
     *
     * You can use body handler in the send or sendAsync ()s
     *
     * To demonstrate this using custom handler,
     *  - copy the ConcurrentPostThreadSafe class and rename it to CustomBodyHandler
     *  - update sendPostsSafeFileWrite to sendPostsFileHandler
     *
     * GoTo CustomBodyHandler.java
     *
     */


    private final Path path;

    private static final Lock lock = new ReentrantLock();

    public ThreadSafeFileHandler(Path path) {
        this.path = path;
    }

    @Override
    public HttpResponse.BodySubscriber<Path> apply(HttpResponse.ResponseInfo responseInfo) {
       // return null;
        return HttpResponse.BodySubscribers.mapping(
                HttpResponse.BodySubscribers.ofString(Charset.defaultCharset()),
                value -> {
                    lock.lock();
                    try {
                        Files.writeString(path, value+ "\r", StandardOpenOption.APPEND);
                        return path;
                    } catch (IOException e) {
                        throw new RuntimeException("Failed to write response" +e);
                    }finally {
                        lock.unlock();
                    }
                }
        );
    }
}
