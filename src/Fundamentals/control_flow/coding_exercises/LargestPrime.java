package Fundamentals.control_flow.coding_exercises;

public class LargestPrime {
    private static final int INVALID_VALUE = -1;

    public static void main(String[] args) {
//        System.out.println(getLargestPrime(21));
//        System.out.println(getLargestPrime(7));
//        System.out.println(getLargestPrime(1));
//        System.out.println(getLargestPrime(217));
//        System.out.println(getLargestPrime(-1));
//        System.out.println(getLargestPrime(0));
//        System.out.println(getLargestPrime(-10));
//        System.out.println(getLargestPrime(1147));
//        System.out.println(getLargestPrime(31));
//        System.out.println(getLargestPrime(2));
        System.out.println(getLargestPrime(45));
//        System.out.println(getLargestPrime(199));
//        System.out.println(getLargestPrime(3127));
//        System.out.println(getLargestPrime(16));
//        System.out.println(getLargestPrime(12));
    }

    public static int getLargestPrime(int number){
        if(number < 2)
            return INVALID_VALUE;

        int factor = -1;
        for (int i = 2; i * i <= number; i++) {
            if (number % i != 0) {
                continue;
            }
            factor = i;
            while (number % i == 0) {
                number /= i;
            }
        }
        return number == 1 ? factor : number;
    }
}
