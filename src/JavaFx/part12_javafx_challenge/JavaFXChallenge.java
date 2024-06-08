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
     * ////////////////////////////////////////
     * Event Handler for Dialog Controller
     * ////////////////////////////////////////
     *
     * - We want dialog to be modal and tied to the parent window of the app,
     *   - assign fx:id to the BorderPane in the main-view fxml
     *
     * MainController
     *   - create an instance variable for the BorderPane above in the ContactController
     *   - add event handler for showing the dialog
     *      - Initialize dialog
     *      - set owner to parent window
     *      - set title
     *      - create FXMLLoader instance
     *      - call setLocation on FXMLLoader instance and pass the contact-view.fxml
     *      - add a try block
     *          - call getDialogPane().setContent() on dialog and pass the contact view
     *          - handle any IOException
     *              - print stacktrace
     *              - abort
     *  - set dialog buttons
     *      - call getDialogPane().getButtonTypes().add(ButtonType.OK) on dialog to add OK button
     *      - call getDialogPane().getButtonTypes().add(ButtonType.CANCEL) on dialog to add CANCEL button
     *
     *  - Call showAndWait() on dialog and store that in result variable of type Optional
     *      - check if OK button is pressed
     *
     * Next,
     * Create an instance variable of the ContactData class
     *  private ContactData data
     *
     * Add initialize()
     *  - create an instance of the ContactData class
     *  - read saved contacts
     *
     * Create an instance variable for the TableView
     *  - Initialize TableView "contactsTable"
     *  - populate contactsTable by calling setItems() and pass
     *
     *
     * Next
     * Handle new contact - info submitted by the user
     * At the moment, this isn't accessible from the main controller
     * We have to get the info using the ContactController and provide a way for the main controller to get the new contact
     * Let's add getNewContact() to the ContactController , which will gather the info provided by the user
     *  - Create a new contact instance
     *  - return the instance to the caller
     *
     *
     * Next
     * When the user presses OK,
     *  - we'll get a reference to the ContactController
     *      - call fxmlLoader.getController() - gets controller associated with the fxml file provided
     *      - call getNewContact() from the Contact class
     *      - pass newContact to the contacts list
     *  - we'll save new contact to the xml file by saving all the contacts
     *      - call saveContacts()
     *  - we'll do this anytime the user adds/edits/deletes a contact
     *
     * Add a new contact
     *  - Added a contact - which appears at the TableView automatically
     *
     * Next,
     * Close the application and run it again and see whether our content will be loaded
     *  - And we now have our contact successfully loaded from the xml file, when the application starts
     *
     * If we did a mistake and we wanted to change things, we cna also do that in the contact.xml file since we haven't added the edit functionality
     *
     *
     * ///////////////////////////////
     * EDIT CONTACT FUNCTIONALITY
     * //////////////////////////////
     *
     * - Add an edit menu to our Contacts menu bar and attach event handler "showEditContactDialog"
     * - We'll re-use the UI that we used for adding contact
     *   - we'll change the header text of the dialog and then fill the fields with the existing values of the
     *     contact that we're editing
     *   - when the user presses OK, we don't want to add a new contact rather update the existing contact instance
     *   - we need to know which contact the user wants to edit, i.e. the contact selected by the user in the table
     *      - if nothing is selected,
     *          - show a msg box that tells the user to select the item that they want to edit
     *          - set the header text to null
     *              - alerts can have header text, just like dialogs, but we don't need it & so we've set it to null
     *          - set the content text - info t what the user needs to do
     *          - call showAndWait() - makes it a modal
     *      - otherwise, if indeed, the contact was selected
     *          - create a dialog so that we can pop up the dialog containing the fields and enable the user to edit
     *              - call editContact on the ContactController
     *              - if user presses OK, call updateContact from ContactController
     *                  - call saveContacts - update xml
     *
     * ContactController.editContact()
     *  - populate the fields on the dialog with the details from the contact object passed to this method
     *  - call setText() on each text field and pass the getters for each field
     *      - e.g.
     *          firstNameField.setText(contact.getFirstName());
     *
     * ContactController.editContact()
     *  - we need to do the opposite
     *      - get the data populated in the dialog and update the xml data
     *      - call the setters for each field and pass the field from the dialog from the contact object
     *      - e.g.
     *          contact.setFirstName(firstNameField.getText());
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     */
}
