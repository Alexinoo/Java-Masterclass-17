package arrays.array_exercise.sorted_array;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        int[] myArray = getIntegers(5); //106, 26, 81, 5, 15

        printArray(sortIntegers(myArray));
    }

    public static int[] getIntegers(int length){
        int[] newArray = new int[length];
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < length; i++) {
            System.out.printf("Please enter element %d %n",i);
            newArray[i] = scanner.nextInt();
        }
        return newArray;
    }

    public static int[] sortIntegers(int[] unsortedArray){
        for (int i = unsortedArray.length-1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if(unsortedArray[j] < unsortedArray[j+1]){
                    int temp = unsortedArray[j];
                    unsortedArray[j] = unsortedArray[j+1];
                    unsortedArray[j+1] = temp;
                }
            }
        }
        return unsortedArray;
    }

    public static void printArray(int[] array){
        for (int i = 0; i < array.length; i++) {
            System.out.println("Element "+i+" contents "+array[i]);
        }
    }
}
