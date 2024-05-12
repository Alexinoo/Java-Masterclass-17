package junit_testing.part2_junit_challenges;

public class ChallengeDescription {

    /*
     * Challenge #1
     * ............
     * - Set up test stubs
     *   - Create a JUnit test class that contains a test method for each method in the Utilities class
     *   - Don't add any test code to the methods yet
     *   - When you run the tests, they should all fail
     *   - Use the naming conventions we discussed in the JUnit lecture
     *   - Add JUnit4 library if you've created a new project
     *
     * Challenge #2
     * ............
     * - Add test code to the removePairs() test method, to test the Utilities.removePairs()
     *   - create tests for the Utilities.removePairs()
     *   - this () accepts a string and removes any pairs it contains by removing one half of the pair
     *   - Example:
     *      Input: AABCDDEFF - Output: ABCDEF
     *      Input: ABCCABDEEF - Output: ABCABDEF
     *   - Write the test code that tests the above 2 scenarios
     *   - Start by writing a test for the first set of input/output
     *   - Once that test passes, add a test for the second input/output pair
     *   - To keep things simple, you can add the same test method, but if you want to strict about it, you can create
     *     another method
     *
     * Challenge #3
     * ............
     * - Come up with 2 more tests for the removePairs()
     *   - Come up with 2 more tests that don't test the same functionality as the existing tests
     *      - Take a look at the code and see if you can think of another string we should pass to test a specific part
     *        of the code
     *   - There's also another special input value that we'd want to test, and which reveal another bug in our code
     *      - You don't have to write the tests, just think of what the inputs and the expected outputs would be
     *
     * Challenge #4
     * ............
     * - Add a test for the everyNthChar()
     *      - Write a test for the everyNthChar() that tests the following scenario
     *          Input: char array containing the letters 'h','e','l','l','o', in that order, and n = 2
     *          Output: char array containing the letters 'e','l'
     *
     * Challenge #5
     * ............
     * - Add a test for the case when n is greater than the length of the array
     *      - Write a test that tests the case when the value of n is greater than the length of the string
     *      - You can add a new test case, or add the test to the existing test case
     *
     * Challenge #6
     * ............
     * - Add tests for the nullIfOddLength()
     *      - Write 2 tests for the nullIfOddLength().
     *      - Include both tests in the nullIfOddLength()
     *      - In 1 test, pass a String that has an even length, and in the second test , pass a String that has an
     *        odd length
     *
     * Challenge #7
     * ............
     * - Add a test for the converter()
     *      - Test the converter() with the following output
     *          Input: a=10 , b=5
     *          Output: 300
     *
     * Challenge #8
     * ............
     * - Write another test for the converter()
     *      - Test a division by 0 scenario
     *          Input: a=10, b=0
     *          Output: ArithmeticException (divide by 0)
     *
     * Challenge #9
     * ............
     *  - Reduce repetition
     *      - Every one of the test cases starts by creating an instance of the Utilities class
     *      - Modify the UtilitiesTest class so that it doesn't have the repetitive code
     *
     * Challenge #10
     * .............
     *  - Test the following 5 scenarios with the removePairs()
     *      - Test the following inputs and outputs
     *      - You could add all these tests to the existing test (), or write a () for each set of input/output
     *         , though that wouldn't be the best way to do this
     *
     *  - Do it in such a way that you only have to write 1 test ()
     *  - Hint:
     *      - Create a new class that's using a particular Junit annotation
     *          Input:"ABCDEFF", Output = "ABCDEF"
     *          Input:"AB88EFFG", Output = "AB8EFG"
     *          Input:"112233445566", Output = "123456"
     *          Input:"ZYZQQB", Output = "ZYZQB"
     *          Input:"A", Output = "A"
     *
     */

    public static void main(String[] args) {
        Utilities utils = new Utilities();
        System.out.println(utils.everyNthChar(new char[] {'h','e','l','l','o'}, 9));

    }
}
