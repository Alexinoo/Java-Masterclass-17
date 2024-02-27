package lambda_expressions.part8_method_ref_deep_dive;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public class Main {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>(List.of("Alex","Cyrus","Evans","Loice"));

        list.forEach(System.out::println);

        //Invoking calulator with different parameter types
        calculator(Integer::sum,10,25);
        calculator(Double::sum,2.5,7.5);
        calculator((s1,s2)-> s1 + s2,"Hello ","World"); // Hello World
        calculator(String::concat,"Hello ","World"); // Hello World - using lambda

        // More examples
        BinaryOperator<String> bi = String::concat;
        BiFunction<String,String,String> bi2 = String::concat;
        UnaryOperator<String> u1 = String::toUpperCase;

        //Executing above
        System.out.println(bi.apply("Hello ","World"));
        System.out.println(bi2.apply("Hello ","World"));
        System.out.println(u1.apply("Hello"));

        //Transform - takes first a Function with a String type as an argument
        // and it returns an Object

        String result1 = "Alex".transform(u1); // ALEX
        String result2 = "Hello".transform(String::toUpperCase); // HELLO
        System.out.println("Result 1 = "+result1);
        System.out.println("Result 2 = "+result2);

        //Manipulating above result
        result1 = result1.transform(String::toLowerCase);
        System.out.println("Result 3 = "+result1); //alex

        //More Complex stuff
        Function<String , Boolean> f0 = String::isEmpty;
        boolean resultBoolean = result1.transform(f0);
        System.out.println("Result 4 = "+resultBoolean); //false




    }

    private static <T> void calculator(BinaryOperator<T> function, T value1, T value2){
        T result = function.apply(value1,value2);
        System.out.println("Result of the operation: "+result);
    }
}
