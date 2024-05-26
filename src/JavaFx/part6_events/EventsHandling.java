package JavaFx.part6_events;

public class EventsHandling {

    /* Events and Events Handling
     * ..........................
     * The JavaFX was design with an MVC pattern in mind and the controller is the part of the application that handles
     *  user input
     * It makes sense that event handlers are actually put into the controller class for that reason
     *
     *
     * Typically, the lifecycle of a UI program is as follows
     *  - Runs the initialization code
     *  - Builds the main UI and waits for the user input
     *  - When the user does something like for example
     *      - pressing a button,
     *      - or type into some field or selecting a menu item,
     *          - The application will run the code that handles that particular event based on what the user has actually
     *              done , or what they've clicked, or what they've interacted with the control on the screen
     *          - In other words, it's going to run the event handler for that particular event
     *  - When the user closes the main window or indicates in some other way that they'd like to exit the application,
     *    any clean up code will run and the application will then exit at that point
     * So in JavaFX, the JavaFX application thread also known as the UI thread waits for user input
     *
     * Implementations
     * ...............
     * Add a Button to the GridPane
     * When the user presses a button, or interacts with any type of control, an event is actually raised on the UI thread
     * The UI thread notices that the user has done something and then checks to see if any part of the application has
     *  expressed interest in handling whatever the user has done for that or interacted with for that particular control
     * If it finds something, it's going to run the event handler, the application that is associated with that event
     * In our case we want to run an event handler when the user presses a button
     *
     * First Step
     * ..........
     * Write the event handler in the EventsHandling .java file
     *  - add onButtonClicked() that prints a simple hello message on the console
     *
     * Second Step
     * ...........
     * Associate the event handler "onButtonClicked()" with the button
     * We do this so that the button actually knows which particular method to call when it's actually pressed
     * Add onAction property to the Button element in the fxml file
     *  - Specify the handler starting with a #
     *      e.g. onAction="#onButtonClicked"
     *
     * Next,
     *  Let's add a TextField to our GridPane, above the button element
     *  Change the output of the event handler by saying Hello to what was typed in the TextField
     *
     * Now we need to reference the TextField in our code
     * So we need to assign a fx:id attribute to the TextField
     * Next, we need to create an instance variable for TextField control, so we can actually retrieve the data out of it
     * The important thing here to note here is that the variable MUST have the exact same name as the fx:id we just assigned
     * Next, we call the variable.getText() to retrieve the data from the TextField
     *
     * But when we run this, we actually get a NullPointerException
     * We need to tell java runtime that when we run our application & it creates the instance of our controller class,
     *  it should assign the TextField it created from the fxml to the name field instance variable
     *  - This is because at the moment there's no real association with the 2 because they're actually separate things
     * We do that by annotating the instance variable declaration with the @FXML annotation
     *
     * So when the javaFX runtime instantiated our controller, it assigned/injected the reference field (fxml) to the TextField
     *  instance variable
     * So it matches fxml definitions to variables by looking for exact name matches between the fx:id value and the
     *  variable names
     * We get an error if the name provided in the fx:id="" doesn't match with the variable name in the Controller class
     *
     * It's also a good idea to annotate the event handlers, though not a requirement, but it really helps developers and
     *  they will be able to see at a glance that a () is an event handler associated with a control in the fxml file
     *
     *
     * Using Parameters for Event Handlers
     * ...................................
     * We can add an event parameter to our existing method
     *  - We do this by calling ActionEvent class and the name of the event which can be anything
     *      - Commonly used is e or evt
     * The reason why we might want to do this, is we might want to use say the same event handler for more than 1 control
     * And therefore, we need to know which control the user interacted with
     * So, if we add this parameter to our (), and then call the getSource(), we can actually figure out which control was
     *  actually used
     *  - This makes our code more concise as we don't have to necessarily create an event handler for each and every control
     *
     * event.getSource()
     *  - Returns a toString() representation of the control/element that was called on getSource()
     *  - Call standard toString() which then returns some info by telling us which class, with the style class also
     *     confirming it's a button
     *
     * Next,
     * Lets add a 2nd button and  attach it to the same event handler ()
     *
     * Next,
     * We can also assign some id to our 2 buttons as well
     * Map both of them in the controller class with Button Class and annotate them respectively
     * Then update onButtonClicked(ActionEvent e) accordingly
     *
     * So now we have 1 method that is acting as an event handle for 2 controls
     *
     * Next,
     * Let's look at some other controls and the events that we may want to handle in our code
     * Right now, we don't check to see if the users typed anything into the text field
     *  - We can either let the user submit empty fields and later tell them that the fields are required or
     *  - Or disable the buttons and enable them once the user enters something to the text field
     *      - This way we can be sure that when our event handler is called, the field isn't empty and the user won't
     *         do something only to be told they can't do it until they've done something else
     * Preventing the user from making a mistake in the first place will result in a much better experience for users
     *
     * So, we want our buttons to be disabled when we first run our application, because we know the text field(s) will
     *  initially be empty, and when they are disabled, they won't respond to user input
     * Then when the user types something, we want to enable the buttons at that point
     * This means we want to be notified whenever a character is typed into the text field
     * So we will write an event handler when a character is typed into the text field
     *  - We will listen to onKeyPress event & then call our handler handleKeyReleased
     *
     * handleKeyReleased()
     *  - Store the string typed into text String variable
     *  - Check if text variable is empty and ignore any whitespaces using trim() and assign to boolean
     *  - call setDisable() on both buttons and pass the boolean value
     *  - Finally attach this () to TextField control via the onKeyReleased property
     *
     * initialize()
     * JavaFX runtime calls this () when it's initializing the UI
     * This () doesn't take any parameters and must be public & we also need to annotate it with @FXML annotation
     * We need to disable the buttons when the application runs
     *  - We need to add initialize() and disable both of the buttons
     *
     *
     *
     */
}
