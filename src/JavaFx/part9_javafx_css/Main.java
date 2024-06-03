package JavaFx.part9_javafx_css;

public class Main {
    /*
     * CSS With JavaFX
     * ...............
     * Styling controls with CSS
     *
     * Add 5 buttons
     *  - Button One
     *  - Button Two
     *  - Button Three
     *  - Button Four
     *  - Button Five
     *
     * Add inline styles
     *      - add style property
     *          style="-fx-background-color:blue;-fx-text-fil:white;";
     *
     *
     *
     * Add external style sheets
     *  - Add our css to a separate file
     *      - Add css file in the main > css > styles.css
     *
     * We use selectors to specify what we want to style
     *  - when we want to fina a style for all the buttons on our application,we use dot (.button) selector and this will
     *     style all instance of the Button class
     *  - we assign properties within a set of curly braces
     *  - Then separate the value with a colon ending that with a semi-colon
     *
     *
     * Associating css file with the fxml
     *  - we do this via the stylesheet property
     *      e.g. stylesheet="@../css/styles.css"
     *
     * Next
     *  - remove inline styling
     *
     * Note
     *  - It turns out that our application has been using CSS all along, JavaFX uses a default style sheet to style
     *    JavaFX applications
     *  - JavaFX uses themes and the default theme is the Medina theme
     *
     * Next,
     * Suppose we want Button 3 in our application to have a blue background
     *  - we have 2 options:
     *      - Add an inline style
     *          - Takes precedence over external styles (overrides the definition in the style sheet)
     *              - in a similar way to how the method overriding works in Java
     *      - Give it some sort of id and reference it in the css file
     *          - reference the id with (#buttonThree) in the css file
     *
     *  - defining css inline is not recommended and we'd rather go with the latter
     *
     *
     * We can also style controls with Java code by calling the appropriate ()s using the instances that we want to style
     *  - Though it's something we'd not do that often
     *
     * Next,
     * Change the appearance of our JavaFX application theme from "Medina" to "Caspian"
     * If we add it to the main.java file , the theme style sheet will apply to every window and dialogue, and control
     *  without specifically adding it to every fxml file that we create
     * We can override the theme style definitions, by adding stylesheets to an fxml file or using inline CSS
     *
     * Order of Precedence
     *  - inline CSS takes precedence over fxml external CSS, which in turn takes precedence over the application's
     *     theme stylesheet
     *  - In fact, when we change how our control looks by using CSS, or by assigning a property in the fxml, we've been
     *     overriding the default setting defined by the theme we're using for the application
     *
     * Setting theme
     * The setUserAgentStylesheet(), accepts a url as a parameter, and all we have to do is provide the URL of the
     *  stylesheet that contains all the style definitions for the theme
     * You can also write your own themes and use them within your applications as well
     *  - call setUserAgentStylesheet(STYLESHEET_CASPIAN) pass STYLESHEET_CASPIAN from the Main.java
     * Appearance
     *  - the buttons appear more rounded than they were in the MEDINA theme
     *
     * Next
     *  - Add a radio button
     * Appearance
     *  - there is a diff in the background color of the radio, MEDINA theme has a light gray while with the CASPIAN theme
     *    ,the background is more of a dark gray
     *
     */
}
