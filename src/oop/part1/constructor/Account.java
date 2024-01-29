package oop.part1.constructor;

public class Account {
    private long accountNumber;
    private double accountBalance;
    private String customerName;
    private String email;
    private String phoneNumber;

    public Account(){
        this(1580073628,2.50,"Default name","Default email","Default phone");
        System.out.println("Account constructor with no params called..");
    }

    public Account(long accountNumber,double accountBalance,String customerName,String email,String phone){
        System.out.println("Account constructor with params called..");
        this.accountNumber = accountNumber;
        this.accountBalance = accountBalance;
        this.customerName = customerName;
        this.email = email;
        phoneNumber = phone;

    }

    public Account(String customerName, String email, String phoneNumber) {
        this(9999,100.55,customerName,email,phoneNumber);
//        this.customerName = customerName;
//        this.email = email;
//        this.phoneNumber = phoneNumber;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void withdrawFunds(double amount){
        if(accountBalance - amount < 0 ){
            System.out.println("Insufficient Funds! You only have $"+accountBalance+" in your account");
            return;
        }

        accountBalance -= amount;
        System.out.println("Withdrawal of $"+amount+" processed, Remaining balance = $"+accountBalance);
    }

    public void depositFunds(double amount){
        accountBalance += amount;
        System.out.println("Deposit of $"+amount+" made. New balance is $"+accountBalance);
    }

    public void accountDetails(){
        System.out.println("Name : "+getCustomerName());
        System.out.println("Account No : "+getAccountNumber());
        System.out.println("Email : "+getEmail());
        System.out.println("Phone : "+getPhoneNumber());
        System.out.println("Account Balance : "+getAccountBalance());
    }
}
