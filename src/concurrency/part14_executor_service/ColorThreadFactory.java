package concurrency.part14_executor_service;

import java.util.concurrent.ThreadFactory;

public class ColorThreadFactory implements ThreadFactory {
    private String threadName;
    public ColorThreadFactory(ThreadColor color) {
        this.threadName = color.name();
    }
    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setName(threadName);
        return thread;
    }
}
