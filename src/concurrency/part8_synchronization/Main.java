package concurrency.part8_synchronization;

public class Main {

    /*
     * Create a new Bank Account class that multiple threads will want to access
     *  Instance Fields
     *      - balance type double and private package
     *  A constructor that initializes the balance
     *  A getter method for accessing the balance
     *  Instance Methods
     *      - deposit
     *      - withdraw
     *
     * Create an instance of the BankAccount class and setup initial balance to be 10000
     *
     * Then set up 3 threads
     *  - Thread 1 - withdraw 2500
     *  - Thread 2 - deposit 5000
     *  - Thread 3 - withdraw 2500
     *
     * Then start these threads asynchronously
     * Don't forget, there's no guarantee which thread is going to start first when we call them
     *  consecutively like this
     *
     * Then print out the final balance of the company's bank account
     * We can do this by joining all 3 threads to the main thread and the order doesn't really matter
     * When we use join we need a try block or specify it in a throw clause
     *
     * Running this:
     *  - When you run this you start to see some weird output
     *  - The order of the tasks being printed isn't consistent.
     *  - Information being printed does not look right
     *  - Starting bal does not line up with the prev statement's new balance
     *
     * Suppose we add volatile modifier to the balance instance variable
     *
     * Running this :-
     *  - We still get the same results as above
     *  - The problem is still there even after adding volatile modifier
     *
     * This is happening because of the threads getting in, and changing the balance in multiple spots and in multiple
     *  ()s
     * One task could be subtracting, at the same time another is adding and since these use compound assignment
     *  operators, and we're working with double values, none of these are atomic operations
     *
     * This is certainly a valid scenario that might occur, so our code should be able to handle this
     *
     * How do we fix this ?
     *  - Revert the last change and remove volatile from the balance
     *
     * There's another solution that deals with both problems, of caching and thread interference by using synchronized
     *  keyword
     *
     * Add synchronized keyword to the 2 methods that modify the balance
     *  - i.e. deposit and withdraw methods
     *
     * Running this again:-
     *  - This time, if we read the 3 statements, in the order they are printed, the math seems logical
     *  - The output might be in a different order and may change if you run it again
     *  - But no matter what, the math is correct for all print statements and final balance is right
     *
     *
     *
     * Synchronized
     * ............
     * Different invocations of synchronized ()s, on the same obj are guaranteed not to interleave
     * When 1 thread is executing a synchronized () for an obj, all other threads that invoke synchronized ()s for
     *  the same obj, block and suspend their execution, until the 1st thread is done with the obj
     * Threads have to sit and wait for access to that code block
     * When a synchronized method exits, it ensures that the state of the obj is visible to all threads similar to what
     *  the volatile keyword would do.
     *
     * But aren't we defeating the purpose of multi-threading env if the threads have to sit and wait ?
     * And the answer to that is YES, for that bit of code that is updating the balance
     *
     * If a class has 3 synchronized ()s, then only one of these ()s can ever run at a time and only by 1 thread
     * This is why it is really important to ensure that the code in your synchronized ()s is limited to just code
     *  that has access to the shared obj
     *
     * Critical Section
     * ................
     * The above is called the critical section
     * THe critical section is the code that is referencing a shared resource like a variable
     * Only 1 thread at a time should be able to execute a critical section
     * When all critical sections are synchronized, the class is thread safe
     *
     *
     *
     * Let's look at what a critical section could be in this BankAccount class
     *
     * Suppose, as an example, that any deposits to this account must be done at a bank - like you have to
     *  physically go in the bank
     * And this process includes talking to the teller
     *  - So let's add a println statement before the sleep and print that the customer is talking to the teller
     *  - Add some time - could be 5 min or longer - will make it 7 sec to give you an idea
     *
     * Let's also add another withdrawal thread - thread4
     *  - withdraw 5000
     * Later, we'll look at better ways to manage multiple threads like this with thread pools
     *
     * Running this :-
     *  - You can see all the threads block, until the employee finishes talking to the teller
     *  - This is not a good thing
     *  - We've synchronized an entire () which included long-running code that had nothing to do with the critical
     *     section of code
     *  - Need to be careful when using synchronized keyword on a () so that you're not unintentionally creating
     *     this kind of situation
     *  - What do you do now though?
     *     - Your employee does not have any choice, he has to go through this deposit process as it's laid out
     *  - But that shouldn't stop your other employees wherever they are from withdrawing money from the ATM
     *
     * We can get more specific or targeted on just a part of code
     *
     * Will do this here on the deposit ()
     *  - Remove synchronized keyword from the method
     *  - Then create a specialized code block wrapping the statement that deal with the balance
     *  - Will wrap the code that modify the bal with synchronized (this){}
     *      - need to pass an obj instance to synchronized()
     *      - add "this" meaning the current instance of this bank a/c
     *
     * Running this:-
     *  - You can see, none of the withdrawal threads got stuck waiting for the teller conversation to end,
     *    and the transactions are still occurring with integrity
     *
     *
     */

    public static void main(String[] args) {

        BankAccount companyAccount = new BankAccount(10000);

        Thread thread1 = new Thread(()-> companyAccount.withdraw(2500));
        Thread thread2 = new Thread(()-> companyAccount.deposit(5000));
        Thread thread3 = new Thread(()-> companyAccount.withdraw(2500));
        Thread thread4 = new Thread(()-> companyAccount.withdraw(5000));

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
            thread4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Final Balance: "+ companyAccount.getBalance());

    }
}
