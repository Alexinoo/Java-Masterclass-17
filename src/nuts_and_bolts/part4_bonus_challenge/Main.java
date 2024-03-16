package nuts_and_bolts.part4_bonus_challenge;

import nuts_and_bolts.part4_bonus_challenge.dice.DiceGame;
import nuts_and_bolts.part4_bonus_challenge.game.GameConsole;

public class Main {

    public static void main(String[] args) {
//        List<Integer> currentDice = new ArrayList<>(
//                List.of(2,2,4,4,4));
//
//        for (ScoredItem s: ScoredItem.values()) {
//            System.out.printf("Score for %s is %d %n",s,s.score(currentDice));
//        }

        var console = new GameConsole<>(new DiceGame("Dice Rolling Game"));
        console.playGame(console.addPlayer());
    }


}
