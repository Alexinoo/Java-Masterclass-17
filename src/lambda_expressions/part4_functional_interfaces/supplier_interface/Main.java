package lambda_expressions.part4_functional_interfaces.supplier_interface;

import java.util.Arrays;
import java.util.Random;
import java.util.function.Supplier;

public class Main {

    public static void main(String[] args) {

        String[] names = {"Ann","Bob","Carol","David","Ed","Fred"};

         var randomList = randomlySelectedValues(15,names,
                    () -> new Random().nextInt(0,names.length)); //upper bound not exclusive

        System.out.println(Arrays.toString(randomList));
    }

    public static String[] randomlySelectedValues(int count,
                                                    String[] values,
                                                    Supplier<Integer> s){
        String[] selectedValues = new String[count];

        for (int i = 0; i < count; i++) {
            selectedValues[i] = values[s.get()];
        }
        return selectedValues;
    }


}
