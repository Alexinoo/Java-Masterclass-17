package nuts_and_bolts.part5_intro_to_bigdecimal;

import java.math.BigDecimal;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        /*
        * Imagine we work for a Life insurance & we are responsible for generating the code
        * to distribute funds to beneficiaries
        * The last client who recently passed had a 100 million dollar policy
        * He has 3 beneficiaries with equal shares
        */

        /* Create a local variable - policy amount and set it to 100m */
        double policyAmount = 100_000_000;

        /* set beneficiaries to 3 */
        int beneficiaries = 3;

        /* Get the percentage first as a float
         * Get the percentage second as a double
         */
        float percentageFloat = 1.0f / beneficiaries ;
        double percentageDouble = 1.0 / beneficiaries;

       /*
        * Then multiply policy amount by that
        * The expression that used float is actually over paying by a dollar for each payout
        */
        System.out.printf("Payout for each beneficiary (float)= %,.2f%n",policyAmount *percentageFloat); //33,333,334.33
        System.out.printf("Payout for each beneficiary (double) = %,.2f%n",policyAmount *percentageDouble); // 33,333,333.33

        /*
         * Get amount remaining after payout for each beneficiary
         * If this is right we should get 0 here
         * This means using a float has overpaid the beneficiaries
         */
        double totalUsingFloat = policyAmount - ((policyAmount * percentageFloat) * beneficiaries);
        System.out.printf("totalUsingFloat: %,.2f%n",totalUsingFloat);   //-2.98

        double totalUsingDouble = policyAmount - ((policyAmount * percentageDouble) * beneficiaries);
        System.out.printf("totalUsingDouble: %,.2f%n",totalUsingDouble); //0.00

        /*
         * But wait a minute
         * Float
         * If we wrote 3 cheques for the payout amount wouldn't we be 2.99 over and not 2.98 since each
         * payout is 1 dollar over
         * This could happen because there is one penny that wasn't included, it couldn't be divided evenly
         * with a 2-digit scale
         * The stored number has additional numbers we are not seeing in the decimal part of the number even
         * though we might have cut a cheque for a 2-digit decimal amount i.i 33 cents in other words
         *
         * Doubles
         * Are working, so that's good enough
         * But then again 33,333,333.33 * 3 =  99,999,999.99 , whey don't we then have an extra penny laying
         * somewhere, i.e 0.01
         * Again, this is caused because we are storing our payout amount without rounding it to 2 dp
         *
         */

        /*
         * For this reason, BigDecimal is recommended for financial applications
         * They let you control how no's are rounded without losing precision in calculations
         * Many floating no's can only approx the decimal no they're supposed to represent
         * Think of 1/3 which has an infinitely repeating decimal
         * This fraction, by necessity will get rounded at some point
         * It's scale wasn't big enough as a float, when we applied it to our fairly large insurance benefit
         * The double worked well for this calculation, but it was still off by a penny, when reconciling a rounded
         * amount, with a calculated double amount
         */

        /*
         * BigDecimal
         * ----------
         * This class stores a floating point number in 2 integer fields
         * The 1st field holds an unscaled value, with a type of BigInteger (another class in java.math package
         * that can store numbers bigger than even long values)
         * The second field is the scale, which can be +ve , 0 or -ve
         * A positive or 0 scale defines how many digits in the unscaled value, are after the decimal point
         * You can use a -ve scale, which means the unscaled value is multiplied by 10 to the power of the negation
         * of the scale
         */

        /* Example - Strings
         * Declare an array initializer with 4 strings
         * An array of BigDecimals & initialize it to the same size as String[]
         * Use Arrays.setAll passing it bds[] and use for each create a new BigDecimal instance using
         * one of the constructors  available which takes a String value
         * Loop through the bds[] and print out the Unscaled value, scale and the precision
         * Format the headers- so the values can be lined up under them
         * Use enhanced for loop
         * We can get the unscaled value,the scale & the precision using methods on the BigDecimal class
         */

        String[] tests = {"15.456","8","10000.000001",".123"};
        BigDecimal[] bigDecimals = new BigDecimal[tests.length];
        Arrays.setAll(bigDecimals, i -> new BigDecimal(tests[i]));

        System.out.printf("%-14s %-15s %-8s %s%n","Value","Unscaled Value","Scale","Precision");
        for (var bigDecimal:bigDecimals) {
            System.out.printf("%-15s %-15d %-8d %d %n",
                    bigDecimal,bigDecimal.unscaledValue(),bigDecimal.scale(),bigDecimal.precision());
        }

        /* Example 2 - Working with doubles
         * We get values rep floating point approximations for the double values specified
         * It's therefore highly recommended that you don't use the BigDecimal constructor that takes a double
         * The string constructor is much more precise
         * Notice the warning from intelliJ on new BigDecimal(doubles[i])
         * Replace with intelliJ suggestion of  BigDecimal.valueOf(doubles[i])
         * We get similar output but with 1 exception i.e number 8 rep as 8.0 , 80 and precision of 2
         * The value.Of() converts the double arg passed to it, to a String using the Double wrapper toString
         * and then calls the constructor version that takes  a string
         * Gives predictable results but not without problems either
         */
        System.out.println("_".repeat(50));
        double[] doubles = {15.456,8,10000.000001,.123};
        Arrays.setAll(bigDecimals, i -> BigDecimal.valueOf(doubles[i]));

        System.out.printf("%-14s %-15s %-8s %s%n","Value","Unscaled Value","Scale","Precision");
        for (var bigDecimal:bigDecimals) {
            System.out.printf("%-15s %-15d %-8d %d %n",
                    bigDecimal,bigDecimal.unscaledValue(),bigDecimal.scale(),bigDecimal.precision());
        }

        /*
         * Create 2 instances of BigDecimal
         * The first one takes a String, that has a number with 25 d.p
         * The second one will use BigDecimal.valueOf with the same numeric value
         *
         * Note that weh we use a String representation of a number, we get value in the BigDecimal exactly
         * as we specified it in the String
         * This isn't true of the BigDecimal.valueOf() passing it a double
         * This is because a double has a scale that's 16 digits long because of the limitations of storing floating point
         * numbers as approx & the limited scale used.
         *
         * You should always use the String Constructor to create a BigDecimal for a predictable value
         */
        System.out.println("_".repeat(50));
        BigDecimal test1 = new BigDecimal("1.1111122222333334444455555");
        BigDecimal test2 =  BigDecimal.valueOf(1.1111122222333334444455555);
        System.out.printf("%-30s %-30s %-8s %s%n","Value","Unscaled Value","Scale","Precision");
        System.out.printf("%-30s %-30d %-8d %d %n",
                    test1,test1.unscaledValue(),test1.scale(),test1.precision());
        System.out.printf("%-30s %-30d %-8d %d %n",
                test2,test2.unscaledValue(),test2.scale(),test2.precision());
    }
}















