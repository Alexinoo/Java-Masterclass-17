package arrays.array_reference;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        int[] myIntArray = new int[5];
        int[] anotherArray = myIntArray;

        System.out.println("Before change");
        System.out.println(Arrays.toString(myIntArray));
        System.out.println(Arrays.toString(anotherArray));

        anotherArray[0] = 1;
        modifyArray(myIntArray); //passed as a reference/address

        System.out.println("After change");
        System.out.println(Arrays.toString(myIntArray));
        System.out.println(Arrays.toString(anotherArray));
    }

    private static void modifyArray(int[] array){
        array[1] = 2;
    }
}
