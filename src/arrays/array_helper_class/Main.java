package arrays.array_helper_class;

import java.util.Arrays;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        int[] firstArray = getRandomArray(5);

        System.out.println(Arrays.toString(firstArray));
        System.out.println();

        //Arrays.sort() - does not return a new array- sorts existing
        Arrays.sort(firstArray);
        System.out.println(Arrays.toString(firstArray));

        //Arrays.fill() - fills an array with specified value
        int[] secondArray = new int[5];
        System.out.println(Arrays.toString(secondArray)); //[0,0,0,0,0]
        Arrays.fill(secondArray,6); // Initializes all values with 6
        System.out.println(Arrays.toString(secondArray)); //[6,6,6,6,6]

        //Arrays.copy() - Makes copy of the original array -
        // creates a new instance of the original array and copies the elements to the new array
        int[] thirdArray = getRandomArray(5);
        System.out.println(Arrays.toString(thirdArray));

        int[] fourthArray = Arrays.copyOf(thirdArray,thirdArray.length);
        System.out.println(Arrays.toString(fourthArray));

        Arrays.sort(fourthArray);
        System.out.println(Arrays.toString(thirdArray)); // remains the same
        System.out.println(Arrays.toString(fourthArray)); //sorted

        //Suppose we pass a size that is less than the no of elements in the original array
        //Gives us the first 3 from the original array
        int[] smallerArray = Arrays.copyOf(thirdArray,3);
        System.out.println(Arrays.toString(smallerArray));


        //Suppose we pass a size that is greater than the number of elements in the original array
        //Gives us the exact values and initializes the rest to default int which is 0
        int[] largerArray = Arrays.copyOf(thirdArray,7);
        System.out.println(Arrays.toString(largerArray));

    }

    /*getRandomArray() - Return an array of random integers
    random.nextInt() returns num from 0 - 99 exclusive of 100
     returns 0 - max integer if bound not specified
    */
    public static int[] getRandomArray(int len){
        Random random = new Random();
        int[] newInt = new int[len];
        for(int i = 0; i < len; i++){
            newInt[i] = random.nextInt(50);
        }
        return newInt;
    }
}
