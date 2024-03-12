package final_classes.part13_game_console;

import final_classes.part13_game_console.game.GameConsole;
import final_classes.part13_game_console.game.ShooterGame;

public class Main {

    public static void main(String[] args) {

        var console = new GameConsole<>(new ShooterGame("The Shootout Game"));
        int playerIndex = console.addPlayer();
        console.playGame(playerIndex);
    }
}
