package lambda_expressions.part2_lambdaExpressions;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<String> listArr = new ArrayList<>(List.of("Alpha","Bravo","Charlie","Delta"));

        //Print list
        normalPrintList(listArr);

        //Enhanced forEach()
        enhancedPrintList(listArr);
    }
    public static void normalPrintList(List<String> arr){
        System.out.println("//// enhanced for loop ////");
        for (var element : arr) {
            System.out.println(element);
        }
    }

    public static void enhancedPrintList(List<String> arr){

        System.out.println("//// forEach() in action ////");
        arr.forEach(element -> System.out.println(element));

        System.out.println("//// Alternatives forEach() implementation ////");
        String prefix = "NATO";
        arr.forEach((var element)->{
            char first = element.charAt(0);
            System.out.println(prefix + " phonetic "+element +" means "+first);
        });
        //prefix = "Something else"; cannot be re-assigned ; it's final or effectvely final
    }
}
