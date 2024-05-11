package junit_testing.part1_intro_to_junit_testing;

import static org.junit.Assert.*;

/*
 * Asserts in JUnit
 * ................
 *
 * We can also add our own test () and we don't have to match a test () to a method in the class we're testing
 *  although in an actual development environment, it wouldn't make sense for us to do so
 *
 * Requirements
 * ............
 *  - Test ()s have to be annotated, though with the org.junit.Test annotation as we can see with the 3 ()s in this class
 *  - Must be public
 *  - Must also return void
 *
 * Though older versions of JUnit didn't have a Test annotation & so we had to begin test method names with test, in order
 *  for the framework to identify test methods
 *
 * When we work with JUnit, we make assertions about what we expect to happen, If these assertions are met, then the test
 *  passes, but if they are not met, then the test fails
 * An implication is that a test can pass, even though our code has bugs, because none of the tests in our test suite
 *  made an assertion that will catch the bug
 *
 * Add dummyTest()
 *  - Tests whether 2 integers are equal
 *
 * assertEquals()
 *  - The 1st parameter is the value we expect
 *  - The 2nd parameter is the value we want to test, or the actual value
 *
 * Instead of running the entire test suite, which we've done in the past, by clicking the run button on BankAccountTest
 *  which we could also do with JUnit pane run icon, we could also use the green arrow to the left in the gutter of
 *  the method
 *  - This will run a test just for this method
 *
 * Running dummyTest() - using the tests above
 *  - This time the assertion fails, since we expected a value of 20 but actually provide 21
 *
 * Notice that the icon to the left of the dummyTest() is now diff ( changes) and is also added to the run
 *  configurations as well as BankAccountTest.dummyTest
 *
 * Let's delete the dummyTest() , since we don't need it anymore
 *  - In my case, I will just comment it
 *  - You can also remove it from the Run configurations by clicking on "Edit Configurations"
 *      - Select it > click (-) button
 *          - click OK
 *
 *
 * ////
 * Next, Write a Test for the deposit() of the Bank Account class
 *  - comment out on the fail()
 *  - create an instance of the Bank Account class
 *  - call the deposit () with a deposit of 200
 *      - we'll set the 2nd parameter to true meaning that the transaction is taking place at a branch with the
 *        help of a teller
 *      - we'll set the 3rd parameter here as 0
 *          - When comparing doubles, we have chosen to use the delta parameter which allows some leeway in the
 *            comparison
 *          - So as long as the diff between the expected and actual values is within the delta we specify, then the
 *            assertion will pass
 *  - call assertEquals() to assert that the expected value should be 1200
 *      - The actual value we will test against, is the value returned by the deposit()
 *      - Note here that when comparing obj(s), the assertEquals() uses the equals() of the objects being compared
 *         to make the comparison
 *  - Running this:
 *      - deposit () is now successful and that test ahs actually succeeded
 *
 *  - We can have more than 1 assertion in a test
 *      - e.g. we, may want to verify that the bank account balance now equals 1200
 *  - Lets' go ahead and add an assertion to that below the first assertion
 *      - This time by calling 1200 against account.getBalance() and the delta
 *
 *  - Running this:
 *      - we still got the deposit test completed successfully, and the withdrawal and getBalance failing as expected
 *        at this point in time, because we have not implemented them yet
 *
 *  - Every test method should be self-contained, i.e. what happens in 1 test method, shouldn't depend on what happened
 *    in another test ()
 *      - Every () shd start afresh, and be able to run independently of the other test ()s
 *      - As we saw earlier, we were able to run the dummyTest() independently
 *
 *  - Having said that, though, we can't have instance variables in our test class
 *      - Depending on the code, it may make sense to create a single instance of a class that we're testing that all
 *        the test ()s use
 *  - In the case of the deposit tests, even though we can add more than 1 assert to the same test, it's not best practice
 *    to do so
 *      - The best practice is actually 1 test per test condition, or assertion
 *
 *  - That said, let's comment out on the 2nd assertion that we added on the deposit()
 *
 *
 * getBalance()
 *  - Create an instance of a new bank account
 *  - Deposit 200 by calling account.deposit()
 *  - assert the balance with the result of calling getBalance()
 *  - Running this:
 *      - the getBalance() sub test and the deposit()s both passed the tests
 *
 *
 * Suppose we wanted to verify the value of the bank account after calling the withdraw()
 *  - Doing this within the getBalance() would be bad practice
 *  - So we're going to add another () for that
 *  - Another good practice to follow, test method names should give some indication of the actual condition they
 *     are testing
 *  - So we can update the getBalance() to getBalance_deposit() so that it can indicate the type of the test we're
 *     doing for that particular test
 *
 *  - And now we can add another () to test the withdrawal
 *
 *  - copy getBalance_deposit() and update the name to getBalance_withdraw()
 *      - call withdraw() and pass 200
 *      - assert that 800 is equal to the value returned from the getBalance()
 *
 * Running this again:
 *  - We've now got 4 tests showing and 3 have succeeded
 *  - We can also now tell from the () names shown down there on the bottom left hand corner of the screen, exactly
 *    what each () was testing
 *
 * N/B
 * ...
 * We can also filter unit tests that have passed/ failed from the JUnit pane
 *
 *
 *
 * ///
 * Next, let's add a () in our BankAccount class that returns true, if the account is a CHECKING account, and false
 *  otherwise
 *  - We could use enum for these , but we'll keep it simple here
 *      - Add CURRENT and initialize it to 1
 *      - Add SAVINGS and initialize it to 2
 *      - add accountType variable to hold the account type
 *          - add it to the constructor as the last param and initialize it accordingly
 *
 *  - Add a getter isCurrent()
 *      - returns true if accountType is Current, otherwise false
 *
 * ///
 * Next, update the constructor to include the type of the account
 *  - set CURRENT as the default
 *
 * ///
 * Next, add isCurrent_true() test method
 *  - Create an instance of a bank account
 *  - then assert whether the expected result true in this case is the same as the value we get from calling isCurrent()
 *     on this instance
 *  - Running this
 *      - We can see that isCurrent_true passed the test because the account is a current acc
 *  - If we pass false and run it again
 *      - our assertion test failed because we expect this to be a current account
 *
 *  - However, when testing expected values against boolean values, it's actually preferable instead of using
 *     assertEquals to use assertTrue() or assertFalse() , both are separate ()s
 *      - Both only take the condition we're testing
 *          - testing if true -  assertTrue(account.isCurrent())
 *          - testing if false -  assertFalse(account.isCurrent())
 *      - Running this with assertTrue - works as expected
 *      - Running this with assertFalse - test fails
 *
 *  - We can also add a msg, to print in case the condition is not met which might be useful to the user when debugging
 *      - Let's test with assertFalse("This is not a current account", isCurrent_true)
 *          - throws an assertion error, meaning that our test has failed with the message that we passed
 *      - We can also change the account from the constructor and test again, should do the same thing
 *
 */

