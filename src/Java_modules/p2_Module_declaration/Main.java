package Java_modules.p2_Module_declaration;

public class Main {
    /*
     * Module Declarations and Statements
     * ..................................
     * Each module is defined using a module declaration using the newly introduced module keyword
     * The syntax is as following
     *   [open] module <moduleName> {
     *      <module-statement>;
     *      <module-statement>;
     *      <module-statement>;
     *      <module-statement>;
     *  }
     *
     * Where
     *  [open]
     *      - declares an open module and is optional
     *      - defines a module that exports all it's packages to be used by reflective access
     *
     *  <moduleName>
     *      - is the name of the defined module
     *      - is mandatory
     *      - must be unique - we can't have the 2 modules inside the same code base with the same name
     *      - it's a good practice to name modules with the same name as packages
     *
     * Inside the curly braces, we can have 0 or more module statements
     * Those statements can be:
     *  - exports statement
     *  - opens statement
     *  - requires statement
     *  - uses statement
     *  - provides statement
     *
     * requires statement
     *  - is used to specify the module that is required by the current module
     *      - if our module depends on some other module we have to specify it using the requires statement
     *  - we can use multiple requires statements depending on how many modules are needed in our module
     *
     * exports statement
     *  - is used to specify the packages that are exported by the current module
     *
     * provides statement
     *  - is used to specify the service implementations that the current module provides
     *
     * uses statement
     *  - is used to specify the service that the current module consumes
     *
     * Please also note that opens statement can't be used in open modules because all packages are open in an
     *  open module
     *
     *
     *
     */
}








