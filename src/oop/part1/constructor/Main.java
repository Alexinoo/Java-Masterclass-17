package oop.part1.constructor;

public class Main {

    public static void main(String[] args) {
       // Account account = new Account(126774470, 50_000.0, "Alex Mwangi", "mwangialex26@gmail.com", "+254717316925");

        Account account = new Account(); //Constructor chaining
        System.out.println();
        System.out.println("Initial Balance : "+account.getAccountBalance());

        account.withdrawFunds(1000);


        account.depositFunds(50000);


        account.withdrawFunds(1000);

        System.out.println("Final Balance : "+account.getAccountBalance());
        System.out.println();
        System.out.println();

        account.accountDetails();

        System.out.println();
        System.out.println();


        Account timsAccount = new Account("Tim","tim@email.com","12345");
        timsAccount.accountDetails();
    }
}
