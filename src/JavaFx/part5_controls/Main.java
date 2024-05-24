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
     * - Add a label element to column index 1
     *  - Add icon Information24.gif
     *
     * Next
     *  - Change the text color of the label to blue
     *      - Use textFill property and set it to Blue
     *  - Change the Font type and size
     *      - Add font element within the label
     *          - Add Font with capital F
     *              - specify the Font type within the name property
     *              - specify the Font size within the size property
     *      - By default, label text doesn't wrap,
     *          - We use wrapText property to true
     *          - Wraps the text in another line if there is no enough room to display the text
     *
     * RadioButton Control
     * ...................
     *  - Can be selected or de-selected
     *  - We group radio buttons together so that when the user selects 1 radio button, the others are automatically deselected
     *  - Add 3 radio buttons
     *      - Set the first to blue, red and green
     *  - Currently, they're all operating independently and not how we'd normally expect them to operate
     *      - We need to group the radio buttons together by adding them to a toggle group
     *  - The RadioButton class descends from the ToggleButton class
     *      - Toggle buttons can be added to a toggle group
     *  - The Toggle Group class doesn't descend from the Node class and therefore can't be added to a scene graph
     *      - Because of that we have to use fx:define element in the fxml to use this toggle group
     *      - We use fx to find when we need to add something to the fxml that isn't part of the scene graph
     *      - We'll assign an id to it as well and see how this works
     *  - This id defined in the toggleGroup element is then referenced from the RadioButton element using the toggleGroup
     *     property using a dollar sign
     *  - When referring to an fx:id value, we put a $ sign in front of it and this tells intelliJ to look for an
     *    fx:id definition with the id of whatever we have typed after the $ sign
     *  - The fx:define element need to be added because the ToggleGroup doesn't descend from node abd can't be added
     *    directly to a scene graph
     *      - This is a way of getting that functionality to work
     *  - Typically when we add the Radio buttons, we also want 1 of them to be selected by default
     *  - Let's make our blue radio button to be selected by default
     *      - We need to set the selected prop to true
     *
     * CheckBox Control
     * ................
     *  - Allows us to select/check more than 1 choices
     *  - Let's add this to the 4th column of our row
     *      - We use CheckBox element to add a checkbox to the scene
     *  - The JavaFX control actually allows for 3 states, but you have to turn on the 3rd state since it's off by default
     *      - This state is known as indeterminate state and when it is turned on, the checkbox will initially contain a
     *        dash
     *      - Once the user clicks the checkbox, there isn't a way to get the dash back by clicking the control
     *      - We turn the state on by adding the indeterminate prop & set it to true
     *
     * Multiple Checkboxes
     * ...................
     * We can't group multiple checkboxes together under a toggle group since checkbox doesn't descend from the ToggleButton
     *  class
     * But we can group the checkboxes together visually using a VBox and then add 2 or more checkboxes
     *  - Add multiple check boxes for Dog, Bird and Cat
     *
     * ToggleButton Control
     * ....................
     * We said that radio buttons descend from the Toggle Button class
     * A toggle button is a JavaFX control in it's own right
     * A Toggle Button looks like a regular button but when we press it, it stays down and when we press it again, it
     *  pops back up
     * Let's add one to our grid using the ToggleButton element
     * We can also add more and wrap them with a HBox
     * If we wanted only 1 to be selected, we would have to put them in a ToggleGroup as we did earlier with radio buttons
     *  and that would enable only 1 to be selected at a time
     *
     *
     *
     *
     *
     */
}
