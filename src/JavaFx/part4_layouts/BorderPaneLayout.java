package JavaFx.part4_layouts;

public class BorderPaneLayout {

    /*
     * BorderPane Layout
     * .................
     * This is one of the most commonly used layouts for top-level window
     * You can place controls into one of the 5 positions
     *  - Top , Bottom , Left , Right and Center
     * It works well for a typical client application, one that you would have a menu and a toolbar near at the top, some'
     *  type of tree or list to the left and a central area that displays data or allows the user to enter data, a status
     *  bar at the bottom & sometimes an info panel or something else to the right-hand side
     * BorderPane sizing & resizing behavior is what we would expect as well which is pretty good
     *  - Children in the Top and Bottom positions will have their preferred heights & extend the width of the BorderPane
     *  - Children in the Left and Right positions will have their preferred widths & extend the full height between the top
     *    and bottom positions
     *  - Center position gets whatever space is left over & will fill that entire space
     *
     * We don't have to place something into every position, when a space is empty, then no space is allocated to it
     *  - i.e. If the right position is empty, then whatever is placed in the center position will extend to the right edge
     *     of the BorderPane, sort of filling that entire right hand section up
     *
     * The thing to remember when using BorderPane, we specify where controls should go by placing them into one of the
     *  BorderPanes 5 positions
     *  - So it doesn't make sense to tell the BorderPane that we want all it's children at the bottom of the pane and then
     *    align to the right as we did for the HBox
     *  - In other words, BorderPane does not support the alignment property
     *
     * We can align the BorderPane children within their positions in the BorderPane itself
     *
     * Next, let's put our HBox into the bottom position
     *  - We can do so by placing the HBox into the bottom element
     *  - This tells the BorderPane layout where we want our Hbox layout to be, which ultimately contains our buttons
     *      - Now aligned properly in the bottom in the left position
     *
     * We have nested HBox into bottom layout which is also nested inside the BorderPane layout
     *  - When using BorderPane, nested layouts is the only way to add a row of buttons into the bottom position
     *
     * Next, we'll add the alignment back to the Hbox, not to the BorderPane because we established that the alignment
     *  property doesn't exist for the BorderPane
     *  - This time we can set it to top-right, center-right or bottom-right, we will see the same result no matter
     *    what alignment we use because the alignment property specifies how layouts children should be aligned within it
     *
     * So, let's align our HBox to the top right positions and add some padding between the bottom and the right edge parts
     *  of the window
     *  - You can see it's now at the bottom right
     *
     * Let's put controls into other BorderPane positions
     *  - Add a label to the top position
     *      - Add top element
     *          - Add Label element and align it at the center
     * - The label appears to be left justified even after specifying the alignment
     *      - The alignment for a label control determines where the label's content will sit when there's empty space within
     *         the label
     * - So we place the label into the top position of the BorderPane, and that means that the layout has given the label
     *   the entire width of the pane, because of that we would mean that the text would be centered within the top position
     *
     * The thing to remember is that most controls would not stretch to fill the width they've been allocated and that's how the label
     *  control behaves
     *  - This means even though the BorderPane is given the label, the entire window in this case, the label remains at its
     *    preferred width which is the width it requires to accommodate the text
     *  - Let's add a border to the label for us to this clearly
     *      - We can see now that the label isn't occupying the entire width of the top position
     *
     * So what we need to do is center the label itself within the top position rather than centering its text
     * As mentioned earlier we can't set the alignment of the BorderPane itself, but we can set an alignment for each of its
     *  children
     * We will use the BorderPane on alignment property when we want to align a child within it's position in the BorderPane
     *  - In short we'll set the BorderPane alignment to center for the label
     *  - And now if we run this, the label is placed at the center of the window
     *
     * Left
     * ....
     *
     * Let's move on now to the BorderPane left & right positions
     *  - The controls in these positions will be sized to their preferred widths and will occupy the entire height of
     *      the position
     *  - Add label to these positions using the left and right child elements
     *
     * Running this
     *  - We've got the labels sized to their preferred widths and place at the top of the left and right positions
     *
     * Center
     * .....
     *  - Let's adda label there and give it some text that will be too long to fit in the remaining space just to see what happens
     *  - The center position will get any space that's left over after controls have been placed in the other positions
     *  - Running this
     *      - We can see that the center label text is cut off, if we widen the window, eventually, the center area is expanding
     *        but the left & the right don't expand because the labels in those positions are already at their preferred width
     *
     *
     */
}
