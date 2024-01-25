package Fundamentals.control_flow;

public class ExceptionHandling {

    public static void main(String[] args) {
        int currentYear = 2022;

        try {
            System.out.println(getInputFromConsole(currentYear));
        } catch (NullPointerException e) {
             System.out.println(e.getMessage());
        }
    }

    public static String getInputFromConsole(int currentYear){
        String name = System.console().readLine("Hi, What's your Name? ");
        System.out.println("Hi "+name+ ", Thanks for taking the course!");

        String yearOfBirth = System.console().readLine("What year were you born ?");

        int age = currentYear - Integer.parseInt(yearOfBirth);

        return "So you are "+age+" years old";
    }


}
