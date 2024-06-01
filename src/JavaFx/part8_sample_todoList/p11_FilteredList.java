package JavaFx.part8_sample_todoList;

public class p11_FilteredList {
    /*
     * Filtered List
     * ..............
     * We could also give the filter functionality to the user.
     * Add a toggle button to the toolbar that's going to let the user hide to-do items that are not due today
     *
     * Add ToggleButton to the toolbar in the fxml file
     *  - when it's selected , show all to-dos due today
     *  - when it's not selected , show all to-dos
     *
     * In a real-world application, the button would probably display an icon , but we'll just keep it simple here
     *
     * Next,
     * We need an event handler that adds/removes the filtering when the toggle btn is pressed
     *  - so we need a fx:id , and an OnAction property as well to our toggle button
     *  - add instances for the toggleButton in the controller
     *  - add the event handler handleFilterButton()
     *
     * handleFilterButton()
     *  - check if filter toggle button is selected and add the filter to-dos due that day
     *  - otherwise, show all to-dos
     *
     * Also, when the application starts, we want to show all to-do(s) by default
     *
     * JavaFX has a FilteredList, just as we used a SortedList to sort the items
     * We can actually do both, filtering and sorting which we'll do here by wrapping our filtered list with a sorted list
     *
     * This means the list can be filtered and whatever remains in the list after filtering is then sorted
     *
     * When we create a filtered list, we have to provide our filtering criteria, which tells the list which to-do items
     *  we want to keep and which ones we want to discard
     * - We're going to provide the criteria as a Predicate which is a functional interface in the java.util.function package
     * - We have to implement the test() to test each and every item for our filter
     *
     * Implementations
     * ...............
     * Since we want to use this FilteredList in our event handler, we need to declare it as an instance variable
     *  private FilteredList<TodoItem> filteredList;
     *
     * Next,
     * Add it in the initialize() just before the sorted List
     *  e.g. filteredList = new FilteredList<>();
     * Then pass 2 args
     *  - 1st - pass the items that we want to filter, which is the ObservableArrayList in this case
     *  - 2nd - pass the function predicate by typing new Predicate() and pressing enter
     *
     * So,
     * - The filteredList will call the test() for every item in the list, that's passed to it
     *      - The test() returns
     *          - true, if the item passes the filter
     *          - false, if the fails the filter and won't be kept
     * - So when we want to show all the to-do items, the test() simply needs to return true because we want every item
     *  to pass the test
     * - We don't have to check for item's deadline in that case to determine whether it passes or fails
     * - We only want to show to-do items with a deadline of today, we'll return true for items that have a deadline of today
     *   and false for items that don't
     *
     * Now,
     * These days, predicates are written as lambda expressions, but we'll spell the test() manually
     *
     * Since we want to show all the items, when the application starts, we need to change the false to true, which will give
     *  us the list of everything by default
     * And now, instead of passing the list of to-do items to the SortedList constructor, we'll pass the filtered list instead
     *  to the SortedList as mentioned earlier
     *
     * Next,
     * Code our functionality in the event handler
     * - If the button is selected
     *  - call setPredicate() and pass a new instance of the Predicate interface
     *  - return to-dos whose deadline equals current date
     *      - will return true, if the to-do item has a deadline marked as the current date
     *      - otherwise, false
     *      - If true, it will be included, if false , then it will be excluded
     *
     * - Otherwise,
     *  - call setPredicate() and pass new Predicate , which is going to return true for all test cases, displaying all
     *    to-dos as a result
     *
     * This works as expected but we can enhance our code since we're creating a new predicate each time we actually select
     *  that option/ or button is pressed
     *
     * So at te top of the Controller, we'll add 2 instances of our 2 Predicates
     *  e.g.
     *      private Predicate<TodoItem> wantAllItems;
     *      private Predicate<TodoItem> wantTodaysItems;
     *
     * Next,
     * Set our code in the initialize()
     *  - set wantAllItems to the Predicate and returns true by default
     *      - pass wantAllItems as the 2nd parameter to the FilteredList
     *
     *  - set wantTodaysItems to the Predicate and returns true if a to-do matches current date
     *
     * Next,
     * Do the same thing thin in the event handler
     *  - Instead of having an anonymous class getting created each time:
     *      - Pass wantTodaysItems predicate if the ToggleButton is selected
     *      - Otherwise, pass wantAllItems Predicate
     *
     * This works as we'd expect, but when the user presses the toggle button, and the list is filtered, the selection state
     *  of the listview is reset so that nothing is selected, but the details area and the deadline are displaying the
     *  values for the item that was last selected
     * Let's fix this by selecting an item after we've finished filtering the data
     *  - i.e. when the user toggles from the filtered list to the full list, it would make sense to select whatever that
     *         was selected when the user pressed the toggle button
     *  - And we know the item will still be in the list
     *
     * But when the user toggles from the full list to the filtered list, we don't know whether the item that was selected
     *  will still be in the list,
     *  - We have to check and if it isn't in the list, we'll select the first item in filtered list when it isn't empty
     *
     * Let's fix that
     * In the else {}
     *  - get the selected item
     *  - set it to select in case of toggling all items after the predicate
     *
     * In the if()
     *  - Check if the filteredList is empty and if so
     *      - clear textarea and set deadline text to an empty string
     *  - Check if the filtered list contains the selected item and if so,
     *      - select it
     *  - Otherwise, if it doesn't contain the selected item
     *      - select first item
     *
     *
     *
     *
     *
     *
     *
     */
}
