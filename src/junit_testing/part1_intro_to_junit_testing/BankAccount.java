package junit_testing.part1_intro_to_junit_testing;

/*
 * Adding JUnit library
 *  - IntelliJ ships with JUnit 3 and JUnit 4 libraries but it doesn't actually add them to the project by default
 *  - So we'd have to add the version of the JUnit we'd like to use
 *      - JUnit 4 is the latest version and we're going to use in this project
 *      - All the code in this lecture will be based on JUnit 4
 *
 * Steps to add JUnit 4 to our project
 * ...................................
 *  - Place the cursor in front of the BankAccount class (Alt + Enter)
 *      - Click on Create Test on the dialog that pops up
 *          - Click OK for the pop-up that follows
 *      - We get a Create Test pop up
 *          - Choose JUnit4 as the Testing library
 *          - Click on Fix, if it says JUnit4 lib not found in the module
 *              - Click OK on the pop-up that follows (with the first option selected)
 *      - Select all the methods listed to generate an equivalent test for all the methods in our class
 *
 * JUnit will create for each class in your project and equivalent Test class
 * It will have the same name as the but with a word Test appended to your class names
 *  - So in this case, JUnit will create a file called BankAccountTest.java
 *
 *  - This generates BankAccountTest.java in the same folder that we specified
 *      - @org.junit.Test tells the JUnit framework that the () is a test method
 *      - Right now, JUnit libraries are unresolved, even though we added the library to our project
 *  - This is because when we clicked the FIX button, and intelliJ edit the library to our class path, it set the
 *    usage of the library to testing which means the library will only be included when we run tests but not when we
 *    compile them
 *      - To fix this
 *          - Project > Open Module Settings > Modules > Dependencies > Select JUnit4 > Change from Testing to Compile
 *          - Click OK
 *
 *  - Next, we'll create a separate run configurations for all our tests, because we want to test a class in our app
 *    , we don't want to run the application itself, we want to run the unit tests *
 *      - Right click on anywhere outside the class
 *          - Click "Run 'BankAccountTest' menu"
 *          - Or select "More Run/Debug"
 *              - proceed with the defaults and click OK/Apply
 *
 *  - Running BankAccountTest.java
 *      - The console at the bottom is splitted into 2 parts
 *          - On the left, we can see, there's a list of all the tests we read ( the 3 corresponds to the method names)
 *              - shows you the name and how long in ms it took to actually execute that test
 *          - On the right, is the normal console, that we are used to see
 *              - There is a toolbar that only appears when you're running tests
 *                  - prints "Tests passed : 3 of 3 tests - 6ms"
 *      - However, this is actually bad, because we haven't actually written any tests
 *          - All we have in our bank account to test class are stubs or just empty methods
 *              - This can confuse, since we can think that we have tested our application and that all the tests passed
 *
 *   - Solution is to have all our tests call JUnit.fail()
 *      - This () reports that a test has failed
 *          - It accepts a String parameter that it will report when the test fails
 *
 *  - Adding that to each of the test methods
 *      - fail("This test has yet to be implemented");
 *
 *  - Running this again
 *      - Now we'll get the 3 errors and we're getting the assertions  "java.lang.AssertionError" with our msgs
 *          test has yet to be implemented
 *      - We get each error for each of our 3 methods
 *  - When the tests failed, they throw  "java.lang.AssertionError" error exceptions, and the msg reported for each
 *      exception
 *  - When we use JUnit, what we essentially do is test the output of a method against an assertion that we've made
 *    about the expected output
 *  - So a test fails if the assertion isn't met
 *  - And that's why JUnit reports test stubs as passes, they don't assert anything, And so now assertions fail
 *
 */

public class BankAccount {
    private String firstName;
    private String lastName;
    private double balance;

    public static final int CURRENT = 1;
    public static final int SAVINGS = 2;

    private int accountType;

    public BankAccount(String firstName, String lastName, double balance, int typeOfAcc) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.balance = balance;
        this.accountType = typeOfAcc;
    }

    /*
     * The branch argument is true if the customer, is performing the transaction at a branch with a teller
     * False, if the customer is transacting at an ATM
     */
    public double deposit(double amount , boolean branch){
        balance += amount;
        return balance;
    }

    /*
     * The branch argument is true if the customer, is performing the transaction at a branch with a teller
     * False, if the customer is transacting at an ATM
     */
    public double withdraw(double amount , boolean branch){
        balance -= amount;
        return balance;
    }

    public double getBalance() {
        return balance;
    }

    public boolean isCurrent(){
        return accountType == CURRENT;
    }


}
