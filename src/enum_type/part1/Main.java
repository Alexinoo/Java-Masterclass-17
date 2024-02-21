package enum_type.part1;

import java.util.Random;

public class Main {

    public static void main(String[] args) {

        /*
            Enum Types
            ==========
            - Is a java's type to support something called enumeration
            - Wikipedia defines enumeration as "A complete ordered listing of all the items in a collection"
            - Java describes the enum type as "A special data type that contains predefined constants".
            - A constant is a variable whose value can't be changed, once assigned;
            - So an enum is little like an array, excepts its elements are known , not changeable,and each element can be referre
              to by a constant name , instead of an index position
            - In it's simplest form, is described like a class but the keyword enum replaces
             the keyword class
            - You can name the enum with any valid identifier, but like a class Upper CamelCase is preferred
            - Within the enum body, you declare a list of constant identifiers separated by commas.
            - By convention,these are all uppercase labels following some natural order

                Example:
                ========
                    public enum DayOfTheWeek{
                        SUNDAY,MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY,SATURDAY
                    }

                    public enum Months{
                        JANUARY,FEBRUARY,MARCH,APRIL,MAY,JUNE,JULY,AUGUST,SEPTEMBER,OCTOBER,NOVEMBER,DECEMBER
                    }

                    public enum CompassDirections{
                        EAST,NORTH,WEST,SOUTH
                    }

                     public enum SetOfSizes{
                        EXTRA_SMALL,SMALL,MEDIUM,LARGE,EXTRA_LARGE
                    }

              - We can use 2 methods on this simple enum type , the first to print the label
                and the second method to give us the ordinal value(or order) of this constant in
                the enum collection itself

              - Like arrays and lists, enums starts with an ordinal value of 0

              - Enum types have a method named values which returns an array of all the enum constant values

              - Another thing you can do is test if your variable is equal to one of the constants using the == operator
        */

        DayOfTheWeek weekDay = DayOfTheWeek.TUESDAY;
        System.out.println(weekDay);

        System.out.println("_".repeat(30));

        for (int i = 0; i < 9; i++) {
            weekDay = getRandomDay();
            System.out.printf("Name is %s, Ordinal Value = %d%n",weekDay.name(),weekDay.ordinal());

            //Extra stuff
            if(weekDay == DayOfTheWeek.MONDAY)
                System.out.println("Found a Monday");
        }
    }

    public static DayOfTheWeek getRandomDay(){
        Random random = new Random();
        int randomInteger = random.nextInt(7);

        var allDays = DayOfTheWeek.values();

        return allDays[randomInteger];
    }
}
