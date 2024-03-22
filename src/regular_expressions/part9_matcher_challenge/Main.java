package regular_expressions.part9_matcher_challenge;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {

        /* Email Address Validator
        *
        * Write a program that validates email addresses, based on a set of pre-defined rules
        *   - shd be formatted as username@domain.
        *   - username part of the email can contain alphanumeric characters, so lowercase a to z,
        *     upper case A to Z, digits 0 to 9 or periods, underscores, and hyphens or dashes(-)
        *   - domain part can contain alphanumeric characters and digits as well as periods or dashes
        *     such as xyz.com, or abc-xyz.org, but could also be xyz.edu.uk
        *
        * Your program shd use regex and the Pattern class to compile the pattern
        * Use the Matcher class to match the entered email address against the compiled pattern
        * For valid email addresses, display the username and the domain name, using one of the group methods
        * Use the examples shown here or ones of your own invention to test your code
        */

        String emailList = """
                john.boy@valid.com
                john.boy@invalid
                jane.doe-smith@valid.co.uk
                jane_Doe1976@valid.co.uk
                bob-1964@valid.net
                bob!@invalid.com
                elaine@valid-test.com.au
                elaineinvalid1983@.com
                david@valid.io
                david@invalid..com
                """;

        /*
         * Start with very few rules
         *
         * (.+)@(.+) - any character at least 1 , then @ sign , followed by any character at least 1
         * Running above prints all valid and invalid emails
         *
         * Username [\\p{Alnum}_.-]+
         * Use character class of our own making, so will use [] and use one of the POSIX character classes \\p{Alnum}
         * follow that with an underscore, a dot, or a hyphen which are all valid characters for username
         * Use + quantifier which means it will match at least one word character but could also match more
         * Alternatively, we can replace both POSIX Alnum and the underscore  \\p{Alnum}_  to with \\w
         *  i.e. "([\\w.-]+)@(.+)");
         *
         * Running this prints the same results as with "([\\p{Alnum}_.-]+)@(.+)")
         * This eliminates one of the invalid emails i.e. bob!@invalid.com
         *
         *
         * Domain (\\w+\\.\\w{2,})
         * The simplest domain is at least one word character a dot and at least 2 word characters
         * Replace (.+) which represents at least 1 character of any kind with a \\w+
         * Follow that with a literal . escaping it with \\.
         * Finally, follow with \\w{2,} and a quantifier of at least 2 characters
         * You can specify a max, e.g. 3,4,5 etc to restrict these to domains such as .org, .net , .com or th country
         * code that follow those
         * Not part of the rules but is fairly common, so will include it there
         *
         *
         * Running this prints valid emails. However, we are missing part of the domain in some of the cases which had the country
         * code at the end
         * This means that the regex is ending too soon
         *
         * Solution ((\\w+\\.)+\\w{2,})")
         * .............
         * We can add another group to specify that the word period part of the domain can occur one or more time
         * We can add parentheses around \\w+\\. the word character class and the period and add a quantifier
         *
         * Printing this prints valid emails. However, 1 valid email is missing "elaine@valid-test.com.au"
         * This is because we have not included a dash as a valid character in the domain part of the address
         * This will require adding 2 custom character classes [] that include a word character and a dash, in both instances
         * where we have just a word character i.e. \\w to [\\w-] in both instances
         * i.e. "([\\w.-]+)@(([\\w-]+\\.)+[\\w-]{2,})"
         */
        Pattern partialPattern = Pattern.compile("([\\w.-]+)@(([\\w-]+\\.)+[\\w-]{2,})");
        Matcher emailMatcher = partialPattern.matcher(emailList);

        emailMatcher.results()
                .forEach(mr -> {
                    System.out.printf("[username= %s, domain=%s]%n",mr.group(1),mr.group(2));
                });

        /*
         * There is another way we can do this also
         * Use a String[] to turn our text block into an array of strings
         * We can call lines() which returns a stream of strings and then call toArray()
         * We have to pass a method reference to create a new String array - if we want the array to be typed as strings
         * and not obj
         * Here we have simulated we are reading a dat from a file
         *
         * We could also have done this with a split with a regex for a new line character \n or \R
         *
         * Let's use our email pattern on each string by looping through the array with enhanced for loop
         * Create a new Matcher each time from the emailPattern, and the current string
         * Use the matches(), because each line should only have an email address on it
         * Print whether the email is valid or not
         *
         * Running this prints whther an email is valid , if so username and domain is printed , otherwise, prints invalid only
         */
        System.out.println("_".repeat(50));
        Pattern emailPattern = Pattern.compile("([\\w.-]+)@(([\\w-]+\\.)+[\\w-]{2,})");
        String[] emailSamples = emailList.lines().toArray(String[]::new);

        for(String email : emailSamples){
            Matcher eMatcher = emailPattern.matcher(email);
            boolean matched = eMatcher.matches();
            System.out.print(email + " is "+ (matched ? "VALID ":"INVALID "));
            if(matched)
                System.out.printf("[username= %s, domain=%s]%n", eMatcher.group(1), eMatcher.group(2));
            else
                System.out.println();
        }
    }
}
