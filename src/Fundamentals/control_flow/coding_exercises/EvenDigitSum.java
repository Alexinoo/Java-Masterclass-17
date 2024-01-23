package Fundamentals.control_flow.coding_exercises;

public class EvenDigitSum {
    private static final int INVALID_VALUE = -1;
    public static void main(String[] args) {
        System.out.println(getEvenDigitSum(123456789));
        System.out.println(getEvenDigitSum(2000));
        System.out.println(getEvenDigitSum(252));
        System.out.println(getEvenDigitSum(0));
        System.out.println(getEvenDigitSum(605254));
        System.out.println(getEvenDigitSum(-10));
        System.out.println(getEvenDigitSum(6688));
        System.out.println(getEvenDigitSum(-22));
        System.out.println(getEvenDigitSum(-468));
        System.out.println(getEvenDigitSum(246824683));
        System.out.println(getEvenDigitSum(987612345));
    }

    public static int getEvenDigitSum(int number){
        if(number < 0) return INVALID_VALUE;

        int sum = 0;
        while( number > 0){
            int lastDigit = number % 10;
            if (lastDigit %2 == 0)
                    sum +=lastDigit;
            number /= 10;
        }
        return sum;
    }
}
