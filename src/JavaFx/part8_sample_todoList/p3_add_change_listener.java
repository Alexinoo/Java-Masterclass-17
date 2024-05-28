package JavaFx.part8_sample_todoList;

public class p3_add_change_listener {
    /* Add Change Listener
     * ...................
     * Select the first Item in the to-do list whenever the application runs
     *
     * We do that in our controller initialize()
     *  - Call getSelectionModel().selectFirst() on the todoListView variable
     *  - Interestingly enough we can see that the first item is selected in the top-left corner, but the details don't
     *    appear on the right-side
     *
     * So why isn't this working ?
     * ..........................
     * Well, if we go back for the ListView on the fxml, we're listening for the onMouseClicked , but in this case, we
     *  did not select the first item by clicking on it but rather selected it programmatically using code
     *
     * Now since we want to select the first item every time we run, we could just set the details text area directly in
     *  the controller's initialize() by calling something along those lines of itemDetailsTextarea.setText() and pass in
     *  the details for the first to do item
     * But there will be other times when we'd to change the selection programmatically
     * For example:
     *  - When a user adds a new to-do item to the list, we want to select it so that its first details are displayed
     *
     * So a better solution would be to listen for selection changes regardless of how they take place, programmatically
     * or through user input
     * So instead of assigning ListView control to the onMouseClick property, we'll run our event handler whenever the selected
     *  item property of the selection model changes
     * So we'll have to set our event handler in code rather than the fxml & we'll do this in the initialize() before we
     *  select the first item
     *  - We do this by calling getSelectionModel().selectedItemProperty().addListener() on toDoListView
     *  - then pass new ChangeListener() which is an anonymous class
     *      - that overwrites changed()
     *  - check as long as newValue is not null
     *      - update text area
     *      - update deadline
     *  - Next ,
     *      - remove onMouseClicked property from the ListView in the fxml since we have written a generic event handler
     *         that handles anytime the value changes
     *  - It's going to pick it up either way, either by clicking on a mouse or programmatically
     *
     *
     */
}
