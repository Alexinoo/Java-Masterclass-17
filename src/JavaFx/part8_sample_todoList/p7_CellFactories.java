package JavaFx.part8_sample_todoList;

public class p7_CellFactories {
    /*
     * Cell Factories
     * ..............
     * We'll add an improvement to our UI by highlighting items in the list that have got a deadline of today's date
     *  - so that the user doesn't have to click on every item to find out what they need to prioritize
     *
     * There are no of ways to do that and we'll use what's called a cell factory
     *
     * Cell Factory
     * ............
     * Each item in the listview is displayed in a cell
     * We can customize how this cells will look by assigning a custom cell factory to the listview
     * Right now it's using the default cell factory, which sets the text to whatever items toString() returns
     * The background color of the cell alternates btwn white and a very light gray & we're going to change this & more
     *
     * When we create a cell factory, we need to provide the callback or the () that the listview will call each time it
     *  wants to paint one of its cells
     *
     * We'll do this in the initialize() in the main-windows controller
     * Add it as the last line
     * We'll call setFactory() on the listview and pass the callback
     *  - we get a call() that we need to update
     *      - define a cell variable of type ListCell<TodoItem> which takes an instance of ListCell class
     *      - we call/override updateItem()
     *  - return cell which will ultimately update
     *
     * In short
     * - we've set the cell factory by calling setCellFactory() on the listview cell
     * - we pass anonymous class that implements the callback interface
     * - this interface is part of the JavaFX API and the interface itself, has got 2 type parameters
     * - 1st parameter
     *   - is the type of the argument provided by the call() and that's a single () within the interface
     *   - in our case we'll pass it the listview control
     * - 2nd parameter
     *   - is the type that's going to be returned from the call()
     *   - in our case an instance of the class this cell is going to be returned
     *
     * - we have also used generics to be more specific about what type of obj(s) the listview and the list cell will
     *   contain, hence the use of to-do item
     * - we've also used an anonymous class to create our ListCell instances and we're overriding the updateItem() in the
     *   list cell class and this () is going to run whenever the listview wants to paint a single cell
     * - the callback() is going to return the cell instance we've created & the listview will use that instance to paint
     *    the cell
     * - as a result , this allows us to paint each/every cell based on the to-do item that it contains
     *
     * Next,
     * - let's add code that's going to color items with a deadline of today in red
     * - our anonymous class is extending the ListCell class which has the Labelled class in its parent hierarchy and controls
     *   that descend from the label class can display text and graphic
     * - the label control also has this class in its parent hierarchy
     * - many of the ()s that are calling when working with the label controls are actually in the Labelled class
     * - for example
     *      - setText(), setGraphic() , setTextField()
     *
     * So,
     * - in the updateItem(), we're going to first call the super () which is already there, & we don't need to change anything
     *   there since we want to keep most of the default appearance provided by the parent class
     * - now since we're providing the cell factory, we have to set the text by calling setText()
     * - we'll also check for empty cells, and
     *      - if a cell is empty, we need to set it's text to null
     *      - otherwise, we have to set the text to the to-do item short description
     * - we'll then use setTextFill() to set the color of the text to red if the item is due today
     *  - we'll compare the to-do item deadline with the value of the LocalDate.now() which returns today's date
     *
     * Next,
     * - create a to-do item with today's date and check if it will be colored in red
     *  - and truly it is colored in red
     *
     * Next,
     * - now that we've added setText() within our cell factory, we actually don't need the toString() which we added in
     *   the To-do item class & we're going to comment it out
     * - this is because the controller code that updates the cell method is handling that for us or the updateItem() from
     *   our cell factory
     * - it's working still, because we're using our custom cell factory that we've implemented
     *
     * Next,
     * Add a to-do item with a deadline for tomorrow ,and probably highlight that with a different color
     * - we need to update the updateItem() to show the items that are due tomorrow in a different color
     *  - we'll add an else if statement that compares the item deadline with LocalDate.now().plusDays(1)
     *
     * Next,
     * - there is a bug in our code
     * - let's add an item that was due last week
     * - it's not highlighted that it's overdue, we've assumed our users are super efficient and always complete their
     *    to-dos on time
     * - let's color our overdue items in red by changing the condition in the cell factory that checks for dates earlier
     *   than or equal to today's date
     *      - use isBefore() then chain LocalDate.now().plusDays(1)
     *      - rep a date that is equal to today's or prior to today's date should be flagged in red
     *
     *
     *
     *
     */
}
