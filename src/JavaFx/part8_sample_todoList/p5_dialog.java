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
     *      - headerText is the text property & it's a string property and that consequently its contents consist of
     *        a string
     * - Set the content area for the dialog
     *      - is where the user will typically input to-do details
     *          - we need 3 Label controls , 2 textfields and a datepicker control
     *      - we'll use a GridPane layout to help layout our controls nicely
     *          - grid scanner will have 3 rows and 2 cols with the 1st column rep labels , 2nd rep text areas
     *  - Add content element
     *      - Add GridPane and set hgap and vgap props
     *          - Add the rest of the fields
     *  - We'll add OK and Cancel buttons through code
     *
     * But where do we add the code to show the add the buttons and show teh dialog ?
     * ..............................................................................
     * You might think that this will be done in the DialogController but this would be incorrect
     * We show the dialog, when the user presses "File" on the main windows menu and then "New"
     * We have to handle this in the main-windows controller
     *  - We need to add an event handler to the Main controller class
     *
     * Add showNewItemDialog()
     *  - Create an instance of the Dialog class
     *  - We want this Dialog to be modal, while it's visible, the users won't be able to interact with any other part of the
     *    app UI
     *  - Will have to dismiss the modal by clicking Ok/Cancel buttons or close btn
     *  - By default, when we create a dialog using this dialog class, the dialog will be modal
     *  - We need to assign an fx:id into the BorderPane in the main-window.fxml so that we can access it
     *
     * It's actually a good practice to set the dialog's owner or parent , which is using the window that dialog was open
     *  from
     * We could set the owner to null and JavaFX runtime would still block input to other parts of the application
     *  - Let's set the owner so that we can see how this is done
     *  - The owner has to be of type window , and that's the reason we set the fx:id
     *  - So to get the parent window instance, we need to assign the id to the BorderPane and then refer it in the
     *     controller
     *      - Add an instance variable to the BorderPane - mainBorderPane
     *      - We will then ask the BorderPane for the reference to its parent, by calling getScene() which will return the
     *        scene from the BorderPane & then call scene.getWindow() and set the result as the owner of the dialog
     *
     * You'll probably recall that we load our main-window.fxml in the Main.java
     *  - We've created an instance of the dialog class but that doesn't load the UI we defined in the fxml
     *  - We've to load the todoItemDialog.fxml from here , same way as we did it in our main class
     *  - Then we'll set the dialog DialogPane to the todoItemDialog.fxml
     *
     * Add try-block
     *  - The load() can throw an IOException
     *      - load the todoItemDialog.fxml using FXMLLoader class
     *      - call getDialogPane().setContent() on the dialog and pass the root to setContent()
     *
     * Next,
     * Add Buttons to the Dialog or DialogPane
     *  - We want to have an OK/Cancel buttons and JavaFx provides us with a way to add those buttons and we can also create
     *     custom btns as well
     *
     * To add OK/Cancel buttons we need to create instances of the ButtonType class & the ButtonType constructor accepts
     *  the text we want on the button  and the button type
     *
     * At this point we're ready to show our dialog,
     *  - There are 2 ()s we could use to show it
     *    - show()
     *    - showAndWait()
     *  - The diff btwn them is that showAndWait() will bring back a blocking dialog and show() will bring a
     *     non-blocking dialog
     *  - The show() will return immediately after showing the dialog - won't wait for the user to press Ok/Cancel btns
     *  - So we'd have to write the code that occasionally poll the dialog for a result ( which is not what we want)
     *  - We want to show the dialog and then we want our event handler to be suspended, while the user interacts with
     *     the dialog
     *  - WHen the user is finished, our event handler will receive the result of the dialog & then resume executing
     *
     * Next,
     * Print which button was pressed i.e. OK/Cancel and check if our code is working
     *  - call showAndWait() on the dialog and store this in result variable of type Optional<ButtonType>
            - call isPresent() on result && get() still on result variable and check if it's OK btn that was pressed
                - print which btn was pressed
     *
     * Next,
     * Associate the menu item with the event handler
     *
     *
     * Next,
     * Write the code that will gather the info from the dialog controls when the user clicks the Ok btn
     * The fxml file that defines the DialogPane and all it's controls is associated with another controller
     * So we can't create instance variables for our dialog controls in our main windows controller
     * We need a way to get into the dialog controller so that when the user presses the Ok btn, we can then ask the
     *  dialog controller to process the data
     * So to get to the controller, we have to change the way we load the fxml
     * Instead of using the load(), we'll do this in several steps
     *  - First create an instance of the fxml loader class above try-block
     *      FXMLLoader fxmlLoader = new FXMLLoader()
     *  - Then tell it the location of the fxml we want to actually load
     *      - call setLocation() on fxmlLoader instance and pass: getClass().getResource("todoItemDialog.fxml")
     *  - then go ahead and load the fxml
     *      - we don't need Parent root anymore - comment it out
     *      - pass fxmlLoader.load() to setContent()
     *
     * Next,
     * Go to todoItemDialog.fxml and add fx:id to the text fields
     * Add instance variables in the DialogController
     *
     * Add processResults()
     *  - To handle processing results / gather user input ,create a new to-do
     *  - Add to our Tododata instance and call addTodoItem() on TodoData singleton class
     *
     * TodoData Singleton class
     * .........................
     * Add addTodoItem()
     * - call add to List<TodoItem> todoItems variable
     *
     * Next,
     * We'll have to call processResults event handler in the main-windows controller
     * We add it at the point where the user presses the ok btn
     *     DialogController controller = fxmlLoader.getController();
     *     controller.processResults();
     *
     *
     *
     */
}
