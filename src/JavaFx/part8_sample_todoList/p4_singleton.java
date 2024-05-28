package JavaFx.part8_sample_todoList;

public class p4_singleton {
    /*
     * Singleton
     * .........
     * - We will add this class so that our Main class and the controller class can access
     * - In a nutshell, we're going to use a singleton when we want there to be only 1 instance of a class created over
     *  the entire run of the application
     * - The singleton class creates the one instance of itself, and it's got a private constructor to ensure that no other
     *  class can create an instance
     * - There is more to it than that but the important thing for us is that the singleton usually contains a static method
     *  that allows any class to get the single instance and to call its methods
     * - This means our main class and our controller will have an easy way to access our data
     * - We're going to add the singleton class to our data-model package
     *
     * Create TodoData class
     *  - Create a new instance of this class and call it instance
     *  - Create a file name where to-do items are going to be saved
     *  - Need to store our items in a List<TodoItem>
     *  - Date time formatter so that we can manipulate the date
     *  - Need a method to return the only instance of our to-do class
     *      - Will call it getInstance() which is traditional in singleton classes to do that - use a getInstance() but we
     *        can ultimately call that whatever we like
     *          - we return instance
     *  - Add a private constructor - prevents anyone from instantiating a new version of this class as an object
     *      - Initialize the formatter with the pattern that we'll use
     *  - Add a () to return out to-do items
     *
     * Next,
     *  - Create a () that will be used to load our to-do items from the file
     *      - use old try-finally syntax
     *
     * loadTodoItems() - reads the todos items from the file
     *  - Initialize to-do items to an observable array list
     *  - Create a path
     *  - Use BufferedReader to read from the file - pass path
     *  - Create a string that's going to contain data for each line and name it input
     *      - read and store the content into the input variable
     *      - split the lines with tab and store in itemPieces array
     *      - access individual details from itemPieces[] through indexes
     *      - convert a date into a format tha we can read it
     *          - use LocalDate.parse()
     *              - pass the dateString and the formatter
     *      - Next, create a to-do Item
     *      - Next add to-do Item above to our tODOItems List
     *
     * storeTodoItems() - save the data
     *  - Create a path
     *  - Use bufferedWriter
     *  - Loop through the to-do Items and save them one entry at a time to the text file
     *      - Use an iterator to iterate through each entry
     *      - call write() and pass description,detail and deadline separated by tabs
     *      - call bw.newLine() - adds a new line to the text file
     *
     * setTodoItems() - setter
     *  - is temporary , we only need it when we run the application and then close it to save the hard-coded items
     *
     * Since this is a singleton, whenever any part of the application wants to access the data, it has to call
     *  TodoData.getInstance.getTodoItems() since we've set our constructor to be a private one, thus you can't
     *  instantiate a new obj from this class by any other means
     *
     *
     *
     * Load and Save to-do items from/to disk
     * ......................................
     * FXCollections.observableArrayList
     *  - We're using setAll() in our controller which then needs to have an array in a format with that that uses
     *     Observable ArrayList
     *
     * Next,
     *  - Add/Override stop() to our Main Java class
     *      - This () runs when the user exits the application usually by closing the main window
     *  stop()
     *      - We want to store our items, and we need to run this once and store the items that are currently hardcoded
     *         in our controller
     *      - We will write our data out so that it is written to the text file, and then we can ultimately start accessing
     *        in the future
     *      - Call storeTodoItems() from the Singleton class and wrap that in a try-=catch block
     *          - Handle any IOException if there is one
     *      - This will save the data to a text file when we close down the program fro the first time
     *
     * initialize() - Controller class
     *      - We'll call setTodoItems() temporarily as we're going to create this file every time we close the application
     *          and is going to save all the changes to the file
     *      - We'll then remove that line of code
     *      - We'll add below after adding the last item - item 5
     *          - TodoData.getInstance().setTodoItems(todoItems);
     * Generating the text file
     *  - Run the program and then exist - a file is generated containing our to-do items in our root folder/project
     *
     * Next,
     *  - load the items from the to-do list file each time the application starts rather than hard-coding them in the
     *     controller's initialize ()
     *
     * init()
     *  - we'll override init() in our Main java class and call loadTodoItems() from the Singleton class
     *  - Wrap in a Try-catch and print out the error if any
     *  - This () will be called automatically by JavaFX every time we start the application
     *
     * Next,
     *  - Comment out on the code that's storing hard coded items in our controller class and remove temporary line as well
     *      that we used to write the content to our singleton class
     *  - We'll then call getToDoItems from singleton and pass to setAll() as we no longer have to-do items
     *
     * Next
     *  - Comment setTodoItems() in our Singleton class which we used temporary
     *      - we only needed it so that the controller could store the hardcoded to-do items
     *
     * Next,
     *  - Running this and should work because we're getting data from the file - Yeah and it works
     *
     * Next,
     *  - We need to provide a way for the user to add a new to-do item to the list
     *  - We'll add menu to our application in the top position of the BorderPane - in the main-window.fxml
     *      - Add top tags
     *          - Add MenuBar
     *              - Add Menu tag and a text property which we'll call "File"
     *                  - Add items that we want to be in that menu within the <items> tags
     *                      - Add MenuItem tag and the text for the menu - "New"
     *                          - Add SeparatorMenuItem tag
     *                      - Add 2nd MenuItem tag and the text for the menu - "Exit"
     *
     * Next,
     *  - We'll type event handlers for the new menu item, so that when the user presses "New", we want to show a dialog
     *    that they can use to create a new item
     *
     *
     *
     *
     *
     *
     */
}
