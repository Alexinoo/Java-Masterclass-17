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
 *
 *
 *
 * More Asserts and Exception Handling
 * ...................................
 * There are other Junit assertion methods
 *  - assertNotEquals()
 *      - Used when we don't want the actual value to be equal to a specific value
 *
 *  - assertArrayEquals()
 *      - Used when we want to verify the value of an array
 *      - assertEquals() won't work with arrays unless they are of the same instance
 *      - considers 2 arrays are equal only when
 *          - the lengths of the 2 arrays is the same
 *          - every element in both arrays is the same and in the same order
 *
 *  - assertNull() & assertNotNull()
 *      - used to asset null values
 *      - we can also use assertEquals to check for null, but as with assertTrue() and assertFalse(), using
 *          these 2 ()s makes the intention clearer, and we only have to pass the actual value in the method
 *
 *  - assertSame() & assertNotSame()
 *      - Used when we want to check whether 2 instances are the exact same instance
 *      - Remember, the assertEquals() uses the equal() to test for equality
 *      - assertSame() compares the obj references
 *
 *  - assertThat()
 *      - compares the actual value against a matcher (not the Matcher in the JDK, but a JUnit matcher class)
 *      - It is more powerful than the other assert ()s , since we can compare the actual value against a range of
 *        values
 *      - This () became available in JUnit4.4
 *
 * Next,
 * - Let's look at other annotations
 * - Note that we are starting each test afresh and each test is independent of each other test
 * - We're creating a bank account instance at the beginning of each and every test
 * - We are using the same values rather for the bank account in every test
 * - Instead of writing same line of code in each test, we can use a () that has the @Before annotation
 *
 * - The method with the @Before annotation is run before every test
 *      - We'll change our code to use a setup()
 *      - So we'll remove the creation of a bank instance ()s from our test ()s
 *      - Instead we'll have an instance variable called account in this class
 *          private BankAccount account;
 *      - Then the setup () will create the instance and set the instance variable & also print some msg
 *          account =  new BankAccount("Alex","Mwangi",1000.00, BankAccount.CURRENT);
 *
 *  - Next,
 *      - We'll remove the creation of the account because we don't need that anymore
 *      - In my case, I will comment it out
 *
 *
 * @org.junit.Before
 *  - Tells the Junit framework to run the setup() every time we run a test
 *
 * Running this:
 *  - We get the same results
 *  - "Running a test" is printed in all the 5 instances since the setup() is executed each time a () is tested prior
 *    to that test being executed
 *
 *
 * Suppose, we want to perform some setup code before the tests, but we only want the setup code to be run only once
 *  - In other words , we don't want to run it after every test
 *  - An example of this is when we want to read data that all the tests will use from a database or file, or
 *    perhaps, we may want to open a network socket, or do some other setups that we really want to do once, once per
 *    run instead of once per () invocation
 * In this scenario, we would use org.junit.BeforeClass annotation and then use org.junit.AfterClass to run the
 *  clean-up code
 *
 * @BeforeClass
 *  - runs before any of the test cases in the test class
 *
 * @AfterClass
 *  - runs after all the test cases in the test class have completed
 *
 * In this case, the methods have to be declared as public static ()s and must return void which is a little bit different
 *  to how we set up the other classes
 *  - Setting up both classes
 *      - @org.junit.BeforeClass is normally set at the top of the class,
 *      - @org.junit.AfterClass is normally set at the bottom of the class,
 *  - The name of the () here can be anything and we're not restricted to beforeClass or afterClass
 *      - The annotation is what Junit is using to determine whether to run the () at the start of the test ()s or at the end
 *
 *  - Running this:
 *      - We can see the messages executed in the order that we have described
 *          - beforeClass() is executed first
 *          - afterClass() is executed as the last ()
 *
 *  - Let's add a static int variable to our class and also a () that we're going to annotate with addAfter
 *      - Each time a () in a class runs, we're going to print out a value of that variable incrementing it by 1
 *      - This will help us to understand the true test of then these ()s are being executed
 *  - Will add this variable in
 *      - beforeClass()
 *
 *  - Then define  @org.junit.After class
 *      - executed after each unit test is tested, similar way like before was executed before executing any tests
 *
 * - Running this:
 *      - Initial count - from beforeClass is 0
 *      - Final count - from afterClass is 6
 *
 *  - This means that even though the output might show in a diff order, the ()s are run in the order we've specified
 *
 * ///
 * Next,
 *  - Lets add the code in our withdraw() in the BankAccount class
 *      - we're not using the branch parameter
 *  - Suppose this bank doesn't allow it's customers to withdraw more than 500$ from an atm
 *      - If the customer tries to withdraw more than 500, and they are not using a teller at the branch, we need to
 *         throw an illegal argument exception
 *      - So let's add that condition and later test it here
 *  - Call withdraw and pass 600.00 and true
 *      - means we're withdrawing 600 from the branch
 *
 *  - Running this:
 *      - Now all our tests have actually passed
 *
 * Let's change the name from withdraw to withdraw_branch and let's create another one for withdrawing on ATM
 *  - In my case, I will comment the original withdraw()
 *  - Then update to withdraw_branch - passing 600.00 and true as the 2nd parameter
 *  - Then update to withdraw_atm - passing 600.00 and false as the 2nd parameter
 *
 * Running this:
 *  - we get an IllegalArgumentException from withdraw_atm which is to be expected because the amount that we're
 *    trying to withdraw from the ATM exceeded the value that we specified in BankAccount.java in the withdraw()
 *
 *  - But the problem here though, is that the test actually passed, in other words, we want the () to throw an
 *    IllegalArgumentException when branch is false , and the amount > 500
 *      - So how do we handle this to correctly indicate that the test has passed
 *          - In this scenario where we are expecting an exception to be thrown, we need to make a modification to the annotation
 *              and pass the exception that we're expecting to the annotation as follows
 *                  @org.junit.Test(expected = IllegalArgumentException.class)
 *
 *  - Running this:
 *      - Now all our tests passed because we've told the () to expect an illegal arg exception
 *      - We no longer see the exception and all our tests passed and working properly
 *
 *
 *
 *
 * ///////// Parameterized Testing /////////////
 * We can further optimize this () a little bit more
 * If we're expecting an exception to be thrown. we didn't have to modify the annotation as we did for withdraw_atm
 *  - We can also remove the assertEquals as well, since we're not testing for equality
 *  - We're just testing to see whether the exceptions are thrown in case of an invalid input
 *      - so I will comment that line out in my case and remove the local variable as we don't need it anymore
 *  - Running this:
 *      - All the tests passed
 *
 * Note that annotations were only introduced in JUnit4
 *  - When using earlier units of all versions of JUnit, What we'd have to do is to surround the code that would
 *    throw an exception with a try catch block & we wouldn't do anything in the catch {}
 *  - Remember that if you don't assert anything, the test will pass
 *      - and so in that case we do the following in the earlier versions
 *  - I have added withdraw_use_try_catch() test this scenario instead of updating withdraw_atm()
 *  - Running this:
 *      - We get the same results - we've caught that exception because we're expecting it and we don't need to do any
 *        processing in the () because we've caught that exception
 *  - We can also add a fail(String str)
 *      - is more explicit on what should happen
 *
 *
 * /// Parameterized Tests
 *  - We want every test to start afresh but that can result in a repetitive code
 *  - Suppose , we want to try depositing 5 different amounts, and verifying the resulting balance
 *      - We could write 5 diff test cases, but we could also write a parameterized test
 *  - We have to use a class annotation to run parameterized tests
 *      - Create a new class for this and call it BankAccountTestParameterized
 *
 */

