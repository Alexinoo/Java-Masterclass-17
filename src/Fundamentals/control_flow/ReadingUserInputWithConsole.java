package Fundamentals.control_flow;

public class ReadingUserInputWithConsole {
    public static void main(String[] args) {
        int currentYear = 2022;
        System.out.println(getInputFromConsole(currentYear));
    }

    public static String getInputFromConsole(int currentYear){
        String name = System.console().readLine("Hi, What's your Name? ");
        System.out.println("Hi "+name+ ", Thanks for taking the course!");

        String yearOfBirth = System.console().readLine("What year were you born ?");

        int age = currentYear - Integer.parseInt(yearOfBirth);

        return "So you are "+age+" years old";
    }
}
