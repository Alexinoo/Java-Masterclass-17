package JavaFx.part4_layouts;

public class StackPaneLayout {
    /*
     * StackPane Layout
     * .................
     * StackPane places all its children in a stack
     * In other words, the control and a StackPane occupy a single cell and are piled on top of each other
     *
     * Implementations
     * ...............
     *
     * Let's add a label and a button to see how this is going to look like
     *
     * If we run this, we can see that the label and the button are on top of each other
     * To find out what is on top of the other , lets change the background of the button color to red and the label
     *  background color to green
     *  - We can see that the label is on top of the button
     *
     * If we swap the order, and put the label and put the label at the top,
     *  - The button now will be at the top of the label and in actual fact we can't see the label anymore because of
     *
     *
     * Let's add another label after the button and set it's background to blue
     *  - Now we can see that the 2nd label is now on top of the button
     *
     * So the order in which we add controls to the fxml will determine the order in which the controls are added to
     *  the StackPane
     *  - The first control will end up at the bottom of the stack & the last control we add will be at the top of the stack
     *
     * Or as the Stack pane documentation puts it, the zeroth child being at the bottom and the last child on top
     *
     * And the same is going to be true, if you do this through code
     *  - The first you add to the layout using .getChildren().add() when that it will end up at the bottom of the stack
     *  - The last child you add will be at the top of the stack
     *
     * Why would we use StackPane Layout ?
     *  - Suppose we added an image to the stack and then place lhe label on top of it, we'd end up with an image with text
     *    imposed on it
     *  - We could also style a text so that it's surrounded by border
     *
     */
}
