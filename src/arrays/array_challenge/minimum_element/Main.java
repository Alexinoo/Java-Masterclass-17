package arrays.array_challenge.minimum_element;

import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        int[] myArray = readIntegers(); //106, 26, 81, 5, 15

        System.out.println(Arrays.toString(myArray));

        int minElement = findMin(myArray);

        System.out.println("Minimum element "+minElement);
    }

    private static int[] readIntegers(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a list of integers, separated by commas:");
        String input = scanner.nextLine();

        String[] splits = input.split(",");
         int[] valuesArray = new int[splits.length];

        for (int i = 0; i < splits.length; i++) {
            valuesArray[i] = Integer.parseInt(splits[i].trim());
        }
        return valuesArray;
    }

    private static int findMin(int[] array){
        int min = Integer.MAX_VALUE;
        for (int el: array) {
            if (el < min)
                min = el;
        }
        return min;
    }
}

