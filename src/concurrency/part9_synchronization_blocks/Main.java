package concurrency.part9_synchronization_blocks;

public class Main {

    /* Synchronization, synchronized blocks
     *..........................................
     *
     * Two uses of synchronization
     *  - using keyword synchronized on the invoking ()s
     *  - use synchronized statement
     *      - requires us to pass an obj instance to that statement - passed "this" which means the curr instance
     * In both these cases a lock was acquired on BankAccount instance
     * In the syncronized (){} - this is explicitly declared
     * When we use the synchronized keyword on a (), the curr instance is implicitly locked
     *
     * What does it mean when an obj is locked?
     *
     * Object Instance Monitor
     * .......................
     * Every obj instance in java has a built-in intrinsic lock also known as monitor lock
     * A thread acquires a lock by executing a synchronized () on the instance, or by using the instance as the param
     *  to a synchronized statement
     * A thread releases a lock when it exits from a synchronized block or (), even if it throws an exception
     * Only 1 thread at a time can acquire this lock, which prevents all other threads from accessing the instance's state
     *  until the lock is released
     * All other threads, which want access to the instance's state through synchronized code will block and wait, until
     *  they can acquire a lock
     *
     * The synchronized statement is usually a better option in most circumstances, since it limits the scope of synchronization
     *  , to the critical section of code
     * In other words, it gives you much more granular control, over when you want other threads to block
     * The synchronized block can use a diff obj, on which to acquire its lock
     * This means that code accessing this bank account instance wouldn't have to block entirely
     *
     * Add a private field to the BankAccount Class
     *  - "name" type String
     *  - Include it in the constructor
     *  - Add a getter getName() and a setter setName()
     *  - Make setName() synchronized
     *      - this is because we don't want the threads to set the last name at the same time
     *      - print the updated name
     *
     * Then nmake the deposit () synchronized - so that it's easier to demonstrate
     *  - Add synchronized to declaration
     *  - Then comment on the synchronized block
     *
     * Update the constructor to take name as the 1st arg in the main()
     *  - Passed "Tom"
     *  - Update thread 3 to setName() - pass "Tim"
     *  - Just to make sure we can really see whose being blocked, let's give the first 2-threads a 500ms headstart
     *
     * Running this code:-
     *  - Each time will be different
     *  - Sometimes, we get the first withdrawal thread before we see "Talking to the teller statement"
     *  - Notice, that the thread that updates the name is blocked, just as the same as those updating the balance
     *
     * All synchronized ()s regardless of what they're doing or accessing, can't run until they've acquired the monitor
     *  lock, on the curr instance
     *
     * Update the setName to use synchronized statement
     *  - remove synchronized keyword from the ()
     *
     * Running this:-
     *  - You can see that updating the name, is blocked until the conversation with the teller ends
     *
     * Even though, we are using a synchronized block in the setName(), we are synchronizing on an instance and that's going
     *  to get locked by our long-running deposit ()
     * But we don't have to always synchronize on the curr instance, the keyword "this", In fact in most instances, I would
     *  not want to synchronize on the curr instance
     *
     * Update setName()
     *  - update "this" to "this.name"
     *
     * Running this :-
     *  - The name gets updated before the deposit thread completed
     *  - It doesn't block for the deposit () thread
     *
     * Why couldn't we do this with the synchronized statement for the deposit ?
     *
     * Update deposit()
     *  - Remove synchronized modifier
     *  - uncomment out the synchronized block
     *  - update to this.balance
     *      - IntelliJ complains that the required type is an obj & not a double
     *      - An intrinsic lock or monitor is only available on an obj not on any of the primitive types
     *      - create a local variable, a Double wrapper and use it instead
     *
     * Running this code:
     *  - This seems to work though we don't really have the right example to test this kind of change
     *
     * IntelliJ warns us on synchronizing on a local variable o the deposit() - boxedBalance
     *
     * Why is synchronizing on a local variable a bad thing ?
     * Local var are stored on the thread's stack, so each thread would have its own copy of this boxedBalance var
     * The lock would be on the thread's stack instance only
     * When you lock on a local variable, it's not going to be a shared lock, so it's useless
     * The only local var that might work is a String, as long as its being interned to the String pool
     *
     * Just don't use any local variable or () arg in the synchronized statement
     *
     * That's fine , but how do we solve this problem then ?
     *  - use BigDecimals but let's keep it simple for now
     *  - create lock obj(s) to manage locking for diff fields
     *
     * In the BankAccount Class - create 2 lock obj(s) and make them private and final typed OBject
     *  - lockName = new Object()
     *  - lockBalance = new Object()
     *
     * Now we can go to the setName() and pass lockName to the synchronized statement
     * Do the same thing to deposit() and pass lockBalance to the synchronized statement
     *  - remove the boxedBalance variable
     *
     * Next
     * Let's add another () "addPromoDollars" on the BankAccount class
     * - Suppose the Bank is running a promotion event and is going to gift 25 usd to anyone who deposits 5000 usd or more
     *  - Make it private and void that takes a double for the deposit amount
     *  - If the amount > 5000 (qualify for this promo)
     *      - will synchronize on the same lock as the other code that updates the bal, so on lock balance
     *      - print that the depositor gets the promotional deposit
     *      - then update the balance by $25
     *  - Will call addPromoDollars() inside the deposit() under the synchronized block and as the last statement
     *    passing it the amount
     *
     * Running this :-
     *  - We can see everything is working normally & we get the promotional deposit with a final balance of $7525
     *
     * Reentrant Synchronization
     * .........................
     * Once a thread acquires a lock, all other threads will block, which also require that lock
     * In this scenario, 1 thread has acquired a lock and is calling another ()
     * This is the same thread calling a diff() which is also trying to acquire the lock
     * Because these () calls are executed from the same thread, any nested calls which try to acquire the lock,
     *  won't block, because the curr thread already has it
     * This feature is called Reentrant Synchronization.
     * Without this, threads could block indefinitely
     * This concept is built on the Java language and it's based on the monitor mechanism
     *
     */

    public static void main(String[] args) {

        BankAccount companyAccount = new BankAccount("Tom",10000);

        Thread thread1 = new Thread(()-> companyAccount.withdraw(2500));
        Thread thread2 = new Thread(()-> companyAccount.deposit(5000));
        Thread thread3 = new Thread(()-> companyAccount.setName("Tim"));
        Thread thread4 = new Thread(()-> companyAccount.withdraw(5000));

        thread1.start();
        thread2.start();

        try{
            Thread.sleep(500);

        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }

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
