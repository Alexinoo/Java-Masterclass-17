package JavaFx.part10_transform_nodes_and_chosers;

public class Main {
    /*
     * Transforming Nodes and Choosers
     * ...............................
     *
     *
     * Transforming Nodes
     * ..................
     *
     * JavaFX allows us to transform nodes in the scene graph
     * This means we can do things like rotate them, zoom in on them, animate them and apply other effects
     *
     * Implementations
     * ...............
     * - Add a Label with a text "JavaFX Effects"
     * - Assign an fx:id
     * - Create StyleController
     *      - Add an instance variable for Label control
     *      - Add initialize()(
     * - scale the size of the label during start up
     *      - call setScaleX() and pass a double value
     *      - call setScaleY() and pass a double value
     *
     * Next,
     * Remove scaling during start up
     *  - comment out on the code in the initialize()
     * Zoom in when the cursor enters it, and zoom out when the cursor leaves the label
     *  - Add handleMouseEnter() handler for onMouseEnter action
     *      - scale to 2.0
     *  - Add handleMouseExit() handler for onMouseExit action
     *      - scale back to regular size - 1.0
     * Attach handles with the Label control
     *      - onMouseEntered="#handleMouseEnter" onMouseExited="#handleMouseExit"
     *
     * JavaFX also allows us to rotate controls
     *  - Let's rotate the label 90 degrees
     *  - Add rotate property to Label control and assign 90
     *
     * Next
     * Add a drop shadow to one of our buttons - Button Four
     *  - We'll do this with code, since fxml support for effects is sketchy
     *  - We can define a couple of the available effects in the fxml, using CSS, though not all of them
     *  - we'll the setEffect() to set a dropShadow and we'll do that in the controller, initialize()
     * Therefore, we need an instance variable for the button in the controller
     *
     *
     * Choosers
     * ........
     * JavaFX has a File chooser class that gives us some type of open and save functionality
     * The File Chooser class provides us with this functionality and we don't have to re-invent the wheel and write these open and save dialogs
     * Can also be customized
     * However, it doesn't descend from the node class, so it can't be added to scene graph, i.e. we can't add it to a layout
     *
     * Implementations
     * ...............
     * Change Button One text to "Open.." and add onClick
     * Then on the controller
     *  - Create a FIle chooser instance
     *  - call showOpenDialog() - which show or shows the file chooser in open mode
     * We have to pass a parent window for the file chooser
     *  - we need to assign a fx:id to our GridPane
     *      - add fx:id="gridPane"
     *      - add an instance of the GridPane
     * Next
     *  - call showOpenDialog() on FileChooser instance and pass null first
     *      - The problem is that if we pass null, we can still interact with the main window
     *      - In fact, we can click on it again and even open a 2nd dialog box
     *      - And even close the main window and still the window remains open - which is not something that we'd want
     *  - We want the file chooser to be modal, so that the user has to navigate to a file or cancel the dialog before they can interact with the main
     *    window again
     *      - This is the main reason we're passing the main window as the parent, to get the behavior we want here
     *  - Change to
     *      - gridPane.getScene().getWindow()
     *  - And now, we can't click anywhere as we saw earlier, and it's completely modal,
     *  - We can either select the file or click Cancel to go back to the main window
     *
     * By default, the file chooser initially chooses the default file system view for that OS that it's running on
     * Instead, we want to show the last directory the user saved to or a directory that's more specified perhaps in the application settings
     * To have the file chooser initially open to a specific directory, we can use setInitialDirectory()
     *
     * Next,
     * Let's run again and open the file chooser, but this time select a directory instead of a file
     *  - Create a DirectoryChooser instance
     *  - call showDialog() and pass the parent window
     *
     * Next
     * Let's store the result in a file and print the path of the selected file
     * We get the full path of the directory that we have chosen
     *
     * Another thing,
     *  - the only thing we can select are only folders, we can see the other filed but they're not selectable & depending on your OS, you might
     *    not even see the files at all
     *  - though on Mac, we can see the other files, but they're not selectable
     *
     * More on Choosers and Web Pages
     * ..............................
     * When the user wants to save , we call the showSaveDialog() instead of the showOpenDialog()
     * We'll work with FileChooser instead
     *  - comment on Directory chooser
     * - call showSaveDialog()
     * - print the path if the the file was chosen, otherwise, print no file chosen
     *
     * The difference between showSaveDialog() & showOpenDialog() is that with showSaveDialog(), the modal shows up with
     *  save button whereas with showOpenDialog(), the dialog has an open btn to help you navigate and select files to
     *  work with
     *
     * Next,
     * We can also set the file chooser extension filters , so that the file can be saved with a certain file extension
     *  that the user needs to define
     * - We get the filters by calling getExtensionFilters() and then we'll add them to the existing list
     *
     * Next
     *  - set Title for the File chooser dialog with setTitle(String str) on FIleChooser instance
     *  - call getExtensionFilters().addAll() and pass (new File.Chooser.ExtensionFilter("Text","*.txt")
     *  - separate with comma if you want to add more
     *      chooser.getExtensionFilters().addAll(
     *          new FileChooser.ExtensionFilter("Text","*.txt"),
     *          new FileChooser.ExtensionFilter("PDF","*.pdf")
     *       );
     * We're are able to specify the file type that we want to save with the file extensions that we have specified
     * above
     * When we construct an instance of an extension filter, we pass the description of the files that have the file
     *  extension as the 1st parameter and the file extension as the 2nd parameter
     *
     * Use Cases of File Choosers
     * ..........................
     * - File extension filters are useful as they limit the files the user sees, in case of an open dialog
     * - When filters are set, the file chooser will only show files that have an extension that matches the filter
     *
     * Catch-all filter,
     * - we can add all catch file extensions - populates All Files option in our File type drop down
     *      - this means we have more flexibility to select any type of files that we need
     *
     * Images Filter
     *  - we're not limited to 1 extension
     *  - we can add a comma separated extensions for image files such as jpg,png, gif etc
     *
     * Select Multiple Files
     *  - we open the dialog with showOpenMultipleDialog() and this returns a list of files
     *  - loop through each if we want to print them
     *
     *
     * HyperLink Control
     * .................
     * It descends from the Labelled class , displays text but is used to display a link
     * - Add 1 to our fxml
     * - changes to a link once we hover it, also underlined
     * - Hyperlink control has 3 states
     *      - unvisited
     *      - clicked
     *      - visited
     * - The appearance of each state is different
     * If we want the appearance of a visited hyperlink to revert back to that of an unvisited hyperlink, we can call the
     *  visited() and pass the false sa the value
     * To have something happen when we click the link, we still would have tro write an event handler
     *  - we don't always have to go to a web page when a hyperlink control is clicked
     *  - we could open a dialog or change something on the UI
     *  - or display contextual help within the application
     * Add onAction to the link - handleLinkClick
     *
     * Next,
     * On the handleLinkClick() event handler
     * Let's show the web page located in www.javafx.com/www.google.com
     * We can do this in 2 ways
     *  - Open through the system default browser
     *      - Add a try block
     *          - Desktop.getDesktop().browse(new URI("https://www.google.com"));
     *      - Catch both IOException and URISyntaxException (s)
     *
     *  - Add a web view controller to our UI and display the contents of the web page within the WebView
     *      - add WebView control
     *
     *
     */
}



















