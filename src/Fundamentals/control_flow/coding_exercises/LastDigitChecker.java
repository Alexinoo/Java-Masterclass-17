package Fundamentals.control_flow.coding_exercises;

public class LastDigitChecker {

    public static void main(String[] args) {
        System.out.println(hasSameLastDigit (9, 99, 19));
        System.out.println(hasSameLastDigit (1, 1, 2));
        System.out.println(hasSameLastDigit (15, 9, 20));
        System.out.println(hasSameLastDigit (15, 10, 9));
        System.out.println(hasSameLastDigit (10, 10, 10));
        System.out.println(hasSameLastDigit (11, 22, 31));
        System.out.println(hasSameLastDigit (-1, 11, 11));
        System.out.println(hasSameLastDigit (23, 32, 42));
        System.out.println(hasSameLastDigit (41, 22, 71));
        System.out.println(hasSameLastDigit (22, 23, 34));
        System.out.println(hasSameLastDigit (-41, 50, 31));
        System.out.println(hasSameLastDigit (10, 11, 81));
        System.out.println(hasSameLastDigit (777, 771, 77));
        System.out.println(hasSameLastDigit (1001, 771, 77));
        System.out.println(hasSameLastDigit (62, 882, 772));

        System.out.println("======================================================");
        System.out.println(isValid(9));
        System.out.println(isValid(10));
        System.out.println(isValid(11));
        System.out.println(isValid(-1));
        System.out.println(isValid(23));
        System.out.println(isValid(41));
        System.out.println(isValid(224));
        System.out.println(isValid(-411));
        System.out.println(isValid(1081));
        System.out.println(isValid(777));
        System.out.println(isValid(1001));
        System.out.println(isValid(1000));
        System.out.println(isValid(6272));
    }

    public static boolean hasSameLastDigit(int first , int second , int third){
        if( !isValid(first) || !isValid(second) || !isValid(third))
            return false;

        int firstRightMost = first % 10;
        int secondRightMost = second % 10;
        int thirdRightMost = third % 10;

        return (firstRightMost == secondRightMost || firstRightMost == thirdRightMost
                || secondRightMost == thirdRightMost );
    }

    public static boolean isValid(int number){
        return (number >= 10 && number <= 1000);
    }
}
