package Java_modules.p3_Module_types;

public class Main {
    /*
     * Module Types
     * ............
     * Project Jigsaw defines 2 types of modules namely:
     *  - named modules
     *  - unnamed modules
     *
     * NAMED MODULES
     * .............
     *  - Each named module has a name
     *  - can be AUTOMATIC modules or NORMAL modules
     *  - are modules declared in the module-info.java file (the module descriptor file)
     *      - This is a requirement for named modules
     *  - all platform modules are named modules
     *
     *  - NORMAL modules
     *      - NORMAL modules don't officially exist in JDK 9, we just use the term normal for a named module that is not
     *        an AUTOMATIC module
     *      - a NORMAL module has a module descriptor file (module-info.java) while AUTOMATIC module does not have a module
     *        descriptor file
     *      - a NORMAL module is declared using a keyword module
     *      - a NORMAL module does not export any of its packages by default
     *      - a NORMAL module is divided into BASIC and OPEN modules
     *
     *          - BASIC modules
     *            - don't officially exist in JDK 9
     *            - we just use the term BASIC for a module that is not an OPEN module
     *            - they are neither automatic nor open
     *            - has the same characteristics as a normal module except it is not an open module
     *
     *          - OPEN modules
     *            - Many 3rd party libraries like Spring and Hibernate use reflection to access the internals of JDK at runtime
     *            - These libraries won't work unless we have an OPEN module
     *            - An OPEN module is defined using an open keyword
     *            - An OPEN module makes all packages inside the module accessible for deep reflection
     *            - The keyword open can be used to declare an open module or to declare specific packages as open
     *            - we'll look at this in action with a JavaFX application
     *               - we'll see how to open some packages for the FXML loader and Application class
     *
     *  - AUTOMATIC modules
     *      - an AUTOMATIC module is created after adding a JAR file to the module path
     *      - an AUTOMATIC module is not explicitly declared by the developer inside the module-descriptor file
     *          - it is automatically created when a JAR file is placed into the module path
     *      - it requires by default all platform modules, all our own modules and all other automatic modules
     *      - it exports all its packages by default
     *      - they are useful for 3rd party code
     *      - they are used for migrating existing application to Java 9
     *
     *
     * UNNAMED MODULES
     * ...............
     * - An UNNAMED module does not have a name & it is not declared
     * - It exports all of its packages
     * - It reads all modules in the JDK and on the module path
     * - Is made up all of all the JAR files from the class path
     *     - All these jar files form the unnamed module
     * - We'll discuss the difference between the module path and classpath later
     * - N/B
     *     - A named module can't require an unnamed module
     *
     *
     * Aggregator Modules
     * ..................
     * - These exist for convenience
     * - Usually, they don't have a code of their own, they just have a module descriptor
     * - They collect and export the contents of other modules
     *   - this is the reason why they're named as an aggregator
     * - For example
     *   - when a few modules depend on let's say 3 modules, we can create an aggregator module for those 3 modules
     *     and that way our modules can depend on that single module ( the aggregator module)
     * - In the JDK, there are several aggregator modules
     *      - e.g. java.se module
     *
     *
     * Module Path and Class Path
     * ..........................
     *
     * Module Path
     * ...........
     * - JDK 9 introduced a module path
     * - Module path can represent
     *   - A path to a sequence of folders that contain modules
     *   - A path to a modular JAR file
     *   - A path to a JMOD file (extended version of a JAR)
     *      - We'll discuss mor about JMOD files later in the course
     * - A module path is used by the compiler to find and resolve modules
     * - Every module from a module path needs to have a module declaration - module-info.java file
     *
     *
     * Class Path
     * ..........
     * A class path represents a sequence of JAR files
     *
     */
}
