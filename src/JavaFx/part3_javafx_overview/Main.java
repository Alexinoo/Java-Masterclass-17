package JavaFx.part3_javafx_overview;

public class Main {

    /* JavaFX Overview
     * ...............
     * - JavaFX was designed with the MVC, or Model-View-Controller. pattern in mind.
     * - In a nutshell, this pattern keeps the code that handles an application's data separate from the UI code
     * - When we're using the MVC pattern, we shouldn't mix the code that deals with the UI and the code that manipulates
     *   the application data in the same class
     *      - The controller is sort of the middleman between the UI and the data
     * - When working with JavaFX,
     *      - the model corresponds to the application's data model
     *      - the view is the fxml
     *      - the controller is the code that determines what happens when a user interacts with the UI
     *          - Essentially, the controller handles events
     *
     * Main Class
     * ..........
     * - The class extends Application which comes from javafx.Applications
     * - JavaFx applications must have a class that extends this application class which is the entry point for the application
     * - The Application class manages the lifecycle of a JavaFX application
     * - It has the following ()s that we might find useful
     *      - init()
     *      - start()
     *      - stop()
     * - When we run the application, application.launch() will be called from the main()
     *
     * launch()
     *      - This () launches the JavaFx application and doesn't return until the application has exited
     *      - When JavaFx app is launched, the init() runs first
     *          - In the Application class, this () is empty, so unless we override it, it won't do anything
     *
     * start()
     *      - Runs right after the init()
     *      - We have to override this class because start() is an abstract () in the application class
     *      - We create the UI in the start()
     *
     * stop()
     *      - When the application finishes, which usually happens when the user closes the application's main window,
     *        the stop() runs
     *      - Just like the init(), this () is empty in the application class, so unless we override it, it won't do anything
     *
     *
     *
     *
     */
}
