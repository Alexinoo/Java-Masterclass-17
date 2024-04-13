package concurrency.part30_watcher_service;

public class WatchServiceIntro {

    /*
     * WatchService Interface
     * ......................
     * This is a special type of service, which watches registered obj(s) for changes and events
     * e.g. A File manager may use a watch service to monitor a directory for changes, so that it can update its display
     *  of the list of files, when files are created or deleted
     *
     * Using a Watch Service
     * .....................
     * A Watchable obj is registered with the watch service
     * When events occur on the registered obj, they're queued on the watch service
     * A consumer can retrieve and process the event info, using the poll or take ()s on the queue
     *
     * Let's look at 1 type of the WatchService , the file watcher
     *
     * Create FileWatcherExample Class
     *
     */
}
