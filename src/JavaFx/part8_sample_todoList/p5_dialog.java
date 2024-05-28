package JavaFx.part8_sample_todoList;

public class p5_dialog {
    /*
     * Add DialogPane
     * ..............
     * We need to create the UI, that the user will see when they press the menu item
     * We'll create a Dialog that will accept the shortDescription, details and the deadline for the new item
     *  - When the user presses the dialog OK button, we'll create a new to-do item at that point and add it to the list
     *    in our data model & update the data in the list view on the screen
     *  - We'll also select the new item so that the user can see the details and deadline they just entered on the screen
     *
     * JavaFX 8-0-40 introduced dialogue and DialoguePane classes that makes creating Dialogs a lot easier
     * We'll create a custom dialogue for the add new to-do item
     * However, we can't create this in the same fxml file that we've used for our application .i.e main-window.fxml
     *  - This is because each fxml file can only have 1 root node, meaning 1 scene graph
     *  - Our dialog will use a different scene graph for the main window & we'll have to create a new fxml file and then
     *     associate a separate controller with it
     *
     * Next,
     *  - Create new fxml file - "todoItemDialog.fxml"
     *  - Point it to DialogController
     *  - create DialogController
     *
     * Every instance of the Dialog wraps a DialogPane instance - or rather the DialogPane is always the top-level layout
     *  of the dialog
     *
     * How does the DialogPane works ?
     * ...............................
     * - It's a layout manager that's used for dialog
     * - Allows us to set 4 properties
     *    - header
     *    - graphics
     *    - content
     *    - button
     *  - Each property above expects a control
     *  - Because we often want to display text for the header and the content, the DialogPane has 2 convenient props
     *    - headerText
     *    - contentText
     *  - When we want header or content to display a string using the convenience properties saves us from having to
     *    create a label property for the text
     *  - If both the non-text property and the text property is set, then the non-text property will take precedence
     *  - For example:
     *      - If we set the header property to a control and we also set the header text property, the header value
     *        will be used and the header text value ignored
     *
     *
     * Building our Dialog - fxml
     * ..........................
     * - Add headerText tag
     *      - Add some content there
     *      - headerText is the text property and it's a string property and that consequently its contents consist of
     *        a string
     * - Set the content area for the dialog
     *      - is where the user will typically input to-do details
     *          - we need 3 Label controls , 2 textfields and a datepicker control
     *      - we'll use a GridPane layout to help layout our controls nicely
     *
     */
}
