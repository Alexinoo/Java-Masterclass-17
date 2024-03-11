package final_classes.part9_unmodifiable_collections_challenge.dto;

public class Transaction {

    private int routingNumber;
    private long transactionId;

    private int customerId;

    private double amount;

    public Transaction(int routingNumber, long transactionId, int customerId, double amount) {
        this.routingNumber = routingNumber;
        this.transactionId = transactionId;
        this.customerId = customerId;
        this.amount = amount;
    }

    public int getRoutingNumber() {
        return routingNumber;
    }

    public void setRoutingNumber(int routingNumber) {
        this.routingNumber = routingNumber;
    }

    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    /*
        toString()

        - [%015d] 15 digit 0-filled routing No
        - [%020d] 20 digit 0-filled customer Id
        - [%015d] 15 digit 0-filled transaction Id
        - [%012.2f] 12 digit 0-filled includes a minus sign for a withdrawal & 2digits for the cents
     */

    @Override
    public String toString() {
        return "%015d:%020d:%015d:%012.2f".formatted(routingNumber,customerId,transactionId,amount);
    }
}
