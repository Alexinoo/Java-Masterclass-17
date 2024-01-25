package Fundamentals.control_flow;

import java.util.Scanner;

public class ReadingInputWithScanner {

    public static void main(String[] args) {

        int currentYear = 2024;

        try {
            System.out.println(getInputFromScanner(currentYear));
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    private static String getInputFromScanner(int currentYear) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("What's your name ?");
        String name = scanner.nextLine();

        boolean validDOB = false;
        int age = 0;

        do{

            try {
                System.out.println("Enter a year of birth >= "+(currentYear - 125)+ " and <= "+currentYear);
                age = checkData(currentYear,scanner.nextLine() );
                validDOB = (age > 0 )? true : false;
            } catch (NumberFormatException badUserData) {
                System.out.println("Characters not allowed !!! Try again.");
            }


        }while(!validDOB);

        return "So you are "+age+" years old and Thank you "+name+" for taking this course..";
    }

    public static int checkData(int currentYear , String yearOfBirth){
        int yob = Integer.parseInt(yearOfBirth);
        int minimumYear = currentYear - 125;

        if(yob > currentYear || yob < minimumYear)
            return -1;

        return currentYear - yob;
    }
}
