package Fundamentals.control_flow.coding_challenge;

import java.util.Scanner;

public class ReadUserInputChallenge {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        double sum = 0;
        int counter = 1;
        do{
            System.out.println("Enter number #"+counter+" :");
            String nextNumber = scanner.nextLine();

            try {
                double number = Double.parseDouble(nextNumber);
                sum+=number;
                counter++;

            } catch (NumberFormatException e) {
                System.out.println("Invalid number");
            }
        }while(counter <= 5);

        System.out.println("The sum of the numbers entered is : "+sum);
    }


}
