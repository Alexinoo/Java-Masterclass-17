package concurrency.part30_watcher_service;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

public class FileWatcherExample {

    /* FileWatcher
     * ..............
     * Get a file watcher service , for a FileSystem instance
     *  - use the default file system, by calling static () - getDefault on the FileSystems class
     *      - then chain a call to newWatchService
     *  - get a path or a dir that we're interested in watching
     *      - use cwd and pass dot .
     *  - Register with the WatchService
     *      - the Path Class has a register () which we can pass the watchService and some flags which indicates
     *        the types of events we want to listen for (e.g. file creation , modification & deletion)
     *          - returns a WatchKey - which you can think of as a handle to the service
     *  - handle checked exception from newWatchService() by adding to the () signature
     *
     * Set up a boolean to drive the while loop
     * Set up a while loop
     *  - need a try catch because like any thread, the watchService might throw an interrupted exception
     *  - query the watch service queue using the take() which returns a WatchKey instance
     *  - handle checked InterruptedException
     *
     * Get a list of events (List<WatchEvent<?>>) using pollEvents() on the watchkey
     *
     * Then loop through these events and get the context of the event
     *  - the context is the Path of the file for which event was registered
     *  - print
     *      - event type by calling kind() on single event
     *      - context
     *
     * Finally, reset the watchKey
     *
     *
     * Running this:
     *  - The application is running but there's no output and the application isn't exiting
     *  - That's because its in while loop polling for an events that might happen on the cwd
     *      which is the root of the project
     *  - Create Testing.txt file on the root dir
     *      - not only did that open an editor window, but look at the output console
     *          - we now have a print statement showing event type because the file Testing.txt was created
     *  - Adding some text to Testing.txt
     *      - prints more output statements showing up immediately
     *          - intelliJ creates a temporary file of the same name with a tilda at the end and that's our original file
     *            and this file was modified
     *          - And then the temp file was deleted
     *      - So intelliJ has some underlying processes here
     *  - Delete Testing.txt from the Project panel
     *      - uncheck Safe Delete option - so that it doesn't affect the running of the program
     *      - we see a similar thing in the output with an ENTRY_DELETE
     *  - We have to manually shut this down right now
     *
     * Let's add the code to shut down the watch service when the test file is deleted
     *  - Check if the filename of the file being deleted is "Testing.txt" and the event type is ENTRY_DELETE
     *      - if so print the watch service is shutting down
     *      - call close() on the watchService to close it
     *      - set the keepGoing flag to false
     *      - and break to exit the while loop
     *
     * Running this and go through the same process again
     *  - create Testing.txt
     *  - add some content
     *  - delete the file
     *  - Now the service shuts down smoothly
     *
     *
     *
     * Lets Explain a few things on this code
     *
     *  FileSystems.getDefault().newWatchService();
     *      - gets a WatchService - a service that's lets us monitor file system changes
     *
     * directory.register(watchService
     *      - A WatchKey is what java documentation calls a token
     *      - rep a Watchable obj registration, with a WatchService
     *      - returned from the register() on Path
     *
     * WatchKey States and Relationship
     * ................................
     * When initially created, the WatchKey is said to be ready
     * When an event is detected, then the WatchKey is signalled, a special state, which means it can be polled
     * A WatchKey gets queued at this point, so that it can be retrieved, by invoking the watch service's poll or
     *  take ()s
     * Once signalled, a WatchKey remains in this state, until its reset() is invoked
     * When that happens, the WatchKey returns to the ready state
     * Events detected while the key is in the signalled state, are added to the WatchKey, but they don't cause the
     *  key to be requeued for retrieval, on the watch service
     * So, not only do we get a WatchService when we register a resource, but additional watch keys are queued when
     *  events occur
     *
     * A WatchKey has a list of events, that occurred while the WatchKey was in the signalled state
     *
     * Each WatchEvent obj rep a specific change, to the Watchable obj
     *
     * Back to the Code
     * ................
     *  watchService.take();
     *      - Notice, we get a WatchKey from the WatchService using the watchService.take();
     *      - we could have also used the poll() , if there's nothing on the queue, the code will wait here, until
     *        something is added
     *      - if something is on the queue, take() gets the element, removing i from the queue
     *
     * List<WatchEvent<?>> events = watchKey.pollEvents();
     *      - Once we have this signalled WatchKey, we next get the WatchEvents from that watchKey using pollEvents()
     *
     * Then loop all the events ,
     *  - print info about each event
     *
     * Then reset the watchKey, meaning all the data has been processed
     *
     * The while loop will wait on the take() until something is added to the queue
     *
     * You can use WatchService on other resources besides files and directories like databases , message queues,
     *  shared memory regions and other types of resources
     *
     */

    public static void main(String[] args) throws IOException {

        WatchService watchService = FileSystems.getDefault().newWatchService();
        Path directory = Paths.get(".");

        WatchKey watchKey = directory.register(watchService,
                StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_MODIFY,
                StandardWatchEventKinds.ENTRY_DELETE );

        boolean keepGoing = true;
        while (keepGoing){
            try {
                watchKey = watchService.take();

            }catch (InterruptedException e){
                throw new RuntimeException(e);
            }
            List<WatchEvent<?>> events = watchKey.pollEvents();

            for (var event : events){
                Path context = (Path)event.context();
                System.out.printf("Event type: %s - Context: %s%n",event.kind(), context);
                if (context.getFileName().toString().equals("Testing.txt") &&
                        event.kind() == StandardWatchEventKinds.ENTRY_DELETE){
                    System.out.println("Shutting down watch service");
                    watchService.close();
                    keepGoing = false;
                    break;
                }
            }
            watchKey.reset();
        }
    }
}
