package enum_type.part2;

import java.util.Random;

public class Main {

    public static void main(String[] args) {

       DaysOfTheWeek weekDay = DaysOfTheWeek.TUES;

        for (int i = 0; i < 9; i++) {
            weekDay = getRandomDay();
            switchDayOfWeek(weekDay);
        }

        System.out.println("_".repeat(30));

        //Toppins Enum
        for (Topping topping :Topping.values()) {
            System.out.println(topping.name()+ " : "+topping.getPrice());
        }

    }

    public static DaysOfTheWeek getRandomDay(){
        int randomInt = new Random().nextInt(7);
        var allDay = DaysOfTheWeek.values();
        return allDay[randomInt];
    }
    public static void switchDayOfWeek(DaysOfTheWeek weekDay){
        int weekDayInteger = weekDay.ordinal() + 1;
        switch (weekDay){
            case WED -> System.out.println("Wednesday is Day "+weekDayInteger);
            case SAT -> System.out.println("Saturday is Day "+weekDayInteger);
            default -> System.out.println(weekDay.name().charAt(0) +
                    weekDay.name().substring(1).toLowerCase()+
                    "day is Day "+ weekDayInteger);
        }
    }
}
