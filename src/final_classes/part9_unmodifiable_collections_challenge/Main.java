package final_classes.part9_unmodifiable_collections_challenge;

import final_classes.part9_unmodifiable_collections_challenge.bank.Bank;
import final_classes.part9_unmodifiable_collections_challenge.bank.BankAccount;
import final_classes.part9_unmodifiable_collections_challenge.bank.BankCustomer;

public class Main {

    public static void main(String[] args) {

        //Add a new bank
        Bank bank = new Bank(3214567);

        //Add a customer with initial & savings deposit
        bank.addCustomer("Joe",500.00,10000.00);

        BankCustomer joe = bank.getCustomer("000000010000000");
        System.out.println(joe);

        //Add some funds to Joe's Account
        //Calling doTransaction to do it
        if(bank.doTransaction(joe.getCustomerId(), BankAccount.AccountType.CHECKING,
                35)){
            System.out.println(joe);

        }

        //Withdrawing 535
        if(bank.doTransaction(joe.getCustomerId(), BankAccount.AccountType.CHECKING,
                -535)){
            System.out.println(joe);

        }

        //And later a penny over
        // if(bank.doTransaction(joe.getCustomerId(), BankAccount.AccountType.CHECKING,-0.01)){
        //System.out.println(joe); // insufficient funds
        //
        // }


        //Get the checking account from the Joe's BankCustomer instance
        // Get transactions from that account
        // Then print all the transactions
        BankAccount checking = joe.getAccount(BankAccount.AccountType.CHECKING);
        var transactions = checking.getTransactions();
        transactions.forEach((k,v) -> System.out.println(k + " : "+v));


        //Try to modify checking account transactions - Won't work  with Map.copyOf(transactions)!!
       // transactions.put(3l, new Transaction(1,1,
       //         Integer.parseInt(joe.getCustomerId()),500));


        //Loop through transactions and try to tamper with each individual transaction
        //Update customerId
        //Update amount
        //Print transactions again
        System.out.println("__________________________________________");

//        for (var transaction: transactions.values() ) {
//            transaction.setCustomerId(2);
//            transaction.setAmount(10000.00);
//        }
//        transactions.forEach((k,v) -> System.out.println(k + " : "+v));


        //Try to clear joe's transactions
        // get the checking account get the transactions on that and invoke the clear()
        // Runs but has no effect on Joe's transactions
        joe.getAccount(BankAccount.AccountType.CHECKING).getTransactions().clear();



        //The question is now that we are able to update the transactions..?
        // Have they just changed on joe's account or just on this copy
        //Let's find out by getting joe's account again
        System.out.println("__________________________________________");
       joe.getAccount(BankAccount.AccountType.CHECKING)
                       .getTransactions()
                       .forEach((k,v)->System.out.println(k + " : "+v));

    }
}
