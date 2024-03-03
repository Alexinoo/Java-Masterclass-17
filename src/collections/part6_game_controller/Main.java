package collections.part6_game_controller;

import collections.part6_game_controller.poker.PokerGame;

public class Main {

    public static void main(String[] args) {

        PokerGame fiveCardDraw = new PokerGame(4,5);
        fiveCardDraw.startPlay();
    }
}
