package JavaFx.part8_sample_todoList;

public class p1_setup {

    /* Setup
     * ......
     *
     * UI
     * Use BorderPane
     *  - Add left tag
     *      - Use ListView control
     *  - Add center tag
     *      - Add a textarea
     *  - Update center tag
     *      - Add a VBox ( change background color to white)
     *          - wrap the textarea we created above
     *          - add VBOX.v-grow property - to adjust with the content and set it to ALWAYS
     *              - displays basically with their preferred heights
     *              - tells VBox to always give as much room as possible to the text area
     *              - the VBox will size all its other children to their preferred heights and give the remaining space
     *                 to the text area
     *              - we now have more space allocated to description  or the textarea
     *      - Add a HBox
     *          - Add 2 labels (specify font-type and font-size)
     *              - 1 for the Due text
     *                  - add a background color
     *              - next one for the deadline we get from to do item ( text will be updated at runtime)
     *                  - give it an fx:id
     *                  - create an instance variable for this Label
     *
     * Data model
     *  - Create a package "datamodel"
     *      - Create TodoItem class
     *          - Each todolist will have
     *              > short description that will show in the ListView & we'll need an instance String variable
     *              > details for the todoitem
     *              > local date field - stores the due date or deadline for the to do item
     *      - Add constructor for all the 3 fields
     *      - Add getters and setters
     *
     * Controller
     * Add a List of type TodoItem "tooItems"
     * Add initialize()
     *  - Initializes our app with some sample data
     *      - Create 5 instances of TodoItem
     *  - Initialize an ArrayList of todoItems and add all the 5 instances*
     * Add handleClickListView()
     *  - When user clicks short description , the details to be displayed on the text area
     *  - call getSelectionModel().getSelectedItem() to return the content of the selected to-do item
     *      - store this in item variable
     *  - add the details and the deadline to the text area for the selected to do
     *      - add TextArea instance variable "itemDetailsTextArea"
     *      - assign this as an fx:id to the TextArea control
     *      - call setText() on itemDetailsTextArea instance variable and pass item.getDetails()
     *  - add the details to a string builder
     *      - then append 4 lines to add some space between the description and the deadline
     *      - then append the due date
     *  - call setText() on itemDetailsTextArea and pass sb
     *  - changed the above after introducing VBox and HBox
     *
     *
     * toString()
     * Overwrite toString() and return "shortDescription"
     */
}
