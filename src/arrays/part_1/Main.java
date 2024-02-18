package arrays.part_1;

public class Main {

    public static void main(String[] args) {

        int[] myIntArray = new int[10];
        myIntArray[0] = 45;
        myIntArray[1] = 1;
        myIntArray[5] = 50;


        double[] myDoubleArray = new double[10];
        myDoubleArray[2] = 3.5;

        //Array Initializer -  Can only be used during initialization
        int[] firstTen = {1,2,3,4,5,6,7,8,9,10};

        //Get Length
        int arrayLength = firstTen.length;
        System.out.println("length of the array = "+arrayLength);

        // Get first element of the array
        System.out.println("first = "+ firstTen[0]);

        // Get last element of the array
        System.out.println("last = "+ firstTen[arrayLength - 1]);


        //Array Initializer - Does not allow this
        // int[] newArray;
        // newArray = {5,4,3,2,1};

        //Array Initializer - This is OK
         int[] newArray;
         newArray = new int[]{5,4,3,2,1};

         //Printing out array elements
        for (int i=0; i< newArray.length;i++){
            System.out.print(newArray[i]+" ");
        }
    }
}
