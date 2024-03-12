package final_classes.part17_final_section_challenge.sealed;

import java.util.function.Predicate;

public sealed interface SealedInterface permits BetterInterface, StringChecker {
    boolean testData(Predicate<String> p , String...strings);
}
