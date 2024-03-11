package final_classes.part9_unmodifiable_collections_challenge.bank;

import final_classes.part9_unmodifiable_collections_challenge.dto.Transaction;

import java.util.LinkedHashMap;
import java.util.Map;

public class BankAccount {

    public enum AccountType { CHECKING,SAVINGS } //immutable type

    private final AccountType accountType;
    private double balance;
    private final Map<Long, Transaction> transactions = new LinkedHashMap<>();

     BankAccount(AccountType accountType, double balance) {
        this.accountType = accountType;
        this.balance = balance;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public double getBalance() {
        return balance;
    }


    //Instead of returning an unmodifiable Map containing immutable data,
    // return a map of immutable instances, Strings
    // Still provides the same behavior without exposing the data to dangerous side effects
    //Changed the return type to Strings
    // Create a local variable txMap and initialize it to empty LinkedHashMap
    // Loop thru the map's entries
    // Insert the same key but the value won't be the transaction but
    // rather the string value for transaction


//    public Map<Long, Transaction> getTransactions() {
//        return Map.copyOf(transactions);
//    }
//
    public Map<Long, String> getTransactions() {
        Map<Long, String> txMap = new LinkedHashMap<>();
        for (var tx : transactions.entrySet() ) {
            txMap.put(tx.getKey(), tx.getValue().toString());
        }
        return txMap;
    }

    @Override
    public String toString() {
        return "%s $%.2f".formatted(accountType,balance);
    }

    //Way to change balance on an account
    //Package private - means only classes in this package can call it i.e. only BankAccount/BankCustomer
    void commitTransaction(int routingNumber,long transactionId,String customerId,double amount){
        balance += amount;
        transactions.put(transactionId,
                  new Transaction(routingNumber,transactionId,Integer.parseInt(customerId),amount));
    }
}
