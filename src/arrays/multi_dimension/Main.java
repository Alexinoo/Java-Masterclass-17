package arrays.multi_dimension;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        int[][] myArray = new int[4][4];

        for (int i = 0; i < myArray.length; i++) {
            for (int j = 0; j < myArray[i].length; j++) {
                System.out.print(myArray[i][j] + "  ");
            }
            System.out.println();
        }

        System.out.println("_".repeat(30));

        // myArray[1] = {1,2,3}; - can't use an anonymous array with the assignment
        myArray[1] = new int[]{1, 2, 3};

        for (int i = 0; i < myArray.length; i++) {
            for (int j = 0; j < myArray[i].length; j++) {
                System.out.print(myArray[i][j] + "  ");
            }
            System.out.println();
        }

        System.out.println("_".repeat(30));

        //Create an array of 3 objects
        Object[] anyArray = new Object[3];

        System.out.println(Arrays.toString(anyArray));  //[null,null,null]

        //Assign anyArray[0] at the index of 0 an array of strings - 1 dimensional
        anyArray[0] = new String[]{"a", "b", "c"};
        System.out.println(Arrays.deepToString(anyArray));  //[[a,b,c],null,null]

        //Assign anyArray[1] at the index of 1 an array of strings - 2 dimensional
        anyArray[1] = new String[][]{
                {"1", "2"},
                {"3", "4", "5"},
                {"6", "7", "8", "9"},
        };
        System.out.println(Arrays.deepToString(anyArray));  //[ [a,b,c], [[1,2],[3,4,5],[6,7,8,9]], null]


        //Assign anyArray[2] at the index of 2 an array of integers - 3 dimensional
        anyArray[2] = new int[2][2][2];

        System.out.println(Arrays.deepToString(anyArray));  //[[a, b, c], [[1, 2], [3, 4, 5], [6, 7, 8, 9]], [[[0, 0], [0, 0]], [[0, 0], [0, 0]]]]


        System.out.println("_".repeat(30));
        //Print using Nested enhanced loop
        for (Object element: anyArray ) {
            System.out.println("Element type = "+element.getClass().getSimpleName());
            System.out.println("Element toString() = "+element);
            System.out.println(Arrays.deepToString((Object[])element));
        }
    }
}
