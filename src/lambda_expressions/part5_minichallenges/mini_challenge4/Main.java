package lambda_expressions.part5_minichallenges.mini_challenge4;

import java.util.function.Function;
import java.util.function.UnaryOperator;

public class Main {

    public static void main(String[] args) {

        UnaryOperator<String> usingUnaryOperatorInterface = source -> {
            StringBuilder returnVal = new StringBuilder();
            for (int i = 0; i < source.length(); i++) {
                if(i % 2 == 1){
                    returnVal.append(source.charAt(i));
                }
            }
            return returnVal.toString();
        };
    }


    ///////////////////////////////////// ///////////////////////////////
    //////// Write a method called everySecondChar() which accepts a ////
    //  Function, or UnaryOperator as a parameter and also takes a //////
    //  second parameter that let's us pass "1234567890" should return //
    //  the result of the call to the functional method /////////////////


    //
    //
    // Option 1

    public static String everySecondChar(Function<String,String> function , String source){

        return function.apply(source);
    }

    //
    //
    // Option 2

    public static String everySecondCharCopy(UnaryOperator<String> function , String source){

        return function.apply(source);
    }
}
