package concurrency.part2_java_threads_threads_basics;

public class CustomThread extends Thread{

    @Override
    public void run() {
        for (int i = 1; i <= 5 ; i++) {
            System.out.print(" 1 ");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
