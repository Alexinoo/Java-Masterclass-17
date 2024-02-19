package arrays.array_challenge.reverse_array;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        int[] oddArray = {1,2,3,4,5};
        int[] evenArray = {10,9,8,7,6,5,4,3,2,1};

        System.out.println("Before Reverse");
        System.out.println(Arrays.toString(evenArray));

        reverse(evenArray);

        System.out.println("After Reverse");
        System.out.println(Arrays.toString(evenArray));

        int[] reversedCopyArray = reverseCopy(evenArray); // creates another memory and very inefficient

        System.out.println("After another Reverse");
        System.out.println(Arrays.toString(reversedCopyArray));

    }

    private static void reverse(int[] array){
        /* O(n) - 5 Iterations -  creates another memory and very inefficient
            int[] reversedArray = new int[array.length];
            for (int i = 0; i < array.length; i++) {
                reversedArray[i] = array[array.length- (i+1)];
            }
            return reversedArray;
         */

        /* O(n) - 2 Iterations */
        int maxIndex = array.length - 1;
        int halfLength = array.length / 2;

        for (int i = 0; i < halfLength; i++) {
            int temp = array[i];
            array[i] = array[maxIndex - i];
            array[maxIndex - i] = temp;
            // System.out.println("-->" + Arrays.toString(array));
        }

    }


    private static int[] reverseCopy(int[] array) {
        int[] reversedArray = new int[array.length];
        int maxIndex = array.length - 1;
        for (int el: array) {
            reversedArray[maxIndex--] = el;
             //System.out.println("-->" + Arrays.toString(reversedArray));
        }

        return reversedArray;
    }

}
