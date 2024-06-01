package JavaFx.part8_sample_todoList;

public class p10_SortedList {
    /*
     * Sorted List
     * ...........
     * Display the items in the order in which they're due
     *  - Items choose sooner are near to the top of the listview & items due later at the bottom
     *
     * JavaFX has a SortedList class that will make implementation of this really quite easy
     * Because our list contains to-do instances and not strings, we need to provide a sorted list with a comparator
     * The comparator will tell the list how the items should be ordered
     * The SortedList will call the Comparator compare() when it's comparing 1 to-do item with another
     *  - In our case, we want the comparator to look at the deadline of the 2 instances when it's comparing
     *
     * Implementations
     * ................
     * Will do this in the initialize() just before setItems()
     * We're still going to use an observableArrayList to store the to-do list items
     * - But instead of using it to populate the listview, we'll wrap it in a sorted list instance & then use the sorted
     *  list itself to populate the listview
     * - Any changes to our observableArrayList will be reflected in the listview just like before, and the items in the list
     *   view will remain sorted
     *    e.g.  SortedList<TodoItem> sortedList = new SortedList<>()
     *
     * - new SortedList<>() takes a no of parameters
     *      - observableArrayList that contains to-do items
     *      - comparator
     *          - use an anonymous class and override the compare()
     *
     * Comparator
     * ..........
     * This compare() takes to-do items to be compared as the parameters o1 and o2
     *  - returns -ve value , if obj o1 is considered to be less than obj o2
     *  - returns 0 , if the 2 obj(s) are considered to be equal
     *  - returns +ve value , if obj o1 is greater than obj o2
     * So, basically we return -1, 0 or 1 depending on the obj(s) that are passed to us
     * - In our case, o1 and o2 are to-do item obj(s) and we want to compare their deadline values
     * - The deadlines are instances of the local date class & we're going to use that class as compareTo() to compare the
     *   2 deadlines
     *
     * Next,
     * We need to populate the listview with the sortedList instead of populating directly like we had done before
     *
     * And now the to-do items are sorted according to their deadline dates, with the ones due sooner at the top
     * and the ones due later at the bottom
     *
     * Next,
     * Let's add a new entry just to ensure this is working as it should
     * - First
     *      - Add a to-do item that's earlier ((Mar 22, 2016) than the task at the very top (Mar 23, 2016)
     *          - displayed at the top
     * - Second
     *      - Add a to-do item with a future date (June 02, 2024) than the current one (June 01, 2024)
     *          - displayed at the bottom
     * - Last
     *      - Add at the middle
     *          - works as expected
     *
     * Next,
     * If we delete an item, the order is still maintained
     *
     */
}
