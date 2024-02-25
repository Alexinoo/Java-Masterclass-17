package lambda_expressions.part5_minichallenges.mini_challenge7;

import java.util.function.Supplier;

public class Main {

    public static void main(String[] args) {

        //////////////////////////////////////////////////
        /// Assign to a variable called supplierResult ///
        /// Print Out I love Java to the console /////////

        Supplier<String> iLoveJava = ()-> "I love Java";

        String supplierResult = iLoveJava.get();
        System.out.println(supplierResult);
    }
}
