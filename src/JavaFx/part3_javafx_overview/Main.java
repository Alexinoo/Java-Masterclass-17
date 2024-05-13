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
     *      - Takes a Stage
     *
     * stop()
     *      - When the application finishes, which usually happens when the user closes the application's main window,
     *        the stop() runs
     *      - Just like the init(), this () is empty in the application class, so unless we override it, it won't do anything
     *
     * Stage
     * ......
     * Stage is the main container and the entry point of the application.
     * It represents the main window and passed as an argument of the start() method.
     *
     * A Stage is a top level JavaFX container that extends the Window class
     *  - Essentially, it's the main window
     *
     * JavaFX runtime constructs the initial stage and passes it into the start()
     * We can create other stages, but most applications will only have 1 top level window
     *  - too many windows can lead to a bad user experience
     *
     *
     * FXML
     * ....
     * JavaFX uses a special FXML markup language to create the view interfaces.
     * This provides an XML based structure for separating the view from the business logic.
     * XML is more suitable here, as it’s able to quite naturally represent a Scene Graph hierarchy.
     * Finally, to load up the .fxml file, we use the FXMLLoader class, which results in the object graph of the scene hierarchy.
     *
     * Features
     * ........
     * - fx:controller attribute tells the runtime which class is the controller for this window
     *      - represented in form of a "package.controller"
     *
     * When we load the fxml file, all of the UI obj(s) defined in the file are constructed
     *
     * Scene
     * ......
     * - We then set the title of the stage
     * - Since the Window class is the parent of the Stage class, and as we learned, the stage is the top-level JavaFX container, this means the window
     *   title will be set to "Hello World" in this case
     * - Then we set the Stage scene
     *
     * The java developers were going for a theatre metaphor - Each stage requires a scene and backing each scene is a scene graph
     * - A scene graph is tree in which each node corresponds to a UI control or an area of the scene
     *
     * When we loaded the fxml, we assign it to a variable of type Parent with the name of the root
     * The parent class descends directly from the node class, which is the base class for scene graph nodes
     * Nodes that descends from parent can have children in the scene graph
     * For the Hello World application, the top level and in fact only node is the GridPane, So the GridPane node will be the root node of the scene
     *  graph and that's what ios returned from the FXMLoader.load()
     * When we construct a scene, we have to pass in the root of the scene graph that will back the scene
     * We're also setting the width and height of the scene which in turn will become the width and height of the main window
     *
     * So why do we have need to have a scene at the stage?
     *  - Well let's go back to the theatre metaphor, and imagine that you're watching a play
     *  - The curtain rises when we watch the first scene, then the curtain folds, stage,hands run around and change the furniture and backdrop &
     *    then the curtain rises again
     *  - We might see a different looking set of different actors, but it's still the same stage
     *  - If we go back to JavaFx, remember that a stage corresponds to a top-level UI container, like a window and a scene is backed by a scene
     *    graph which contains the UI nodes
     *  - If you want to change what's shown in a stage, the window, all we have to do is change the scene, the UI
     *  - We don't have to construct a new stage obj
     *  - In Practice, we would load a different6 fxml file into a new scene and then call stage.setScene()
     *
     * For Example
     *  - If we had a wizard, that we wanted the user to step through using next and previous buttons, we could use the same window stage and just
     *    change the scene every time they clicked next or previous or when we wanted to change parts of the UI in response to a user action, we
     *    don't have to remove UI components or make them invisible and add new ones, we just switch the scene
     *  - Sometimes, it just makes more sense to just hide a few controls , but other times, switching the entire scene is the better option and this
     *    can depend on what you want to do
     *  - Essentially, those scenes make it easy to re-use a container
     *
     * show()
     *  - We show the scene by calling show() on our stage
     *
     * The Stage class provides all the window declarations, such as close, resize and minimize button
     *
     *
     *
     *
     *
     *
     */
}
