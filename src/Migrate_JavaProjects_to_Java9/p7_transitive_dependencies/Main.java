package Migrate_JavaProjects_to_Java9.p7_transitive_dependencies;

public class Main {
    /*
     * Transitive Dependencies
     * .......................
     * Let's find out about transitive dependencies and the module graph
     * Then we'll update our project to use transitive dependencies
     *
     * What is a transitive dependency ?
     * ................................
     * Let's say we have modules A, B and C
     * When module A requires module B, module A can read public and protected types exported in module B
     * If module B also requires module C, module B can have methods that return types exported in module C
     *
     * In our project, we have our 3 modules as common , db and ui
     * Module "db" requires/reads common , while module "ui" reads both db and common
     * Module "db" or actually the Datasource class has some methods that return types from module "common"
     *
     * So every module that uses Datasource class will require common as well
     *  - This is where transitive dependencies can help us
     *
     * Instead of adding requires statement to ui, we can use keyword transitive with requires statement
     * That way, every module that reads "db" module will automatically be able to read common module
     *
     *
     * Module Graph
     * ............
     * The result of java modules depending on each other , is that we can now draw a complete graph of dependencies
     *  with the modules as nodes and the relationships between nodes as module dependencies
     * This kind of image is called a module graph
     * Keep in mind that this graph only shows our modules, not other modules that we're using like javafx.base
     *
     * Well, so what does this has to do with dependencies
     *  - We can see how our UI module depends on both the COMMON and DB modules
     *  - And remember, that the DataSource class returns some types from the COMMON module and the UI module is using
     *    the Datasource class to get results from the database,
     *  - So instead of requiring the COMMON module, in the UI module, we can declare it as a transitive dependency in the
     *     DB module
     *  - So that way, whatever modules use the DB module, again we'll also have access to the COMMON module
     *
     * That way, all modules that require the DB module now have access to packages that are exported by the COMMON module
     *  - And we then don't have to specify the requires COMMON module in the UI module, because it now reads it
     *    automatically
     *
     *
     * Next
     * Open module-info for from the DB module
     * Update from
     *  requires academy.learnprogramming.common;
     * TO
     *  requires transitive academy.learnprogramming.common;
     *
     * Now the UI module can have access to all exported packages by the COMMON module.
     *  - In other words, we don't need to depend on the COMMON module anymore in the UI module
     *
     * Next
     * Open module-info for from the UI module
     * Remove below line since we have direct access to COMMON module via DB module
     *  requires academy.learnprogramming.common;
     *
     * This means we should be able to run our application and get the desired results
     *
     * With transitive dependencies
     *  - we don't need to require every single module
     *
     * So what other require statements can be removed ?
     * .................................................
     *
     * Right-control click on javafx.base
     *  - we can see there, what packages are exported by default
     *  - for example
     *      - we see that it requires java.desktop
     *
     * If we Right-control click on javafx.fxml, we can see that it requires transitive javafx.base with the transitive
     *  keyword
     * So, this now means that we don't have to depend on the javafx.base module because we are already depending on
     *  the javafx.fxml module which is using the transitive keyword to require the javafx.base module
     *
     *
     * If we Right-control click on javafx.controls, it's got 2 transitive statements there , in other words, if we
     *  require javafx.controls, we can use the types from both javafx.base and javafx.graphics
     *
     * This means we don't need some of the statements because they're being basically used by a transitive statement
     *  elsewhere or associated elsewhere
     *
     * Remove javafx.base and javafx.graphics
     *
     * So through transitive dependencies, the UI module can now read and use COMMON modules, because our DB module
     *  declared the COMMON module as a transitive dependency
     *
     * Running this one more
     *  - working nicely and the application is running without errors
     *
     * We're able to display the artist and the very songs they've got for albums
     *
     *
     *
     */
}
