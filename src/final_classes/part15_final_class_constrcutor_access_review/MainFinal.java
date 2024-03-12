package final_classes.part15_final_class_constrcutor_access_review;

import final_classes.part15_final_class_constrcutor_access_review.game.GameConsole;
import final_classes.part15_final_class_constrcutor_access_review.pirate.PirateGame;

//class SpecialGameConsole<T extends Game<? extends Player>>
//        extends GameConsole<Game<? extends Player>> {
//    public SpecialGameConsole(Game<? extends Player> game) {
//        super(game);
//    }
//}
public class MainFinal {

    public static void main(String[] args) {
        GameConsole<PirateGame> game = new GameConsole<>(new PirateGame("Pirate Game"));


    }
}
