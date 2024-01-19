package Fundamentals.coding_challenges;

public class Challenge {

    public static void main(String[] args) {

        double myFirstDoubleValue = 20.00d;

        double mySecondDoubleValue = 80.00d;

        double myValuesTotal = (myFirstDoubleValue + mySecondDoubleValue) * 100.00d;

        System.out.println("MyValuesTotal = "+myValuesTotal);

        double theRemainder = myValuesTotal % 40.00d;

        System.out.println("The Remainder = "+theRemainder);

        boolean isNoRemainder = theRemainder ==0 ? true : false;
        System.out.println("isNoRemainder = "+isNoRemainder);

        if(!isNoRemainder)
            System.out.println("Got some remainder");
    }
}
