package JavaFx.part8_sample_todoList;

public class p6_DataBinding_and_Observable {
    /*
     * DataBinding and Observable
     * ..........................
     * - When we add to-do items, we're explicitly updating the ListView by repopulating it again
     * - This is not only inefficient, but in a more complex application, that's got many ways for the user to change
     *   what control is displaying, it would be really easy for the controller to get out of sync with the data
     * - Instead, of explicitly trying to manage what the ListView is displaying, we're going to use DataBinding
     *
     * Data binding
     * .............
     * - When we bind a control to the data, the control is going to notice when the data changes without us coding
     *   for this
     * - The control will seemingly update itself because JavaFX developers have written all the code to handle these
     *    changes
     * - It works similarly to how event handling works
     * - When the controller is populated with an Observable Collection, it's going to react to events raised by that
     *   collection by running a handler
     * - So when items are added or deleted from that collection, the control will then change what it's displaying on
     *    screen
     *
     * Next,
     * Let's update our code to use data binding
     * We'll start with our TodoData class
     *  - update "private List<TodoItem> todoItems" to an observable list and this is going to help this data binding to
     *    work
     *  - next update the getters now to return an ObservableList instead of a List
     *
     * Next,
     * In the main-windows controller, we'll populate the ListView by calling the setItems() rather than calling
     *  todoListView.getItems().setAll(TodoData.getInstance().getTodoItems());
     * Now call
     *  todoListView.setItems(TodoData.getInstance().getTodoItems())
     *
     * These are the only steps we need to do to bind the ListView to the ObservableList in the TodoData class
     * We can remove the code that explicitly populates the listview when the user adds a new to-do item
     * i.e. comment out below
     *  todoListView.getItems().setAll(TodoData.getInstance().getTodoItems());
     * since it's now being handled automatically by data binding
     *
     * In other words, we have taken out the code that was explicitly re-populating the ListView menu manually
     * And this all works as before
     *
     * Next,
     * The reason why we're using FXCollections.observableArrayList while we're loading to-do items from the lodTodoItems()
     * rather than say using a general list from the java.util.collections package
     *  - We're doing it for performance reasons
     *      - observable lists will raise events & because ()s within the list classes may call each other when a list is
     *        changed , it's then possible for more than 1 event to be raised for a single change
     *  - For example:
     *      - if ()s a, b and c all raise events and when an item is added then method a() calls b(), which then calls c()
     *        then 3 events will be raised whenever an item 's added
     * And the UI operations can be expensive and that's because the UI control has to paint the screen or part of itself
     *  on the actual screen on the display
     * So we don't want to control like a ListView to run it's handler multiple times when an item is added or deleted
     * Ideally, what we want is to only run with handler once
     *
     * The FXCollections package contains a copy of all the classes & static ()s in the java.util.collections package but
     *  the code has been optimized to reduce the no of events or notifications raised when the collections changed
     *
     * All ()s are optimized in a way that only yield limited no of notifications
     *
     * On the other hand, java.uti.collections might call modification ()s or an observable list multiple times resulting
     *  in a number of notifications
     *
     * The bottom line is that we really always want to use the FXCollections versions & not the version in java.util.collections
     * And that's why we've used the observableArrayList
     *
     * Next,
     * Let's add a new to-do item with some lengthy details and make sure we're able to access all the details
     *  - generate 3000 words with Lorem Ipsum
     *  - check for 1 paragraph
     * Paste in the details area
     *  - span across and we have to scroll horizontal bar
     *
     * But the problem is that the text doesn't wrap and what we need to do is to add wrapText property and set it to true
     *  to the text area
     * Need to update the text area for the main-window and add wrapText property & set it to true
     *
     * Next,
     * We'll highlight items in the list that have a deadline of set to delay, so that's the user doesn't have to click on each item to figure out
     *  which ones they need to prioritize
     *
     */
}
