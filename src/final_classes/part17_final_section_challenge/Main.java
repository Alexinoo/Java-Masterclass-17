package final_classes.part17_final_section_challenge;

import final_classes.part17_final_section_challenge.game.GameConsole;
import final_classes.part17_final_section_challenge.pirate.PirateGame;

public class Main {

    public static void main(String[] args) {

//        PirateGame.getTowns(0).forEach(t -> System.out.println(t.information()));
//        System.out.println("____________________________________________");
//        PirateGame.getTowns(1).forEach(t -> System.out.println(t.information()));

        var console = new GameConsole<>(new PirateGame("The Pirate Game"));
        int playerIndex = console.addPlayer();
        console.playGame(playerIndex);

    }
}
