package lambda_expressions.part4_functional_interfaces.predicate_interfaces;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        ///////////////////////////////////////////////
        // ////////////////////////////////////////////
        // Example That Uses Predicate Interface //////
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

    }
}
