package final_classes.part17_final_section_challenge.sealed;

import java.util.function.Predicate;

public final class StringChecker implements SealedInterface {
    @Override
    public boolean testData(Predicate<String> p, String... strings) {
        return false;
    }
}
