package JavaFx.part1_jdk11_global_lib_conf;

public class Main {
    /*
     * JDK11 Global Library Configuration
     * ..................................
     * Java applications can have graphical user interfaces that contains buttons, lists, check boxes, menus, toolbars
     *  & many other controls that allow users to interact with rich client applications
     *
     * JavaFX
     * .......
     * JavaFx is a set of API(s) that we use to build user interfaces in java
     * It's essentially a set of packages, and it's the successor to swing which was java's UI toolkit for many years
     * You can use JavaFx to develop UI for desktop applications, internet applications and mobile devices
     *
     * With the release of JDK 11, JavaFx is no longer part of the core JDK
     * That means we need to download the JavaFx jar files and configure them separately to get them working
     *
     * Let's look at the steps to set up the JavaFx package to work with the JDK 11
     *  - Download the file from "https://gluonhq.com/products/javafx/"
     *      - Download the window version of the JDK - version 11
     *  - Create a lib folder in the documents folder and paste the download
     *      - Extract the contents to this folder
     *  - Next, we need to configure a global library so that we can automatically use javafx in any projects we create
     *      > Project
     *          > Open Module Settings
     *              > Global Libraries
     *                  > Click +
     *                      > Select Java
     *                          > goTo C:\Users\ALEX\Documents\lib
     *                              > select all the javaFx jar files from the lib folder
     *                                  > Change the name to JavaFx-11
     *          > Click Apply
     *
     */
}
