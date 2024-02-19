package array_lists.array_lists_part2;

import array_lists.array_lists_part1.GroceryItem;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        /*
        *    ArrayList<GroceryItem> groceryList = new ArrayList<>();
        *
        *   - Not allowed to omit new ArrayList<>(); to new ArrayList();
        *   - add() - is the simplest form of add() - add elements to the end of the list
        *   - Printing - we can simply use System.out.println(groceryList) and no need to use Arrays.toString() helper class
         */
        ArrayList<GroceryItem> groceryList = new ArrayList<>();
        groceryList.add(new GroceryItem("butter"));
        groceryList.add(new GroceryItem("milk"));
        groceryList.add(new GroceryItem("oranges","PRODUCE",5));
        System.out.println(groceryList);

        /* Suppose we want to add apples at the beginning of the list
        *  use add(index,new GroceryItem(...))
        * */

        groceryList.add( 0,new GroceryItem("apples","PRODUCE",6));
        System.out.println(groceryList);

        /* Suppose we want to update apples to something else
         *  use set(index,new GroceryItem(...))
         * */
        groceryList.set( 0,new GroceryItem("banana","PRODUCE",6));
        System.out.println(groceryList);

        /* Suppose we want to remove milk from the list
         *  use remove(index)
         * */

        groceryList.remove( 2);
        System.out.println(groceryList);
    }
}
