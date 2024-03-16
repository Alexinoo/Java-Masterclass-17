package nuts_and_bolts.part1_math_class;

public class Main {

    public static void main(String[] args) {

        /* Math Class
         * Think about the Math Class as your calculator
         * Has functions you'd find on a basic calculator as well as a few scientific methods
         */

        /* Create an integer that with MAX_VALUE minus 5 - it's only 5 integers away
         * from an overflow situation
         */

        int maxMinusFive = Integer.MAX_VALUE - 5;

        /*
         * Add a for loop that will cause an integer overflow
         * Add 2 variables in the loop initialization
            - id which will rep a variable we might use for an ID initialized to maxMinusFive val
            - j as the loop counter initialized to 0
         * Loop 10 times only
         * Incrementing both id variable and j in each iteration
         * Print id in each iteration
         *
         * My ids turned negative after the 6th iteration which is not a good thing
         */
        for (int j = 0,id = maxMinusFive; j < 10; id++,j++) {
            System.out.printf("Assigning id %,d%n",id);
        }

        /*
         * In cases where there is a chance you'll overflow, Java offers a series of methods on Math
         * which throws an exception if the value overflows or underflows
         * Replacing post increment operator on the id variable with a call to
         *  Math.incrementExact(id) passing id as the parameter
         * Remember to assign the result of this method to the id variable
         *
         * Running this throws an ArithmeticException indicating an integer overflow
         *
         * There are also methods named decrementExact, addExact, subtractExact and a couple of
         * others that can be used to catch an overflow situation

        System.out.println("_".repeat(50));
        for (int j = 0,id = maxMinusFive; j < 10; id = Math.incrementExact(id),j++) {
            System.out.printf("Assigning id %,d%n",id); // Throws an ArithmeticException
        }

        */



        /*
         * We also have the method Math.abs() for getting an absolute value of a number
         * Returns the magnitude of a number effectively eliminating any negative sign
         * Print the result of calling Math.abs on a negative no
         */
        System.out.println("_".repeat(50));

        System.out.println(Math.abs(-50)); //50

        /*
         * Suppose, we pass in Integer's min value to Math.abs()
         * This caused an integer overflow since the MAX value can only be 2147483647 and not 2147483648
         * If we wanted the absolute value of Integer.MIN_VALUE, we could use ABS Exact
         */
        System.out.println("_".repeat(50));

        System.out.println(Math.abs(Integer.MIN_VALUE)); // -2147483648

        /*
         * If we wanted the absolute value of Integer.MIN_VALUE, we could use Math.absExact
         * This throws an Integer flow exception
         * We can handle this exception by casting the result to a long type instead
         * Commenting out
         * Add casting to long - calls the overloaded version of the Math.abs that takes longs
         */
        System.out.println("_".repeat(50));

        //System.out.println(Math.absExact(Integer.MIN_VALUE)); // Throws an overflow exception
        System.out.println(Math.absExact((long)Integer.MIN_VALUE)); // 2147483648

        /*
         * Math.max()
         * Takes 2 numbers and return the maximum
         */
        System.out.println("_".repeat(50));
        System.out.println("Max = "+Math.max(10, -10)); //Max = 10

        /*
         * Math.min()
         * Pass 2 decimal no's that are pretty close together
         */
        System.out.println("_".repeat(50));
        System.out.println("Min = "+Math.min(10.0000002, 10.001)); //Min = 10.0000002

        /*
         * Suppose we make both of these floats by adding f suffix to both
         * We now get Min = 10.0
         * Float values aren't precise enough to maintain that least digit
         */
        System.out.println("_".repeat(50));
        System.out.println("Min = "+Math.min(10.0000002f, 10.001f)); //Min = 10.0

        /*
         * If we make the first one a double by removing f
         * We get Min = 10.0000002
         */
        System.out.println("_".repeat(50));
        System.out.println("Min = "+Math.min(10.0000002, 10.001f)); //Min = 10.0000002

        /*
         * Let's look at Math.round()
         * Takes either a double or a float and rounds down to a whole number
         * If the decimal number part is < 0.5 , it rounds down,
         * Otherwise if >= 0.5 , it rounds up
         * However, if the arguments are double, a long will be returned, otherwise an int will
         * be returned
         */
        System.out.println("_".repeat(50));
        System.out.println("Round down = "+ Math.round(10.2)); //10
        System.out.println("Round up = "+ Math.round(10.8)); // 11
        System.out.println("Round ? = "+ Math.round(10.5)); // 11

        /*
         * Math.floor()
         * Use this instead , if you don't want to round up
         * Returns a double
         */
        System.out.println("_".repeat(50));
        System.out.println("Floor = "+ Math.floor(10.8)); //10.0



        /*
         * Math.ceil()
         * Use this instead , if you don't want to round down
         * Returns a double
         */
        System.out.println("_".repeat(50));
        System.out.println("Ceil = "+ Math.ceil(10.2)); // 11.0

        /*
         * Math.sqrt()
         * returns the square root as a decimal number or a double
         */
        System.out.println("_".repeat(50));
        System.out.println("Square root of 100 = "+ Math.sqrt(100)); // 10

        /*
         * Math.pow(2,3)
         * used to calculate a number raise to the power of some other number
         * Accepts 2 params and returns a decimal number or a double
         */
        System.out.println("_".repeat(50));
        System.out.println("2 to the third power (2*2*2) = "+ Math.pow(2,3)); //8.0
        System.out.println("10 to the fifth power (10*10*10*10*10) = "+ Math.pow(10,5)); //100000.0


        /*
         * Math.random()
         * Call it in a for loop 10 times
         * Print what we get from Math.random()
         *
         * Return numbers between 0 and 1 including 0 but not 1
         */
        System.out.println("_".repeat(50));

        for (int i = 0; i < 10; i++) {
            System.out.println(Math.random());
        }





    }
}
