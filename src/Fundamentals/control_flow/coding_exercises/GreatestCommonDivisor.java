package Fundamentals.control_flow.coding_exercises;

public class GreatestCommonDivisor {
    private static final int INVALID_VALUE = -1;

    public static void main(String[] args) {
        System.out.println(getGreatestCommonDivisor(25,15));
        System.out.println(getGreatestCommonDivisor(12,30));
        System.out.println(getGreatestCommonDivisor(81,153));
        System.out.println(getGreatestCommonDivisor(132,1573));
        System.out.println(getGreatestCommonDivisor(20,15));
        System.out.println(getGreatestCommonDivisor(-20,15));
        System.out.println(getGreatestCommonDivisor(20,-15));
        System.out.println(getGreatestCommonDivisor(-20,-15));
        System.out.println(getGreatestCommonDivisor(10,35));
        System.out.println(getGreatestCommonDivisor(1010,10));
        System.out.println(getGreatestCommonDivisor(18,9));
        System.out.println(getGreatestCommonDivisor(9,18));
        System.out.println(getGreatestCommonDivisor(961,1271));
        System.out.println(getGreatestCommonDivisor(1155,1089));
        System.out.println(getGreatestCommonDivisor(14,18));
        System.out.println(getGreatestCommonDivisor(21,30));
        System.out.println(getGreatestCommonDivisor(12,16));
        System.out.println(getGreatestCommonDivisor(14,21));
    }

    public static int getGreatestCommonDivisor (int first , int second){
        if( first < 10 || second < 10)
            return INVALID_VALUE;

        int greatestCommonDivisor = 1;
        int start = 2;
        int quotientOne = first;
        int quotientTwo = second;

        while(quotientOne >= start &&  quotientTwo >= start){
            if(quotientOne % start ==0 && quotientTwo % start ==0) {
                quotientOne = quotientOne / start ;
                quotientTwo = quotientTwo / start ;
                greatestCommonDivisor *= start;
            }else{
                start++;
            }
        }

        return greatestCommonDivisor;
    }
}
