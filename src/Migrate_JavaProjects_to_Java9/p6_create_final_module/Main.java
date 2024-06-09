package Migrate_JavaProjects_to_Java9.p6_create_final_module;

public class Main {
    /*
     * Creating the final module (UI)
     * ..............................
     *
     * steps
     *  1. Create the ui module
     *  2. Move the ui package to the new ui module
     *  3. Fix all module-info files ( all 3 may need to be changed)
     *  4. Note: The project needs to run the same as before without any exceptions
     *
     *
     * Task completed successfully !!
     *
     * ui- module
     *   requires javafx.graphics;
     *   requires javafx.fxml;
     *   requires javafx.controls;
     *
     * Also requires
     *  requires academy.learnprogramming.common;
     *  requires academy.learnprogramming.db;
     *   - export this in the "db" module
     *
     * Also opens itself for javafx.graphics,javafx.fxml to read
     *   opens academy.learnprogramming.ui to javafx.graphics,javafx.fxml;
     *
     *
     */
}
