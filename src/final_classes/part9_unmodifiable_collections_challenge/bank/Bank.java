package final_classes.part9_unmodifiable_collections_challenge.bank;

import java.util.HashMap;
import java.util.Map;

public class Bank {

    //Every bank has a routingNumber that uniquely identifies it
    //Set when we create a bank and shouldn't change and therefore make it final
    public final int routingNumber;

    //Each bank will keep track of the last transactionId
    private long lastTransactionId = 1;

    //Collection of customers
    //Map - look up via customer Id
    private final Map<String,BankCustomer> customers;

    public Bank(int routingNumber) {
        this.routingNumber = routingNumber;
        customers = new HashMap<>();
    }

    public BankCustomer getCustomer (String id){
        BankCustomer customer = customers.get(id);
        return customer;
    }

    //Add new customer via BankCustomer()
    //Add it to customers Map
    public void addCustomer(String name,double checkingInitialDeposit,
                            double savingsInitialDeposit){
        BankCustomer customer = new BankCustomer(name,
                checkingInitialDeposit,
                savingsInitialDeposit);

        customers.put(customer.getCustomerId(),customer);
    }
    //Returns a boolean whether transaction was successful
    //

    public boolean doTransaction(String id,BankAccount.AccountType accountType,
                                double amount){
        BankCustomer customer = customers.get(id);
        if(customer != null){
            BankAccount account = customer.getAccount(accountType);
            if(account != null){
                if((account.getBalance() + amount) < 0){
                    System.out.println("Insufficient funds");
                }else{
                    account.commitTransaction(routingNumber,
                            lastTransactionId++,id,amount);
                    return true;
                }
            }
        }else{
            System.out.println("Invalid Customer id");
        }

        return false;
    }


}
