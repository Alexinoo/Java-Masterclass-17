package regular_expressions.part7_matcher_methods_replacement;

import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {

        /* Create a text block with html tags */
        String htmlSnippet = """
                <H1>My Heading</H1>
                <h2>Sub-heading</h2>
                <p>This is a paragraph about something</p>
                <p>This is another paragraph about something else.</p>
                <h3>Summary</h3>
                """;

        /* Create Pattern instance and pass it to a Matcher instance */
        var htmlPattern = Pattern.compile("<[hH](?<level>\\d)>(.*)</[hH]\\d>");
        var htmlMatcher = htmlPattern.matcher(htmlSnippet);

        /* Use while loop with find() on htmlMatcher to print two capturing groups in our regex
         * for multiple html tags in my text block
         */

        while (htmlMatcher.find()){
            System.out.println(htmlMatcher.group("level")+ " "+htmlMatcher.group(2));
            System.out.println("index = "+htmlMatcher.start("level"));
        }

        /*
         * Let's look at another matching method introduced in JDK9
         * Reset htmlMatcher because this method picks up from where we left off matching
         */
        htmlMatcher.reset();

        /*
         * After the reset, invoke the results() on the matcher
         * This returns a Stream of obj, instances of an interface called MatchResult
         * Like any stream, we can use forEach terminal operation on this with a lambda expression
         * Print text from our 2 capturing groups
         *
         * htmlMatcher.results()
         * Returns a stream of natch results, one for each subsequence tht matches the pattern
         * These are streamed in the same order, as they're matched
         * Underneath the covers, this is really calling a series of find() methods somewhat similar
         * to what we did in the while loop
         * This method doesn't reset the matcher as we had to reset first because we had used Matcher
         * previously, exhausting all matches

         */
        System.out.println("_".repeat(50));
        htmlMatcher.results()
                .forEach(mr -> System.out.println(mr.group(1) + " "+ mr.group(2)));

        /* The Pattern class also has a method that can give us a stream, though it's a stream of strings,
         * which is a stream of the matched sub-sequences
         * Set up a text block with a tab as space between rather than spaces
         * Split our text by each line, using the lines() on the String class
         * For each line, use the flatMap operation to get another stream,splitting each line
         * using a regex pattern for tab
         * We have to escape that with an extra backslash in a regex
         * Print each tab delimited text value
         *
         */
        System.out.println("_".repeat(50));
         String tabbedText = """
                 group1 group2  group3
                 1  2   3
                 a  b   d
                 """;
        tabbedText.lines()
                .flatMap(s -> Pattern.compile("\\t").splitAsStream(s))
                .forEach(System.out::println);

        /*
         * Review Matcher replacement methods
         * Reset htmlMatcher again
         * set up a String variable updated snippet
         *
         * replaceFirst()
         * Replaces entire <H1>My Heading</H1> with First heading
         * start index is 0 since it was reset and end index 19 (matcher ends here)
         * group2 however returns the text we had in the original h1 tag "My Heading"
         * So, how can we replace the first header with some other tag, but using the captured
         * heading text, in this case "My heading"
         *
         * If we try to do this, we get an error because we are trying to use the capturing group
         * before any matches were actually done
         *      htmlMatcher.replaceFirst("<em>"+htmlMatcher.group(2)+"</em>");
         * The replaceFirst() will perform a match, but in this case, we are trying to access the
         * result before the method is even called
         * replaceFirst() resets the Matcher everytime it's called and there is an overloaded method
         * that takes a function AFTER a match - This is the place to use your capturing group
         * It takes 1 parameter of type MatchResult mr and needs to return a String
         * We can use mr to query the capturing groups
         *
         * Running this code swaps the <h1> tags with <em> maintaining the same original text
         *
         */
        System.out.println("_".repeat(50));
        htmlMatcher.reset();

        //String updatedSnippet = htmlMatcher.replaceFirst("First Header");
        //String updatedSnippet = htmlMatcher.replaceFirst("<em>"+htmlMatcher.group(2)+"</em>");
        String updatedSnippet = htmlMatcher.replaceFirst((MatchResult mr) -> "<em>"+ mr.group(2) +"</em>");
        System.out.println(updatedSnippet);
        System.out.println(htmlMatcher.start() + " : "+ htmlMatcher.end());
        System.out.println(htmlMatcher.group(2));

        /*
         * There's another way, we can do this by including what's called a back reference in the string
         * that we pass to replace()
         * We have seen that groups in a regex are implicitly indexed
         * You can use back references to refer to the text, captured by these groups in regex, and also in the
         * replacement methods on Matcher
         * In a regex, a back reference is identified by a backslash and a number, which is the capturing group
         * index
         * In a replacement string in Java though, a back reference starts with a $, then a number, rep group index
         * You can also use capturing group names in back references, replacing the index with the named group
         *
         * Using number as the capturing group
         * We get the same result
         *
         */
        htmlMatcher.usePattern(Pattern.compile("<([hH]\\d)>(.*)</\\1>"));
        System.out.println("_".repeat(50));
        htmlMatcher.reset();
        System.out.println("Using back reference.. \n");
        System.out.println(htmlMatcher.replaceFirst("<em>$2</em>"));

        /*
         * Change our regex pattern to use a back reference to match ending tag
         * Do this above the code above
         * We need to call usePattern, a static () on Matcher class and pass our Patter instance
         * We will use the pattern as before with a slight change at the end of the 2nd group
         * Instead of repeating the pattern we are specifying \\1 a back reference to our first group
         *
         * Running this code, gives us the same result as before.. so this patter works like the earlier pattern
         */

        /*
         * replaceAll() and 2 overloaded versions for this method
         * Running below - replaces all the header tags with <em> tags
         * In addition to this method, there are 2 more methods that are used together to do more customized
         * replacements if needed
         */

        System.out.println("_".repeat(50));
        String replacedHTML = htmlMatcher.replaceAll((MatchResult mr) -> "<em>"+ mr.group(2) +"</em>");
        System.out.println(replacedHTML);

        /*
         * Explore replaceAll()
         *  - First create a StringBuilder
         *  - Then sets up a do while loop
         *  - And in that loop, there is a call to appendReplacement(sb,replacement) that takes a StringBuilder
         *    variable
         *  - After the loop finishes, the code invokes the appendTail(sb)
         * Copy the code below from appendReplacement documentation API
         * Replace m with htmlMatcher instance
         * Reset matcher first
         *
         * Running this, we get dog everywhere we had a h tag - probably not what we wanted
         * In this case, we can replace all h1 headers with a <head> tag, h2 headers with an <em> tag and
         * the others we keep the tag but add a number
         *
         * Set up an index variable
         * Remove literal text dog and add a switch statement which will return a variety of changes
         * Test the first group value, making sure it's lower case .i.e. h1,h2..etc
         * IF it's h1
         *  - replace those tags with <head>$2</head>
         * IF it's h2
         *  - replace those tags with <em>$2</em>
         * ANY other headers
         *  - Keep the original so h3, h4 and so on
         *  - Include a number in the text - so increment index after using it
         *  - Followed by a back reference for my capturing group
         */
        htmlMatcher.reset();

        StringBuilder sb = new StringBuilder();
        int index = 1;
        while (htmlMatcher.find()) {
            htmlMatcher.appendReplacement(sb,
                switch (htmlMatcher.group(1).toLowerCase()){
                 case "h1" -> "<head>$2</head>";
                 case "h2" -> "<em>$2</em>";
                    default -> "<$1>" +index++ +". $2</$1>";
                });
        }
        htmlMatcher.appendTail(sb);
        System.out.println(sb);



    }
}
