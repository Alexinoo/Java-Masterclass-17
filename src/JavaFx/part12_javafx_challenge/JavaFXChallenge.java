package JavaFx.part12_javafx_challenge;

public class JavaFXChallenge {
    /*
     * JavaFX Challenge
     * ................
     * - Create a Simple contact application that has a single main window
     * - The application will allow the user to
     *      - add a contact
     *      - edit a contact
     *      - delete a contact
     * - Display the contacts using a TableView control
     *
     * - Create a Single Contact Menu with Add/Edit/Delete menu items
     *
     *
     * - You'll want to load and store the contacts, which isn't a JavaFX topic
     * - The instructor has provided a ContactData class that contains a quick and dirty way to load and store
     *   contacts using XML
     *
     * - At this point, you don't have to understand the code, or change it
     * - It doesn't do verification, so when we add and edit a contact, all the values must be provided for the code to
     *   work
     * - If we leave some values out, the code will throw an exception when we try to read the values from the application
     *
     * Next,
     * - Add code to the ContactData class to complete the challenge
     *      - There are comments that indicate where your code should go
     *
     * Hints:
     * 1. Create a Contact class to store individual contacts
     *   - For each contact, store the first name, last name, phone number and notes
     * 2. To get Data binding to work, store the contact information as SimpleStringProperty fields
     * 3. The To-doList app we worked on in an earlier lecture covers 90% of what you'll need to complete this challenge
     *
     *
     * Steps to reproduce
     * ..................
     * - Create a data model package
     *   - is where data for the application will be stored
     *   - the data model classes should be the only classes that can change the data
     *   - data will be saved to an xml file
     *
     * But in a real world application, you might do the same or alternatively read from a database or even fetch it from
     *  some API
     * Since only the data model classes can touch the data, if we want to change where the data is coming from in the future
     *  or the way we fetch the data, then we should only change the data model classes and won't have to touch the rest
     *  of the application
     * That's why keeping the data and the UI separately is highly recommended
     *
     * Step 1
     * Create Data model package
     * Create 2 classes
     *  - Contact class that contain info for a single contact
     *  - ContactData class that contain all the contacts
     *      - is where we'll initialize the List of contacts,
     *      - load and save contacts
     *      - if we ever want to change or load and store contacts, we'll only do those changes from this class
     *
     * Contact Class
     *  - Fields: (Declare them as of type SimpleStringProperty rather than strings to take advantage of the data binding)
     *    - Initialize all of them to empty string by calling SimpleStringProperty("")
     *      - SimpleStringProperty firstName
     *      - SimpleStringProperty lastName
     *      - SimpleStringProperty phoneNumber
     *      - SimpleStringProperty notes
     *
     *  - Constructor
     *  - Parameterized Constructor
     *   - generate constructor manually - if we use IntelliJ generator, this will create a constructor that accepts
     *     SimpleStringProperty arguments, but we only want to accept strings,
     *   - we don't want the ContactData class to have to know that we're storing every bit of contact info as SimpleStringProperty
     *   - use set() to set the value of the property
     *  - Default Constructor
     *   - no args constructor used when loading our saved contacts
     *
     * Getters and Setters
     *  - Generate getters and setters for the 4 fields
     *      - Generates 2 setters
     *       - one that returns a String and another one that returns SimpleStringProperty which is just fine
     *
     * toString()
     *  - Override toString() , though we may not use it but if we ever need to print the contact, we'd want to see the
     *    values and not the obj reference
     *
     *
     * Next
     * Work on our ContactData class which will hold all our contacts
     * - Create ContactData class
     * - Copy code from the resource section and add it here
     *
     * Default Constructor
     * - Initialize contacts to an observableArrayList
     *   - We've created an empty list by calling observableArrayList() from the FXCollections class
     *
     * Next
     * Add ObservableList<Contact> getContacts()
     *  - returns contacts

     * Add void addContacts(Contact item)
     *  - call add() on contacts list and pass the new item
     *
     * Add void deleteContacts(Contact item)
     *  - call remove() on contacts list and pass the contact to remove
     *
     *
     *
     * //////////////////////////////////////////////////////////////////////
     * User Interface
     * /////////////////////////////////////////////////////////////////////
     *
     * - Will contain a table that will occupy the entire window
     * - We'll use BorderPane rather than the AnchorPane
     *   - Add a center position
     *      - Add TableView control and assign it a fx:id
     *        - define the table using columns elements and TableColumn class
     *          - then set the text property to the text that we want for the column heading to display
     *        - add a cellValueFactory element
     *          - add PropertyValueFactory with a property properties and set it to "firstName" which rep the fields
     *      - Copy TableColumn element and update the properties respectively
     * - Since we want to take advantage of data binding and have the table automatically updated, when a contact is added
     *   , deleted or changed, we needed to assign a cellValueFactory for each column
     * - We've assigned an instance of the property value factory class which is specifically designed to be used with the
     *   cell value factory of a table column
     * - This is how we use data-binding with tables
     * - We've assigned self-explanatory labels to each column,
     * - The value we assigned to the property field, of property field value MUST match the name of the corresponding
     *   field in the Contact class - mapping
     *
     * - Once we've associated the table with our contacts list, the table column will notice when the first name value
     *   of a contact changes, and it will update the value it's displaying
     * - In other words, we won't have to update the table explicitly ourselves
     *
     * Next
     * Add menu to the top position of the BorderPane
     *  - Add <MenuBar>
     *      - Add <menus>
     *          - Add <Menu> with "Contacts" text
     *              - Add <items>
                        - Add <MenuItem> with "New.." as the text
     *                      - add event handler for opening the add contact dialog "showAddContactDialog"
     *
     * Next
     * Add the fxml for dialog modal when showAddContactDialog() is invoked when the menu is selected
     *  - create a new fxml file "contact-dialog.fxml"
     *  - use DialogPane as the root element
     *      - Add headerText element with some instructions to the user
     *      - Add content element
     *          - add GridPane to align labels and text fields nicely
     *          - add fx:id to the text fields to reference them in the ContactController
     *
     *
     *
     *
     *
     *
     *
     */
}
