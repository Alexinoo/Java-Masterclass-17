package nuts_and_bolts.part4_bonus_challenge.game;

import java.util.function.Predicate;

public record GameAction(char key, String prompt, Predicate<Integer> action) {
}
