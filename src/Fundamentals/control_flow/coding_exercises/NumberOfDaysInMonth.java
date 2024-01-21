package Fundamentals.control_flow.coding_exercises;

public class NumberOfDaysInMonth {
    private static final int INVALID_INPUT = -1;

    public static void main(String[] args) {

        System.out.println(getDaysInMonth(1,2020)); //31
        System.out.println(getDaysInMonth(2,2020)); //29
        System.out.println(getDaysInMonth(2,2018)); //28
        System.out.println(getDaysInMonth(2,2016)); //29
        System.out.println(getDaysInMonth(2,2000)); //29
        System.out.println(getDaysInMonth(2,1900)); //28
        System.out.println(getDaysInMonth(3,2020)); //31
        System.out.println(getDaysInMonth(4,2020)); //30
        System.out.println(getDaysInMonth(5,2020)); //31
        System.out.println(getDaysInMonth(6,2020)); //30
        System.out.println(getDaysInMonth(7,2020)); //31
        System.out.println(getDaysInMonth(8,2020)); //31
        System.out.println(getDaysInMonth(9,2020)); //30
        System.out.println(getDaysInMonth(10,2020)); //31
        System.out.println(getDaysInMonth(11,2020)); //30
        System.out.println(getDaysInMonth(12,2020)); //31
        System.out.println(getDaysInMonth(12,10000)); //-1
        System.out.println(getDaysInMonth(-1,2020)); //-1
        System.out.println(getDaysInMonth(0,2020)); //-1
        System.out.println(getDaysInMonth(22,2020)); //-1
        System.out.println(getDaysInMonth(1,-2020)); //-1
        System.out.println(getDaysInMonth(13,-2020)); //-1
        System.out.println(getDaysInMonth(-1,-2020)); //-1

        System.out.println("============================================");

        System.out.println(isLeapYear(-1024)); //false
        System.out.println(isLeapYear(0)); //false
        System.out.println(isLeapYear(10000)); //false
        System.out.println(isLeapYear(9000)); //false
        System.out.println(isLeapYear(1800)); //false
        System.out.println(isLeapYear(1855)); //false
        System.out.println(isLeapYear(1846)); //false
        System.out.println(isLeapYear(1870)); //false
        System.out.println(isLeapYear(1900)); //false
        System.out.println(isLeapYear(1924)); //true
        System.out.println(isLeapYear(1944)); //true
        System.out.println(isLeapYear(1980)); //true
        System.out.println(isLeapYear(2000)); //true
        System.out.println(isLeapYear(2014)); //false
        System.out.println(isLeapYear(2016)); //true
        System.out.println(isLeapYear(2020)); //true
        System.out.println(isLeapYear(2100)); //false
        System.out.println(isLeapYear(2104)); //true
        System.out.println(isLeapYear(2140)); //true
        System.out.println(isLeapYear(2150)); //false

    }

    public static boolean isLeapYear(int year){
        if( year < 1 || year > 9_999)
            return false;
        if(year % 4 == 0){
            return year % 100 != 0 || year % 400 == 0;
        }else{
            return false;
        }
    }

    public static int getDaysInMonth(int month , int year){
        if( year < 1 || year > 9_999)
            return INVALID_INPUT;

        return switch(month){
            case 1,3,5,7,8,10,12-> 31;
            case 2 -> isLeapYear(year) ? 29 : 28;
            case 4,6,9,11-> 30;
            default-> INVALID_INPUT;
        };
    }
}
