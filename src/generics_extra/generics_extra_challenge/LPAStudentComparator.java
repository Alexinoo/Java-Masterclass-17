package generics_extra.generics_extra_challenge;

import java.util.Comparator;

public class LPAStudentComparator implements Comparator<LPAStudent> {
    @Override
    public int compare(LPAStudent o1, LPAStudent o2) {
        return (int)(o1.getPercentComplete() - o2.getPercentComplete());
    }
}
