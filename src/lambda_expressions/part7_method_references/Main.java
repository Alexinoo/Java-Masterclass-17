package lambda_expressions.part7_method_references;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;

public class Main {
    public static void main(String[] args) {

        List<String> list = new ArrayList<>(List.of("Alex","Cyrus","Evans","Loice"));

        list.forEach(System.out::println);

        //Invoking calulator with different parameter types
        calculator(Integer::sum,10,25);
        calculator(Double::sum,2.5,7.5);

        //Supplier is a function interface that has a functional method get() that takes no arguments but returns an instance
        //Setting it up with a lambda expression that returns a new instance of the PlainOld obj
        // Our variable here is Supplier<PlainOld> using PlainOld as type

        Supplier<PlainOld> reference = PlainOld::new; // Execution deferred

        //Executing
        PlainOld newPojo = reference.get();

        //Calling seedArray()
        System.out.println("//// Getting array//////////");
        PlainOld[] pojo = seedArray(PlainOld::new,10);
    }

    private static <T> void calculator(BinaryOperator<T> function,T value1,T value2){
        T result = function.apply(value1,value2);
        System.out.println("Result of the operation: "+result);
    }

    private static PlainOld[] seedArray(Supplier<PlainOld> reference,int count){
        PlainOld[] array = new PlainOld[count];
        Arrays.setAll(array, i -> reference.get());
        return array;
    }
}
