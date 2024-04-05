package concurrency.part15_fixed_thread_pool;

/*
 * Before adding constants add color field - private one
 * This color will consist of ANSI unicode characters
 *
 * Follow IntelliJ warning to add a constructor
 * Now we have a constructor that takes a string, the color and sets the enum's color field to that
 *
 * Adding enum constants with a constructor declared for each, that will define a String
 * This string when printed will make all the following output be that color
 * Each of these strings are ANSI representations of a special combination of char(s) that for most OS, will change
 *  the color of the console's text
 * We can reset the color as well
 *
 * Add an accessor () for color
 *
 * We are going to use the record way of creating an accessor and skip the get prefix
 *
 */

public enum ThreadColor {
    ANSI_RESET("\u001B[0m"),
    ANSI_BLACK("\u001B[30m"),
    ANSI_WHITE("\u001b[37m"),
    ANSI_BLUE("\u001b[34m"),
    ANSI_CYAN("\u001b[36m"),
    ANSI_GREEN("\u001b[32m"),
    ANSI_PURPLE("\u001b[35m"),
    ANSI_RED("\u001b[31m"),
    ANSI_YELLOW("\u001b[33m");



    private final String color;

    ThreadColor(String color) {
        this.color = color;
    }

    public String color() {
        return color;
    }
}
