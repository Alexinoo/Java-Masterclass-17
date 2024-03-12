package final_classes.part15_final_class_constrcutor_access_review.game;

import java.util.function.Predicate;

public record GameAction(char key, String prompt, Predicate<Integer> action) {
}
