package arrays.array_exercise.min_element;

import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        int arraySize = readInteger();

        int[] returnedArray = readElements(arraySize);

        System.out.println(Arrays.toString(returnedArray));

        int minElement = findMin(returnedArray);

        System.out.println("min element = "+minElement);
    }

    private static int readInteger(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the size of your array: ");
        int inputSize = scanner.nextInt();

        return inputSize;
    }

    private static int[] readElements(int length){
        Scanner scanner = new Scanner(System.in);

        int[] intArray = new int[length];

        for (int i = 0; i < length; i++) {
            System.out.println("Please enter element : "+i);
            intArray[i] = scanner.nextInt();
        }
        return intArray;

    }

    private static int findMin(int[] array){
        int min = Integer.MAX_VALUE;
        for (int el: array ) {
            if(el < min)
                min = el;
        }
        return min;
    }
}
