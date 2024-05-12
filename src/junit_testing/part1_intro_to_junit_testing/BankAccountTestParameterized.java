package junit_testing.part1_intro_to_junit_testing;

/*
 * Parameterized Tests
 * ....................
 * - We want every test to start afresh but that can result in a repetitive code
 *  - Suppose , we want to try depositing 5 different amounts, and verifying the resulting balance
 *      - We could write 5 diff test cases, but we could also write a parameterized test
 *  - We have to use a class annotation to run parameterized tests
 *      - Created this class for testing
 *
 * Next,
 *  - Create an instance variable of the Bank account
 *  - Use Before annotation and create a new instance of a BankAccount class
 *      - (Remember @Before is run before every test is ran)
 *
 * Next,
 *  - We need to tell JUnit that this isn't a normal test class
 *  - We need to use RunWith @ on the class definition
 *      - @RunWith(Parameterized.class)
 *      - you should get 2 little green arrows in the gutter to the left of the class definition
 *
 *  - Suppose we're going to call the deposit(), with branch equal to true and a no of values, say 5 diff values
 *      - What we have to do is tell Junit that these are the parameters we want to use
 *      - We're going to do this with a static () annotated with @Parameters annotation
 *           @Parameterized.Parameters
 *      - This () needs to return a collection of Obj
 *          - returns a new obj array
 *      - We have added a set of parameters we want to test followed by the expected value
 *      - When we run the parameterized test, Junit sub will create a new instance of the bank account test parameterized
 *        class for each set of test data and will use the class constructor to set instance variables to the values
 *        we've specified
 *      - We need to do 2 thins though,
 *          - add instance variables for the deposit amount, branch value & expected value
 *          - add a constructor that accepts the values and sets the instance variables
 *
 * Next, copy getBalance_deposit() from BankAccountParameterized.java and paste it here
 *  - Then modify it to use the instance variables rather than hard coding the values
 *  - update the name to deposit() for simplicity
 *
 * Next, we can run the tests by clicking the arrow to the left of the deposit() that we've just added
 *  - We can see it ran various tests and we've actually got some output showing the various tests
 *  - The 3rd one is interesting because we've got this expected value of 1325.14 , but the actual value is 1325.13999
 *      - This is where the delta option comes in
 *  - So we need to change in the delta to take that into account and update it to say 0.01
 *      - Running the tests again, and this works
 *          - useful or comes into play when testing double primitive types
 *
 */

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class BankAccountTestParameterized {
    private BankAccount account;
    private double amount;
    private boolean branch;
    private double expected;

    public BankAccountTestParameterized(double amount, boolean branch, double expected) {
        this.amount = amount;
        this.branch = branch;
        this.expected = expected;
    }

    @org.junit.Before
    public void setup(){
        account =  new BankAccount("Alex","Mwangi",1000.00, BankAccount.CURRENT);
        System.out.println("Running a test");
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testConditions(){
        return Arrays.asList(new Object[][]{
                {100.00, true , 1100.00},
                {200.00, true , 1200.00},
                {325.14, true , 1325.14},
                {489.33, true , 1489.33},
                {1000.00, true , 2000.00}
        });
    }

    @org.junit.Test
    public void deposit() {
        account.deposit(amount, branch);
        assertEquals(expected,account.getBalance(),0.01);
    }
}
