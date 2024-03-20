package regular_expressions.part1_intro_to_regex;

public class Main {

    public static void main(String[] args) {

        /*
         * Regular expression is simply text
         * May contain characters or character combinations that have special meaning called metacharacters
         * These combinations are interpreted by a regular expression - pattern processor
         *
         * .formatted - was introduced in JDK-15
         * There is a static method on String class called format() which calls the same code under the hood
         *
         */
        String helloWorld = "%s %s".formatted("Hello","World");
        String helloPeople = String.format("%s %s","Hello","World");

        System.out.println("Using string's formatted method: "+helloWorld);
        System.out.println("Using String.format(): "+helloPeople);

        /*
         * Invoking formatContains
         */
        String helloContains = Main.formatContains("%s %s","Hello","Contains");
        System.out.println("Using Main.format: "+helloContains);

        /*
         * Invoking formatMatches
         */
        String helloMatches = Main.formatMatches("%s %s","Hello","Matches");
        System.out.println("Using Main.format: "+helloMatches);


    }

    /*
     * Write our own specifier formatting code
     * Using contains()
     */
    private static String formatContains(String regexp, String...args){
        int index = 0;
        while(regexp.contains("%s")){
            regexp = regexp.replaceFirst("%s",args[index++]);
        }
        return regexp;
    }

    /*
     * Using matches()
     * The method doesn't work at all - returns the original string back
     * Matches() tries to match the entire string to the expression passed
     * Since we are not using any regular expression character combinations, so Matches("%s") is just testing
     * whether "%s %s" is equal to just %s and it's not
     * We can make this work by including characters before and after the character combination of %s in this pattern
     * Using a period or dot is a way to match on any character, except a new line, so you can think of it as a wildcard - ".%s."
     *
     * Running after updating  "%s" to ".%s." still does not work though - is because a dot or period by itself will match one and only one character
     *
     * To match all the characters before and after, we need to say how many to match
     *
     * How do we do that though, when this may vary from 0 to unknown amount? - We can use another special character , an asterisk - which is
     * called a quantifier - Works!
     *
     * You can think of .* combination as matching any and all characters, including no characters at all
     *
     * In this case, ANY & ALL characters UP TO the first literal combination of %s, then ANY & ALL characters AFTER THAT, describes a match for
     * any string that has a %s in it
     *
     */

    private static String formatMatches(String regexp, String...args){
        int index = 0;
        while(regexp.matches(".*%s.*")){
            regexp = regexp.replaceFirst("%s",args[index++]);
        }
        return regexp;
    }
}
