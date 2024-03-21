package regular_expressions.part6_matcher_methods;

import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        /*
         * matcher.group()
         * This method returns the String which is the input subsequence matched by the previous match.
         * Mostly used in a loop with find()
         */

        /* Create a text block with html tags */
        String htmlSnippet = """
                <H1>My Heading</H1>
                <h2>Sub-heading</h2>
                <p>This is a paragraph about something</p>
                <p>This is another paragraph about something else.</p>
                <h3>Summary</h3>
                """;
        /*
         * Suppose we want a list of the headers h1,h2,h3
         * Set up a pattern by calling pattern.compile() and provide regex for looking html tags
         * Get a matcher instance from that pattern passing it the htmlSnippet
         * Find matches using a while loop - loop while htmlMatcher.find() returns true
         * out the group, or the matched sub expression
         *
         * Prints 3 html headers with the tags included
         *
         * If we wanted just the text, and not the tags, we can call the substring() on that text
         *  but there is a better way
         * Regex provides metacharacters , in the form of parenthesis to identify a special section of
         *  the expression called a capturing group
         *
         * (.*) rep text within the tags and the text we want to capture
         * Running after updating .* to (.*) gives us the same results
         * When you call the group() without arguments, you always get the part of the string that matched the entire
         *  regex
         * If we want to get just the captured group, we need to use the overloaded version of group, passing an index
         *
         * group /group(0) - gives the same result
         * However, group(1) - returns the text that was captured in the parentheses
         * Commenting group and group(0)
         *
         * We only get the heading text printed out now
         * However, we can only use ann appropriate index
         * Since we only have 1 group defined, we can either use index 0 to get the whole match or index 1
         *  to get the capturing group
         *
         * Changing to group(2) gives throws an err because there is no group 2
         *
         * Let's add another group - Let's say we want to use the h level for some reason, so wrap \\d as (\\d)
         * This creates 2 capturing groups, but it has no effect on the matching
         * Next change the print statement to print both captured groups
         *
         * Now the digit is printed which is the first capturing group and then the text within the header tag,
         * which is the second capturing group
         *
         * You can name your capturing group with a special set of meta characters
         * This starts with a question mark and the name contained, within angle brackets.
         * Change regex (\d) to (?<level>\\d)
         * We can reference this by level now when we call another overloaded version of group() - instead of passing
         * 1 to group, we can pass the name of the capturing group
         * Update group(1) to group("level"
         *
         * Running this prints the same results
         * The end and start methods on Matcher instance, will all take either a group index or a capturing group name
         *
         * Print start index of the current level in the string with htmlMatcher.start("level")
         */

        var htmlPattern = Pattern.compile("<[hH](?<level>\\d)>(.*)</[hH]\\d>");
        var htmlMatcher = htmlPattern.matcher(htmlSnippet);

        while (htmlMatcher.find()){
           // System.out.println("group: "+htmlMatcher.group());
           // System.out.println("group0: "+htmlMatcher.group(0));
            System.out.println(htmlMatcher.group("level")+ " "+htmlMatcher.group(2));
            System.out.println("index = "+htmlMatcher.start("level"));
        }

    }
}
