package final_classes.part6_immutable_challenge;

import final_classes.part6_immutable_challenge.bank.BankAccount;
import final_classes.part6_immutable_challenge.bank.BankCustomer;

import java.util.List;

public class Main {

    public static void main(String[] args) {

       // BankAccount account = new BankAccount(BankAccount.AccountType.CHECKING, 500); //Checking 500
        BankCustomer joe = new BankCustomer("Joe", 500.00,10000.00);
        System.out.println(joe);

        /* Output **
           Customer: Joe (id:000000010000000)
            CHECKING $500.00
            SAVINGS $10000.00
        */

        //Get joe accounts and check if it's really immutable
        List<BankAccount> accounts = joe.getAccounts();
        accounts.clear();
        System.out.println(joe);
        /* Output **
           Customer: Joe (id:000000010000000)
        */


//        //We can also add accounts this way
//        accounts.add(new BankAccount(BankAccount.AccountType.CHECKING,150000));
//        System.out.println(joe);
//         /* Output **
//           Customer: Joe (id:000000010000000)
//            CHECKING $150000.00
//        */
    }
}
