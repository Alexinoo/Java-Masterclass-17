package Fundamentals.control_flow.coding_exercises;

public class PerfectNumber {

    public static void main(String[] args) {
        System.out.println(isPerfectNumber(6));
        System.out.println(isPerfectNumber(9));
        System.out.println(isPerfectNumber(28));
        System.out.println(isPerfectNumber(496));
        System.out.println(isPerfectNumber(33550336));
        System.out.println(isPerfectNumber(0));
        System.out.println(isPerfectNumber(10));
        System.out.println(isPerfectNumber(1147));
        System.out.println(isPerfectNumber(31));
        System.out.println(isPerfectNumber(2));
        System.out.println(isPerfectNumber(8128));
        System.out.println(isPerfectNumber(199));
        System.out.println(isPerfectNumber(3127));
    }

    public static boolean isPerfectNumber(int number){
        if( number < 1 )
            return false;

        int sum = 0;
        for (int i = 1; i < number; i++) {
            if(number % i == 0)
                sum+=i;

        }
        return sum == number;
    }
}
