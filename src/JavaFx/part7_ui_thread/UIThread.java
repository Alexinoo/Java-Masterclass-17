package JavaFx.part7_ui_thread;

public class UIThread {
    /*
     * UI Thread
     * .........
     * Let's add a checkbox to the UI below the buttons (2,0) with a colspan of 2 because of the long text
     *  - Add a text "Clear the field after saying Hello or Bye"
     *
     * We'll listen for the event when the user checks/unchecks our checkbox
     * We'll also look at the state of our checkbox when the user presses the button, so if the checkbox is checked,
     *  - we're going to clear the text field after we printed the Hello/Bye message,
     *  - otherwise, we won't clear the text field
     *
     * Next,
     *  - Assign a fx:id to the checkbox
     *  - Add the CheckBox instance variable
     *  - Then add handleChange() that prints whether the checkbox is selected into the console
     *  - Attach handleChange() event handler to the checkbox
     *
     * handleChange()
     *  - Checks the state of the checkbox
     *
     * onButtonClicked()
     *  - Clear the fields after we print Hello/Bye on the console
     *
     * However, we now got a bug and what happened is that the buttons are now enabled after clearing the fields and
     *  there is nothing in our text field
     *  - Therefore we need o disable the buttons when we clear the text field
     *
     *
     * UI Thread
     * .........
     * The UI Thread sits and waits for user input
     * When the user does something, the UI checks if the application is listening for that event and if so, it dispatches
     *  the event to the event handler
     * The event handler itself runs on the UI thread
     * This means that while an event handler is running, the UI thread is busy & it is no longer paying attention to the
     *  user input
     *  - In other words, the user will not be able to interact with the UI while that is happening
     *  - And if he/she tries to do anything at that point, the application won't actually respond
     *  - The application would freeze up and no matter how you smash the button, nothing happens and then suddenly the
     *    application becomes responsive again
     *      - This could be because of an event handler that has taken too long to run and consequently the application is
     *        basically in limbo until it was finished
     *
     * Next,
     * Add a code before we actually do a check on whether the checkbox is selected
     *  - We will put the UI thread to sleep for 10 sec after we print the msg to the console
     * When we run the app and type something and press Hello/Bye button , the application prints to the console
     *  and then hangs & basically we can't do anything and depending on the OS that you're running on, you might actually
     *  have to kill the application
     */
}
