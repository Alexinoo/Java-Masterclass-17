package lambda_expressions.part3_lambda_exprss_contnd;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<String> listArr = new ArrayList<>(List.of("Alpha","Bravo","Charlie","Delta"));

        //Print list
        normalPrintList(listArr);

        //Enhanced forEach()
        enhancedPrintList(listArr);

        System.out.println();
        System.out.println("//// Lambda Expression using Operation Interface - @FunctionalInterface ////");

        //Call calculator()
        //Variations
        int result0 = calculator((a,b)-> { int sum = a + b; return sum;},5,0);
        int result1 = calculator((a,b)-> { return a + b;},5,1);
        int result2 = calculator((a,b)-> a + b,5,2);
        var result3 = calculator((var a,var b)-> a + b,5,3);
        var result4 = calculator(( a, b)-> a / b,10.0,2.5);
        int result5 = calculator((Integer a,Integer b)-> a + b,5,4);
        double result6 = calculator((Double a,Double b)-> a + b,5.0,5.0);
        var result7 = calculator(( a, b)-> a.toUpperCase() + " " +b.toUpperCase(),"alex","mwangi");
    }

    public static <T> T calculator(Operation<T> function , T value1,T value2){
        T result = function.operate(value1,value2);
        System.out.println("Result of the operation: "+result);
        return result;
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
