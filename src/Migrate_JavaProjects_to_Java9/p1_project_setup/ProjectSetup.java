package Migrate_JavaProjects_to_Java9.p1_project_setup;

public class ProjectSetup {
    /*
     * Project Test and Setup
     * ......................
     * - We'll look at how to setup an existing java project in intelliJ, for migration
     * - We'll also run the project with JDK 8 and JDK 9
     *
     * - Download music-ui-start.zip from the download section
     *    - Extract and paste to C:\JMC17 folder
     * - Launch IntelliJ
     *    - Click on Open existing project
     *    - navigate to project location C:\JMC17\music-ui-start
     * - Open Module Settings
     *    - Under Project
     *      - Select SDK - 1.8 (Java 8)
     *      - Language Level - Lambdas, type and annotations
     *    - Under SDK
     *      - Select 1.8 (Java 8)
     *    - Under Problems
     *      - This line appears "Library sqlite-jdbc-3.21.0 [Fix]
     *        - Click Fix and then click on "Add to dependencies" option
     *        - Press OK to proceed
     * Run the project
     *  - The project is running under jdk 8 with no problems
     *  - Displays the Artist their albums
     *
     *
     *
     *
     * Next,
     * Let's get this project running on JDK-9
     * Open Module Settings or goto File > Project Structure
     *  - Under Project menu
     *      - on SDK - Select Java 9
     *      - on Language Level - Select 9 - Modules, private methods in interfaces etc
     *  - Click OK to apply changes
     *
     * The first time you run this under JDK 9, you'll find that it may take some time to run
     * But it's run once, you'll find that it'll be significantly faster
     * Run the project
     *  - The project is running under JDK 9 with no problems
     *  - Displays the Artist their albums
     *
     * Summary
     * A project written in JDK version 8, works without any problems when using JDK 9
     * That's only the start though, let's start discussing how to split the project into multiple modules
     *
     *
     *
     */
}
