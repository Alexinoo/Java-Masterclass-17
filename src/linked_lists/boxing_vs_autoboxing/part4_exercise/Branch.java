package linked_lists.boxing_vs_autoboxing.part4_exercise;

import java.util.ArrayList;

public class Branch {

    private String name;
    private ArrayList<Customer> customers;

    public Branch(String name) {
        this.name = name;
        this.customers = new ArrayList<Customer>();
    }

    public String getName() {
        return name;
    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public boolean newCustomer(String customerName, double initialAmount){
        Customer customer = findCustomer(customerName);
        if(customer == null){
          customers.add(new Customer(customerName,initialAmount));
          return true;
        }
        return false;
    }

    public boolean addCustomerTransaction(String customerName, double amount){
        Customer existingCustomer = findCustomer(customerName);
        if(existingCustomer != null){
            existingCustomer.addTransaction(amount);
            return true;
        }
        return false;
    }

    private Customer findCustomer(String customerName){
        for (Customer customer : customers){
            if(customer.getName().equalsIgnoreCase(customerName))
                return customer;
        }
        return null;
    }
}
