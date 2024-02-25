package lambda_expressions.part4_functional_interfaces.function_interface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        ///////////////////////////////////////////////
        // ////////////////////////////////////////////
        // Example That Uses Function Interface //////
        ///////////////////////////////////////////////
        ///////////////////////////////////////////////
        System.out.println("/// Example That Uses Predicate Interface ///");
        List<String> listArr = new ArrayList<>(List.of("Alpha","Bravo","Charlie","Delta"));
        listArr.removeIf(element -> element.equalsIgnoreCase("Bravo"));
        listArr.forEach(element -> System.out.println(element));

        ///Add more elements to the listArr
        listArr.addAll(List.of("Echo","Eclesiastics", "Foxtrot", "Golf", "Hotel"));

        //Printing List again
        System.out.println("/// NATO phonetic alphabet ///");
        listArr.forEach(element -> System.out.println(element));

        System.out.println("_".repeat(30));


        //Remove a string that starts with 'e' and 'a'
        listArr.removeIf(element->element.startsWith("Ec"));

        //Printing List again
        System.out.println("/// NATO phonetic alphabet - Removed alphabet starting with Ec ///");
        listArr.forEach(element -> System.out.println(element));


        //////////////////
        //
        // STARTED HERE !!!
        //
        // ////////

        //
        //
        // replaceAll()

        // Takes a Unary Operator as an argument
        // Returns a result which has the same type as the argument
        // We are guaranteed to get the same type we pass to it
        // i.e we can pass an array element and get a transformed  array elmnt back
        listArr.replaceAll(element->element.charAt(0) + " - "+element.toUpperCase());
        listArr.forEach(element -> System.out.println(element));


        /*
        //
        // setAll() - on Java.util.Arrays
        // Similar to the list.replaceAll but it takes an IntFunction
        // IntFunction - a function interface that has the apply() with an int primitive
           argument and it returns any type, but when executed as part of setAll, the type will
           match the type of the array element

         */

        //Create a new Array of Strings
        String[] emptyStrings = new String[10];
        System.out.println(Arrays.toString(emptyStrings)); //[null, null, null, null, null, null, null, null, null, null]

        //
        //
        //
        // Arrays.fill() - takes a single string and sets each element to that string
        //Fill the arrays with ""
        Arrays.fill(emptyStrings,"");
        System.out.println(Arrays.toString(emptyStrings));

        /*
        //
        //
        //setAll()
          - It lets us use a formula to populate each element this interface is different from
           list.replaceAll() because of instead having access to each element,
           we actually have access to each index value in the array
          - So the parameter is an integer rep the current index of the array element
         */
        Arrays.setAll(emptyStrings,(i)->" "+(i + 1)+". ");
        System.out.println(Arrays.toString(emptyStrings));


        /// Using switch(){} expression directly in the lambda expression
        Arrays.setAll(emptyStrings,(i)->" "+(i + 1)+". "+
                switch(i){
                 case 0 -> "One";
                 case 1 -> "Two";
                 case 2 -> "Three";
                    default -> "";
                });
        System.out.println(Arrays.toString(emptyStrings));









    }
}
