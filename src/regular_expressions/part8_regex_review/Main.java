package regular_expressions.part8_regex_review;

import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {

        /* Create a text block with a US phone number on each line
         * But with various formats
         * Any one of these could be a valid format for a 10-digit US phone no
         * Create a patter shown on the slide
         *
         */
        String phoneList = """
                (800) 123-4567
                (800)123-4567
                (800) 123 4567
                800-123-4567
                800 123-4567
                800 123 4567
                8001234567
                """;
        /*
         * Below pattern starts with
         * \\( 2
         *  - Parentheses are used in regex as a meta character identifying groups in most cases
         *  - In this case, we want a literal opening parenthesis because we want to match
         *    parentheses character in my string , So we need to escape it that way
         * [0-9]{3}
         *  - [0-9] is a character class that defines a range, numeric digits, 0 through 9 because we
         *    only want numbers
         *  - This {3} is a quantifier that says I want 3 digits here after the opening parentheses
         * \\)
         *  - We need to escape closing parentheses for it to be used literally
         *  (space)
         *  - We have included a space character after that, which is a literal space, so it won't include all
         * white spaces - won't match on a tab for instance
         * [0-9]{3}
         *  - [0-9]{3} - A character class in [] for the full range of digits with a quantifier of 3 digits
         * - dash
         *  Does not need to be escaped in Java
         *  - [0-9]{4} - A character class in [] for the full range of digits with a quantifier of 4 digits
         *
         * Now that we have our regex, let's check our string for all matches
         *
         *
         *
         */

        Pattern phonePattern = Pattern.compile("\\([0-9]{3}\\) [0-9]{3}-[0-9]{4}");
    }
}
