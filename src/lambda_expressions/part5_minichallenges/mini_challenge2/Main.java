package lambda_expressions.part5_minichallenges.mini_challenge2;

import java.util.function.Function;
import java.util.function.UnaryOperator;

public class Main {

    public static void main(String[] args) {

    }

    public static String everySecondChar(String source){
        StringBuilder returnVal = new StringBuilder();
        for (int i = 0; i < source.length(); i++) {
            if(i % 2 == 1){
                returnVal.append(source.charAt(i));
            }
        }
        return returnVal.toString();
    }
    //////////////////////////////////////////////////
    ////// Rewrite above method in Lambda expression//
    //////////////////////////////////////////////////

    //
    //
    // Option 1
    // - The method has to take a String and return a String
    // - We can do this Function with both type arguments as String

    Function<String , String> usingFunctionInterface = source -> {
        StringBuilder returnVal = new StringBuilder();
        for (int i = 0; i < source.length(); i++) {
            if(i % 2 == 1){
                returnVal.append(source.charAt(i));
            }
        }
        return returnVal.toString();
    };

    //
    //
    // Option 2
    // - We can also use UnaryOperator with a single String type argument
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
