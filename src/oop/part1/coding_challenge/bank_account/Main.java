package oop.part1.coding_challenge.bank_account;

public class Main {

    public static void main(String[] args) {

         BankAccount account = new BankAccount();

         account.setCustomerName("Alex Mwangi");
         account.setAccountNumber(126774470);
         account.setEmail("mwangialex26@gmail.com");
         account.setPhoneNumber("+254717316925");

        System.out.println();
        System.out.println("Initial Balance : "+account.getAccountBalance());

        account.withdrawFunds(1000);


        account.depositFunds(50000);


        account.withdrawFunds(1000);

        System.out.println("Final Balance : "+account.getAccountBalance());
        System.out.println();
        System.out.println();
        
        account.accountDetails();
    }
}
