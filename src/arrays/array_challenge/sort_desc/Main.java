package arrays.array_challenge.sort_desc;

import java.util.Arrays;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        int[] myIntArray = getRandomArray(5);

        System.out.println("Before sorting..");
        System.out.println(Arrays.toString(myIntArray));

        System.out.println("Sorted in Desc..");

        for (int i = myIntArray.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if(myIntArray[j] < myIntArray[j+1]){
                    int temp = myIntArray[j];
                    myIntArray[j] = myIntArray[j+1];
                    myIntArray[j+1] = temp;
                }
            }
        }

        System.out.println(Arrays.toString(myIntArray));

    }

    public static int[] getRandomArray(int len){
        Random random = new Random();
        int[] newArray = new int[len];
        for (int i = 0; i < len; i++) {
            newArray[i] = random.nextInt(100);
        }
        return newArray;
    }
}
