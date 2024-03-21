package regular_expressions.part4_regex_min_challenges;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        /* Mini-challenge 1
         * Write a regular expression that matches the exact sentence "Hello, World!"
         * Use the matches() on String to check if the input sentence matches this pattern
         * Use only literal characters in the regex
         *
         * String.matches() - matches every character in the string
         */
        String sentence = "Hello, World!";
        boolean isMatching = sentence.matches("Hello, World!");
        System.out.println(isMatching);

        /* Mini-challenge 2
         * Create a regex that matches a sentence starting with an uppercase letter, followed by zero or more
         * lowercase letters, and ending with a period
         *
         * e.g. "The bike is red." or "I am a new student", should match your regular expression
         * The sentences "hello World." or "How are you?" should not
         * Use the matches() to verify the pattern on each of these examples
         *
         *
         * Solution
         * ........
         * start with "[A-Z]" capital letter - no quantifier for this - we want it to be a single capital letter
         * "[A-Z].*" - use dot and asterisk (*) quantifier - match any character - sentence could be a single letter
         *  but that's OK
         * "[A-Z].*\\." - use \\. since we want this to be a period and not a metacharacter need to escape it with \\
         *
         */
        System.out.println("_".repeat(50));
         String regex = "[A-Z].*\\.";
         for (String s : List.of("The bike is red.","I am a new student.","hello World.","How are you?")){
             boolean matched = s.matches(regex);
             System.out.println(matched + ": " +s);
         }

         /*
          * Another slightly different version that does the same thing
          *
          * Replace the . with [a-z\\ss] - means replace dot with a character class that contains a range for lower case
          * letters from a to z and then space character specified with \\s
          * This means the first character can be followed by any lower case letter or space to match, matching on zero to
          * or many of these characters
          * Then change the asterisk to a + - which means there has to be at least 1 other character between the first
          * capital letter and the last period
          * We can also change the \\. to [.] which is also another way to specify a single period
          */
        System.out.println("_".repeat(50));

        regex = "[A-Z][a-z\\s]+[.]";
        for (String s : List.of("The bike is red.","I am a new student.","hello World.","How are you?")){
            boolean matched = s.matches(regex);
            System.out.println(matched + ": " +s);
        }

        /* Mini Challenge 3
         *
         * Modify the requirements to mini-challenge 2 slightly..matching sentences that end in different punctuation marks
         * but still maintain the other criteria
         * i.e. The sentence shd start with a capital letter, followed by one or more words separated by spaces, and end with
         * either an exclamation mark , question mark or period
         * In addition, punctuation marks within the sentence should be permitted, as are capital letters and abbreviations
         * Used a different set of sentences from above
         *
         *
         * Solution
         * ........
         * The simplest solution is to replace [a-z\s] with a (.)
         * Then add ? and ! to [.] - [.?!]
         */

        System.out.println("_".repeat(50));

        regex = "[A-Z].+[.?!]";
        for (String s : List.of(
                "The bike is red, and has flat tires.",
                "I love being a new L.P.A. student!",
                "Hello, friends and family: Welcome!",
                "How are you, Mary?")){
            boolean matched = s.matches(regex);
            System.out.println(matched + ": " +s);
        }

        /*
         * Another solution to Mini challenge 3
         * ....................................
         *
         * Replace . with [\\p{all}] - all - both alphabetical and numerical characters
         * Include anchors for the start/end of the sentence
         * Carat character ^ and a dollar sign $ at the end to produce "^[A-Z][\p{all}]+[.?!]$"
         * However, using ^ and $ is somehow redundant and can be omitted as well
         */

        System.out.println("_".repeat(50));

        regex = "[A-Z][\\p{all}]+[.?!]";
        for (String s : List.of(
                "The bike is red, and has flat tires.",
                "I love being a new L.P.A. student!",
                "Hello, friends and family: Welcome!",
                "How are you, Mary?")){
            boolean matched = s.matches(regex);
            System.out.println(matched + ": " +s);
        }
























    }
}
