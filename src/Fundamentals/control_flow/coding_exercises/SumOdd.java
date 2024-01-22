package Fundamentals.control_flow.coding_exercises;

public class SumOdd {

    public static void main(String[] args) {
        System.out.println(sumOdd(1,11));
        System.out.println(sumOdd(10,20));
        System.out.println(sumOdd(1,100));
        System.out.println(sumOdd(100,1000));
        System.out.println(sumOdd(10,10));
        System.out.println(sumOdd(13,13));
        System.out.println(sumOdd(10,5));
        System.out.println(sumOdd(-4,6));
        System.out.println(sumOdd(-15,-10));
        System.out.println(sumOdd(1,-5));
        System.out.println(sumOdd(2,7));

        System.out.println("==================");

        System.out.println(isOdd(-5));
        System.out.println(isOdd(-1));
        System.out.println(isOdd(24));
        System.out.println(isOdd(4));
        System.out.println(isOdd(7));
        System.out.println(isOdd(3));
        System.out.println(isOdd(23));
        System.out.println(isOdd(107));
        System.out.println(isOdd(208));
        System.out.println(isOdd(11));
    }

    public static boolean isOdd(int number){
        if(number < 0) return false;
        if(number % 2 != 0) return true;
        return false;

        //return number > 0 && number % 2 != 0;
    }

    public static int sumOdd(int start, int end){
        if( start < 1 || end < 1 || start > end) return -1;

        int sum = 0;

        for (int i = start; i <= end; i++) {
            if(isOdd(i))
                sum+=i;
        }
        return sum;
    }
}
