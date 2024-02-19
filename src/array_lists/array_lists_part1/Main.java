package array_lists.array_lists_part1;

import java.util.Arrays;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

         /* CREATE AN ARRAY OF OBJECTS
         Limitations - groceryArray[2] = "5 Oranges"; Takes in  "5 Oranges" -
            Therefore might cause problems to any code that is going to process this data
            since groceryArray[2]  is assigned a string
                Object[] groceryArray = new Object[3];
                groceryArray[0] = new GroceryItem("milk");
                groceryArray[1] = new GroceryItem("apples","PRODUCE",6);
                groceryArray[2] = "5 Oranges";
                System.out.println(Arrays.toString(groceryArray));

                ////  OUTPUTS - Works Just Fine/////
            [
             GroceryItem[name=milk, type=DAIRY, count=1],
             GroceryItem[name=apples, type=PRODUCE, count=6],
              5 Oranges
              ]
        */

        /* CREATE AN ARRAY OF GroceryItem
        *  The compiler won't allow us to assign groceryArray[2] a string i.e.  "5 Oranges";
        *   groceryArray[2] = "5 Oranges";
        * */

        GroceryItem[] groceryArray = new GroceryItem[3];
        groceryArray[0] = new GroceryItem("milk");
        groceryArray[1] = new GroceryItem("apples","PRODUCE",6);
        groceryArray[2] = new GroceryItem("oranges","PRODUCE",5);
        System.out.println(Arrays.toString(groceryArray));


        /* Using ArrayList */
          /*
            Raw use of parameterized class 'ArrayList'
            If we don't specify a type, with an ArrayList, it's going to use the Object class by default
            This is called the raw use of this type and might results to the same problems we saw when we had an object array
            Meaning, any object at all could be put in this ArrayList
            And like what we had b4, a String may not be what the code wants or is expecting and generally not the behavior that we want
               e.g. objectList.add("yoghurt");
         */
        ArrayList objectList = new ArrayList();
        objectList.add(new GroceryItem("butter"));
        objectList.add("yoghurt");


        /* SO HOW DO WE FIX THIS !!
        *   - When you declare an ArrayList, you probably know what data you want in that list
        *   - In our case , it's a GroceryItem record and we don't want anything else but GroceryItems in the list
        *   - SO how do we specify type for an ArrayList
        *        ArrayList<GroceryItem> groceryList = new ArrayList<GroceryItem>();
        *   - We use greater than & less than signs also called angle brackets and we specify the type
        *   - This is the type we want all elements in this list to be , a GroceryItem record
        *   - It's normally included immediately following the ArrayList class in the declaration of the variable on the left
        *   - It's also specified in the instantiation of this list on the right of the assignment operator, or the equals sign
        *   - However, when we hover on this [ new ArrayList<GroceryItem>(); ]
        *           IntelliJ suggests to us that explicit type argument GroceryItem can be replaced with<> which means that it is not necessary
        *   - <> are also called diamond operators
        *   - Now the compiler will not let us add below to our GroceryItem list
        *       groceryList.add("yoghurt");
        *   - If we hover , it says that GroceryItem is the required type
        *   - Also .. notice we never specified the size of the arrayList,
        *       since it's a resizeable-array implementation meaning it's not a fixed size and therefore specifying might not be necessary
        *   - It will grow automatically as needed
        *

         * */
        ArrayList<GroceryItem> groceryList = new ArrayList<>();
        groceryList.add(new GroceryItem("butter"));

    }

}
