package JavaFx.part5_controls;

public class Main {

    /*
     * JavaFX Controls
     * ...............
     * JavaFX has got a wide variety of controls and we're now familiar with 2 of them, button and label controls
     * We'll start with those 2 and then move to other controls
     * Use GridPane
     *  - Add a button with a text "Click me" in (0,0)
     *  - Add a Label in (0,1)
     *
     * Button (JavaFX 8 )
     *  - There is a class hierarchy for the button
     *  - These are the other classes that the Button class is extended from
     *      - One of the ancestors is the Control class
     *      - And further up the hierarchy as we move up, there's also a Node as well
     *  - The Node class is the base class for all scene graph nodes
     *  - The Control class is the Base class for scene graph nodes that the user can interact with
     *      - This means there can be nodes in the scene, that aren't UI nodes since there is 2 types there
     *  - There's also a lot of properties that are inherited from these other classes that make up the Button class
     *      - e.g. height from the Region class
     *      - e.g. skin from the Control class
     *              - responsible for rendering this control
     *      - e.g. text from the Labeled class
     *  - So there's lots of properties that collectively make up a button
     *
     *  - The button can also have an image, either in addition to the text or on it's own
     *  - Java has an icon repository that's pretty old now and should not be used in real apps but we'll actually use it
     *     for our apps because the icons are free and we've got permission to use them
     *  - So to get the icon package
     *      - get the link from the resources section
     *      - download jlfgr-1_0
     *      - Add it to our project
     *          - open Module Settings
     *          - Click + icon
     *          - Click on Dependencies tab
     *              - Click + ad select Add jars or directories
     *              - Select Classes
     *                  - Adds the jar to the class path
     *              - Click OK to apply changes
     *  - Next, let's add an icon to our Button control just to see what it looks like
     *     - We'll add a graphic child element to the button element
     *     - And inside that add an image view element
     *          - Get rid of the closing tag of the Button element
     *          - Add graphic tags
     *              - add ImageView tags
     *                  - Specify our image url inside Image tags through url property
     *                      - Since we've added them to the class path start by @ then /
     *                      - also means go to the root, then toolbarButtonGraphics/general
     *          - Now the icon is appearing to the left of the text which is pretty cool
     *
     *
     * Label Control
     * .............
     *
     */
}
