package array_lists.array_lists_part3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        /*
            ArrayLists
            ===========

            - You can retrieve an element in arrayList by it's index as we reference them in array with square brackets
            - But for ArrayList we use get() method and not the square brackets
            - ArrayLists start with 0 like arrays
         */
        String[] items = {"apples","bananas","milk","eggs"};

        List<String> list = List.of(items);

        ArrayList<String> groceries = new ArrayList<>(list);
        groceries.add("yoghurt");

        ArrayList<String> nextList = new ArrayList<>(
                List.of("pickles","mustard","cheese")
        );

        groceries.addAll(nextList);
        System.out.println(groceries);

        // Accessing an element Get 3rd Item
        System.out.println("Third item ="+groceries.get(2));

        /* Searching an element in the list
            - ArrayList provides several methods to achieve this
                - contains() - returns boolean
                - indexOf() ,lastIndexOf() - returns the index pos of the elmnt in the list if found
                            instead of returning true/false ,Otherwise returns -1
            - ArrayLists can have duplicate elements
         */
        if(groceries.contains("mustard"))
            System.out.println("List contains mustard");

        groceries.add("yoghurt");
        System.out.println("first = "+groceries.indexOf("yoghurt"));
        System.out.println("last = "+groceries.lastIndexOf("yoghurt"));

        /* Remove element from the list
            - ArrayList provides several methods to achieve this
                - remove by index if we know it
                - remove by element value
            - Removes a single element
         */
        System.out.println(groceries);
        groceries.remove(1);
        System.out.println(groceries);
        groceries.remove("yoghurt");
        System.out.println(groceries);

         /* Remove all element from the list
            - ArrayList provides
                - removeAll() - removes the elements specified in the collection
                - retainAll() - opposite from remove - specifies the elements to be retained / deletes others not specified
                - clear() - removes all elements
         */
        groceries.removeAll(List.of("apples","eggs"));
        System.out.println(groceries);

        groceries.retainAll(List.of("apples","milk","mustard","cheese"));
        System.out.println(groceries);

        groceries.clear();
        System.out.println(groceries);
        System.out.println("isEmpty = "+groceries.isEmpty());

         /* addAll()
            - ArrayList provides
                - addAll() - adds the elements specified in the collection
                - Arrays.asList("","",) - use a helper class from java.util.Arrays
         */
        groceries.addAll(List.of("apples","milk","mustard","cheese"));
        groceries.addAll(Arrays.asList("eggs","pickles","mustard","ham"));

        /* sort()
            - ArrayList provides
                - sort() - sort the elements (alphabetically , ascending,reverse alphabetical order and so on...)
                - Takes 1 argument which is a type called Comparator
                        - Allows instances to be compared with one another
                - naturalOrder is a static method from Comparator interface class
                - Strings are ordered alphabetically from A-Z
                - Numbers will be ordered from smallest to largest
                - we can also call reverseOrder() static method on the Comparator type to sort in reverse
         */
        System.out.println(groceries);

        groceries.sort(Comparator.naturalOrder());
        System.out.println(groceries);

        groceries.sort(Comparator.reverseOrder());
        System.out.println(groceries);

        /*
            toArray() - Getting a list from an array with a different method on ArrayList
            - This is an overloaded method appropriately called toArray()
            - The most commonly used version which takes a typed array as an argument
            - It returns an array, the same size as the array we pass to it, and of same type
         */

        var groceryArray = groceries.toArray(new String[groceries.size()]);

        System.out.println(Arrays.toString(groceryArray));



    }
}
