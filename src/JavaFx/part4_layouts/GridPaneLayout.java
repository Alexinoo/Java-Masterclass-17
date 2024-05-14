package JavaFx.part4_layouts;

public class GridPaneLayout {

    /* GridPane Layout
     * ...............
     * Layouts allows us to add controls which are UI components to a container without having to write the code required
     *  to manage the positioning and also the resizing behavior of those controls
     *
     * JavaFX has got 8 layouts which include:
     *  - GridPane
     *  - AnchorPane
     *  - StackPane
     *  - Hbox
     *  - vBox
     *  - FlowPane
     *  - TilePane
     *  - BorderPane
     *
     * PreferredSize
     * .............
     * Every control computes its preferred size based on its contents
     *  - i.e. The preferred width and height of the control when it's displayed
     * For example, the Button control will size itself so that there's enough room to display its contents (text)
     * Layouts often use the preferred size of the controls they're laying out to determine how much space a controller
     *  will ultimately get
     * So when a controller is placed into a layout, it becomes a child of that layout
     * So some layouts will ensure that their children display at their preferred widths or heights
     *  - But sometimes on where the controls are placed within the layout
     *
     * GridPane
     * ........
     * GridPane lays out its children in flexible rows and columns (lays out its children in a grid hence the name GridPane)
     * Each position in a grid is called a cell by default, rows and cols will be sized against their content
     * A row can be tall as the tallest control it contains, and a column will be as wide as the widest control it contains
     *
     * Deep Dive
     * .........
     * Add 5 buttons to the GridPane
     *  - We use the Button control in JavaFX to create OK/Cancel buttons or any other type of button the application needs
     *  - We'll use the text property to set it to different values and see how they look like
     *       <Button text="Button One" />
     *       <Button text="Button Two" />
     *       <Button text="Button Three" />
     *       <Button text="Button Long Button Four" />
     *       <Button text="Button Five" />
     *  - Running this:
     *      - The actual buttons are stacked on top of each other and that's the reason for that since we didn't specify
     *         a row and column for each button
     *  - Since we're using a GridPane we need to do that in order for them to actually appear as how we would expect
     *     them to do in the right area without overlapping each other
     *  - By not specifying, the rows and columns, GridPane adds all the buttons in the default position which is 0,0 and
     *    basically, all the buttons have appeared in that same row and column
     *
     * We specify the row by using GridPane.rowIndex attribute and passing the value enclosed by double quotes
     * We specify the col by using GridPane.colIndex attribute and passing the value enclosed by double quotes
     *  - <Button text="Button One" GridPane.rowIndex="0" GridPane.columnIndex="0" />
     *  - <Button text="Button Two" GridPane.rowIndex="0" GridPane.columnIndex="1" />
     *
     * Running this:
     *  - The buttons appear in the same row but now in different columns as we would expect them to
     *
     * The other thing to note is that each column is the width of the widest control in that column
     *  - It's given enough space to hook this button 4 as well
     * Each row is the height of the tallest control in the row
     *  - So if we had a taller control in one of the rows for arg(s) sake, the rows height would be greater than the other
     *    rows
     *
     * When the IntelliJ generates GridPane definition for us, it sets the hgap, vgap and the alignment properties and
     *  the alignment is set to center
     * So the GridPane is centered within the GridPanes width and height and is basically using the middle part of the window
     * The GridPane is occupying the entire window and the grid is centered within that window
     *
     * There's also a 10px gap between the rows as specified by the hgap property which we've set to 10
     * There's also a 10px gap between the cols as specified by the vgap property which we've set to 10
     *
     * The gap looks larger for the first row because the first button doesn't occupy the entire width of itself
     *
     * To help us see this we can actually use a debugging property that the GridPane has
     *  - We'll set the grid line visible property true and now when we run the app you'll be able to see this a bit clearer
     *
     * Running this:
     *  - We can now see the 10px gap btwn the rows and the cols
     *      - Buttons 1,3 and 5 don't stretch to fill the width with of their cells, so that's why it appears that there's
     *        a larger gap between the 1st and 3rd cols
     *
     * Next, thing to think about is what happens if we resize the window containing the GridPane ?
     *  - If we resize, the GridPane is actually staying in the center and so as we go smaller or larger, it's still
     *     centering itself automatically
     *  - The reason for that is because it's alignment is set to center, but that said, it doesn't resize because it's already
     *     large enough to accommodate the children in all itself
     *
     * So let's add some more text to another button to make it really, really long and see what actually happens
     *  - Update button 4 to have more text
     *
     * Running this now:
     *  - We can see the text for the most buttons have been cut off, particularly the ones in the 1st col, but when we resize
     *    the window, the GridPane will resize until it's large enough to accommodate all it's children
     *  - Once it's resized to some certain level, it doesn't try and resize anymore
     *
     * So what happens is that when we changed button four text to be really long, initially GridPane gave more space to
     *  the second column because the text was so long that's before we resized it
     * So obviously it gave more size here to try and accommodate as much of the text it could fit in
     *
     * What if we want each column to be given 50% of the GridPane as an arg ?
     *  - That can be quite handy to do & we can actually do that quite easily , we can use the column constraints class
     *
     *  - add
     *      <columnConstraints>
     *          <ColumnConstraints percentWidth="50.0" />
     *          <ColumnConstraints percentWidth="50.0" />
     *      </columnConstraints>
     *
     *  - so all we're doing here is that we're using the ColumnConstraints to set the initial width of each column
     *      - we can actually use px or by a %
     *      - ordering is really important
     *  - We add a column constraints child element to the grid, and within it, we adda column constraints element for
     *    each column
     *  - So basically , each one of these tha we're adding within this column constraints block is the column, is the
     *    percentage width for that column in order, so ordering is important
     *  - SO the first column constraints element will apply to the first column, the second to the second column etc
     *
     * Running that:
     *  - We can now see that each column now occupies 50% of the width
     *  - We also notice now that the grid occupies the full width of the window
     *      - If we make it bigger, it's still actually utilizing the entire space of the window
     *      - And that's because we've told the GridPane that we want each col to be 50% of its width rather than
     *        being only large enough to accommodate the widest controls preferred width
     *
     * Comment out the column constraints to revert back to default layout we had before
     *
     *
     *
     *
     */
}
