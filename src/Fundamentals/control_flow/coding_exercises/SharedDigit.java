package Fundamentals.control_flow.coding_exercises;

public class SharedDigit {
    private static final boolean IS_SHARED = false;

    public static void main(String[] args) {
        System.out.println(hasSharedDigit(9,9));
        System.out.println(hasSharedDigit(9, 99));
        System.out.println(hasSharedDigit(100, 10));
        System.out.println(hasSharedDigit(10, 100));
        System.out.println(hasSharedDigit(19, 99));
        System.out.println(hasSharedDigit(99, 9));
        System.out.println(hasSharedDigit(12, 23));
        System.out.println(hasSharedDigit(12, 13));
        System.out.println(hasSharedDigit(21, 23));

    }

    public static boolean hasSharedDigit(int first, int second) {

        boolean firstValid = first > 9 && first < 100;
        boolean secondValid = second > 9 && second < 100;

        if (!firstValid || !secondValid) {
            return false;
        }

        int firstLeftDigit = first / 10;
        int firstRightDigit = first % 10;

        int secondLeftDigit = second / 10;
        int secondRightDigit = second % 10;

        boolean firstShared = firstLeftDigit == secondLeftDigit || firstLeftDigit == secondRightDigit;
        boolean secondShared = firstRightDigit == secondLeftDigit || firstRightDigit == secondRightDigit;

        return firstShared || secondShared;
    }
}
