package JavaFx.part8_sample_todoList;

public class p8_ContextMenu {
    /*
     * Context Menu
     * ............
     * Add delete functionality
     *
     * We can do this in 2 ways:
     *  - Add a delete item to file menu - though not a very ideal place to add it there
     *      - we could assume the user wants to delete the selected item & probably what the user will expect
     *      - we shd make it crystal clear to the user that we're going to delete that selected item
     *  - Add a context menu pop up when the use clicks on an item in the list
     *      - it's called a context menu because the menu that appears depends on the item that's been right-clicked on
     *
     * Implementations
     * ...............
     * - Add ContextMenu instance variable
     * - Initialize ContextMenu in the initialize()
     *
     * Next
     * - create the delete menu item which will be part of the context menu & associate an event handler
     *      MenuItem deleteMenuItem = new MenuItem("Delete");
     * - Add event handler by calling setOnAction() on deleteMenuItem instance
     * - setOnAction() takes an anonymous EventHandler class with a handle() that we need to override
     *
     * handle()
     *  - get selected to-do in the list
     *  - call deleteItem() and pass the selected item
     * That's all we need to add in our context menu
     *
     * Next,
     * Add the delete item menu itself to the context menu
     *  - call getItems on our context menu and then add the delete item using the addAll()
     *      listContextMenu.getItems().addAll(deleteMenuItem);
     *
     * Next,
     * Implement deleteItem()
     *  - Add alert
     *   - JavaFX provides us with canned dialogs for common use cases , & we'll use a confirmation dialog to confirm that the user really wants to go
     *     ahead and delete an item
     *   - Alert class has been available since java8u40, which is what we'll use here to create 1 of those JavaFX pre-created dialog
     *
     * When we create an instance of the Alert, we have to pass in the type of dialog that we want to create
     *  - We have 5 choices available for us
     *      - Confirmation
     *      - Error
     *      - information
     *      - warning
     *      - none (no properties get initialized, need to set manually)
     * Based on the type we chose, the alert constructor will initialize some of those instances properties for us based on the type of the dialog
     *  we chose
     * In this case, we want a confirmation dialog that confirms deletion
     *
     * Next,
     * Set properties that determine what the dialog will display on the screen,
     *  e.g. title - will set this to "Delete to-do item"
     *       header - will provide info about the selected item, so that users know what item they're deleting
     *       header text - set text to "Delete item" followed by the selected items to-do short desc
     *       content text - Will ask the user if they're want to proceed or something along those lines
     *
     * In terms of buttons, we don't have to set which buttons we want here because the alert class will provide those buttons automatically based on
     *  the type that we passed with the alert type
     * Next,
     * Is to show the dialog
     *  - call showAndWait() on alert and store that to result variable of type Optional<ButtonType>
     *
     * Check which buttons they pressed (OK/Cancel)
     * - If OK
     *      - call deleteTodoItem() on the Singleton class the item
     *      - add deleteTodoItem(TodoItem item)
     *          - call remove() on todoItems and pass the to-do
     * - Otherwise
     *      - don't do anything
     *
     * Next,
     * We've created the context menu and associated it with a handler's code
     * But we haven't associated the menu with the listview, but since this is a context menu,  and it's going to appear when the user right clicks
     *  on a particular item/cell in this case, it makes sense to associate the menu in our cell factory
     * In our cell factory class, we'll add the code that associates the context menu after we've created the cell
     * We only want non-empty cells to have context menu in this case
     *
     * Next,
     * Let's associate the context menu to our cell factory
     * - call emptyProperty().addListener() on cell instance
     *      - If cell is empty , call cell.setContextMenu() and pass null
     *      - Otherwise, call cell.setContextMenu() and pass listContextMenu that we defined above
     *
     * So,
     * - That's basically it, and is just an alternative of adding a listener () by using an anonymous ()
     *
     * We've now added a listener for the cell's empty property & when the cell becomes non-empty, the context menu is going to be associated with it
     * When it is empty, we'll remove the association by setting the cell's context menu to null
     *
     * The to-do item is deleted and the list gets refreshed
     *  - Notice we didn't write any code to remove the item from the listview, and that's because we're using data binding , the item was removed from
     *    the collection and the listview updated itself accordingly
     *
     * Another way we could let the user delete items is to listen for the delete key
     *  - when the user selects an item and presses the delete key, pop the confirmation dialog and proceed by either clicking OK/Cancel
     *
     *
     *
     */
}












