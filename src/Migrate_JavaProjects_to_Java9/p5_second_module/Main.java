package Migrate_JavaProjects_to_Java9.p5_second_module;

public class Main {
    /*
     * Creating the 2nd Module (Database)
     * ..................................
     * Create the db module
     * Then migrate the code to that db module - which is the data source class in this case
     * Ensure the application still works
     *
     * Steps
     * .....
     * - Create a new module
     *      File > New > Module > modulename
     * - Create a module descriptor
     *      src > New > module-info.java
     * - Move DB package to the new module
     *   - first create "academy.learnprogramming" package
     *   - drag and drop or cut-and-paste db package from the common module
     * - And now the package db has been removed from the commons module and it's now showing in our db module
     *
     * Next
     * Let's open the DataSource class
     * There are compilation errors we'll have to fix
     *  - for java.sql and some other as well
     * Since the db module uses the class Album and Artist , but also some classes from the sql module, we need to add
     *  the required statements into the module-info.java
     *
     * So let's add the one for sql by highlighting it and click add dependency to module-info file
     * However, we can't add below to the module descriptor
     *  import academy.learnprogramming.common.Album;
     *  import academy.learnprogramming.common.Artist;
     * This is because the module common is not in the dependencies in the intelliJ, and we have to add the module as a
     *  dependency
     * We can do that either in code
     * Add below to module-info.java
     *  - requires academy.learnprogramming.common;
     * Then add it from the Datasoure.java by highlighting and adding it as a dependency
     *
     * But since only the db module requires sqlite, we're going to remove the sqlite from "common" module and add it to
     *  "db" module
     * And also we need to add the common module to the DB module dependencies because the db module requires both Artist and Album class
     *
     * The easiest way to do that is through the project structure
     *  - so add sqlite to "db" module
     *
     * Next
     *  - remove sqlite from the "common" module
     *
     * Next
     *  - add a dependency for sqlite jdbc which is an automatic java 9 module
     *    - remember that automatic modules are modules that are created when the jar is added to the module path
     *    - we had to add sqlite dependency to the "db" module which we just did
     *
     * Previously, with our common module, we did not need to add the require statement for the sqlite jdbc module and that's
     *  because we only had 1 module
     * But now we have to add the requires statement to the module descriptor file for our db module
     *  - e.g. requires sqlite.jdbc;
     * That's the general rule for adding a jar file & you want to add an automatic object as a requirement for your module
     *
     * Next,
     * We still got some compilation errors in our Datasource class
     * If we hover, it tells us that we need to export the common module from the common module so that the db module can
     *  use the Artist and Album classes
     * And without exporting packages, we can't get access to classes from another module
     *
     * So we need to open the module-info.java, the descriptor file from the common module and add an export statement
     *  for that as below
     *   exports academy.learnprogramming.common;
     *
     * So,
     * There are 2 ways to export
     *   exports academy.learnprogramming.common;
     *      - exporting without specifying the mmodule we are exporting to
     *   exports academy.learnprogramming.ui to javafx.graphics , javafx.fxml ;
     *      - exporting to a specific package using the to keyword
     * Back to the Datasource and the errors have now disappeared because we've got the correct exports statements in the
     *  common module descriptor file
     *
     * Another problem here is that the controller and the Main class also use the Datasource class
     *  - since we got some compilation errors on Datasource
     *  - the issue here is that the import for the Datasource can't be resolved
     * HOWEVER,
     *  - This is not as easy as you might think to solve, this is because adding the db module as a dependency to the
     *    common module would result in a cyclic dependency
     *
     * In other words, we have 2 modules where both depend on each other
     * Cyclic dependencies are not a good practice and Java 9 doesn't actually allow the cyclic module dependencies
     *
     * One way to solve this issue is to use TRANSITIVE DEPENDENCIES (cover in the next section)
     *  - But the goal now would be to move the ui package to the ui module
     * And the process of doing that should resolve some of these other problems
     *
     * Next,
     * We'll move the UI package currently in the commons module to its own new ui module
     *
     */
}
