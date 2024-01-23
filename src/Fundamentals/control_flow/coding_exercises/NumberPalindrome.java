package Fundamentals.control_flow.coding_exercises;

public class NumberPalindrome {

    public static void main(String[] args) {

        System.out.println(isPalindrome(11));
        System.out.println(isPalindrome(10));
        System.out.println(isPalindrome(101));
        System.out.println(isPalindrome(-222));
        System.out.println(isPalindrome(25));
        System.out.println(isPalindrome(33));
        System.out.println(isPalindrome(123321));
        System.out.println(isPalindrome(777));
        System.out.println(isPalindrome(15));
        System.out.println(isPalindrome(175));
        System.out.println(isPalindrome(37273));
        System.out.println(isPalindrome(21232));
        System.out.println(isPalindrome(21312));
        System.out.println(isPalindrome(213312));
        System.out.println(isPalindrome(21345312));

    }

    public static boolean isPalindrome(int number){

        int reverse = number;
        int original = number;

        reverse = 0;

         /*
         707                                -1221
        1st iteration
         lastDigit = 707 % 10 = 7           lastDigit = -1221 % 10 = 9
         reverse = 0 * 10 + 7 = 7           reverse = 0 * 10 + 9 = 9
         original = 707 / 10 = 70           original = -1221 / 10 = -122

        2nd iteration
         lastDigit = 70 % 10 = 0            lastDigit = -122 % 10 = -2
         reverse = 7 * 10 + 0 = 70          reverse = 9 * 10 + -2 = 88
         original = 70 / 10 = 7             original = -122 / 10 = -12

        3rd iteration
         lastDigit = 7 % 10 = 7            lastDigit = -12 % 10 = -2
         reverse = 70 * 10 + 7 = 707       reverse = 88 * 10 + -2 = 9
         original = 7 / 10 = 0             original = -12 / 10 = -1

        Original == 0 -- Exit the loop     Original == 0 -- Exit the loop

         */
        while(original != 0){
            int lastDigit = original % 10;
            reverse = reverse * 10 + lastDigit;                     //add lastDigit to reverse
            original = original / 10;

        }
        return reverse == number;
    }
}
