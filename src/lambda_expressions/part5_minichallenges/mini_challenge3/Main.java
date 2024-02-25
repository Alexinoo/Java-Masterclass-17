package lambda_expressions.part5_minichallenges.mini_challenge3;

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

        ////////////////////////////////////////////////////////
        /// Write the code to execute above lambda expression //
        /// Use 1234567890 as the parameter ////////////////////

       String result = usingUnaryOperatorInterface.apply("1234567890");
       System.out.println(result);
    }
}
