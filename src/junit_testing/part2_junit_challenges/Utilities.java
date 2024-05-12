package junit_testing.part2_junit_challenges;

public class Utilities {

    /*
     * Returns char array containing every nth char
     * When sourceArray.length < n, return entire array
     *
     * Example
     *  sourceArray - 'h','e','l','l','o'
     *  - sourceArray.length = 5
     *  - value of n = 2
     *  - returnedLength = 5 / 2 = 2
     *      - Initializes an array with a length of 2
     *          char[] result = new char[2]; // [ , ]
     */
    public char[] everyNthChar(char[] sourceArray, int n){
        if (sourceArray == null || sourceArray.length < n)
            return sourceArray;
        int returnedLength = sourceArray.length / n;
        char[] result = new char[returnedLength];
        int index = 0;
        for (int i = n-1; i < sourceArray.length; i+=n) {
            result[index++] = sourceArray[i];
        }
        return result;
    }

    /*
     * Remove pairs of the same character, that are next to each other by removing one occurrence of the character
     * Example
     *  "ABBCDEEF" -> "ABCDEF"
     *  "ABCBDEEF" -> "ABCBDEF" (The 2 B's aren't next to each other & so they aren't removed)
     *
     * If length is less than 2, there won't be any pairs, return source
     */

    public String removePairs(String source){
        if (source.length() < 2)
            return source;
        StringBuilder sb = new StringBuilder();
        char[] string = source.toCharArray(); // ['A','B','B','C','D','E','E','F']

        for (int i = 0; i < string.length - 1; i++) {
            if ( string[i] != string[i+1] )
                sb.append(string[i]);
        }
        sb.append(string[string.length - 1]); // Add the final character which is always safe
        return sb.toString();
    }

    /*
     * Perform a conversion based on some arbitrary internal business rules
     */
    public int converter(int a , int b){
        return (a/b) + (a * 30) - 2;
    }

    /*
     * Returns null, if we've got an odd length of the input string
     * Otherwise, return the input string itself (if even)
     */
    public String nullIfOddLength(String source){
        if (source.length() % 2 == 0)
            return source;
        return null;
    }
}
