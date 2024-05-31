package JavaFx.part8_sample_todoList;

public class p9_KeyEvents_Toolbars {
    /*
     * KeyEvents and Toolbars
     * ......................
     * Implement a way for the user to delete items by pressing the delete key and the confirmation dialog pops up for the user to proceed
     * - listen for key press on delete key when the listview has got focus
     * - typically, a controller is given focus when the user clicks on it or clicks within a text field, it will be given focus
     *
     * When the user presses a key, a key event is raised and key event handlers associated with the text field will then be called
     * The focus determines which controls, events handlers are called when an event is raised
     * To associate the event handler with the listview, we need to set it's onKeyPressed property in the fxml and then call our event handler once that's
     *  done
     *
     * Implementations
     * ...............
     * Add onKeyPressed="#handleKeyPressed" on ListView tags
     *
     * handleKeyPressed(KeyEvent event)
     *  - get the selected item
     *  - If the selected item is not null,
     *      - check if the user has pressed the delete key
     *      - call deleteItem() and pass the selected item
     *
     * What we've learnt so far..
     * - Lay out a main window
     * - Populate a list view control
     * - Handle selection and key events
     * - Create menu(s)
     * - Create Cell factory
     * - Create Context menu(s)
     * - Data binding - means we don't have to explicitly update the UI when the data backing and control changes
     *
     * Also, everything we've learnt in relation to the listview control can also be applied to controls like the table view, tree view and the tree
     *  table view and these all display data collections
     *
     * Adding a Toolbar to our application
     * ...................................
     * Will only have the new item on it
     * We only want both the menu and the toolbar to display at the top of our application, we're going to move our menu into a VBox
     * Then add a toolbar below menubar
     *  - lay out our toolbar items in a HBox
     *      - Add the button to add a new item
     *          - when clicked, call "showNewItemDialog" handler that displays add new to-do dialog
     *
     * That's cool and is working, but we'd usually display icons in the toolbar
     *  - let's change our toolbar to display an icon rather than the text
     *  - will use the same icon jar file we used in the JavaFX controls lecture
     *      - Add jar file the Classpath
     *  - add graphic icons
     *      - add ImageView
     *          - add Image
     *              - Add url and point to your desired icon
     *  - remove "New..." text
     *
     * For a nice User experience, now that we have removed the text "New.." from the toolbar icon, It would be nicer to have a tooltip , so that it's
     * easy for users to know what that icon is doing if they hover it
     * Let's add that next
     *
     * The Button itself has a tooltip property
     *  - add tooltip property to the Button control
     *
     * However, we get an error when we run
     * It turns out that the button's tooltip property expects to be assigned an instance of a tooltip class and not a string
     * When we run, the JavaFX runtime loads the fxml and it's going to try and convert the value with assigned which is a string into an instance of
     *  the tooltip class
     * It obviously can't do that because they are separate classes & that's consequently the reason why it's throwing an exception
     *
     * Interestingly, IntelliJ didn't complain about the fxml , since it knows that a tooltip is valid property from inspecting the Button class, but
     *  for some reason, it didn't understand the tooltip property expects a class and not a string
     *
     * Gotcha's
     * ........
     * You might have noticed some fxml elements begin with a capital letter and others don't
     * We generally start with a capital letter when we're defining a class,
     * We start with a lower case when we're referring to a property within a class
     *  e.g. Button class and text property
     *
     * And graphic is also a property of button that we've used to surround our ImageView in
     *  - It expects a node instance for its value & we can't directly assign it
     *
     * fxml seems to be inconsistent about this, sometimes when assigning a property that takes a class instance, we have to write the definition
     *  within the properties tags and sometimes we don't as in this case
     *
     * The image Class has a url property which we've set to the path for the icon that we want to see there
     *
     * This mean we have to assign the tooltip for our button in the same way we assign its graphic
     *  - so remove the tooltip and add it within tags
     *      - then wrap Tooltip control as shown below
     *           <tooltip>
                     <Tooltip text="Add a new to-do.."/>
                 </tooltip>
     *
     *
     *
     */
}










