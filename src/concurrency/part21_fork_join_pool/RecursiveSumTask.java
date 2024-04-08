package concurrency.part21_fork_join_pool;

import java.util.concurrent.RecursiveTask;

public class RecursiveSumTask extends RecursiveTask {

    private final long[] numbers;
    private final int start;
    private final int end;
    private final int division;

    public RecursiveSumTask(long[] numbers, int start, int end, int division) {
        this.numbers = numbers;
        this.start = start;
        this.end = end;
        this.division = division;
    }

    @Override
    protected Long compute() {
        if ((end - start) <= (numbers.length / division)){
            System.out.println(start + " : "+ end);
            long sum = 0;
            for (int i = start; i < end; i++) {
                sum += numbers[i];
            }
            return sum;
        }else{
            int mid  = (start + end) / 2;
            RecursiveSumTask leftTask = new RecursiveSumTask(numbers, start,mid,division);
            RecursiveSumTask rightTask = new RecursiveSumTask(numbers, mid,end,division);

            leftTask.fork();
            rightTask.fork();

            return (long) (leftTask.join())  + (long)rightTask.join();
        }
    }
}
