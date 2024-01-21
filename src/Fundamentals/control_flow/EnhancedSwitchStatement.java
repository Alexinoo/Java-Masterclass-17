package Fundamentals.control_flow;

public class EnhancedSwitchStatement {
    public static void main(String[] args) {
        int switchValue = 0;

        switch (switchValue) {
            case 1 -> System.out.println("Value is 1");
            case 2 -> System.out.println("Value is 2");
            case 3, 4, 5 -> {
                System.out.println("Value is   3 , 4 or 5");
                System.out.println("Actually it's a " + switchValue);
            }
            default -> System.out.println("Values is neither 1,2,3,4 or 5");
        }

        //more code
        String month = "JANUARY";
        System.out.println(month + " is in the "+getQuarter(month)+ " quarter");

        month = "APRIL";
        System.out.println(month + " is in the "+getQuarter(month)+ " quarter");

        month = "OCTOBER";
        System.out.println(month + " is in the "+getQuarter(month)+ " quarter");

        month = "XYZ";
        System.out.println(getQuarter(month)+ " quarter");
    }

    public static String getQuarter(String month){
        String badResponse = "";
        return switch (month) {
            case "JANUARY", "FEBRUARY", "MARCH" -> { yield "1st";}
            case "APRIL", "MAY", "JUNE" -> "2nd";
            case "JULY", "AUGUST", "SEPTEMBER" -> "3rd";
            case "OCTOBER", "NOVEMBER", "DECEMBER" -> "4th";
            //default -> "Invalid month";
            default -> {
                badResponse = month + " is an invalid";
                // return badResponse - DOES NOT WORK WITH return keyword;
                yield badResponse;
            }
        };
    }
}
