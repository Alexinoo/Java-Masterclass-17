package Fundamentals.control_flow.coding_challenge;

public class CountPrimeNumbersChallenge {

    public static void main(String[] args) {

        int counter = 0;
        for (int i = 10; counter < 3 && i <= 50; i++) {

            if(isPrime(i)){
                counter++;
                System.out.println("Number "+i+" is a prime number");
            }

//            if(counter == 10) {
//                System.out.println("Found 10 ..Exiting for loop");
//                break;
//            }
        }

        System.out.println("Total number of prime numbers between 10 and 50 is "+counter);
    }

    public static boolean isPrime(int wholeNumber){
        if (wholeNumber <= 2)
            return wholeNumber == 2;

        for(int divisor = 2; divisor < wholeNumber; divisor++){
            if( wholeNumber % divisor == 0)
                return false;
        }
        return true;
    }
}
