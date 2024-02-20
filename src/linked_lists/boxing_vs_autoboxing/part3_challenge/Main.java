package linked_lists.boxing_vs_autoboxing.part3_challenge;

import java.util.ArrayList;

record Customer(String name, ArrayList<Double> transactions){
    public Customer(String name , double initialDeposit){
        this(name.toUpperCase() , new ArrayList<>(500));
        transactions.add(initialDeposit);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", transactions=" + transactions +
                '}';
    }
}
public class Main {

    public static void main(String[] args) {
        Customer customer = new Customer("Alex",50000.00);
        System.out.println(customer);

        Bank bank = new Bank("Chase");
        bank.addNewCustomer("Nancy MWANGI",500.0);
        System.out.println(bank);

        bank.addTransaction("Nancy MWANGI",-10.25);
        bank.addTransaction("Nancy MWANGI",-75.01);
        bank.printStatement("Nancy mwangi");

        //Add bob - Not possible we can only add through addNewCustomer()
        //bank.addTransaction("Bob",100);
        //bank.printStatement("Bob");
        bank.addNewCustomer("bob s",500.0);
        bank.addTransaction("Bob s",100);
        bank.printStatement("Bob s");

    }
}

class Bank{
    private String name;
    private ArrayList<Customer> customers = new ArrayList<>(5000);

    public Bank(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Bank{" +
                "name='" + name + '\'' +
                ", customers=" + customers +
                '}';
    }

    //Fetch customer by Name
    private Customer getCustomer(String customerName){
        for (var customer:customers ) {
            if(customer.name().equalsIgnoreCase(customerName))
                return customer;
        }
        System.out.printf("Customer %s wasn't found %n",customerName);
        return null;
    }

    //Add a new Customer
    public void addNewCustomer(String customerName,double initialDeposit){
        if(getCustomer(customerName) == null){
            Customer customer= new Customer(customerName,initialDeposit);
            customers.add(customer);
            System.out.println("New Customer added:"+customer);
        }

    }

    public void addTransaction(String name,double transactionAmount){
        Customer customer = getCustomer(name);
        if(customer != null){
            customer.transactions().add(transactionAmount);
        }
    }

    public void printStatement(String customerName){
        Customer customer = getCustomer(customerName);
        if(customer == null)
            return;
        System.out.println("_".repeat(30));
        System.out.println("Customer Name: "+customer.name());
        System.out.println("Transactions");
        for (double d: customer.transactions()) { //unboxing
            System.out.printf("$ %10.2f (%s)%n",d,d < 0 ?"debit":"credit");
        }
    }
}
