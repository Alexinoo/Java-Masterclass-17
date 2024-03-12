package final_classes.part16_sealed_class.sealed;

import java.util.function.Predicate;

public sealed interface SealedInterface permits BetterInterface, StringChecker {
    boolean testData(Predicate<String> p , String...strings);
}
