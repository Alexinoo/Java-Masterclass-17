package JavaFx.part4_layouts;

public class HBoxLayout {

    /* HBox Layout
     * ...........
     * HBox lays out its children horizontally in a single row and sizes its children to their preferred widths
     * If there's any horizontal space left over, it's going to stretch itself to fill the excess rather than stretching
     *  it's children
     * What it does with any extra height will depend on the fill height property
     *  - When fill height is true , which it is by default, the HBox is going to stretch itself to fill any extra height
     *  - If false, its going to resize its children to fill that extra height
     * We often use a Hbox layout to lay out a set of buttons in a dialogue
     * Because all the layouts inherit from the same parent class, which is pane, they share a lot of the same properties
     *
     * Create h-box-layout and link it to the main app
     *  - vgap , hgap and gridLinesVisible don't exist to the hbox layout
     *  - We can leave the alignment to top_center
     *  - Add 3 buttons, Ok, Cancel and Help
     *      - Buttons are laid horizontally at the top (as set in the alignment) next to each other with no space between them
     *  - Because the hbox's fill height prop is set to true by default, it will have stretched itself to take up any extra
     *    height
     *      - In this case, the extra height is actually below the buttons
     *      - To see that the HBox has stretched itself to fill any excess width & height, let's add a border around
     *          the hbox , we can do this with CSS
     *          - set border with style using the prop below
     *              -fx-border-color:red; -fx-border-width:3; -fx-border-style:dashed;
     *      - Now we can see tha the HBox is now occupying the entire width and height of its parent
     *          - Adding a border to the layout is a good way to visualize how much space is given to the child
     *
     * Let's change the alignment to the bottom right because that's where normally buttons appear
     *  - Set alignment to "bottom_right"
     *  - We can see that the HBox has stretched itself above the buttons to fill the extra height
     *
     * Let's add a gap between the buttons
     *  - Add spacing property in the hbox and set it to 10 tio achieve this
     *  - Adds 10px gap between each child
     *
     * Let's now add some space between the bottom of the window and also some space to the right edge of the window
     *  - We use padding and insets similar to what we did in the GridPane
     *  - Set bottom and right property to 10
     *  - This adds 10px gap to the bottom and to the right edges of the window
     *
     * Resizing, won't make any diff, elements stays the same no matter how large/small we resize the window
     *
     * Let's add the same width to all the buttons just to have a consistent size for all buttons
     *  - When controls are placed in a hbox, they take on their preferred width by default as long as there's enough room
     *    to do so
     *  - To make all the buttons have the same width, we'll set the preferred width to the same value for every button
     *  - set the width to 90
     *  - This sets the uniform width to each button and they now appear uniform as well
     *
     * Normally, hbox is not a complex layout and we wouldn't be using it as a layout for a top level window
     *  - Commonly used in dialogue situation, we would normally add HBox as a child of another layout
     *
     *
     *
     *
     */
}
