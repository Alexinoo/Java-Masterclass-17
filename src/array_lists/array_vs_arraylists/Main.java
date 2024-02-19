package array_lists.array_vs_arraylists;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        /*
            Arrays.asList()
                - Returns a special type of List
                - This is a good case to use var keyword which lets us declare variable
                  without knowing the type exactly
                - Now originalList is backed by originalArray and therefore we have access to
                  List's methods to make changes to the array which backs it
                - There4 , any method we use on the list, will have an effect on the array, that was the source of the list

           - Used as a way of just dressing the array that is passed as an argument to this method to make it look like a list
           - It's almost like a List Wrapper
           - You do this , if you want to use list functionality to manipulate the underlying array such as
                sorting the array but using List's sort method to do it
           - The list we get back from Arrays.asList(), isn't resizeable again because it's ultimate source is
                originalArray and arrays aren't resizeable
           - However, this method takes variable arguments
           - We can use it to create a fixed sized list , from a list of elements
         */
        String[] originalArray = new String[] {"First","Second","Third"};
        var originalList = Arrays.asList(originalArray);

        //Scenario 1 - Update 1st element
        originalList.set(0,"one");
        System.out.println("list: "+originalList);
        System.out.println("array: "+Arrays.toString(originalArray));

        System.out.println("_".repeat(30));

        /*
            Scenario 2 - Sort the list

            - The natural Order of sorting is to have uppercase letters considered less than lowercase letters
         */
        originalList.sort(Comparator.naturalOrder());
        System.out.println("list: "+originalList);
        System.out.println("array: "+Arrays.toString(originalArray));

         /*
            Scenario 3 - Add/remove an element

            - This type of list has limitations
            - Since it is backed by an array - we can't add/remove elements

          // originalList.remove(0); //Throws an UnSupportedOperationException
        // originalList.add("fourth"); //Throws an UnSupportedOperationException
         */

        /*
           - However, this method takes variable arguments
           - We can use it to create a fixed sized list , from a list of elements
           - The list returned by this method is modifiable
         */

        List<String> newList = Arrays.asList("Sunday","Monday","Tuesday");
        System.out.println(newList);



    }
}
