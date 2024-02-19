package array_lists.more_lists;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        //Create an Array of Strings with 4 items
        String[] items = {"apples","bananas","milk","eggs"};

        /*
            Use a method on List - List.of(items) which is a factory method
            factory method - We call factory method on a class and it returns a new instance of a class for us
            List.of(items) - of() is a static method on List simply called of and it returns a list of string elements back
            Then assigning it to a List variable of type String
        */

        List<String> list = List.of(items);
        System.out.println(list);

        System.out.println(list.getClass().getName()); //java.util.ImmutableCollections$ListN - Nested classes
        //list.add("yoghurt");
        // WON't work - Immutable



        //Create An ArrayList of Strings and add list to it
        ArrayList<String> groceries = new ArrayList<>(list);
        groceries.add("yoghurt");
        System.out.println(groceries);


        //Create An ArrayList of Strings
        // Pass items to the static method of List - List.of() explicitly from ArrayList constructor
        // This is one way to create and populate a small ArrayList in 1 stmnt with a few known elements

        ArrayList<String> nextList = new ArrayList<>(
                List.of("pickles","mustard","cheese")
        );
        System.out.println(nextList);

        //Other ways to add ELements
        //addAll() method - Takes any list as an argument
        // add all items from the nextList to groceries
        groceries.addAll(nextList);
        System.out.println(groceries);

    }
}
