package Fundamentals.control_flow;

import java.util.Scanner;

public class ExceptionHandlingScannerIntro {

    public static void main(String[] args) {
        int currentYear = 2024;
        try {
            System.out.println(getInputFromConsole(currentYear));
        } catch (NullPointerException e) {
            System.out.println(getInputFromScanner(currentYear));
        }
    }

    public static String getInputFromConsole(int currentYear){
        String name = System.console().readLine("Hi, What's your Name? ");
        System.out.println("Hi "+name+ ", Thanks for taking the course!");

        String yearOfBirth = System.console().readLine("What year were you born ?");

        int age = currentYear - Integer.parseInt(yearOfBirth);

        return "So you are "+age+" years old";
    }

    public static String getInputFromScanner(int currentYear){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Hi, What's your Name? ");
        String name = scanner.nextLine();

        System.out.println("What year were you born ?");
        String yearOfBirth = scanner.nextLine();

        int age = currentYear - Integer.parseInt(yearOfBirth);

        return "So, you are "+age+" years old , Thanks "+name+" for taking this course.";
    }
}
