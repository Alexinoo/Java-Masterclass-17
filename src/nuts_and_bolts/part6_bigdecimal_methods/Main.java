package nuts_and_bolts.part6_bigdecimal_methods;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        int beneficiaries = 3;
        /*
         * With BigDecimal, you get to decide how to round a number and what scale you want to use
         * Suppose we only want 2 dp..these no's rep financial no's so we want a scale of 2 in all cases
         * BigDecimal has a setScale() which we can include after the printf statmnt
         * copy printing below to see what it does
         * We get an err because we need to tell java how we want numbers that have bigger scale than 2
         *  need to be rounded
         * In this case, when we specify nothing,Java uses it's default rounding which is not a round at all
         * There are cases where you know it's safe not to specify rounding mode, but in most cases you will
         * likely pick one
         * These modes are stored on enum called RoundingMode which we can pass as a 2nd parameter to setScale
         * We can use RoundingMode.HALF_UP which is pretty normal in financial apps - means if anything is higher
         * in the insignificant digit, then it will be rounded up
         *
         * However, we still don't see changes because BigDecimal is immutable and intelliJ warns us on
         *   bigDecimal.setScale(2, RoundingMode.HALF_UP);
         * that the result is ignored meaning it's going to return a new instance of BigDecimal and
         * we need to assign to some sort of variable
         * Really important to remember that every method you use on BigDecimal needs to be assigned to a variable
         *
         */
        BigDecimal[] bigDecimals = new BigDecimal[4];
        double[] doubles = {15.456,8,10000.000001,.123};
        Arrays.setAll(bigDecimals, i -> BigDecimal.valueOf(doubles[i]));

        System.out.printf("%-14s %-15s %-8s %s%n","Value","Unscaled Value","Scale","Precision");
        for (var bigDecimal:bigDecimals) {
            System.out.printf("%-15s %-15d %-8d %d %n",
                    bigDecimal,bigDecimal.unscaledValue(),bigDecimal.scale(),bigDecimal.precision());
            bigDecimal = bigDecimal.setScale(2, RoundingMode.HALF_UP);
            System.out.printf("%-15s %-15d %-8d %d %n",
                    bigDecimal,bigDecimal.unscaledValue(),bigDecimal.scale(),bigDecimal.precision());
        }


        /*
         * Example - Using similar to the life insurance example but using BigDecimal instead
         * Printing it
         * We get an err - turns out like with Double wrapper, if we can create a BigDecimal with s string,
         * we can't use numeric literal feature like underscores , or suffixes such as L or F
         * Also can't use commas
         * So let's remove 100_000_000 the underscores here
         */
        System.out.println("_".repeat(50));
        BigDecimal policyPayout = new BigDecimal("100000000");
        System.out.printf("%-15s %-15d %-8d %d %n",
                policyPayout,policyPayout.unscaledValue(),policyPayout.scale(),policyPayout.precision());

        /*
         * Using 100000000.00 instead
         */
        System.out.println("_".repeat(50));
         policyPayout = new BigDecimal("100000000.00");
        System.out.printf("%-15s %-15d %-8d %d %n",
                policyPayout,policyPayout.unscaledValue(),policyPayout.scale(),policyPayout.precision());

        /*
         * Using e notation
         * since my number is 100 million use 100e6 since it's easier to remember that 6 stands for a million
         * We now have a -ve scale -6 which corresponds negatively to e6
         * A -ve scale does not mean the number is purely fractional, which you might imagine first
         * It is simplifying having to include all the places less significant than a million
         * In fact, when you now apply rounding to a number with a -ve scale, you'd be rounding in the millions

        System.out.println("_".repeat(50));
        policyPayout = new BigDecimal("100e6");
        System.out.printf("%-15s %-15d %-8d %d %n",
                policyPayout,policyPayout.unscaledValue(),policyPayout.scale(),policyPayout.precision());
        */


        /*
         * We want our percentage value based on my beneficiaries
         * BigDecimal provides some static BigDecimal values for us, so we don't have to create the most common values
         * These are BigDecimal.ZERO , BigDecimal.ONE & BigDecimal.TEN
         * Use BigDecimal.ONE and call divide on that passing it a new instance of BigDecimal that we get by using
         * valueOf, and passing it my integer, beneficiaries
         * Printing the result
         * We get an err since we will get a floating point number and we havent specified how we want this to be rounded
         * Therefore ,we need to add MathContext.UNLIMITED, but we get an err since this is the default if you don't specify
         *
         * We have several other presets to chose from such as
         *  MathContext.DECIMAL32 which gives us 0.3333333 (7 digits after decimal)
         *  MathContext.DECIMAL64 which gives us 0.3333333333333333 (16 digits after decimal)
         *  MathContext.DECIMAL128 which gives us 0.3333333333333333333333333333333333 (34 digits after decimal)
         */
        //BigDecimal percent = BigDecimal.ONE.divide(BigDecimal.valueOf(beneficiaries)); //err
        //BigDecimal percent = BigDecimal.ONE.divide(BigDecimal.valueOf(beneficiaries), MathContext.UNLIMITED); //err default
        //BigDecimal percent = BigDecimal.ONE.divide(BigDecimal.valueOf(beneficiaries), MathContext.DECIMAL32);
        //BigDecimal percent = BigDecimal.ONE.divide(BigDecimal.valueOf(beneficiaries), MathContext.DECIMAL64);
        BigDecimal percent = BigDecimal.ONE.divide(BigDecimal.valueOf(beneficiaries), MathContext.DECIMAL128);
        System.out.println(percent);

        /*
         * If we wanted more precision than that we can always create our own MathContext instance & specify exactly how
         * precise we want it and how we want it rounded
         * Notice the above ended in 3
         * Let's say for whatever reason, we want to round up the last digit and we want a precision of 60
         * We can pass a new MathContext with the values 60 and RoundingMode.Up in that constructor
         *  We get 0.333333333333333333333333333333333333333333333333333333333334 which is an even more precise rep of 1/3
         * than the other 3 standards
         * BigDecimal is said to have arbitrary precision because you the developer get to decide what precision you need
         * and want to use in your math operations
         */
        percent = BigDecimal.ONE.divide(BigDecimal.valueOf(beneficiaries),
                new MathContext(60,RoundingMode.UP));

        System.out.println(percent);

        /*
         * Let's now check amount for each beneficiary using more BigDecimal maths
         * Won't use MathContext
         * Print formatting nicely
         * We get 333333333.33 but is this really the value
         * add setScale(2,RoundingMode.HALF_UP)
         */
        BigDecimal checkAmount = policyPayout.multiply(percent);
        System.out.printf("%.2f%n",checkAmount);
        checkAmount = checkAmount.setScale(2,RoundingMode.HALF_UP);
        System.out.printf("%-15s %-15d %-8d %d %n",
                checkAmount,checkAmount.unscaledValue(),checkAmount.scale(),checkAmount.precision());

        /*
         * Get the total amount of the checks
         * And subtract from the policyPayout
         * we get Combined: 99999999.99
         * and Remaining = 0.01
         * This means we haven't lost a penny
         */
        BigDecimal totalChecksAmount = checkAmount.multiply(BigDecimal.valueOf(beneficiaries));
        System.out.printf("Combined: %.2f%n",totalChecksAmount);
        System.out.println("Remaining = "+policyPayout.subtract(totalChecksAmount));

        /*
         * For good Measure
         * the scale is still 2
         */
        System.out.printf("%-15s %-15d %-8d %d %n",
                totalChecksAmount,totalChecksAmount.unscaledValue(),totalChecksAmount.scale(),checkAmount.precision());

    }
}
