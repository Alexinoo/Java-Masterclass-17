package Java_modules.p1_Intro_to_modules;

public class Main {
    /*
     * Introduction to Modules
     * .......................
     * - One of the most important and exciting features of JDK-9 is the module system, which was developed under a project
     *  codename of Jigsaw.
     * - Java 9 introduced a new program component known as module
     *      - You can think of a Java application as a collection of modules
     * - The module system was designed to have a reliable configuration, strong encapsulation and a modular JDK/JRE
     * - The purpose is to solve the problems typically involved with developing and deploying Java applications before
     *   Java 9
     * - These modularity features in Java 9 are collectively known as the Java Platform Module System or JPMS
     *
     * What is a Module ?
     * ..................
     * A module is a named collection of data and code
     * Modules can contain Java code that is organized as a set of packages
     *  - Each package can contain classes, interfaces and so on
     * Think of a module as a container of packages
     * Every module needs to have some configuration
     *  - name -> unique name of the module
     *  - inputs -> what the module needs to use and what is required for the module to be compiled and run
     *  - outputs -> what the module outputs or exports to other modules
     * By doing this way we don't need to specify every package that your module needs, only the name of module you
     *  depend on
     *
     * Module Introduction
     * ...................
     * The Java SE 9 Platform is divided into a set of modules that are known as platform modules
     *  - This provides a scalable Java Runtime.
     *  - Standard modules have names prefixed with Java
     *  - e.g. java.base , java.sql
     * As an example, in this course we have used JavaFX to create graphical UI
     * The JavaFX module names are prefixed with javafx
     *
     * Every module comes with a module-descriptor file that describes the module and contains metadata about the module
     * A module descriptor file is always located directly at the module root folder, and always has the name
     *  module-info.java
     * For Example
     *      module academy.learnprogramming.common {
     *       }
     * There is a new keyword module and this is followed by the module name and curly braces.
     * Inside the curly braces, you can optionally add metadata about the module
     *  - In other words inputs and outputs
     * A module declaration introduces a module name that can be used in other module declarations to express relationships
     *  between modules
     * A module name consists of one or more java identifiers separated by "." tokens
     * There are 2 types of modules
     *  - normal modules
     *  - open modules
     * The kind of module determines access to the module's types and the members of those types, for code outside the
     *  module
     *
     * normal module
     *  - A normal module without the open modifier, grants access at compile time and run time to types in only those
     *    packages which are explicitly exported
     *
     * open module
     *  - An open module, with the open modifier, grants access at compile time to types  in only those packages that are
     *    explicitly exported, but grants access at runtime to types in all its packages as if all packages had been
     *    exported
     *
     *
     * Before java 9, we had a source directory with packages that contained java files
     * With Java 9, there is another directory "module root directory" which will contain module-info.java file as well as
     *  package dir(s)
     * With Java 9 modular programming, we don't have to build applications as 1 big java project
     *  - we can create modular units that work together to form an application
     * Modules are ideally components that are re-usable that solve specific requirements for that app
     * You can think of modules as high-level packages, but keep in kind that they're actually not packages
     *
     *
     * Summary
     * .......
     * - Project Jigsaw has the following primary goals:
     *  1. Scalable platform
     *      - The ability to scale the platform down to smaller computing devices is achieved by moving from a monolithic
     *        runtime
     *  2. Security and Maintainability
     *      - To make a more maintainable platform code base that has a better organization, and has its internal API
     *        hidden, so we have better modular interfaces to improve platform security
     *  3. Improved Application Performance
     *      - A platform that is smaller with only the necessary runtimes, resulting in faster performance
     *  4. Easier developer experience
     *      - As a result of the Module system and the modular platform combination to make it easier for developers to
     *        create applications and libraries
     *
     */
}
