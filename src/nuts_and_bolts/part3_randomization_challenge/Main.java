package nuts_and_bolts.part3_randomization_challenge;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
/*
 * Implementing The Game
 * .......................
 * Use the ints() on Random to generate a finite stream, to create a list of  5 random integers,
 * all between 1 and 6
 *
 * Use Scanner with System.in to have the user enter one of the following
 *      Press Enter to end the play
 *      Enter All to rethrow all five dice
 *      Enter Numbers rep dice face values for those dice to be rolled again
 * Continue to loop and throw the dice selected by the user, until the user presses Enter
 */

public class Main {
    /*
     * Add 2 static final variables one for random and another one for scanner
     */
    private static final Random random = new Random();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        /*
         * Create a List variable of type Int - easier to use & modify than Arrays
         */
        List<Integer> currentDice = new ArrayList<>();

        /*
         * Testing
         * Put the call in a do while loop which runs once and ends as long as pickLosers returns false
         * Call rollDice passing the currentDice variable
         *
         *
         * Prints a message when the user exits this loop
         */
        do{
            rollDice(currentDice);
        }while (!pickLosers(currentDice));

        System.out.println("Game Over. Real game would score and continue.");

    }
        /*
         * rollDice(List<Integer> list)- used for rolling & re-rolling dice as well
         * randomCount() is the number of dice that get rolled
         * start with 5 since currentDice will be an empty list thus create 5 random numbers
         * Use static random and call ints on that
         * Pass 3 args
         *  - 1st - no of integers we want set to randomCount
         *  - 2nd & 3rd arg - 1 through 6 ( 1-lower, 7-upper exclusive )
         * Sort the no's
         * Since we want them in a list, box them to Integer wrappers
         * Return an unmodifiable list
         *
         * Lastly add the newDice list to the currentDice
         * This means this method is mutating currentDice
         * Then print out what dice the player has
         */

    private static void rollDice(List<Integer> currentDice){
        int randomCount = 5 - currentDice.size();
        var newDice = random
                .ints(randomCount,1,7)
                .sorted()
                .boxed()
                .toList();
        currentDice.addAll(newDice);

        System.out.println("You're dice are: "+ currentDice);

    }

    /*
     * Prompt a user to decide if they want to keep all the dice ,re roll all the dice or pick just a couple of
     * the dice to re-roll
     * Implement pickLosers()
     * Returns a boolean and take the currentDice list
     * Create a multi-line prompt for the user using a text block
     * Print out the prompt with an arrow indicator for the user
     * Call scanner.nextLine to get user input
     * If user press Enter - test that with isBlank() - end the loop
     * use a try-catch block to catch errors if any
     * Call removeDice() and pass the currentDice list and the space separated user input
     *
     *
     * print out the Exception trace
     * Return false - to continue looping and prompt the user again
     *
     */
    public static boolean pickLosers(List<Integer> currentDice){
        String prompt = """
                
                Press Enter to score.
                Type "ALL" to re-roll the dice.
                List numbers (separated by spaces) to re-roll selected dice
                        """;
        System.out.println(prompt + "--> ");
        String userInput = scanner.nextLine();
        if(userInput.isBlank())
            return true;
        try{
            removeDice(currentDice,userInput.split(" "));
        }catch(Exception e){
            e.printStackTrace(System.out);
            System.out.println("Bad input, Try again");
        }
        return false;

    }

    /*
     * removeDice()
     * Takes current dice list and string array - user's input split by spaces
     * If user enter ALL, clear the current dice list
     * If they entered a list of numbers, process this in an else block
     * Loop through the list of the no's entered passed as a String[] to this method
     * Call remove on the current dice list which removes only 1 element
     * we have an overloaded version which takes an index or an obj - be careful
     * i.e use Integer.valueOf(removed) and not the parse()
     *
     * Print the dice being kept
     *
     */
    private static void removeDice(List<Integer> currentDice, String[] selected){
        if(selected.length == 1 && selected[0].contains("ALL")) {
            currentDice.clear();
        }else{
            for (String removed : selected ) {
                currentDice.remove(Integer.valueOf(removed));
            }
            System.out.println("Keeping "+currentDice);
        }
    }
}














