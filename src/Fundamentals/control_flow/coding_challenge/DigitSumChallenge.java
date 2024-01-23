package Fundamentals.control_flow.coding_challenge;

public class DigitSumChallenge {
    private static final int INVALID_VALUE = -1;

    public static void main(String[] args) {
        System.out.println("The sum of the digits in number is 1234 is " +sumDigits(1234));
        System.out.println("The sum of the digits in number is -125 is " +sumDigits(-125));
        System.out.println("The sum of the digits in number is 4 is " +sumDigits(4));
        System.out.println("The sum of the digits in number is 32123 is " +sumDigits(32123));
    }

    public static int sumDigits(int number){
        if(number < 0) return INVALID_VALUE;

        /*
        1st iteration
         1234 % 10 = 4 sum = 4
         1234 / 10 = 123

        2nd iteration
         123 % 10 = 3 sum = 7
         123 / 10 = 12

        3rd iteration
         12 % 10 = 1 sum = 10
         12 / 10 = 1

        4th iteration
         1 % 10 = 1 sum = 11
         1 / 10 = 0

         -- Exit the loop
         */
        
        int sum = 0;

        while(number > 0){
           int cumulativeSum = number % 10;
            sum += cumulativeSum;
            number = number / 10;
        }
        
        return sum;
    }
}
