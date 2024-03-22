package regular_expressions.part8_regex_review;

import java.util.regex.Matcher;
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
         * Create a matcher instance from phonePattern's matcher() - Pass phone list text block
         *
         * Use results, printing the value of each MatchResult's group method
         * If we use the group() without an index, this will print the entire matching subsequence
         * If we run it, only the phone no's that matched this expression will get printed
         *  i.e. (800) 123-4567
         * The regex pattern is pretty restrictive and maybe that's a good thing. However, we want to make it more
         * flexible
         *
         * First, let's make the first opening parentheses optional
         * You remember the quantifier * will match on zero or more characters, so we can include an asterisk after
         * the opening parentheses
         * Do the same thing after the closing parentheses
         *      i.e. "\\(*[0-9]{3}\\)* [0-9]{3}-[0-9]{4}");
         * This will make the parentheses around the 1st 3-digits optional
         *
         * Running with the updated pattern results to
            (800) 123-4567
             800 123-4567
         * The way the above expression is written, a space is required, but we are going to change this
         * Create a character class that will consist of the closing parentheses and a space, but instead of a space
         * character, will instead use the character class for any white space
         *
         * \\s is a character class that matches any white space
         *  i.e. "\\(*[0-9]{3}[)\\s]*[0-9]{3}-[0-9]{4}");
         *
         * [)\\s]* - This character class with an asterisk quantifier will match either a closing parentheses or a space
         * , zero or many times
         *
         * Running with the updated pattern results to
            (800) 123-4567
            (800)123-4567
            800 123-4567

         * We can actually remove one of the backslashes before the s in that character class
         *   i.e. "\\(*[0-9]{3}[)\\s]*[0-9]{3}-[0-9]{4}");
         * However, intelliJ gives a warning that use of escape sequence \s is discouraged.. therefore revert back
         *
         * Next, let's add a dash in the character class we just created, so that a dash could alternately follow the first
         * 3 numbers
         *  i.e. "\\(*[0-9]{3}[)\\s-]*[0-9]{3}-[0-9]{4}");
         *
         * Running with the updated pattern above results to
            (800) 123-4567
            (800)123-4567
            800-123-4567
            800 123-4567

         * [\s-]*
         *  - Added above after the next 3 digits - include space/no space or a dash and an * quantifier
         *  i.e. "\\(*[0-9]{3}[)\\s-]*[0-9]{3}[\\s-]*[0-9]{4}");
         *
         * Running this with the updated pattern above prints all th phone numbers listed in the text block
            (800) 123-4567
            (800)123-4567
            (800) 123 4567
            800-123-4567
            800 123-4567
            800 123 4567
            8001234567

         * This "\\(*[0-9]{3}[)\\s-]*[0-9]{3}[\\s-]*[0-9]{4}") is a much more flexible regex
         * It could allow users some flexibility when entering a number in a user interface or matching different
         *  phone no formats in a file
         *
         * There are different ways to specify a number, will cover 2 ways in this video
         *
         * FIRST WAY - Using \\d
         * .........
         * We can replace [0-9] with \\d
         * We don't need [0-9] if we are only using one of these special meta characters
         * i.e. "\\(*[0-9]{3}[)\\s-]*\\d{3}[\\s-]*[0-9]{4}")
         *
         * SECOND WAY - Using \\p{Digit}
         * ...........
         * Do something similar for the last 3 digits
         * Use \\p{Digit}
         *  i.e. "\\(*[0-9]{3}[)\\s-]*\\d{3}[\\s-]*\\p{Digit}{4}")
         *
         * Running this with the updated pattern above prints all the phone numbers as before confirming
         *  that these replacements are good alternatives to the [0-9]
         *
         * However, try to be consistent with 1 style instead of using 3 different styles for the digits in
         * different instances
         */

        Pattern phonePattern = Pattern.compile("\\(*[0-9]{3}[)\\s-]*\\d{3}[\\s-]*\\p{Digit}{4}");
        Matcher phoneMatcher = phonePattern.matcher(phoneList);

        phoneMatcher.results().forEach(mr -> System.out.println(mr.group()));


        /*
         * Let's now look at more complicate regex that matches on HTML tags
         * Set up a block htmlSnippets rep diff tags and some diff way you can format them
         */

        String htmlSnippets = """
                <H1>My Heading</h1>
                <h2>Sub-heading</h2>
                <p>This is a paragraph about something.</p>
                <p style="abc">This is another paragraph about something else.</p>
                <h3 id="third">Summary</h3>
                <br/>
                <p>Testing</p>
                """;

        /*
         * Set up a pattern, using the regex from the slide
         *
         * Let's take this regex apart
         * This expression starts with an angle bracket <
         * < is a meta character when used if you're naming your group, but the regex processor is smart enough
         * to check context, and no need to escape it
         *
         * This means the regex will start matching when it finds the < of a tag
         * Next there is group defined, which is automatically indexed as group 1
         * Remember group 0 is the entire matching subsequence
         * (\\w+)
         *       \\w is a meta character rep word character, so it's an alphabetical US ASCII character in
         *           either lower/upper case, an underscore or any digit
         *          + is followed by + quantifier which means it will match at least one word character but
         *            could also match more
         * This is being grouped because we want to pull out just the tag type . h1, p , or br
         *
         * [^>]*>
         *     - We have a character class in [] that starts with a carat ^
         *     - ^ in square brackets means to ignore the characters that follow, so in this case a >
         *      closing angle bracket will stop the matching
         *     - This is followed by an *, so any character that's not a closing angle bracket is going to match
         *     - This is followed by > closing bracket for the tag
         *     - This code will match tags that have no body, as well as tags that have attributes
         *
         * ([^\v</>]*)
         *      - Another group follows,
         *      - It's purpose is to capture the text that's contained between <> and </> html tags
         *      - You might expect this to be .* but there are some characters that we don't want to match on, so again
         *        will use a custom character class with a caret, so that any character that follows the carat will end
         *        the matching that follows the Caret will end the matching for this subsequence
         *      - These characters are new lines or carriage returns, opening and closing angle brackets and the
         *        backslash character
         *      - Line breaks or carriage returns are called vertical white space and can be matched using \\v
         *      - After that we have </> - means the code will match any character that's not one of these and because
         *        we are using the * quantifier, this can be empty
         *
         * (</\1>)*
         *      - The final group is really only a group because we want to use a quantifier for the entire set
         *      - We have </.. This is how most html tags end, and then they are followed by the tag label,p, or h1
         *        or whatever
         *      - \\1 - rep back reference to the first group
         *      - This means the ending label need to match the opening label
         *      - We are also using quantifier on this last group, meaning this could be omitted as it will be in one
         *        of an example that uses xhtml formatted br tag, that includes a backslash before the closing bracket
         *
         * Create a Matcher instance from htmlPattern matcher() passing the htmlSnippets
         * use results() - simplest to do this
         * Print
         *  group 0 - which is the match to the full regex
         *  group 1 - tag type
         *
         * All our tags match which is good
         *
         * We can replace \\w with [a-zA-Z_0-9]
         * [a-zA-Z_0-9] rep lowercase a through z, uppercase A through Z, an underscore or digits 0 to 9
         * This is the underlying meaning of \\w
         *
         * Running this code gives us the same results
         *
         * Let's change the ending tag of the h1 to be lower case in the htmlSnippet
         * After running this, the Full Tag is missing the closing </h1> tag
         * The back reference \\1 we are using is causing the match to look for a <H1>
         * There is a way to specify certain overlooked behavior for a group
         * We can add (?i) at the beginning of the group on the (</\\1>)
         *  to be ((?i)</\1>)
         * This does not rep an additional group & it means ignore case for the group
         *
         * Running the code prints the ending </h1> tag in the output
         *
         * We can do something similar by setting flags when we compile the pattern
         * Let's revert the last change.. remove (?i) and add a 2nd parameter
         * The 2nd pattern will be a Pattern.CASE_INSENSITIVE - a constant on the Pattern class
         *
         * Running the code prints the ending </h1> tag in the output because this flag is now set
         *
         * Using Pattern.CASE_INSENSITIVE means the entire pattern is case-sensitive, which may or may not
         * be what you want
         * (?i) - allows you to be more specific
         *
         * If we remove A through Z from  [a-zA-Z_0-9] , this will still work because again the flag is set
         *
         * One more thing why we results is instructors favorite
         *  - We could just filter one of the tags in my stream
         *  - Suppose we are only interested with header tags
         *  - The tags h1,h2 and h3 are captured in group 1
         *  - The captured text will have the actual which may or may not be upper case, so let's make the tag
         *    lowercase before we check that it starts with letter h
         *
         * Running this means that returns the data for the 3 header tags
         */
        System.out.println("_".repeat(50));
        Pattern htmlPattern = Pattern.compile("<([a-z_0-9]+)[^>]*>([^\\v</>]*)(</\\1>)*",
                Pattern.CASE_INSENSITIVE);
        Matcher m = htmlPattern.matcher(htmlSnippets);
        m.results()
                .filter(mr -> mr.group(1).toLowerCase().startsWith("h"))
                .forEach(mr -> System.out.println(
                        "Full Tag: "+ mr.group(0)+
                        "\n\tType: "+ mr.group(1)+
                        "\n\tText: "+ mr.group(2)
                ));
















    }
}
