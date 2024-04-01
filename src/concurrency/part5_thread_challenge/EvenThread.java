package concurrency.part5_thread_challenge;

public class EvenThread implements Runnable{
    @Override
    public void run() {
        for (int i = 2; i <= 10; i+=2) {
            System.out.println("evenThread: "+ i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("even Thread was interrupted");
                break;
            }
        }
    }
}
