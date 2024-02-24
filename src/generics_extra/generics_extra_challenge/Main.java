package generics_extra.generics_extra_challenge;

import generics_extra.generics_extra_challenge.util.QueryList;

import java.util.Comparator;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        QueryList<LPAStudent> queryList = new QueryList<>();

        for (int i = 0; i < 25; i++) {
            queryList.add(new LPAStudent());
        }

        System.out.println("Ordered");
        queryList.sort(Comparator.naturalOrder());

        //print students
        printList(queryList);

        //Filters students taking python course and percent complete is < 50
        System.out.println("Matches");
        var matches =queryList
                .getMatches("PercentComplete","50")
                .getMatches("Course","Python");

        //sorts by PercentComplete - using the LPAStudentComparator class
        matches.sort(new LPAStudentComparator());
        printList(matches);

        //sorts by studentId -
        System.out.println("Ordered");
        matches.sort(null);
        printList(matches);
    }

    public static void printList(List<?> students){
        for (var student: students) {
            System.out.println(student);
        }
    }
}
