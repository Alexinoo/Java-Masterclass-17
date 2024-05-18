package JavaFx.part4_layouts;

public class FlowPaneLayout {
    /*
     * FlowPane Layout
     * .................
     * This is very similar to the hbox and vbox layouts
     * It differs since it wraps its children
     * With hbox, the children are laid out horizontally as a single row
     *  - if the window is resized, & there isn't
     *    enough room, then the children will be cut off and that principle applies to vbox too
     *
     * With vbox, the children are laid out vertically
     *  - if the window is resized, & there isn't
     *    enough room to fill them all, then some children will be cut off
     *
     * When using a FlowPane, children won't be cut off unless the user resizes the window so that it's not possible to
     *  show them all
     *
     * When the orientation of the FlowPane is set to horizontal and the user resizes the window so that all the children
     *  can't fit into a single row, the layout will actually wrap the children to the next row
     *
     * If the user increases the width of the window, the children will pop back to the previous row
     * The same applies when the FlowPane orientation is set to vertical
     *  - If there isn't enough height for the children to fit into a single column, some children will wrap into the
     *      next column
     *
     *
     * Implementations
     * ...............
     * - Setup a FlowPane with 10 buttons and play around with orientation
     *  - Set the orientation property to HORIZONTAL which is the default anyway
     *
     * - Prints 2 rows based on the size that we specified
     *      - First row - Buttons 1 up to Buttons Six
     *          - This is because there is enough room to show 5 buttons
     *      - Second row - Buttons Seven up to Buttons Ten
     *          - FlowPane wrapped Button 6 through 10 to the second row
     *
     * - If we widen the windows, they will pop back to the first row automatically
     * - Same happens if we shrink the window, buttons will wrap themselves to the next row if there isn't enough room
     *
     * - Change the orientation to VERTICAL
     *      - stack them 1 through 10
     * - If we resize the window upwards, will try to make them fit based on how wide the window is
     *
     *
     *
     */
}
