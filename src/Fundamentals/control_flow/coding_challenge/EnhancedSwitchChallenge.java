package Fundamentals.control_flow.coding_challenge;

public class EnhancedSwitchChallenge {

    public static void main(String[] args) {

        printDayOfWeek(0);
        printDayOfWeek(1);
        printDayOfWeek(2);
        printDayOfWeek(4);
        printDayOfWeek(5);
        printDayOfWeek(6);
        printDayOfWeek(7);

        System.out.println("===== second method ================");

        printWeekDay(0);
        printWeekDay(1);
        printWeekDay(2);
        printWeekDay(4);
        printWeekDay(5);
        printWeekDay(6);
        printWeekDay(7);


    }

    public static void printDayOfWeek(int day){
        String dayOfTheWeek =  switch (day){
            case 0 -> { yield "Sunday"; }
            case 1 -> "Monday";
            case 2 -> "Tuesday";
            case 3 -> "Wednesday";
            case 4 -> "Thursday";
            case 5 -> "Friday";
            case 6 -> "Saturday";
            default -> "Invalid day";
        };
        System.out.println(day +" is a "+dayOfTheWeek);
    }

    public static void printWeekDay(int day){
        String dayOfTheWeek = day +" is an Invalid day";
        if( day == 0){
            dayOfTheWeek = day +" is a Sunday";
        }else if(day == 1){
            dayOfTheWeek = day +" is a Monday";
        }else if(day == 2){
            dayOfTheWeek = day +" is a Tuesday";
        }else if(day == 3){
            dayOfTheWeek = day +" is a Wednesday";
        }else if(day == 4){
            dayOfTheWeek = day +" is a Thursday";
        }else if(day == 5){
            dayOfTheWeek = day +" is a Friday";
        }
        else if (day == 6){
            dayOfTheWeek = day +" is a Saturday";
        }
        System.out.println(dayOfTheWeek);
    }

}
