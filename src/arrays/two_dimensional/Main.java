package arrays.two_dimensional;

import java.util.Arrays;
import java.util.Random;

public class Main {

    public static void main(String[] args) {

        int[][] multiArray = new int[4][3];
        System.out.println(Arrays.toString(multiArray));
        System.out.println("multiArray length = "+multiArray.length);

        //System.out.println("first row -->1 = "+multiArray[0].length);
        //System.out.println("second row -->2 = "+multiArray[1].length);
        //System.out.println("third row -->3 = "+multiArray[2].length);
        //System.out.println("fourth row -->4 = "+multiArray[3].length);

        //Loop with Enhanced loop
        for (int[] outer: multiArray ) {
            System.out.println(Arrays.toString(outer));
        }

        System.out.println("_".repeat(30));

        //Loop with traditional nested for loop
        // int rows = multiArray.length;
        for (int i = 0; i <  multiArray.length; i++) {
            for (int j = 0; j < multiArray[i].length; j++) {
                System.out.print(multiArray[i][j] +"  ");
            }
            System.out.println();
        }

        System.out.println("_".repeat(30));

        //Nested Enhanced For loop
        for(var outer : multiArray){
            for(var element : outer){
                System.out.print(element +"  ");
            }
            System.out.println();
        }

        //Arrays.deepToString - Helps us to print 2 dimensional arrays
        System.out.println("_".repeat(30));

        System.out.println(Arrays.deepToString(multiArray));


        System.out.println("_".repeat(30));


        //Initializing Two dimension with random values and printing them
        for (int i = 0; i < multiArray.length; i++) {
            for (int j = 0; j < multiArray[i].length; j++) {
                multiArray[i][j] = getRandomInt();
                System.out.print( multiArray[i][j] + "  ");
            }
            System.out.println();
        }



    }

    private static int getRandomInt(){
        Random random = new Random();
        int randomInt = random.nextInt(100);
        return randomInt;
    }
}
