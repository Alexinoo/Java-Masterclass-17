package regular_expressions.part5_pattern_matcher;

import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        /*
         * Set up a String to test regex on
         * Call a static () on Pattern class called matches()
         * Takes a regex of type String as 1st argument
         * 2nd parameter is the String you want to match to this expression
         * Returns a boolean
         */
        String sentence = "I like B.M.W. motorcycles.";

        boolean matched = Pattern.matches("[A-Z].*[.]", sentence);
        System.out.println(matched + ": "+sentence);

        /*
         * Suppose we want to test this on another sentence e.g. Hello World
         * If we call this same method, the code will again compile the string into a pattern
         * If we are going to use this regular expressions to check thousands/millions of characters
         * sequences, this method would be highly inefficient
         *
         * Instead, we can create a pattern instance for this expression by calling compile(String regex) - another static method on Pattern
         * Then pass our regex
         * Call matcher() which takes char sequence  and returns an instance of a Matcher class
         * When you compile a regex into a pattern instance, you need to use it with this Matcher instance then pass our sentence variable as
         * the arg to the constructor
         * Matcher class has matches() static () which we can call from our Matcher instance
         */
        Pattern firstPattern = Pattern.compile("[A-Z].*[.]");
        var matcher = firstPattern.matcher(sentence);
        System.out.println(matcher.matches() + ": "+sentence);

        /*
         * Matcher Class Advantage
         * Has 2 other operations for partial matching
         *  - lookingAt()
         *  - find()
         * Matcher also supports capturing groups and access to the text within the group
         * Among others
         */

        /*
         * Matcher Class DisAdvantage
         * An instance of a Matcher class has state, changes as operations are performed on it
         * Matcher class is not thread safe
         * state may need to be reset, before a new string is evaluated
         */

        /*
         * Print out sentence length
         * Print Matcher's ending index by calling the end accessor method which gets set after match is done
         * We can see that after matching, the ending index equals the string length meaning the entire length was consumed in the match
         */
        System.out.println("sentence.length: "+sentence.length());
        System.out.println("Matched Ending index: "+matcher.end());

        /*
         * lookingAt()
         * Always start at the beginning of the string, but will stop matching returning true, even before it finds the end of the string
         * It doesn't have to match the entire string
         * Returns a boolean
         *
         */
        System.out.println("_".repeat(50));
        System.out.println(matcher.lookingAt() + ": "+sentence);
        System.out.println("Matched Ending index: "+matcher.end());

        /*
         * Adding B.M.W. to the sentence
         * We expect matcher.lookingAt() to stop looking after B. - "I like B."
         * This is not happening because our regex "[A-Z].*[.]" uses asterisk (*) and this is what's called a greedy regular expression
         *
         *
         * Greedy regular expression match as many characters as possible
         * The expression .* which is a greedy expression, matches any number of characters, including the empty string
         *
         *
         * Reluctant regular expressions, match as few characters as possible from the input text.
         * The regex .*? matches any number of characters , but stops at the earliest successful point, where the overall pattern is matched
         * The default type of regex is greedy
         * You can use the ? to be a quantifier modifier, making the regex reluctant
         *
         * Changing our greedy expression to reluctant expression
         * THe result from the matches is both true and the matched ending index is 9 and not 26
         * This means the lookingAt() matched this reluctant regex in the first 9 characters
         *
         */

        firstPattern = Pattern.compile("[A-Z].*?[.]");
        matcher = firstPattern.matcher(sentence);

        System.out.println("_".repeat(50));
        System.out.println(matcher.lookingAt() + ": "+sentence);
        System.out.println("Matched Ending index: "+matcher.end());

        /*
         * We can use the end accessor method to print out the exact String, that matched the pattern
         * Then use substring with a beginningIndex 0 and matcher.end() as endIndex
         * This returns "I like B." which is the least amount of characters, for the regex to be true
         *
         * However, the matches() requires that the whole string matches and does not honour the request to use only the least
         * amount of characters in this match
         * It will always use every character in the string to determine a match
         */
        System.out.println("_".repeat(50));
        System.out.println("Matched on : "+sentence.substring(0,matcher.end()));

        /*
         * Let's look at the find()
         * We get Matched Ending index here as 11 and Matched on "I like B.M."
         * This output isn't quite right because we didn't actually match that substring shown above
         * To get the actual match, we need to change the last statement which is currently using 0 as the startIndex
         * Instead , we should use matcher.start() which is where the find() started searching
         * However, we get now Matched on : M. and this is because our regex really matched on just M period part of the
         * sentence
         * The find() picks up where the lookingAt() left off
         * If we want to start at the beginning, we need to reset this Matcher instance by calling a no args reset() on matcher
         *
         * Let's insert it above the find()
         *
         * We now get the same result as calling the lookingAt() above
         *
         * We didn't have to call reset() between the matches and lookingAt() because these methods don't set an incremental state
         * that will be used in their execution
         *
         * Whenever we call lookingAt(), they will always use a starting index of 0
         * But the find() uses the endingIndex of the Matcher instance as it's start to the next match
         *
         *
         */
        matcher.reset();

        System.out.println("_".repeat(50));
        System.out.println(matcher.find() + ": "+sentence);
        System.out.println("Matched Ending index: "+matcher.end());
        System.out.println("Matched on : "+sentence.substring(matcher.start(),matcher.end()));
    }
}
