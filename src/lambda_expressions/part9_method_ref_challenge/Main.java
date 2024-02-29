package lambda_expressions.part9_method_ref_challenge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.UnaryOperator;

public class Main {

    private static Random random = new Random();

    public static void main(String[] args) {
       String[] names = {"Anna","Bob","Cameron","Donald","Eva","Francis"};

       Person tim = new Person("Tim");


       List<UnaryOperator<String>> list = new ArrayList<>(List.of(
               String::toUpperCase,
               s -> s += " "+getRandomChar('D','M')+ ".",
               s-> s += " "+ reverse(s,0,s.indexOf(" ")),
               Main::reverse,
               String::new,
               //s -> new String("Howdy "+s),
               String::valueOf,
               tim::last,
               (new Person("Mary"))::last
               ));

        System.out.println("--> Transform Array to Uppercase");
       applyChanges(names,list);
    }

    public static void applyChanges(String[] namesArray,
                                    List<UnaryOperator<String>> stringFunctions){

        List<String> backedByArray = Arrays.asList(namesArray);
        for (var function: stringFunctions) {
            backedByArray.replaceAll(s -> s.transform(function));
            System.out.println(Arrays.toString(namesArray));
        }

    }

    private static char getRandomChar(char startChar, char endChar){
        return (char) random.nextInt((int)(startChar),(int)endChar + 1);
    }

    private static String reverse(String s){
        return reverse(s,0,s.length());
    }

    private static String reverse(String someString ,int startIndex,int endIndex){
        return new StringBuilder(someString.substring(startIndex,endIndex)).reverse().toString();
    }
}
