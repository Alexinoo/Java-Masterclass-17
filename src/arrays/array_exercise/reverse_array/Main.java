package arrays.array_exercise.reverse_array;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        int[] myArray;
        myArray = new int[]{1,2,3,4,5};
        reverse(myArray);

        System.out.println("_".repeat(45));

        myArray = new int[]{0,-2,38,42,15};
        reverse(myArray);

        System.out.println("_".repeat(45));


        myArray = new int[]{0,0,1,0,0};
        reverse(myArray);

        System.out.println("_".repeat(45));

        myArray = new int[]{5,4,3,2,1};
        reverse(myArray);
    }

    private static void reverse(int[] array){
        System.out.println("Array = "+Arrays.toString(array));
        int halfLength = array.length / 2;
        int maxIndex = array.length - 1;
        for (int i = 0; i < halfLength; i++) {
            int temp = array[i];
            array[i] = array[maxIndex - i];
            array[maxIndex - i] = temp;
        }
        System.out.println("Reversed array = "+Arrays.toString(array));
    }
}
