package Fundamentals.control_flow.coding_exercises;

import java.util.Scanner;

public class InputCalculator {

    public static void main(String[] args) {
        inputThenPrintSumAndAverage();
    }

    public static void inputThenPrintSumAndAverage(){
        Scanner scanner = new Scanner(System.in);

        double sum = 0;
        double avg = 0;
        int loopCounter = 1;

        while(true){

                System.out.println("Enter a number, or a character to quit ");
                String nextNumber = scanner.nextLine();

            try {
                double number = Double.parseDouble(nextNumber);
                sum+=number;
                avg = sum / loopCounter;
               loopCounter++;

            } catch (NumberFormatException e) {
                break;
            }

        }

        System.out.println("SUM = "+sum+ " AVG = "+avg);


    }
}
