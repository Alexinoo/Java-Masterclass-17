package Fundamentals.control_flow;

public class ForLoop {

    public static void main(String[] args) {
        for (int counter = 1; counter <= 5 ; counter++) {
            System.out.println(counter);
        }

        System.out.println("===================================");

        for(double rate = 2.0; rate <= 5.0; rate++){
            double interestAmount = calculateInterest(10000.0,rate);
            System.out.println("10,000 at " +rate+ "% interest = "+interestAmount);
        }

        System.out.println("========== Interest rates starts at 7.5% and increases by 1/4 after each iteration up to 10%=========================");

        for (double rate = 7.5; rate <= 10; rate+=0.25) {
            double interestAmount = calculateInterest(100.0,rate);
            System.out.println("$100 at " +rate+ "% interest = $"+interestAmount);
        }

        System.out.println("============ Exit If interest Amount > 8.5 =======================");

        for (double rate = 7.5; rate <= 10; rate+=0.25) {
            double interestAmount = calculateInterest(100.0,rate);
            if(interestAmount > 8.5)
                break;
            System.out.println("$100 at " +rate+ "% interest = $"+interestAmount);
        }


        /*
        System.out.println("10,000 at 2% interest = "+calculateInterest(10000.0,2.0));
        System.out.println("10,000 at 3% interest = "+calculateInterest(10000.0,3.0));
        System.out.println("10,000 at 4% interest = "+calculateInterest(10000.0,4.0));
        System.out.println("10,000 at 5% interest = "+calculateInterest(10000.0,5.0));

         */

    }

    public static double calculateInterest(double amount, double interestRate){
        return (amount * (interestRate / 100));
    }
}
