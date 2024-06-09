package Migrate_JavaProjects_to_Java9.p2_project_structure;

public class Main {
    /*
     * Structure the new project
     * .........................
     * We'll start with a mini challenge
     * Then we'll discuss the project structure with modules
     * Finally, we'll decide how we'll split the project into multiple modules
     *
     * Structuring Project - Challenge
     * ...............................
     * Think of how you would split this project into multiple videos, let's say 3 modules
     * Just think about the separation, you don't have to write any code.
     *  - Can you break down the project into 3 distinct areas
     * You can write these areas (module names) on paper
     *
     *
     * Solution
     * ........
     * We'll split the project to 3 modules
     *  1. academy.learnprogramming.common
     *      - for the common code, anything else that didn't fit in those 2 areas
     *  2. academy.learnprogramming.db
     *      - for the User Interface
     *  3. academy.learnprogramming.ui
     *      - for the database
     *
     * All prefixed with academy.learnprogramming to make a unique module name for each
     *
     * Next,
     * We'll start by creating this modules
     *  - First
     *    - we'll create academy.learnprogramming.common module
     *      - we call it common because it contains, or it does contain or will contain common classes for the other 2 modules
     *      - another good name instead of common might be core
     *      - the classes that'll be in the common module will be the Album and the Artist
     *      - These classes will read data and display in the UI
     *  - Second
     *    - we'll create academy.learnprogramming.db module
     *      - will only have 1 class, which is the data source that we use to get data from the database
     *
     * As you can see, the common module doesn't know anything about the database module
     *  - It doesn't care how the artist or the album will be saved, whether it will be on database, or xml, or even a file
     * However, the database module, requires the common module to be able to convert the result from the database to an
     *  artist or an album
     *  - It goes one way and not the other
     *
     *  - Lastly
     *     - we'll create academy.learnprogramming.ui module
     *      - will contain the Main class and the controller as well as the javafx fxml file
     *      - this module requires the database module, because the results from the database are displayed on the UI
     *      - keep in mind also that modules can contain source code, but also resources like images and in our case an
     *        fxml file
     */
}
