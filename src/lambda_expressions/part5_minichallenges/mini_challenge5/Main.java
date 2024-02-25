package lambda_expressions.part5_minichallenges.mini_challenge5;

import java.util.function.UnaryOperator;

public class Main {

    public static void main(String[] args) {

        UnaryOperator<String> everySecondChar = source -> {
            StringBuilder returnVal = new StringBuilder();
            for (int i = 0; i < source.length(); i++) {
                if(i % 2 == 1){
                    returnVal.append(source.charAt(i));
                }
            }
            return returnVal.toString();
        };

        //Call everySecondCharacter() and passing it function above and second parameter

        String result = everySecondCharacter(everySecondChar,"1234567890");
        System.out.println(result);
    }

    public static String everySecondCharacter(UnaryOperator<String> function ,
                                              String source){
        return function.apply(source);
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
}