public class BankAccountTest {
    private BankAccount account;
    private static int count;
    @org.junit.BeforeClass
    public static void beforeClass(){
        System.out.println("This executes before any test cases. Initial Count = "+ count++);
    }

    @org.junit.Before
    public void setup(){
      account =  new BankAccount("Alex","Mwangi",1000.00, BankAccount.CURRENT);
      System.out.println("Running a test");
    }

    @org.junit.Test
    public void deposit() {
       // fail("This test has yet to be implemented");
       //  BankAccount account = new BankAccount("Alex","Mwangi",1000.00, BankAccount.CURRENT);
        double balance = account.deposit(200.00, true);
        assertEquals(1200.00,balance,0);
        //assertEquals(1200.00,account.getBalance(),0);
    }

//    @org.junit.Test
//    public void withdraw() {
//        //fail("This test has yet to be implemented");
//        double balance = account.withdraw(600.00, true);
//        assertEquals(400.00,balance,0);
//    }

    @org.junit.Test
    public void withdraw_branch() {
        double balance = account.withdraw(600.00, true);
        assertEquals(400.00,balance,0);
    }

    @org.junit.Test (expected = IllegalArgumentException.class)
    public void withdraw_atm() {
        account.withdraw(600.00, false);
        //double balance = account.withdraw(600.00, false);
        //assertEquals(400.00,balance,0);
    }

    @org.junit.Test
    public void withdraw_use_try_catch() {
        try{
            account.withdraw(600.00, false);
        }catch (IllegalArgumentException e){

        }
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
        //BankAccount account = new BankAccount("Alex","Mwangi",1000.00, BankAccount.CURRENT);
        account.deposit(200.00, true);
        assertEquals(1200.00,account.getBalance(),0);
    }
    @org.junit.Test
    public void getBalance_withdraw() {
        //fail("This test has yet to be implemented");
        //BankAccount account = new BankAccount("Alex","Mwangi",1000.00,BankAccount.CURRENT);
        account.withdraw(200.00, true);
        assertEquals(800.00,account.getBalance(),0);
    }

    @org.junit.Test
    public void isCurrent_true() {
        //BankAccount account = new BankAccount("Alex","Mwangi",1000.00,BankAccount.CURRENT);
        //assertEquals(false,account.isCurrent());
       // assertEquals(true,account.isCurrent());
       // assertFalse("The account is not a current account",account.isCurrent());
        assertTrue(account.isCurrent());
    }

    @org.junit.After
    public void tearDown(){
        System.out.println("Count after the above test is done (tearDown) = "+count++);
    }


    @org.junit.AfterClass
    public static void afterClass(){
        System.out.println("This executes after all test cases. Final Count = "+ count++);
    }

//    @org.junit.Test
//    public void dummyTest(){
//        assertEquals(20,21);
//    }

}
