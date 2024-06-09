package Migrate_JavaProjects_to_Java9.p3_first_module;

public class Main {
    /*
     * Creating our first module (common)
     * ..................................
     * Let's create the common module
     * Then  we'll start by moving the necessary files that are currently elsewhere in our java project into this module
     * Then we'll work on our module descriptor
     *
     * First
     * - we'll have to create the module in the root folder
     * - Click File > New > Module
     *      - Select Java (if not already selected)
     *      - Ensure Module SDK is set to 9
     *      - Click Next
     *      - Enter Module name
     *          academy.learnprogramming.common
     *
     * Next
     * - will move all classes into common module initially, but in their relevant packages
     * - Then will later move those packages to the other modules as we create them in future videos
     * - In other words, it's a 2-step process where we're putting everything into common module temporarily, but with
     *   the right packaging and then later we'll actually move them as we create the other modules
     *
     * Next
     * - in the "academy.learnprogramming.common\src" folder
     *     - create 1st package named "academy.learnprogramming.common"
     *     - create 2nd package named "academy.learnprogramming.db"
     *     - create 3rd package named "academy.learnprogramming.ui"
     *
     * Next
     * - move the code from the old src folder into each relevant package
     * - first,start with classes that needs to go to common package. i.e. Artist and Album
     *   - Highlight both Artist and Album classes, drag and drop into "academy.learnprogramming.common" package
     * - second, move the Datasource file to "academy.learnprogramming.db" package
     * - lastly, move Controller.java,Main and main.fxml to "academy.learnprogramming.ui" package
     *
     * Next
     * - select the old src folder and delete it since we've moved everything to its relevant package in the
     *   "academy.learnprogramming.common" module
     *
     * Now we've got all our code sitting in the common module at this stage
     *
     * Run the project
     * We get the errors because it's looking at the old imports "sample.model." whatever name the class is
     *  - we need to fix that in order to get this to compile and be able to work
     *  - need to import the classes manually, if your intelliJ is not configured to auto-import the classes
     *   - first in Controller.java
     *   - second in Main.java
     *
     * Run the project again after adding the imports
     * We get below error
     *  - No suitable driver found for jdbc:sqlite:music.db
     * This means we can't connect to the database, because the module can't find the sqlite driver to connect to the db
     *  - this is because we didn't add it add this drive , this jar file , our driver for the SQLite db, we never added
     *    it as a module dependency
     * It is added as a project dependency, but now the root project doesn't have any code and for that reason the
     *  dependency no longer does anything and it's not actually finding it here
     * We need to got to our project structure again and make a change
     * Goto
     *  - File > Project Structure > Modules > select our new module (which does not have a sql driver)
     * However, when we click on our project, it has the sql driver but our module doesn't have
     *
     * N/B
     * - when using modules, we need to specify/add the sqlite-jdbc-3.21.0 driver to our module
     * - remove it from our main music-ui-start project and add it in the common module that we have created
     *
     * Adding the Jar file
     *  - click on the module
     *  - Click Plus
     *  - Click Add Jar as Dependencies
     *  - Navigate to project folder > music-start-ui > libs > Select sqlite-jdbc-3.21.0.jar file
     *  - click OK
     *
     * And now we have assign that to the module dependency & the project music-start-ui no longer has a dependency for it
     *
     * Running the project
     *  - We still get the error , because we had set this as a relative path, we need to redirect below
     *      FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
     *  - to our module as follows
     *       FXMLLoader loader = new FXMLLoader(getClass().getResource("/academy/learnprogramming/ui/main.fxml"));
     *  - so that the path is found at the start or at the root level
     *
     * Running the project
     *  - This now works & we're able to select Artist and display their albums
     *
     *
     *
     *
     *
     *
     *
     *
     *
     */
}
