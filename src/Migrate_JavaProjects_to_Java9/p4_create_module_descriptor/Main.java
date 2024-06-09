package Migrate_JavaProjects_to_Java9.p4_create_module_descriptor;

public class Main {
    /*
     * Creating Module Descriptor
     * ..........................
     * - we can easily add module descriptor by clicking on the src folder
     *  module > src > New > module-info.java
     * - automatically creates 1 line code for us
     *  module academy.learnprogramming.common {
     *
     *  }
     * - now that we've added that, if we do nothing else, this is going to result in a lot of compilation errors
     *   - This is because we've now got 1 java module "academy.learnprogramming.common" defined in the module descriptor
     *   - Essentially and obviously, all these errors are related to modules, because clearly before we added the module
     *     descriptor file, we were able to run the app
     *   - The problem is now we're using classes from JDK modules & we have to require those modules, go through and add
     *      that keyword to get things to work
     *
     * - for example, one of the errors we're getting is shown below
     *   - java: package javafx.beans.property is not visible
     *         (package javafx.beans.property is declared in module javafx.base,but module
     *              academy.learnprogramming.common does not read it)
     * - the above means we're using the package from javafx.base module, but we're not actually reading that module
     * - to read a module or to use a module in our modules, we have to declare it with the requires statement
     *
     * - we could add the "requires" statement manually, or we can get intelliJ to help us with these errors
     *
     * Album class
     * Let's start with the Album class, and looking at the imports, we get the first one
     *  javafx.beans.property.SimpleIntegerProperty
     * And since this class is an inside module, we can't use it until we require the module
     * So, highlight and click add to module-info.java
     *
     * Once we do that, what happens is that the require statement is added for javafx.base
     * Now the Artist and the Album class no longer have any compilation errors, since we've now added or the IntelliJ has
     *  added the requires statement for us
     *
     *
     * Datasource class
     * Here, we basically need classes from the java.sql package, therefore we need another require statement in the
     *  module-info file
     *
     * Do the same for Controller.java for the following modules
     *   import javafx.concurrent.Task;
     *   import javafx.fxml.FXML;
     *   import javafx.scene.control.ProgressBar;
     *   import javafx.scene.control.TableView;
     *
     * Running the project
     *  - we get below error
     *    Caused by: java.lang.RuntimeException:
     *      Unable to construct Application instance: class academy.learnprogramming.ui.Main
     *    Caused by: java.lang.IllegalAccessException: class com.sun.javafx.application.LauncherImpl
     *      (in module javafx.graphics) cannot access class academy.learnprogramming.ui.Main
     *      (in module academy.learnprogramming.common) because module academy.learnprogramming.common
     *       does not export academy.learnprogramming.ui to module javafx.graphics
	 *      at java.base/jdk.internal.reflect.Reflection.newIllegalAccessException(Reflection.java:361)
	 *
	 * - This means that the JavaFX Launcher can't launch our application because we didn't export our package to the
	 *    javafx.graphics module
	 * - If we don't export any package classes in the module, they can't be accessed
	 * - To export a package, we need to use the export statement
	 * - export statement is used to specify the package that are exported by the current module
	 *
	 * Solution,
	 * Add
	 *  exports academy.learnprogramming.ui to javafx.graphics;
	 * To the module-info.java
	 *
	 * So by exporting our package to the javafx.graphics module, javafx launcher should be able to access our package
	 *  and therefore run the application
	 *
	 * Next,
	 * We get an error this time
	 *  academy.learnprogramming.common does not export academy.learnprogramming.ui to module javafx.fxml
	 * the solution is to add a comma and add javafx.fxml to the line above
	 *  exports academy.learnprogramming.ui to javafx.graphics , javafx.fxml ;
	 *
	 * The reason we're adding a comma in the 2nd module here is that , using another export statement here for the same
	 *  package in this case "academy.learnprogramming.ui" will lead to a compilation error
	 * So if we do need to export to multiple modules, we'll use a comma and then the module name to achieve that
	 *
	 * Running this again
	 * We get an error this time
	 *  Caused by: java.lang.reflect.InaccessibleObjectException:
	 *  Unable to make field private javafx.scene.control.TableView academy.learnprogramming.ui.Controller.artistTable accessible:
	 *  module academy.learnprogramming.common does not "opens academy.learnprogramming.ui" to module javafx.fxml
     * And the problem here is that we have to open the package UI to javafx.fxml to that module
     *  - The reason for that is because fxml loader needs to set fields annotated with the @FXML annotation
     *  - And without opening the package, the fxml loader can't load the fxml and inject or set the controller fields
     *
     * Therefore we need to allow that by actually typing the hint it's giving us as below
     *  opens academy.learnprogramming.ui to javafx.fxml
     *
     * Next
     * Add opens academy.learnprogramming.common to module javafx.base
     *
     * Running this now works
     *
     * We can see that the process of converting to a module is fairly intuitive if you take the time to read the
     *  exceptions
     *
     * Currently, now we got our entire base inside a single module now
     * Our current module info has got some redundant line though
     *  - this is because some modules that we require depend on other modules, but we'll explore this later on
     *
     * Next, we'll start by separating the single module into multiple modules
     *
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
