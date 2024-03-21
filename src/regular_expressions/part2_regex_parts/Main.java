package regular_expressions.part2_regex_parts;

public class Main {

    public static void main(String[] args) {
        /*
         * A regular expression can be made up of the following combinations
         *  - Literals/Character classes -i.e. [abc] [a-g] [A-Z] [0-9] [^abc] \d \s \w
         *  - Quantifiers i.e * + ?
         *  - Boundary matchers or anchors - ^ $ \b
         *  - Groups - ()
         */

        /* Initialize a test String with a sentence to test a variety of things with
         * Set up a replacement string which will be used to replace each matched expression
         * Define an array of patterns to match
         * Iterate through each pattern in the array and use replaceFirst() to replace the
         * first occurrence of the pattern with the replacement string (-)
         *
         *
         * Character literals are case-sensitive and are hopefully pretty self-explanatory
         * Matching exactly the character specified, and matching on the same case for this method
         */

        String testString = "Anyone can learn abc's , 123's, and any regular expression";
        String replacement = "(-)";

        String[] patterns = {
                "abc","123","A"
        };

        for (String pattern: patterns ) {
            String output = testString.replaceFirst(pattern,replacement);
            System.out.println("Pattern: "+pattern+ " => "+output);
        }

        /*
         * Using square brackets
         * Does not replace abc in the first output or 123 in the second output
         * [] - in a regex define a special kind of character class
         * Instead of all the characters in the same sequence making a match,
         * this patterns means an occurrence of just one of the characters within the
         * brackets, makes a match
         *
         */

        System.out.println("_".repeat(50));
        String[] patternsBrackets = { "[abc]","[123]","[A]" };

        for (String pattern: patternsBrackets ) {
            String output = testString.replaceFirst(pattern,replacement);
            System.out.println("Pattern: "+pattern+ " => "+output);
        }


        /*
         * Using a metacharacter or, which is a single piped character (|)
         * Works the same as square brackets
         */

        System.out.println("_".repeat(50));
        String[] patternsPiped = { "a|b|c" };

        for (String pattern: patternsPiped ) {
            String output = testString.replaceFirst(pattern,replacement);
            System.out.println("Pattern: "+pattern+ " => "+output);
        }

        /*
         * Using a combination of classes
         * Checks for either the combination of ab or bc and replace the first occurrence
         */
        System.out.println("_".repeat(50));
        String[] patternsCombined = { "ab|bc" };

        for (String pattern: patternsCombined ) {
            String output = testString.replaceFirst(pattern,replacement);
            System.out.println("Pattern: "+pattern+ " => "+output);
        }

        /*
         * Predefined character classes
         * [a-z] - matches any alphabetical letter
         * [0-9] - matches any single digit
         * [A-Z] - matches any single capital letter
         *
         * All replaces first occurrence
         */
        System.out.println("_".repeat(50));
        String[] patternsPredefined = {"[a-z]","[0-9]","[A-Z]"};

        for (String pattern: patternsPredefined ) {
            String output = testString.replaceFirst(pattern,replacement);
            System.out.println("Pattern: "+pattern+ " => "+output);
        }

        /*
         * Combining Predefined character classes
         * [a-zA-Z] - first pattern replaces the first letter regardless of case
         * Serves as one way to match any alphabetical character for the English language regardless of case
         *
         * Replaces first occurrence of any capital/lower case letter
         */
        String[] patternsPredefinedCombined = {"[a-zA-Z]"};

        for (String pattern: patternsPredefinedCombined ) {
            String output = testString.replaceFirst(pattern,replacement);
            System.out.println("Pattern: "+pattern+ " => "+output);
        }

        /*
         * Add quantifiers to these patterns (*)
         * [a-zA-Z]* - Replaced Anyone until the space
         * [0-9]* - Did not replace 123 but added (-) to the start of the string - Matches characters that you specify
         * [A-Z]* - Removes first occurrence of any Capital letter - In this case  A
         *
         */
        System.out.println("_".repeat(50));
        String[] patternsQuantifiers = {"[a-zA-Z]*","[0-9]*","[A-Z]*"};

        for (String pattern: patternsQuantifiers ) {
            String output = testString.replaceFirst(pattern,replacement);
            System.out.println("Pattern: "+pattern+ " => "+output);
        }

        /*
         * Using plus quantifier (+)
         * [0-9]+  Matches one-to-many occurrences & replaces 123 completely
         *
         */
        System.out.println("_".repeat(50));
        String[] plusQuantifier = {"[0-9]+"};

        for (String pattern: plusQuantifier ) {
            String output = testString.replaceFirst(pattern,replacement);
            System.out.println("Pattern: "+pattern+ " => "+output);
        }

        /*
         * Using plus quantifier (+)
         * [0-9]{2}  Matches an exact no of digits specified
         * First 2 digits of 123 are replaced to (-)3
         *
         */
        System.out.println("_".repeat(50));
        String[] plusExactQuantifier = {"[0-9]{2}"};

        for (String pattern: plusExactQuantifier ) {
            String output = testString.replaceFirst(pattern,replacement);
            System.out.println("Pattern: "+pattern+ " => "+output);
        }

        /*
         * Boundary matchers
         * There are 3 common boundary matchers
         *  ^ - "^." - Matches first character in a string
         *  $ - ".$" - Matches last character in a string
         *  \b - "\\b" - Matches first word in a string - whenever the character has a backslash - need to escape
         *  the backslash with a 2nd backslash in the Java String literal
         *
         * Example below
         * [a-zA-Z]*$ - Replaces expression with (-) which is the last character in that string
         * ^[a-zA-Z]{3} - Replaces the first 3 characters that matches upper/lower case alphabetic letters
         * "[aA]ny\\b" - Matches the word any and replaces with (-)
         */
        System.out.println("_".repeat(50));
        String[] patternsBoundaryMatchers= {"[a-zA-Z]*$","^[a-zA-Z]{3}","[aA]ny\\b"};

        for (String pattern: patternsBoundaryMatchers ) {
            String output = testString.replaceFirst(pattern,replacement);
            System.out.println("Pattern: "+pattern+ " => "+output);
        }


    }
}
