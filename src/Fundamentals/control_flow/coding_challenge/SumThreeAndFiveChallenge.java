package Fundamentals.control_flow.coding_challenge;

public class SumThreeAndFiveChallenge {

    public static void main(String[] args) {

        int sumOfMatches =0, countOfMatches = 0;

        for (int i = 1; countOfMatches < 5 && i <= 1000; i++) {
            if( i % 3 == 0 && i % 5 == 0){
                System.out.print(i +" ");
                sumOfMatches +=i;
                countOfMatches++;
            }

            // if (countOfMatches = 5)
            // break;
        }

        System.out.println();
        System.out.println("The sum of the numbers are "+sumOfMatches);
    }
}