public class BankAccountTest {
    @org.junit.Test
    public void deposit() {
       // fail("This test has yet to be implemented");
        BankAccount account = new BankAccount("Alex","Mwangi",1000.00, BankAccount.CURRENT);
        double balance = account.deposit(200.00, true);
        assertEquals(1200.00,balance,0);
        //assertEquals(1200.00,account.getBalance(),0);
    }

    @org.junit.Test
    public void withdraw() {
        fail("This test has yet to be implemented");
    }

//    @org.junit.Test
//    public void getBalance() {
//        //fail("This test has yet to be implemented");
//        BankAccount account = new BankAccount("Alex","Mwangi",1000.00);
//        account.deposit(200.00, true);
//        assertEquals(1200.00,account.getBalance(),0);
//    }

    @org.junit.Test
    public void getBalance_deposit() {
        //fail("This test has yet to be implemented");
        BankAccount account = new BankAccount("Alex","Mwangi",1000.00, BankAccount.CURRENT);
        account.deposit(200.00, true);
        assertEquals(1200.00,account.getBalance(),0);
    }
    @org.junit.Test
    public void getBalance_withdraw() {
        //fail("This test has yet to be implemented");
        BankAccount account = new BankAccount("Alex","Mwangi",1000.00,BankAccount.CURRENT);
        account.withdraw(200.00, true);
        assertEquals(800.00,account.getBalance(),0);
    }

    @org.junit.Test
    public void isCurrent_true() {
        BankAccount account = new BankAccount("Alex","Mwangi",1000.00,BankAccount.CURRENT);
        //assertEquals(false,account.isCurrent());
       // assertEquals(true,account.isCurrent());
       // assertFalse("The account is not a current account",account.isCurrent());
        assertTrue(account.isCurrent());
    }

//    @org.junit.Test
//    public void dummyTest(){
//        assertEquals(20,21);
//    }
}
