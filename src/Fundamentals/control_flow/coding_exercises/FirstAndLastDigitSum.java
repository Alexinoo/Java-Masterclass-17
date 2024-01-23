package Fundamentals.control_flow.coding_exercises;

public class FirstAndLastDigitSum {
    private static final int INVALID_VALUE = -1;

    public static void main(String[] args) {
        System.out.println(sumFirstAndLastDigit(-5));
        System.out.println(sumFirstAndLastDigit(0));
        System.out.println(sumFirstAndLastDigit(5));
        System.out.println(sumFirstAndLastDigit(7));
        System.out.println(sumFirstAndLastDigit(11));
        System.out.println(sumFirstAndLastDigit(10));
        System.out.println(sumFirstAndLastDigit(101));
        System.out.println(sumFirstAndLastDigit(-222));
        System.out.println(sumFirstAndLastDigit(257));
        System.out.println(sumFirstAndLastDigit(3322));
        System.out.println(sumFirstAndLastDigit(123321));
        System.out.println(sumFirstAndLastDigit(777));
        System.out.println(sumFirstAndLastDigit(154));
        System.out.println(sumFirstAndLastDigit(275));
        System.out.println(sumFirstAndLastDigit(37273));
        System.out.println(sumFirstAndLastDigit(99));
        System.out.println(sumFirstAndLastDigit(81));
    }

    public static int sumFirstAndLastDigit(int number){
        if(number < 0) return INVALID_VALUE;

        int firstDigit = 0;
        int lastDigit = number % 10;

        while(number >= 10){
            number /= 10;
        }
        firstDigit = number;


        return firstDigit + lastDigit ;
    }
}
