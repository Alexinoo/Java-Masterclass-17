package abstraction.interfaces.part8_comparable;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        /*
            Comparable
            =============

            - For an array, we can simply call Arrays.sort() and pass it an array and the elemnts
              in an array need to implement Comparable

            - Types like String, or primitive wrapper classes like Integer or Character are sortable
              and this is because they implement this interface

            - Comparable interface declaration in Java

                public interface Comparable<T>{

                    int compareTo( T o);
                  }
             - We can deduce that it's a generic type, meaning it's parameterized

             - Any class that implements this interface, needs to implement the compareTo()

             - This method takes one object as an argument denoted as letter o and compare it to the
               current instance shown as this
             - This is because we can refer to the current instance with the keyword this

             - The results of the compareTo() mean below when implemented and returns an integer

                resulting value   Meaning
                    zero            0 == this
                    negative value  this < 0
                    positive value  this > 0
         */

        //INTEGERS Implementing Comparable Interface

        Integer five = 5;
        Integer[] others = {0,5,10,-50,50};

        for (Integer i: others) {
            int val = five.compareTo(i);
            System.out.printf("%d %s %d: compareTo=%d%n",five,
                    (val == 0)? "==":(val < 0 ? "<" : ">"),i,val);
        }

        //String Implementing Comparable Interface
        // Compares integer values of the characters in the strings
        // Compares the first character and if they are same, compares the second and so on
        // returning the diff between the character's underlying integer values
        String banana = "banana";
        String[] fruits = {"apple","banana","pear","BANANA"};

        for (String fruit: fruits) {
            int val = banana.compareTo(fruit);
            System.out.printf("%s %s %s: compareTo=%d%n",banana,
                    (val == 0)? "==":(val < 0 ? "<" : ">"),fruit,val);
        }

        Arrays.sort(fruits);
        System.out.println(Arrays.toString(fruits));

        //Corresponding integer types
        //chars are stored in memory as positive integers values
        System.out.println("A:"+(int)'A' + " "+ "a:"+(int)'a');
        System.out.println("B:"+(int)'B' + " "+ "b:"+(int)'b');
        System.out.println("P:"+(int)'P' + " "+ "p:"+(int)'p');


        //CLASS EXAMPLE
        Student tim = new Student("Tim");
        Student[] students = {
          new Student("Zach"),
          new Student("Tim"),
          new Student("Ann"),
        };
        Arrays.sort(students);
        System.out.println(Arrays.toString(students));

        System.out.println("result = "+tim.compareTo("Mary")); // Fails throws ClassCastException



    }
}
