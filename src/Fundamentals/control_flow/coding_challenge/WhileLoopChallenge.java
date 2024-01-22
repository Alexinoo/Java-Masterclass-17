package Fundamentals.control_flow.coding_challenge;

public class WhileLoopChallenge {

    public static void main(String[] args) {

        int number = 4;
        int finishNumber = 20;
        int evenCount = 0 , oddCount = 0;
        while(number <= finishNumber){
            number++;

             if(!isEvenNumber(number)){
                 oddCount++;
                 continue;
             }

            evenCount++;
            if(evenCount >= 5) break;

            System.out.println("Even number "+number);
        }
        System.out.println("Even numbers count = "+evenCount);
        System.out.println("Odd numbers count =  "+oddCount);

    }

    public static boolean isEvenNumber(int number){

        return number > 0 && number % 2 ==0;

    }
}
