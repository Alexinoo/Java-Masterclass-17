package lambda_expressions.part4_functional_interfaces;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;

public class Main {

    public static void main(String[] args) {
        System.out.println("//// Lambda Expression using BinaryOperator Interface - @FunctionalInterface ////");

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

        ////
        //
        // EXAMPLE THAT USES BOTH Consumer AND BiConsumer
        //
        //


        //List of arrays with 2 double values rep lat and long of 3 points on Mississipi river

        var coords = Arrays.asList(
                new double[]{47.2160 , -95.2348},
                new double[]{29.1566 , -89.2495},
                new double[]{35.1566 , -90.0859});

        // Print co-ordinates of Mississipi River
        printList(coords);

        //A lambda expression can be assigned to a local variable
        BiConsumer<Double,Double> p1 = (lat,lng) -> System.out.printf("[lat:%.3f lon:%.3f]%n",lat,lng);

        //Create a local variable and assign the first element in the co-ordinates using .get()
        //Returns first co-ordinate pair [47.2160 , -95.2348]
        var firstPoint = coords.get(0);

        //Call processPoint by passing firstPoint[0] -47.2160  and firstPoint[1]-95.2348
        // As well as p1 which is our lambda expression variable
        processPoint(firstPoint[0],firstPoint[1],p1);

        //Print all points with forEach()
        //Variations
        System.out.println("-".repeat(30));
        coords.forEach(coordinate -> processPoint(coordinate[0],coordinate[1],p1));
        System.out.println("//// Another forEach() variation - Passing actual lambda expression ///");
        coords.forEach(coordinate -> processPoint(coordinate[0],coordinate[1],(lat,lng) -> System.out.printf("[lat:%.3f lon:%.3f]%n",lat,lng)));



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

    public static <T> T calculator(BinaryOperator<T> function , T value1, T value2){
        T result = function.apply(value1,value2);
        System.out.println("Result of the operation: "+result);
        return result;
    }

    public static void printList(List<double[]> arr){
        arr.forEach( element -> System.out.println(Arrays.toString(element)));
    }

    public static <T> void processPoint(T t1, T t2, BiConsumer<T,T> consumer){
        consumer.accept(t1,t2);
    }


}
