package nuts_and_bolts.part2_randomization;

import java.util.Random;

public class Main {

    public static void main(String[] args) {
        /*
         * Math.random()
         * The Math.random() - uses an instance of the Random class and invokes the
         * nextDouble() on that class
         * The first time you call Math.random(), a single new instance of
         * java.util.Random is created & used for all subsequent calls
         * In general, you would probably use the random class but needs to be
         *  aware wht the code is doing
         * We can use Math.random() with a multiplier of 10
         * This gives us the numbers between 0 - 9 as double
         */
        for (int i = 0; i < 10; i++) {
            System.out.println(Math.random() * 10);
        }

        /*
         * We can also cast them to int
         */
        System.out.println("_".repeat(50));
        for (int i = 0; i < 10; i++) {
            System.out.println((int) (Math.random() * 10));
        }

        /*
         * If we wanted numbers between 1 - 10
         * Need to add 1 to the expression
         * Is a commonly used way to get a range of numbers
         * The lower bound is 1  and upper is the exclusive bound
         * In this case the upper bound is implied to be 11
         */
        System.out.println("_".repeat(50));
        for (int i = 0; i < 10; i++) {
            System.out.println((int) (Math.random() * 10) + 1);
        }

        /*
         * This looks familiar to the code showed on the first slide
         * This gets a random no between 65 and 91(65 + 26)
         */
        System.out.println("_".repeat(50));
        for (int i = 0; i < 10; i++) {
            System.out.println( (int) (Math.random() * 26) + 65);
        }

        /*
         * But why is this important ..?
         * Use printf instead of println
         * Print the random no first as a number using %d & positional identifier 1$
         * Say it is equal to a character specifier %c & the same positional identifier 1$
         */
        System.out.println("_".repeat(50));
        for (int i = 0; i < 10; i++) {
            System.out.printf("%1$d = %1$c%n", (int) (Math.random() * 26) + 65);
        }


        /*
         * Doing the same thing with Math.Random()
         * Create an instance of random
         * Use r.nextInt(lower , upper) - introduced in JDK17
         * Pass 65 as lower inclusive bound
         * Pass 91 as upper exclusive bound
         * These are 65 plus 26 letters in the English alphabet
         * Overloaded versions of nextInt,nextDouble and nextLong have default implementations
         */
        System.out.println("_".repeat(50));
        Random r = new Random();
        for (int i = 0; i < 10; i++) {
            System.out.printf("%1$d = %1$c%n", r.nextInt(65,91));
        }

        /*
         * Using characters in such cases - improves readability
         * change 65 and instead cast 'A' to an int
         * change 91 and instead cast 'z' to an int
         * Add 1 since this bound is exclusive
         * We get the same as above
         *
         * This means we can quickly change this to a diff character range without
         * having to look up the corresponding integers
         */
        System.out.println("_".repeat(50));
        for (int i = 0; i < 10; i++) {
            System.out.printf("%1$d = %1$c%n",
                    r.nextInt((int)'A',(int)'Z' + 1));
        }

        /*
         * Overloaded version of nextInt() with no args
         * get the full range of integers randomized including -ve integers
         */
        System.out.println("_".repeat(50));
        for (int i = 0; i < 10; i++) {
            System.out.printf("%1$d%n",r.nextInt());
        }

        /*
         * We can specify a negative lower bound also and pass a +ve upper bound too
         * prints numbers between -10 and 9
         */
        System.out.println("_".repeat(50));
        for (int i = 0; i < 10; i++) {
            System.out.printf("%1$d%n",r.nextInt(-10,10));
        }

        /*
         * Other methods on random that provide random streams
         * JDK added support, to get streams of random numbers with methods on this class
         * r.ints()
         * Returns an Int Stream
         * We can limit the stream to 10 ints and terminate the stream with a forEach that
         * simply prints the int
         *
         * Prints 10 random integers both +ve and -ve bounded by the integers MIN_VALUE & MAX_VALUE
         */
        System.out.println("_".repeat(50));
        r.ints()
                .limit(10)
                .forEach(System.out::println);

        /*
         * We can specify the lower and upper bound as well to r.ints()
         * Pass 0 and 10 to r.ints() as lower and upper bounds respectively
         */
        System.out.println("_".repeat(50));
        r.ints(0,10)
                .limit(10)
                .forEach(System.out::println);

        /*
         * There is 1 more version of this method
         * Has 3 arguments, 2nd and 3rd are the lower and upper bounds
         * The 1st arg specifies the size of the stream, meaning a finite will be returned
         * Instead of an infinite stream
         * Remove the limit() intermediate operation as it's no longer needed
         */
        System.out.println("_".repeat(50));
        r.ints(10,0,10)
                .forEach(System.out::println);

        /*
         * We can also pass a single argument to this method
         * Though it's important in this case when passing 1 arg to remember that we are only specifying
         * the stream size and not the upper bound
         * Running this prints 10 randomly generated ints, both -ve and +ve
         */
        System.out.println("_".repeat(50));
        r.ints(10)
                .forEach(System.out::println);

        /*
         * You can instantiate a new instance of random, with an overloaded constructor that takes
         * a single argument
         * This argument is a seed
         * In most programming languages, the random functions aren't truly random
         * Algorithms exist tht creates distribution of numbers, that achieve what a random distribution
         * might look like , over a large range of values
         * These are called pseudorandom number generators and they're dependent on an initial value
         * called a seed
         *
         *
         * Check the Random API
         *
         *
         * Create a long variable and set it to the System.nanoTime
         * This is what java code was using in parts of its expression
         * It returns the running JVM's high resolution time source in nanoseconds
         * Use it as our seed and create a new instance of random call it pseudoRandom
         * Call ints() with the pseudoRandom variable and pass streamSize,lower and upper bound
         * Use a lambda expression on forEach to print on single line with space instead of a method reference
         */
        System.out.println("_".repeat(50));

        long nanoTime = System.nanoTime();
        Random pseudoRandom = new Random(nanoTime);

        pseudoRandom.ints(10,0,10)
                .forEach( i -> System.out.print(i + " "));

        /*
         * Copy above and change from pseudoRandom to notReallyRandom
         * Notice we are using nanoTime as a seed in both instances
         * We get the same result as above since we are seeding both instances with the exact same number
         * If we re-run it, we get different numbers because nanoTime is a different seed each time
         * But they will be the same for these 2 instances of Random
         *
         * You could also use the setSeed() to do something similar
         */
        System.out.println();
        System.out.println("_".repeat(50));
        Random notReallyRandom = new Random(nanoTime);

        notReallyRandom.ints(10,0,10)
                .forEach( i -> System.out.print(i + " "));

        /*
         * You might use these methods to ensure you get a predictable set of pseudo randomized no's
         * either for testing or maybe verification of some statistical model that's based on a random population
         */



    }
}
