package lambda_expressions.part5_minichallenges.mini_challenge6;

import java.util.function.Supplier;

public class Main {

    public static void main(String[] args) {

        //////////////////////////////////////////////////////////////////////////////////////////////////////////
        /// Write a lambda expression that is declared with the Supplier Interface ///////////////////////////////
        /// The lambda should return the String "I love java" and assign it to a local variable called iLoveJava//

        Supplier<String> iLoveJava = ()-> "I love Java";
        System.out.println(iLoveJava.get());
    }
}
