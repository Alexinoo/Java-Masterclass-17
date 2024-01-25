package Fundamentals.control_flow.coding_challenge;

import java.util.Scanner;

public class MinAndMaxChallenge {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int minNumber = 0;
        int maxNumber = 0;
        int loopCount = 1;

        while (true) {

            System.out.println("Enter a number, or any character to exit : ");
            String nextNumber = scanner.nextLine();

            try {
                int number = Integer.parseInt(nextNumber);
                if (loopCount == 1 || number > maxNumber)
                    maxNumber = number;

                if (loopCount == 1 || number < minNumber) {
                    minNumber = number;
                }
                loopCount++;

            } catch (NumberFormatException e) {
                break;
            }

        }
        if (loopCount > 1){
            System.out.println("Maximum number : " + maxNumber);
            System.out.println("Minimum number : " + minNumber);
        }else{
            System.out.println("No valid data data was entered");
       }
    }
}
