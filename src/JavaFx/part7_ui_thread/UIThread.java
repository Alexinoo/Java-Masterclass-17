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
     *
     *
     * Threads and Runnable
     * ....................
     * Basically the event handler that is still running on the UI thread isn't the optimal solution for processes that
     *  take some time to complete
     *  - In other words , if our event handler needs to do something that will take more that let's say a second, then what
     *    it should really be doing is starting another thread
     *  - The new thread will do the work that's going to take a while to process, so the event handler which is still running
     *    on the UI thread will be able to return and the UI thread can listen for user input again
     *
     * So from the perspective of the user who is running the application, they won't see pauses and the delays & the weird things
     *  that were happening in our application
     *
     * So we refer to the thread that's going to take up the work that will take a while as the background thread as opposed to the
     *  UI thread
     * When the background thread has completed the work, it notifies the UI
     * For example:
     *  - If the event handler needed to retrieve data from db, or perhaps the internet, it would kick of this background
     *     thread to do the data retrieval
     *  - When the background thread has the data and it has completed what it needs to do, it will inform the UI and then
     *     the data can be loaded into the UI
     *
     * So instead of putting the UI thread to sleep as we did previously, we're going to kick off a background thread and put
     *  that to sleep
     * And when it wakes back up, we're going to notify the UI, and update a control on the screen that the process is actually
     *  completed
     *
     * So let's
     *  - add a label to our GridPane below the checkbox with an fx:id of ourLabel
     *  - add an instance variable for ourLabel
     *
     * Next,
     * Create a Runnable called task that will ultimately contain the code that we want to run in the background thread
     * So this process is going to do the work of setting up and running this code in the background
     * It will no longer be running it on a UI thread, but will create another process virtually like another program , or
     *  another instance of something running in the background on your computer to do whatever processing we want
     *  - In this case, the only processing we want is to run Thread.sleep() , or sleep for just 10 sec
     *
     * So,
     * We want to update our label, when the thread wakes up
     *  - We'll set/update the text of our label right after Thread.sleep() returns , i.e. when it has finished after 10sec
     *    then we want to update our label so we can see it's actually updated
     *
     * Next,
     * We need to invoke or start it, so it actually starts processing, because it's just like declaring a variable
     *  - Declaring it is one step and using it is another one
     *
     * However, after running this, we get an IllegalStateException, Not On FX Application Thread and some other info regarding
     *  that thread
     *
     * So what's going on here and what does that actually mean ?
     * ..........................................................
     * Well, it turns out that the scene graph isn't thread safe
     * The code assumes that nodes in the scene graph, will only ever be updated by the same thread, namely, the UI thread
     *  also known as the JavaFX application thread
     * So if more than 1 thread could update a node, it would be possible for the internal integrity of the node to be
     *  compromised
     * For Example:
     *  - If Thread-A runs and start to change the state of a node & then it's suspended, so that Thread-B can run,
     *    nothing in the JavaFX code prevents Thread-B from also updating the same node
     *  - In other words, Thread-B could wipe out, what Thread-A had started, so there would be some sort of compromise
     *     between the 2 processes on the computer running at the same time and they're sort of conflicting with each other
     *  - So basically, Thread-B could wipe out what Thread-A had started, and when Thread-A runs again it continues to
     *     update not knowing that it's previous updates were overwritten by Thread-B
     *
     * So we can see how the internal state or integrity of a node could be compromised in that situation
     *
     * Solution
     * ........
     * The developers of JavaFX were keenly aware of this problem, and so whenever we want to work with nodes on the
     *  scene graph, we actually must do so on the JavaFX Application thread or the UI thread
     * If we don't , in other words if we try to update something that's not in the UI thread, we're going to get an
     *  exception like this one
     * So we got this error, because we're trying to update a label ( a node from the scene graph) from the background
     *  thread
     *
     * So, how do we actually fix this ?
     * .................................
     * When we're doing a long task on a background thread, we usually want to update a part of the UI when the task is finished
     * So we really need to force the code to run on the UI thread and JavaFX actually provides us with a way to do this
     * We'll use the runLater() in the Platform class
     *
     * runLater()
     *  - Accepts a runnable parameter
     *  - Puts the runnable parameter that's passed onto the UI threads queue
     *  - In other words, it's going to force the runnable obj to run on the UI thread and in this process that's exactly
     *     what we want it to do
     *  - So in the run() itself, we're going to use Platform.runLater() to update the label when the long running task
     *     of the background thread has finished executing
     *      - In other words, we're not going to put this first runnable using Platform.runLater() because we've got the
     *        same issues and it's effectively going to be running on the UI thread
     *      - It's only after we finished our processing sort of as the last line we execute in the run() that we're going
     *        to do this extra bit of code
     *  - So basically, instead of trying to put our update ourLabel, we're going to put Platform.runLater() which accepts
     *     a runnable and then put our update label statement in the run()
     *  - So basically, what we're doing here is that we're still pausing for 10 sec, but then after that, invoke the command
     *     that the label needs to be updated on the UI thread so that we don't get exceptions
     *
     * So, our thread quite happily paused for 10sec while we were doing other things, and the UI was quite responsive during
     *  that time and we didn't have any slowdowns, or pauses or weird things happening and it was all running like you
     *  would have expected to do so
     *
     * Now the isFxApplicationThread() is a useful () in the Platform class, and it actually returns true when the code is
     *  running on the JavaFX application thread and false if otherwise
     * We can use this () when we're trying to debug our code because we're not getting the exception anymore and we're
     *  really not sure which thread the code is running on
     * So,
     * Let's add this code just to see how we'd go about doing that
     *
     * This is quite a useful way to debug code since we're not getting any exception as it's working correctly , if
     *  we're trying to figure out where our code is actually been executed
     * Using Platform.runLater() is one way to ensure the code runs on the UI thread
     *
     * JavaFX also provides a set of API's that help us run background threads and also to communicate with the UI
     * The API's are in the javafx.concurrent package which is a really much more advanced topic
     *
     *
     *
     */
}
